package com.kevin.framework.configdata;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * xss 配置
 *
 * @author 李振
 * @date 2020/4/15
 */
@ConfigurationProperties(prefix = com.kevin.framework.configdata.XssFilterConfig.XSS_PREFIX)
@Data
public class XssFilterConfig {
    public static final String XSS_PREFIX = "xss";
    /**
     * 是否打开
     */
    private boolean enabled;
    /**
     * 排除链接
     */
    private String excludes;
    /**
     * 包含链接
     */
    private String includes;
    /**
     * 过滤链接
     */
    private String urlPatterns;
    /**
     * 字符串编码
     */
    private String encoding;
}
