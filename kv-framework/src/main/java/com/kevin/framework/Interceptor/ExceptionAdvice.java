package com.kevin.framework.Interceptor;

import com.alibaba.fastjson.JSON;
import com.kevin.common.exception.business.BusinessException;
import com.kevin.common.result.Result;
import com.kevin.common.utils.IpUtil;
import com.kevin.common.utils.ServletUtils;
import com.kevin.framework.factory.AsyncFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author 马凯文
 * @date 2019/2/1 10:09
 */
@RestControllerAdvice
@Slf4j
@ApiIgnore
public class ExceptionAdvice {

    private final AsyncFactory asyncFactory;

    public ExceptionAdvice(AsyncFactory asyncFactory) {
        this.asyncFactory = asyncFactory;
    }

//    /**
//     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
//     * @param binder
//     */
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        log.info("binder.getFieldDefaultPrefix {}",binder.getFieldDefaultPrefix());
//    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     * @param model
     */
//    @ModelAttribute
//    @ApiIgnore
//    public void addAttributes(Model model) {
//        model.addAttribute("author", "harry");
//    }

    /**
     * Description : 全局异常捕捉处理
     * Group :
     * @param ex
     * @return
     * @author kevin
     * @date 2020/2/1 0001 10:34
     */
    @ExceptionHandler(BusinessException.class)
    @ApiIgnore
    public Result apiExceptionHandler(BusinessException ex) {
        log.error("BusinessException 异常抛出：{}", ex.toString());
        Map<String, String[]> parameters = ServletUtils.getRequest().getParameterMap();
        asyncFactory.asyncSendMail("2213564487@qq.com", "BusinessException 异常抛出",
                "请求路径：" + ServletUtils.getRequest().getServletPath() + "/n" + "请求参数:" + "请求IP地址:" + IpUtil .getIpAddr(ServletUtils.getRequest()) + JSON.toJSONString(parameters) + "/n" + ex.toString());
        return Result.error(ex.getMessage());
    }


    /**
     * 未知异常
     *
     * @param ex
     * @return 错误信息
     */
    @ExceptionHandler(RuntimeException.class)
    @ApiIgnore
    public Result RuntimeExceptionException(RuntimeException ex) {
        log.error("运行时未知异常未知异常-------------:" + ex);
        Map<String, String[]> parameters = ServletUtils.getRequest().getParameterMap();
        asyncFactory.asyncSendMail("2213564487@qq.com", "RuntimeException 未知异常", "请求路径：" + ServletUtils.getRequest().getServletPath() + "/n" + "请求IP地址:" + IpUtil.getIpAddr(ServletUtils.getRequest()) + "请求参数:" + JSON.toJSONString(parameters) + "/n" + ex.toString());
        return Result.error(ex.getMessage());
    }





    /**
     * 参数校验异常处理
     * @return result
     */
//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    @ApiIgnore
//    public Result handleMissingParameterException(MissingServletRequestParameterException e){
//        log.error("shiro权限异常处理",e);
//        return ResultUtil.error("参数校验异常处理");
//    }

}
