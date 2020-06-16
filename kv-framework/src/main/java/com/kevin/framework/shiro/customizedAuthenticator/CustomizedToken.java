package com.kevin.framework.shiro.customizedAuthenticator;

import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;

/**
 * 自定义shiro登录操作
 *
 * @author Kevin
 * @create 2020-05-22 16:42
 **/
@Data
public class CustomizedToken extends UsernamePasswordToken implements Serializable {

    /**
     * 登录类型
     */
    private String loginType;


    public CustomizedToken(final String username, final String password,
                           String loginType) {
        super(username, password);
        this.loginType = loginType;
    }


    public CustomizedToken(final String username, final String password,
                           String loginType, boolean rememberMe) {
        super(username, password, rememberMe);
        this.loginType = loginType;
    }

    @Override
    public String toString() {
        return "loginType="+ loginType +",username=" + super.getUsername()+",password="+ String.valueOf(super.getPassword());
    }
}

