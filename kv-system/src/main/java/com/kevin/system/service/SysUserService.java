package com.kevin.system.service;

import com.kevin.system.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserService extends IService<SysUser> {

    List<String> getPermsByRole( String userID);


}



