package com.kevin;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kevin.system.domain.SysUser;
import com.kevin.system.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Kevin
 * @create 2020-05-22 12:28
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
//多模块单元测试时候包名 要统一不然会报错
public class KevinApplicationtest {

    @Resource
    SysUserService sysUserService;

   @Test
    public void getAll() {
//       sysUserService.list();
//       sysUserService.count();
       SysUser sysUser=new SysUser();
       sysUser.setId("111");
       Wrapper<SysUser> wrapper=new QueryWrapper();

       sysUserService.list(new QueryWrapper<>(sysUser,"user_name","login_date"));
    }
}
