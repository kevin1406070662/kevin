package com.kevin.common.enums;

public enum ResultEnum {
    UNKNOWNERROR(-1,"未知错误"),
    NOCUSTOMER(1001,"用户不存在"),
    PARAMETERERROR(1002,"参数错误"),
    NOPRESENT(1003,"暂无权限"),
    SUCCESS(1,"操作成功"),
    WXSLOGINFAILED(2001,"小程序登陆失败"),
    NETWORKFAILED(3001,"网络请求异常");
    private int code;
    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}