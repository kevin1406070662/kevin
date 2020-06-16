package com.kevin.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevin.system.dao.SysUserMapper;
import com.kevin.system.domain.SysUser;
import com.kevin.system.service.SysUserService;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    @Autowired
    SysUserMapper sysUserMapper;


    @Override
    public List<String> getPermsByRole(String userID) {
        return sysUserMapper.selectPermsByRole(userID);
    }
}



