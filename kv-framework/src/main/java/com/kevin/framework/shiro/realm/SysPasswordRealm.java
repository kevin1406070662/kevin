package com.kevin.framework.shiro.realm;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kevin.common.utils.StringUtils;
import com.kevin.framework.shiro.customizedAuthenticator.CustomizedToken;
import com.kevin.framework.shiro.jwt.utils.JWTUtil;
import com.kevin.system.domain.SysUser;
import com.kevin.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 马凯文
 * @date 2020/1/18 10:34
 * @Description: 自定义密码登录令牌
 */
@Slf4j
public class SysPasswordRealm extends AuthorizingRealm {

    @Autowired
    SysUserService sysUserService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomizedToken;
    }

    /**
     * 获取授权信息
     *
     * @param principals principals
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //给资源进行授权
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        String token = (String) subject.getPrincipal();
        // 解密获得username，用于和数据库进行对比
        String uuid = JWTUtil.getSubject(token); //获取uuid
        //查询当前用户绑定角色所对应的权限
        List<String> mapList = null;
        mapList = sysUserService.getPermsByRole(uuid);
        //查询当前用户绑定角色所对应的权限
        Set<String> permsSet = new HashSet<>();
        log.info("查询当前用户uuid[{}]" , uuid);
        log.info("查询当前用户绑定角色所对应的权限" + mapList.toString());
        mapList.stream().forEach(perm -> {
                    if (StringUtils.isNotEmpty(perm)) {
                        permsSet.addAll(Arrays.asList(perm.trim().split(",")));
                    }
                }
        );
        //权限加入AuthorizationInfo认证对象
        authorizationInfo.setStringPermissions(permsSet);
        return authorizationInfo;
    }

    /**
     * 获取身份认证信息
     *
     * @param authenticationToken authenticationToken
     * @return AuthenticationInfo
     * @throws AuthenticationException AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        CustomizedToken token = (CustomizedToken) authenticationToken;
        log.info("SysPasswordRealm中账号为--------->>>>>>" + token.getUsername() + "开始登录认证");
        // 根据账号查询用户
        SysUser sysUser = new SysUser();
        sysUser.setAccount(token.getUsername());
        SysUser sysUserDB = sysUserService.getOne(new QueryWrapper<>(sysUser));
        if (sysUserDB == null) {
            // 抛出账号不存在异常
            throw new UnknownAccountException();
        }
        // 1.principal：认证的实体信息，可以是手机号，也可以是数据表对应的用户的实体类对象
        // 2.credentials：密码
        Object credentials = sysUserDB.getPassword();
        // 3.realmName：当前realm对象的name，调用父类的getName()方法即可   也可以定义realm名字
        String realmName = "SysPasswordRealm";
        log.info("super.getName()" + super.getName() + sysUserDB.getSalt());
        // 4.盐,取用户信息中唯一的字段 uuid 来生成盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(sysUserDB.getSalt());
        return new SimpleAuthenticationInfo(sysUserDB, credentials, credentialsSalt, realmName);
    }
}
