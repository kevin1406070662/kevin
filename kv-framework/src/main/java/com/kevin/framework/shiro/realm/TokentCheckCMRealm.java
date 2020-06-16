package com.kevin.framework.shiro.realm;


import com.kevin.common.enums.LoginType;
import com.kevin.common.enums.ReamType;
import com.kevin.common.utils.StringUtils;
import com.kevin.framework.shiro.jwt.JwtToken;
import com.kevin.framework.shiro.jwt.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 后台Token身份校验
 *
 * @author Kevin
 * @date 2019/12/18
 * @deprecated 只做JWTFilter中提交的JwtToken 进行校验 并且可以从Token 取出身份类型
 */
@Slf4j
public class TokentCheckCMRealm extends AuthorizingRealm {


    /**
     * 大坑！，必须重写此方法，不然Shiro会报错<br/>
     * 设置realm支持的authenticationToken类型
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        return null;
    }

    /**
     * 登陆认证 用于校验 token
     *
     * @param auth jwtFilter传入的token
     * @return 登陆信息
     * @throws AuthenticationException 未登陆抛出异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();//目前只存储用户的uuid
        // 解密获得username，用于和数据库进行对比
//        String uuid = JWTUtil.getSubject(token); //获取uuid
//        if (StringUtils.isEmpty(uuid)) {
//            throw new AuthenticationException("token 无效");
//        }
//        //获取当前Token 中用户类型
//        String tokenType = JWTUtil.getTokenType(token);
        try {
            //校验Token合法性
//            if (JWTUtil.verify(token, uuid)) {
//                //3.realmName：当前realm对象的name，调用父类的getName()方法即可   也可以定义realm名字
//                String realmName = judgeRelam(tokenType);
//                return new SimpleAuthenticationInfo(token, token, realmName);
//            } else {
//                throw new AuthenticationException("token 验证失败");
//            }
            return new SimpleAuthenticationInfo(token, token, "SysPasswordRealm");
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }

    }

    @Override
    protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
        return super.getAuthorizationInfo(principals);
    }

    /**
     * 通过这里自定义ream名称决定进行权限校验时候去哪里
     */
    public String judgeRelam(String tokenType) {
        if (ReamType.ADMIN_REAM_TYPE.toString().equals(tokenType)) {
            //进入后台用户认证
            return LoginType.PASSWORD_LOGIN_TYPE.toString();
        } else if (ReamType.Merchant_REAM_TYPE.toString().equals(tokenType)) {
            //进入商家端用户认证
            return LoginType.CODE_LOGIN_TYPE.toString();
        } else if (ReamType.Client_REAM_TYPE.toString().equals(tokenType)) {
            //进入客户端用户认证
            return LoginType.PHONE_LOGIN_TYPE.toString();
        } else {
            return null;
        }
    }
}
