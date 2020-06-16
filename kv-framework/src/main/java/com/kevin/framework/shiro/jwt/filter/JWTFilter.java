package com.kevin.framework.shiro.jwt.filter;


import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.kevin.common.result.Result;
import com.kevin.common.utils.MyDateUtils;
import com.kevin.common.utils.StringUtils;
import com.kevin.framework.shiro.TokenService;
import com.kevin.framework.shiro.jwt.JwtToken;
import com.kevin.framework.shiro.jwt.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * token认证过滤器
 *
 * @author kevin
 * @date 2020/1/13
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {
    private static final String CHARSET = "UTF-8";
    private TokenService tokenService;

    public JWTFilter() {};

    public JWTFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * 检测用户是否登录
     * 检测header里面是否包含x-auth-token字段即可
     *
     * @param request  请求
     * @param response 响应
     * @return 是否登录
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = ((HttpServletRequest) request).getHeader("x-auth-token");
        log.info(MyDateUtils.getCurrentTime() + "当前token--------{}", token);
        return StringUtils.isNotEmpty(token);
    }

    public static void main(String[] args) throws InterruptedException {
        Date d = new Date();
        System.out.println(d);
        Thread.sleep(1000l);
        Date b = new Date();
        System.out.println(b);
        System.out.println(b.after(d));
    }

    /**
     * 执行登录认证  判断当前请求是否登录
     *
     * @param request     ServletRequest
     * @param response    ServletResponse
     * @param mappedValue mappedValue
     * @return 是否成功
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (this.isLoginAttempt(request, response)) {
            try {
                this.executeLogin(request, response);
                return true;
            } catch (Throwable e) {
                String message = e.getMessage();
                Throwable throwable = e.getCause();
                if (throwable instanceof TokenExpiredException) {
                    // AccessToken已过期
                    if (this.refreshToken(request, response)) {
                        return true;
                    }
                } else {
                    log.error("用户token信息异常：{}", message);
                }
            }
        }
        return false;
    }
    /**
     * 如果这个Filter在之前isAccessAllowed()方法中返回false,
     * 则会进入这个方法。我们这里直接返回错误的response,说明登录认证失败了
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        this.response401(response);
        return false;
    }
    /**
     * 无需转发，直接返回Response信息
     */
    private void response401(ServletResponse response) {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        String contentType = "application/json;charset=" + CHARSET;
        httpResponse.setCharacterEncoding(CHARSET);
        httpResponse.setStatus(401);
        httpResponse.setContentType(contentType);
        try {
            ServletOutputStream outputStream = httpResponse.getOutputStream();
            Result result = new Result();
            result.setCode(401);
            result.setMsg("Sorry, you need to log in!");
            outputStream.write(JSONObject.toJSONString(result).getBytes());
            outputStream.close();
        } catch (IOException e) {
            log.error("直接返回Response信息出现IOException异常:" + e.getMessage());
        }
    }

    /**
     * 执行登录方法(由自定义realm判断,吃掉异常返回false)
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        checkToken(request, response);
        return true;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        log.info(MyDateUtils.getCurrentTime() + "当前路径JWTFilter--------{   }", httpServletRequest.getServletPath());
        res.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        res.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        res.setHeader("Access-Control-Allow-Credentials", "true");
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            res.setStatus(HttpStatus.OK.value());
            return true;
        }
        return super.preHandle(request, response);
    }

    /**
     * 刷新AccessToken，进行判断RefreshToken是否过期，未过期就返回新的AccessToken且继续正常访问
     */
    private boolean refreshToken(ServletRequest request, ServletResponse response) {
        //获取token
        String token = ((HttpServletRequest) request).getHeader("x-auth-token");
        String uuid = JWTUtil.getSubject(token);
        //获取当前Token 中用户类型
        String tokenType = JWTUtil.getTokenType(token);
        // token 获取可允许刷新时间
        Date tokenExpireTime = JWTUtil.getTokenExpireTime(token);
        // 在可刷新时间范围内
        if (tokenExpireTime.after(new Date())) {
            //生成新增Token
            String newToken = JWTUtil.sign(uuid, true, tokenType);
            if (StringUtils.isNotNull(newToken)) {
                // 使用AccessToken 再次提交给ShiroRealm进行认证，如果没有抛出异常则登入成功，返回true
                JwtToken jwtToken = new JwtToken(newToken);
                this.getSubject(request, response).login(jwtToken);
                // 设置响应的Header头新Token
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                //览器默认是不让获得header中其他值，只能获得 Content-Type  所以我这里设置允许浏览器获取 x-auth-token
                httpServletResponse.setHeader("Access-Control-Expose-Headers", "x-auth-token");
                httpServletResponse.setHeader("x-auth-token", newToken);
                return true;
            }
        }
        return false;
    }

    /**
     * 检验Token
     */
    private void checkToken(ServletRequest request, ServletResponse response) {
        //获取token
        String token = ((HttpServletRequest) request).getHeader("x-auth-token");
        if (StringUtils.isEmpty(token)) {
            String msg = "executeLogin method token must not be null";
            throw new IllegalStateException(msg);
        }
        //进入后台用户认证 JwtToken
        //如果没有抛出异常则代表登入成功，返回true,并生成一个新的token
        JwtToken jwtToken = new JwtToken(token);
        this.getSubject(request, response).login(jwtToken);

    }
}
