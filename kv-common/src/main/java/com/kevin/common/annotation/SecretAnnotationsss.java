package com.kevin.common.annotation;
import java.lang.annotation.*;

/**
 * @author Kevin
 * @create 2020-05-26 15:33
 * ElementType.METHOD 用于描述方法
 * ElementType.TYPE 用于描述类、接口(包括注解类型) 或enum声明
 * RetentionPolicy.RUNTIME  注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
 **/

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SecretAnnotationsss {

    /**
     * 是否加密
     * 默认false
     * 加密时传值为true
     * @return
     */

    boolean encode() default true;
    /**
     * 是否解密
     * 默认为false，
     * 解密时传值为true
     * @return
     */
    boolean decode() default true;
}
