package com.kevin.system.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.kevin.system.dao.SysRoleMapper;
import com.kevin.system.domain.SysRole;
import com.kevin.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 角色 服务层实现
 * 
 * @author kevin
 * @date 2020/05/30
 */

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService{

	@Autowired
	private SysRoleMapper sysRoleMapper;

	
}
