package com.kevin.framework.shiro.jwt;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * 参照UsernamePasswordToken,用于扩展业务，由于rest api不需要rememberMe，已丢弃
 * 后台管理端认证
 *
 * @author Kevin
 * @date 2019/12/18
 */

public class JwtToken implements AuthenticationToken {
    private static final long serialVersionUID = 1L;

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public String toString() {
        return "JwtToken{" +
                "token='" + token + '\'' +
                '}';
    }
}
