package com.kevin.common.enums;

/**
 * @author 马凯文
 * @date 2020/1/18 10:34
 */
public enum LoginType {

    /**
     * 客户端手机号登录
     */
    PHONE_LOGIN_TYPE("Phone"),
    /**
     * 后台管理登录
     */
    PASSWORD_LOGIN_TYPE("SysPasswordRealm"),
    /**
     * 商家端登录
     */
    CODE_LOGIN_TYPE("Merchant");


    private String type;

    LoginType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }

}
