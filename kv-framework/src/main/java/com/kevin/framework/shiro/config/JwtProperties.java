package com.kevin.framework.shiro.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * token配置类
 *
 * @author novel
 * @date 2019/3/18
 */
@ConfigurationProperties(prefix = "token")
@Data
public class JwtProperties {
    /**
     * token过期时间，单位分钟
     */
    Long tokenExpireTime = 60L;
    /**
     * 记住密码时token的过期时间，单位分钟<br/>
     * 默认15天
     */
    Long rememberMeTokenExpireTime = 60 * 24 * 15L;
    /**
     * 刷新Token过期时间，单位分钟
     */
    Long refreshTokenExpireTime = 30L;
    /**
     * Shiro缓存有效期，单位分钟
     */
    Long shiroCacheExpireTime = 30L;
    /**
     * token加密密钥
     */
    String secretKey;
}