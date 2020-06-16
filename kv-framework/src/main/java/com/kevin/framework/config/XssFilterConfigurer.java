package com.kevin.framework.config;


import com.kevin.framework.configdata.XssFilterConfig;
import com.kevin.framework.xss.XssFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.Map;

/**
 * xss 过滤动装配
 *
 * @author 李振
 * @date 2020/4/15
 */
@Configuration
@EnableConfigurationProperties(XssFilterConfig.class)
public class XssFilterConfigurer {
    private XssFilterConfig xssFilterConfig;

    public XssFilterConfigurer(XssFilterConfig xssFilterConfig) {
        this.xssFilterConfig = xssFilterConfig;
    }

    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistrationBean() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns(xssFilterConfig.getUrlPatterns().split(","));
        registration.setName("XssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        Map<String, String> initParameters = new HashMap<>(4);
        initParameters.put("excludes", xssFilterConfig.getExcludes());
        initParameters.put("includes", xssFilterConfig.getIncludes());
        initParameters.put("enabled", Boolean.toString(xssFilterConfig.isEnabled()));
        initParameters.put("encoding", xssFilterConfig.getEncoding());
        registration.setInitParameters(initParameters);
        return registration;
    }

}
