package com.kevin.web;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kevin.common.annotation.EnableEncryptBody;
import com.kevin.common.annotation.Log;
import com.kevin.common.enums.BusinessType;
import com.kevin.common.enums.LoginType;
import com.kevin.common.enums.ReamType;
import com.kevin.common.result.Page;
import com.kevin.common.result.Result;
import com.kevin.framework.redis.ICacheService;
import com.kevin.framework.shiro.customizedAuthenticator.CustomizedToken;
import com.kevin.framework.shiro.jwt.utils.JWTUtil;
import com.kevin.system.domain.SysUser;
import com.kevin.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 测试
 *
 * @author Kevin
 * @create 2020-05-22 11:06
 **/
@RestController
@RequestMapping("/api/test/")
@Api(tags = "接口test")
@Slf4j
@EnableEncryptBody
public class TestController {

    @Resource
    SysUserService sysUserService;

    @Resource
    ICacheService ICacheService;

    @GetMapping("getAll")
    @ApiOperation("查询所有")
    public List<SysUser> getAll(String kevin) {
      return   sysUserService.list();
    }

    @GetMapping("getAlllist")
    @ApiOperation("查询所有分页")
    public Result page(SysUser sysUser,@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer limit) {

        PageHelper.startPage(page, limit);
        List<SysUser> consults = sysUserService.list(new QueryWrapper<SysUser>(sysUser));
        PageInfo<SysUser> pageInfo = new PageInfo<SysUser>(consults);
        return Result.success(new
                Page<SysUser>(pageInfo.getTotal(),limit,page,consults));
    }

    @PostMapping("addUser")
    @ApiOperation("新增系统用户")
    @Log(title = "新增系统用户", businessType = BusinessType.INSERT)
    public Result addUser(@RequestBody SysUser sysUser) {
        sysUser.setSalt(RandomUtil.randomString(10));
        // 获取盐值，即用id
        ByteSource salt = ByteSource.Util.bytes(sysUser.getSalt());
        //将原始密码加盐（上面生成的盐），并且用md5算法加密3次，将最后结果存入数据库中
        /*
         * MD5加密：
         * 使用SimpleHash类对原始密码进行加密。
         * 第一个参数代表使用MD5方式加密
         * 第二个参数为原始密码
         * 第三个参数为盐值，即用户名
         * 第四个参数为加密次数
         * 最后用toHex()方法将加密后的密码转成String
         * */
        String pwd = new SimpleHash("MD5", sysUser.getAccount(), salt, 3).toHex();
        sysUser.setPassword(pwd);
        boolean save = sysUserService.save(sysUser);
        if (save){
            log.info("新增表数据为------->"+JSONObject.toJSON(sysUser)+"成功!");
            return Result.success();
        }else {
            log.info("新增表数据为------->"+JSONObject.toJSON(sysUser)+"失败!");
            return Result.error();
        }
    }
    @PostMapping("login")
    @ApiOperation("登录")
    public Result login(@RequestBody SysUser sysUser, HttpServletRequest httpServletRequest){
        // 1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        // 3.封装用户数据
        CustomizedToken token = new CustomizedToken(sysUser.getAccount(), sysUser.getPassword(),
                LoginType.PASSWORD_LOGIN_TYPE.toString());
        // 4.执行登录方法
        try {
            subject.login(token);
            SysUser sysUserdb = (SysUser) subject.getPrincipal();
            String newToken = JWTUtil.sign(sysUserdb.getId(), true, ReamType.ADMIN_REAM_TYPE.toString());
            // 异步修改用户登录 ip 地址  缓存信息
            ICacheService.set(sysUserdb.getId(),sysUserdb,7l*24L*6L*10L);
            return Result.success("登录成功",200,true,newToken);
        } catch (UnknownAccountException e) {
            return Result.error("账号不存在");
        } catch (ExpiredCredentialsException e) {
            return Result.error("凭证过期");
        } catch (IncorrectCredentialsException e) {
            return Result.error("密码不对");
        }
    }


    @GetMapping("getUserInfo")
    @ApiOperation("获取用户信息")
    public Result getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("x-auth-token");
        String uuid = JWTUtil.getSubject(token); //获取uuid
        return Result.success((JSONObject)ICacheService.get(uuid));
    }
    @DeleteMapping("/remove")
    @ApiOperation("删除信息")
    public Result remove(@RequestBody SysUser sysUser){
        boolean remove = sysUserService.remove(new UpdateWrapper<SysUser>(sysUser));
        if (remove){
            log.info("删除表中数据为------->"+JSONObject.toJSON(sysUser)+"成功!");
            return Result.success();
        }else {
            log.info("删除表中数据为------->"+JSONObject.toJSON(sysUser)+"失败!");
            return Result.error();
        }
    }

    @PutMapping("/update")
    @ApiOperation("根据id更新表数据")
    public Result update(@RequestBody SysUser sysUser){
        boolean remove = sysUserService.updateById(sysUser);;
        if (remove){
            log.info("更新表中数据表中数据为------->"+JSONObject.toJSON(sysUser)+"成功!");
            return Result.success();
        }else {
            log.info("更新表中数据表中数据为------->"+JSONObject.toJSON(sysUser)+"失败!");
            return Result.error();
        }
    }
    public static void main(String[] args) {
        // 获取盐值，即用id
            ByteSource salt = ByteSource.Util.bytes("n5yey76lc0");
        //将原始密码加盐（上面生成的盐），并且用md5算法加密3次，将最后结果存入数据库中
        /*
         * MD5加密：
         * 使用SimpleHash类对原始密码进行加密。
         * 第一个参数代表使用MD5方式加密
         * 第二个参数为原始密码
         * 第三个参数为盐值，即用户名
         * 第四个参数为加密次数
         * 最后用toHex()方法将加密后的密码转成String
         * */
        String pwd = new SimpleHash("MD5", "admin88888888", salt, 3).toHex();
        log.info(pwd);

        String subject = JWTUtil.getSubject("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ1c2VyIiwidG9rZW5FeHBpcmVUaW1lIjoxNzE2MDE3NTkxLCJuYmYiOjE1OTAzOTE5OTEsImlzcyI6ImtldmluIiwicmVtZW1iZXJNZSI6dHJ1ZSwidG9rZW5UeXBlIjoiYWRtaW4iLCJleHAiOjE1OTE2MDE1OTEsImlhdCI6MTU5MDM5MTk5MX0.eJcVqwgGR1sLzLUmAgbCpJxY96r9Y7_rujv94UwuhZw");
        log.info(subject);

    }
}
