package com.kevin.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kevin.system.domain.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单权限 数据层
 *
 * @author kevin
 * @date 2020/06/10
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> selectMenByRole(@Param("roleID") Integer roleID);

}
