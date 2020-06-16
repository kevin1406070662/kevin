package com.kevin.common.constants;

/**
 * 常量类
 * @author 马凯文
 * @date 2020/1/18 10:34
 */
public class JWTConstant {

    /**
     * 验证码过期时间 此处为五分钟
     */
    public static Integer CODE_EXPIRE_TIME = 60 * 5;

    /**
     * jwtToken过期时间
     */
    public static Long TOKEN_EXPIRE_TIME = 24 * 60 * 60 * 1000L;

    /**
     * token请求头名称
     */
    public static String TOKEN_HEADER_NAME = "authorization";

    /**
     * token 私钥
     */
    public static String TOKEN_SECRET = "hhh8IZW+t0HfA8YxfnDVjtM6hIbrcJVb1iFc4mIKhOUsPeSqeJl6Yz7vOQ+Kg++6";
}
