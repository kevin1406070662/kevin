package com.kevin.common.annotation;

/**
 * @Description
 * @Author kevin <1406070662@qq.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/5/27
 */
public class SecretAnnotation {
    /**
     * 是否加密
     * 默认false
     * 加密时传值为true
     * @return
     */

    boolean encode= true;
    /**
     * 是否解密
     * 默认为false，
     * 解密时传值为true
     * @return
     */
    boolean decode= true;

}
