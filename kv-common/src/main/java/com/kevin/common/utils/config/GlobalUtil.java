package com.kevin.common.utils.config;


import com.kevin.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置类
 *
 * @author novel
 * @date 2019/6/5
 */
@Component
public class GlobalUtil {
    private static final Logger log = LoggerFactory.getLogger(GlobalUtil.class);

    private static Environment env;

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = new HashMap<>();

    private GlobalUtil(Environment env) {
        GlobalUtil.env = env;
    }

    /**
     * 获取配置
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (StringUtils.isEmpty(value)) {
            try {
                value = env.getProperty(key);

                map.put(key, StringUtils.isNotEmpty(value) && !"null".equals(value) ? value : StringUtils.EMPTY);
            } catch (Exception e) {
                log.error("获取全局配置异常 {}", key);
            }
        }
        return StringUtils.isNotEmpty(value) && !"null".equals(value) ? value : StringUtils.EMPTY;
    }


    /**
     * 获取配置
     *
     * @param key          配置key
     * @param defaultValue 默认值
     * @return 配置值
     */
    public static String getConfig(String key, String defaultValue) {
        String value = map.get(key);
        if (value == null) {
            try {
                value = env.getProperty(key, defaultValue);
                map.put(key, value);
            } catch (Exception e) {
                log.error("获取全局配置异常 {}", key);
            }
        }
        return value;
    }

    /**
     * 设置全局属性
     *
     * @param key   属性key
     * @param value 属性值
     */
    public static String setConfig(String key, String value) {
        if (StringUtils.isNotEmpty(value)) {
            map.put(key, value);
            return value;
        } else {
            return null;
        }
    }
}
