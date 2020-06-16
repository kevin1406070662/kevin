package com.kevin.common.utils;



import com.kevin.common.exception.business.BusinessException;

import java.util.List;

/**
 * 断言
 *
 * @author kevin
 * @date 2019/6/4
 */
public class Assert {

    /**
     * 数组对象不为空<br/>
     * 如果数组对象为空，则抛出异常<br/>
     *
     * @param object  数组
     * @param message 错误信息
     */
    public static void isNotNull(Object[] object, String message) {
        if (object == null || object.length == 0) {
            throw new BusinessException(message);
        }
    }


    /**
     * 数组对象不为空<br/>
     * 如果数组对象为空，则抛出异常<br/>
     *
     * @param object 数组
     */
    public static void isNotNull(Object[] object) {
        if (object == null || object.length == 0) {
            throw new BusinessException("业务异常");
        }
    }


    /**
     * 对象不为空<br/>
     * 如果对象为空，则抛出异常<br/>
     * 适用于删除查询操作
     *
     * @param str 要判断的对象
     */
    public static void isNotNull(String str) {
        if (str == null || str.isEmpty()) {
            throw new BusinessException("业务异常");
        }
    }

    /**
     * 对象不为空<br/>
     * 如果对象为空，则抛出异常<br/>
     * 适用于删除查询操作
     *
     * @param str     要判断的对象
     * @param message 错误消息
     */
    public static void isNotNull(String str, String message) {
        if (str == null || str.isEmpty()) {
            throw new BusinessException(message);
        }
    }


    /**
     * 集合不为空<br/>
     * 如果集合为空，则抛出异常
     *
     * @param object  要判断的对象
     * @param message 错误消息
     */
    public static void isNotNull(List<?> object, String message) {
        if (object == null || object.size() <= 0) {
            throw new BusinessException(message);
        }
    }


    /**
     * 判断对象是否为空
     *
     * @param object
     * @param message
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new BusinessException(message);
        }
    }
}
