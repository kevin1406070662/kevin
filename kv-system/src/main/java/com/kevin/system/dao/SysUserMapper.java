package com.kevin.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kevin.system.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {

    List<String> selectPermsByRole(@Param("userID") String userID);

}
