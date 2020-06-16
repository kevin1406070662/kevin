package com.kevin.framework.redis;


import com.kevin.common.ICache;

import java.util.List;
import java.util.Set;

/**
 * Redis 服务接口
 *
 * @author novel
 * @date 2019/6/4
 */
public interface ICacheService extends ICache {

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    boolean set(final String key, Object value);

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    boolean set(final String key, Object value, Long expireTime);

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    @Override
    void remove(final String... keys);

    /**
     * 批量删除key
     *
     * @param pattern
     */
    void removePattern(final String pattern);

    /**
     * 删除对应的value
     *
     * @param key
     */
    @Override
    void remove(final String key);

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    @Override
    boolean exists(final String key);

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    @Override
    <T> T get(final String key);

    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    void hmSet(String key, Object hashKey, Object value);

    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    Object hmGet(String key, Object hashKey);

    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    void lPush(String k, Object v);

    /**
     * 列表获取
     *
     * @param k
     * @param l
     * @param l1
     * @return
     */
    List<Object> lRange(String k, long l, long l1);

    /**
     * 集合添加
     *
     * @param key
     * @param value
     */
    void add(String key, Object value);

    /**
     * 集合获取
     *
     * @param key
     * @return
     */
    Set<Object> setMembers(String key);

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param source
     */
    void zAdd(String key, Object value, double source);

    /**
     * 有序集合获取
     *
     * @param key
     * @param source
     * @param source1
     * @return
     */
    Set<Object> rangeByScore(String key, double source, double source1);

}
