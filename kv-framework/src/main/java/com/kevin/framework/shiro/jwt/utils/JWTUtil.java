package com.kevin.framework.shiro.jwt.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kevin.common.utils.Md5Util;
import com.kevin.common.utils.MyDateUtils;
import com.kevin.framework.shiro.config.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * jwt认证工具类
 *
 * @author kevin
 * @date 2019/3/18
 */
@Component
public class JWTUtil {

    private static final String CLAIM_NAME = "username";
    private static final String REFRESH_TOKEN_EXPIRE_TIME = "refreshTokenExpireTime";

    // 过期时间30天
    // private static final long EXPIRE_TIME = 24 * 60 * 30 * 1000;
    //token 可刷新时间
    private static final String TOKEN_EXPIRE_TIME = "tokenExpireTime";
    private static final String SESSION_ID = "sessionId";
    //token 类型
    private static final String TOKEN_type = "tokenType";

    //是否记住我
    private static final String REMEMBER_ME = "rememberMe";
    /**
     * token 私钥
     */
    public static String TOKEN_SECRET = "hhh8IZW+t0HfA8YxfnDVjtM6hIbrcJVb1iFc4mIKhOUsPeSqeJl6Yz7vOQ+Kg++6";
    @Autowired
    private static JWTUtil jwtUtil;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 校验token是否正确
     *
     * @param token token签名
     * @param uuid  用户uuid
     * @return
     */
    public static boolean verify(String token, String uuid) {

        // 根据密码生成JWT效验器 （相当于盐值）
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm)
                .withSubject(uuid)
//              .withClaim(CLAIM_NAME, user.getUserName())
                .build();
        verifier.verify(token);
        return true;
    }

    /**
     * 获取token可刷新时间
     *
     * @param token token
     * @return 可刷新时间
     */
    public static Date getTokenExpireTime(String token) {
        Claim claim = getClaim(token, TOKEN_EXPIRE_TIME);
        return claim == null ? null : claim.asDate();
    }

    /**
     * 获取token类型
     *
     * @param token token
     * @return 可刷新时间
     */
    public static String getTokenType(String token) {
        Claim claim = getClaim(token, TOKEN_type);
        return claim == null ? null : claim.asString();
    }


    /**
     * 获取id
     *
     * @param token token签名
     * @return id
     */
    public static String getId(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getId();
    }



    /**
     * 获取主体
     *
     * @param token token签名
     * @return 主体
     */
    public static String getSubject(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }

    /**
     * 获取token签发时间
     *
     * @param token token签名
     * @return 签发时间
     */
    public static Date getIssuedAt(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getIssuedAt();
    }

    /**
     * 获取认证信息
     *
     * @param token token
     * @param claim claim
     * @return 认证信息
     */
    private static Claim getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim);
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成签名
     *
     * @param uuid 用户 uuid
     * @return token 签名
     */
    public static String sign(String uuid, Boolean rememberMe, String reamType) {
        long currentTimeMillis = System.currentTimeMillis();
        // 指定过期时间 14 * 24 * 60 * 60 * 1000L
        Date date = new Date(currentTimeMillis + jwtUtil.jwtProperties.getRememberMeTokenExpireTime());
       //Date date = new Date(currentTimeMillis + 14 * 24 * 60 * 60 * 1000L);
        // token 过期后检测是否在可刷新范围内(时间往后累加一天)
        Date refreshDate = MyDateUtils.accumulationTime(60 * 24, date);
        // 根据密码生成JWT效验器 （相当于盐值）
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        return JWT.create()
//                .withClaim(CLAIM_NAME, user.getUserName())
//                .withClaim(SESSION_ID, sessionId)
//                .withClaim(REFRESH_TOKEN_EXPIRE_TIME, currentTimeMillis)
                .withClaim(REMEMBER_ME, rememberMe)
                .withClaim(TOKEN_EXPIRE_TIME, refreshDate)
                .withClaim(TOKEN_type, reamType)
                .withIssuedAt(new Date(currentTimeMillis))//签发时间
                .withIssuer("kevin")//签发者
                //.withJWTId(sessionId)//jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
                .withSubject(uuid)// jwt所面向的用户
                .withExpiresAt(date)//token 超时时间
                .withAudience("user")//颁发给谁
                .withNotBefore(new Date(currentTimeMillis))//在此时间前不可用
                .sign(algorithm);
    }

    @PostConstruct
    public void init() {
        jwtUtil = this;
        jwtUtil.jwtProperties = this.jwtProperties;
    }
}
