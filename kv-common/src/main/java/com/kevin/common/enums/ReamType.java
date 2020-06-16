package com.kevin.common.enums;

/**
 * author:马凯文
 * date: 2020/3/13 10:42
 * description: token 认证类型
 */
public enum ReamType {
    /**
     * 后台管理 认证
     */
    ADMIN_REAM_TYPE("admin"),
    /**
     * 商家端 认证
     */
    Merchant_REAM_TYPE("Merchant"),

    /**
     * 客户端 认证
     */
    Client_REAM_TYPE("Client");

    private String type;

    ReamType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
