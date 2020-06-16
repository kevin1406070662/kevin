package com.kevin.framework.shiro.realm;



import com.kevin.framework.shiro.customizedAuthenticator.CustomizedToken;
import com.kevin.framework.shiro.jwt.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author 马凯文
 * @date 2020/1/18 10:34
 * 继承 ModularRealmAuthenticator
 * 重写doAuthenticate 根据用户登录的类型 赋值对应realm
 */
@Slf4j
public class CarWashModularRealmAuthenticator extends ModularRealmAuthenticator {
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        assertRealmsConfigured();
        // 所有Realm
        Collection<Realm> realms = getRealms();
        // 登录类型对应的所有Realm
        Collection<Realm> typeRealms = new ArrayList<>();
        // 强制转换回自定义的Token
        try {
            JwtToken jwtToken = (JwtToken) authenticationToken;
            for (Realm realm : realms) {
                 // 当每次校验token 时候每次会从这里分发到公用的 校验
                log.info("校验token------>>>>>>>>分发到共用处理token realm(TokentCheckCMRealm)");
                if (realm.getName().contains("TokentCheckCMRealm")) {
                    typeRealms.add(realm);
                }
            }
            return doSingleRealmAuthentication(typeRealms.iterator().next(), jwtToken);
        } catch (ClassCastException e) {
            // 强制转换回自定义的UserToken
            CustomizedToken customizedToken = (CustomizedToken) authenticationToken;
            // 登录类型
            String loginType = customizedToken.getLoginType();
            for (Realm realm : realms) {
                //判断是否为loginType类型的Realm
                if (realm.getName().contains(loginType))
                    typeRealms.add(realm);
            }
            // 判断是单Realm还是多Realm
            if (typeRealms.size() == 1) {
                return doSingleRealmAuthentication(((ArrayList<Realm>) typeRealms).get(0), customizedToken);
            } else {
                return doMultiRealmAuthentication(typeRealms, customizedToken);
            }
        }
    }
}
