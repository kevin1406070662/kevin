package com.kevin.framework.aspectj;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.kevin.common.annotation.Log;
import com.kevin.common.enums.BusinessStatus;
import com.kevin.common.exception.base.BaseException;
import com.kevin.common.utils.IpUtil;
import com.kevin.common.utils.ServletUtils;
import com.kevin.common.utils.StringUtils;
import com.kevin.framework.shiro.UserUtils;
import com.kevin.system.domain.SysOpenLog;
import com.kevin.system.domain.SysUser;
import com.kevin.system.service.SysOpenLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

/**
 * 操作日志记录处理
 *
 * @author novel
 * @date 2019/5/14
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Autowired
    SysOpenLogService sysOpenLogService;
    @Autowired
    UserUtils userUtils;

    // 配置织入点
    @Pointcut("@annotation(com.kevin.common.annotation.Log)")
    public void logPointCut() {
    }

    /**
     * 环绕通知 @Around  ， 当然也可以使用 @Before (前置通知)  @After (后置通知)
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Throwable throwable = null;
        long time = 0;
        Object result = null;
        try {
            long beginTime = System.currentTimeMillis();
            result = point.proceed();
            time = System.currentTimeMillis() - beginTime;
        } catch (Throwable e) {
            throwable = e;
            throw e;
        } finally {
            handleLog(point, throwable, result, time);
        }
        return result;
    }


    private void handleLog(final JoinPoint joinPoint, final Throwable e, Object jsonResult, Long processingTime) {
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            // 获取当前的用户
            SysUser currentUser = userUtils.getUserInfo();
            // *========数据库日志=========*//
            SysOpenLog operLog = new SysOpenLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            operLog.setProcessingTime(processingTime);
            // 请求的地址
            HttpServletRequest request = ServletUtils.getRequest();
            String ipAddr = IpUtil.getIpAddr(request);
            operLog.setOperIp(ipAddr);
            // 返回参数
            operLog.setJsonResult(ObjectUtil.isNotNull(jsonResult) ? JSON.toJSONString(jsonResult) : "{}");
            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            operLog.setOperName(currentUser.getUsername());
            if (e != null) {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                if (e instanceof BaseException) {
                    operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
                } else if (e instanceof SQLException) {
                    operLog.setErrorMsg("数据库异常");
                } else {
                    operLog.setErrorMsg("程序异常");
                }
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(controllerLog, operLog);
            // 异步查询ip地理位置，并保存到数据库
            recordOper(operLog, currentUser);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
//          exp.printStackTrace();
            log.error(joinPoint.toString());
        }
    }


    @Async
    public void recordOper(SysOpenLog operLog, SysUser currentUser) {
//        if (StringUtils.isNotNull(currentUser.getDeptId())) {
//            SysDept sysDept = sysDeptService.selectDeptById(currentUser.getDeptId());
//            operLog.setDeptName(sysDept.getDeptName());
//        }
        String operIp = operLog.getOperIp();
        if ("127.0.0.1".equals(operIp) || "localhost".equals(operIp)) {
            operLog.setOperLocation("本地");
        }else {
           String ipInfo = IpUtil.getIpInfo(operIp);
            operLog.setOperLocation(ipInfo);
        }
        // 保存数据库
        sysOpenLogService.save(operLog);
    }


    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log 切点
     */
    private void getControllerMethodDescription(Log log, SysOpenLog operLog) {
        // 设置action动作
        operLog.setOperationType(log.businessType().ordinal());
        // 设置标题
        //operLog.setTitle(log.title());
        // 设置操作人类别
        //operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(operLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     */
    private void setRequestValue(SysOpenLog operLog) {

        HttpServletRequest request = ServletUtils.getRequest();
        Map<String, String[]> param = request.getParameterMap();

        Set<String> keySet = param.keySet();
        for (String key : keySet) {
            if (key != null && key.toLowerCase().contains("password")) {
                param.put(key, new String[]{"******"});
            }
        }
        String params = JSONObject.toJSON(param).toString();
        operLog.setOperParam(StringUtils.substring(params, 0, 2000));
    }


    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }
}
