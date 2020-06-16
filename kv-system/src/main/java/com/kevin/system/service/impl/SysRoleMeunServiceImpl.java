package com.kevin.system.service.impl;


import org.springframework.stereotype.Service;
import com.kevin.system.dao.SysRoleMeunMapper;
import com.kevin.system.domain.SysRoleMeun;
import com.kevin.system.service.SysRoleMeunService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 角色菜单 服务层实现
 *
 * @author kevin
 * @date 2020/06/10
 */

@Service
public class SysRoleMeunServiceImpl extends ServiceImpl<SysRoleMeunMapper, SysRoleMeun> implements SysRoleMeunService{

	@Autowired
	private SysRoleMeunMapper sysRoleMeunMapper;


}
