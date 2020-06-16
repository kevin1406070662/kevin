package com.kevin.common;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 系统缓存接口
 *
 * @author novel
 * @date 2019/6/4
 */
public interface ICache {
    /**
     * 写入缓存
     *
     * @param key   键
     * @param value 值
     * @return 结果
     */
    boolean set(final String key, Object value);

    /**
     * 写入缓存设置时效时间
     *
     * @param key        键
     * @param value      值
     * @param expireTime 超时时间
     * @return 结果
     */
    boolean set(final String key, Object value, Long expireTime);

    /**
     * 写入缓存设置时效时间
     *
     * @param key        键
     * @param value      值
     * @param expireTime 超时时间
     * @param unit       单位
     * @return 结果
     */
    boolean set(final String key, Object value, Long expireTime, TimeUnit unit);

    /**
     * 批量删除对应的value
     *
     * @param keys 键
     */
    void remove(final String... keys);

    /**
     * 删除对应的value
     *
     * @param key 键
     */
    void remove(final String key);

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key 键
     * @return 结果
     */
    boolean exists(final String key);

    /**
     * 读取缓存
     *
     * @param key 键
     * @return 结果
     */
    <T> T get(final String key);

    /**
     * 清空redis
     */
    void clear();

    /**
     * 获取所有key
     *
     * @param pattern 匹配字符
     * @return 结果
     */
    Set<String> keys(String pattern);

    /**
     * 批量获取
     *
     * @param <T>  结果泛型
     * @param keys 匹配字符
     * @return 结果
     */
    <T> List<T> multiGet(Set<String> keys);
}
