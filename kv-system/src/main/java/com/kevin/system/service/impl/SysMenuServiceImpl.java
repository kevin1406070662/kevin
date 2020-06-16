package com.kevin.system.service.impl;


import org.springframework.stereotype.Service;
import com.kevin.system.dao.SysMenuMapper;
import com.kevin.system.domain.SysMenu;
import com.kevin.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * 菜单权限 服务层实现
 *
 * @author kevin
 * @date 2020/06/10
 */

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService{

	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Override
	public List<SysMenu> selectMenByRole(Integer roleID) {
		return sysMenuMapper.selectMenByRole(roleID);
	}
}
