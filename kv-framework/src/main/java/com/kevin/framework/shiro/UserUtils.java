package com.kevin.framework.shiro;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.kevin.common.utils.ServletUtils;
import com.kevin.common.utils.StringUtils;
import com.kevin.framework.redis.ICacheService;
import com.kevin.framework.shiro.jwt.utils.JWTUtil;
import com.kevin.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * ClassName kevin-boot - UserUtils
 * Description 获取用户信息
 * Author kevin
 * Date 2020/5/30 14:17
 * Version 1.0
 **/
@Component
@Slf4j
public class UserUtils {
    private  final  ICacheService iCacheService;
    public UserUtils( ICacheService iCacheService) {
        this.iCacheService = iCacheService;
    }

    /**
     * 获取用户信息
      * @return
     */
    public   SysUser getUserInfo(){
        String header = ServletUtils.getRequest().getHeader("x-auth-token");
        if (StringUtils.isEmpty(header)){
            return  null;
        }
        String subject = JWTUtil.getSubject(header);
        Object o = iCacheService.get(subject);
        log.info("获取用户id为==============={}的信息为{}",subject,o);
        JSON parse = JSONUtil.parse(o);
        SysUser sysUser = parse.toBean(SysUser.class);
        return sysUser;
    }
}
