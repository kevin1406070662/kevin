package com.kevin.system.service;

import com.kevin.system.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 菜单权限 服务层
 *
 * @author kevin
 * @date 2020/06/10
 */
public interface SysMenuService extends IService<SysMenu> {
    List<SysMenu> selectMenByRole(Integer roleID);

}
