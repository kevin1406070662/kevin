package com.kevin.framework.shiro;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;

import com.kevin.common.constants.Constants;
import com.kevin.common.utils.StringUtils;
import com.kevin.framework.redis.ICacheService;
import com.kevin.framework.shiro.config.JwtProperties;
import com.kevin.framework.shiro.jwt.utils.JWTUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * token 操作服务
 *
 * @author novel
 * @date 2019/12/12
 */
@Component
public class TokenService {
    protected static final long MILLIS_SECOND = 1000;
    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private final JwtProperties jwtProperties;
    private final ICacheService iCacheService;

    public TokenService(JwtProperties jwtProperties, ICacheService iCacheService) {
        this.jwtProperties = jwtProperties;
        this.iCacheService = iCacheService;
    }

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(Object loginUser) {
        setUserAgent(loginUser);
        return refreshToken(loginUser);
    }


    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public String refreshToken(Object loginUser) {
        //sessionId
        String sessionId = IdUtil.fastUUID();
      //  loginUser.setSessionId(sessionId);
        String token = null;//JWTUtil.sign(loginUser.getUser(), loginUser.getSessionId());
        //loginUser.setLoginTime(JWTUtil.getIssuedAt(token).getTime());
        //设置token到期时间
        //loginUser.setExpireTime(loginUser.getLoginTime() + Optional.ofNullable(JWTUtil.getTokenExpireTime(token)).orElse(0L) * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenCacheKey(token);
       // iCacheService.set(userKey, loginUser, Optional.ofNullable(JWTUtil.getTokenExpireTime(token)).orElse(0L), TimeUnit.MINUTES);
        return token;
    }

    public String refreshToken(String token) {
        String userKey = getTokenCacheKey(token);
        Object loginUser = (Object) iCacheService.get(userKey);
        String newToken = "";
        if (ObjectUtil.isNotNull(loginUser)) {
//            newToken = JWTUtil.sign(loginUser.getUser(), loginUser.getSessionId());
//            //设置token到期时间
//            loginUser.setExpireTime(JWTUtil.getIssuedAt(newToken).getTime() + Optional.ofNullable(JWTUtil.getTokenExpireTime(token)).orElse(0L) * MILLIS_MINUTE);
            // 根据uuid将loginUser缓存
         //   iCacheService.set(userKey, loginUser, Optional.ofNullable(JWTUtil.getTokenExpireTime(token)).orElse(0L), TimeUnit.MINUTES);
        }
        return newToken;
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public Object getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        return getLoginUser(token);
    }

    public Object getLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userKey = getTokenCacheKey(token);
            return (Object) iCacheService.get(userKey);
        }
        return null;
    }

    public void setUser(String username, Object user) {
        if (StringUtils.isNotEmpty(username)) {
            Set<String> keys = iCacheService.keys(Constants.ACCOUNT + username + ":*");
            if (keys != null && keys.size() > 0) {
                for (String key : keys) {
                    Object loginUser = (Object) iCacheService.get(key);
                   // loginUser.setUser(user);
                    iCacheService.set(key, loginUser);
                }
            }
        }
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userKey = getTokenCacheKey(token);
            iCacheService.removePattern(userKey);
            iCacheService.removePattern(Constants.SHIRO_REDIS_PREFIX + ":*:" + JWTUtil.getSubject(token) + ":" + JWTUtil.getId(token));
        }
    }

    /**
     * 删除该用户名下所有登录用户
     *
     * @param token token
     */
    public void delLoginUsersByToken(String token) {
        if (StringUtils.isNotEmpty(token)) {
            iCacheService.removePattern(Constants.ACCOUNT + JWTUtil.getSubject(token) + ":*");
            iCacheService.removePattern(Constants.SHIRO_REDIS_PREFIX + ":*:" + JWTUtil.getSubject(token) + ":*");
        }
    }

    /**
     * 通过用户名退出指定用户
     *
     * @param userName 用户名
     */
    public void delLoginUsersByUserName(String userName) {
        if (StringUtils.isNotEmpty(userName)) {
            iCacheService.removePattern(Constants.ACCOUNT + userName + ":*");
            iCacheService.removePattern(Constants.SHIRO_REDIS_PREFIX + ":*:" + userName + ":*");
        }
    }

    /**
     * 通过sessionId退出指定用户
     *
     * @param sessionId sessionId
     */
    public void delLoginUsersBySessionId(String sessionId) {
        if (StringUtils.isNotEmpty(sessionId)) {
            iCacheService.removePattern(Constants.SHIRO_REDIS_PREFIX + ":*:" + sessionId);
            iCacheService.removePattern(Constants.ACCOUNT + "*:" + sessionId);
        }
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        return request.getHeader(Constants.AUTHORIZATION);
    }

    private String getTokenCacheKey(String token) {
        return Constants.ACCOUNT + JWTUtil.getSubject(token) + ":" + JWTUtil.getId(token);
    }

    private String getShiroCacheKey(String token) {
        return Constants.SHIRO_REDIS_PREFIX + ":*:" + JWTUtil.getSubject(token) + ":" + JWTUtil.getId(token);
    }

    /**
     * 设置用户代理信息
     *
     * @param loginUser 登录信息
     */
    public void setUserAgent(Object loginUser) {
//        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
//        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
//        loginUser.setIpaddr(ip);
//        loginUser.getUser().setLoginIp(ip);
//        loginUser.getUser().setLoginDate(new Date());
//        loginUser.setLoginLocation(AddressUtils.getRealAddress(ip));
//        loginUser.setBrowser(userAgent.getBrowser().getName());
//        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }
}
