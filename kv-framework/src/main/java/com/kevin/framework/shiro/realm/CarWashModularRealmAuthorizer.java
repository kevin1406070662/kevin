package com.kevin.framework.shiro.realm;


import com.kevin.common.enums.LoginType;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

/**
 * author:马凯文
 * date: 2020/3/4 15:44
 * description: 判断用户登录之后进行角色授权时候使用哪个 realm 认证
 */
@Slf4j
public class
CarWashModularRealmAuthorizer extends ModularRealmAuthorizer {

    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        assertRealmsConfigured();
        Set<String> realmNames = principals.getRealmNames();
        //获取realm的名字
        log.info("realmNamelist is" + realmNames.toString());
        String realmName = realmNames.iterator().next();
        log.info("realmName is" + realmName);
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) continue;
            //匹配名字 所有的权限都集中到UserRealm 中处理
            if (realmName.equals(LoginType.PASSWORD_LOGIN_TYPE.toString())) {
                if (realm instanceof SysPasswordRealm) {
                    return ((SysPasswordRealm) realm).isPermitted(principals, permission);
                }
            }
            if (realmName.equals(LoginType.CODE_LOGIN_TYPE.toString())) {
                if (realm instanceof MerchantRealm) {
                    return ((MerchantRealm) realm).isPermitted(principals, permission);
                }
            }
        }
        return false;
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        assertRealmsConfigured();
        Set<String> realmNames = principals.getRealmNames();
        //获取realm的名字
        String realmName = realmNames.iterator().next();
        log.info("realmName is", realmName);
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) continue;
            //匹配名字 所有的权限都集中到UserRealm 中处理
            if (realmName.equals(LoginType.PASSWORD_LOGIN_TYPE.toString())) {
                if (realm instanceof SysPasswordRealm) {
                    return ((SysPasswordRealm) realm).isPermitted(principals, permission);
                }
            }
            if (realmName.equals(LoginType.CODE_LOGIN_TYPE.toString())) {
                if (realm instanceof MerchantRealm) {
                    return ((MerchantRealm) realm).isPermitted(principals, permission);
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        assertRealmsConfigured();
        Set<String> realmNames = principals.getRealmNames();
        //获取realm的名字
        String realmName = realmNames.iterator().next();
        log.info("realmName is", realmName);
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) continue;

            if (realmName.equals(LoginType.PASSWORD_LOGIN_TYPE.toString())) {
                if (realm instanceof SysPasswordRealm) {
                    return ((SysPasswordRealm) realm).isPermitted(principals, roleIdentifier);
                }
            }
            if (realmName.equals(LoginType.CODE_LOGIN_TYPE.toString())) {
                if (realm instanceof MerchantRealm) {
                    return ((MerchantRealm) realm).isPermitted(principals, roleIdentifier);
                }
            }
        }
        return false;
    }

}
