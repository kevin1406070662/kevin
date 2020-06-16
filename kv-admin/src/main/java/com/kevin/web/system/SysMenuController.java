package com.kevin.web.system;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kevin.common.result.Page;
import com.kevin.common.result.Result;
import com.kevin.system.domain.SysMenu;
import com.kevin.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单权限 信息操作处理
 *
 * @author kevin
 * @date 2020/05/30
 */
@RestController
@RequestMapping("/system/sysMenu")
@Slf4j
@Api(tags = "接口--------菜单管理")
public class SysMenuController {

    @Autowired
    SysMenuService sysMenuService;

    /**
     * 根据角色id 查询菜单
     */
    @ApiOperation("根据角色id 查询菜单")
    @GetMapping("/getMenByRole")
    public Result getMenByRole(
            @ApiParam(value = "角色id") @RequestParam(defaultValue = "1") Integer roleID) {
        List<SysMenu> sysMenus = sysMenuService.selectMenByRole(roleID);
        return Result.success(sysMenus);
    }

    /**
     * 分页查询菜单权限列表
     */
    @ApiOperation("分页查询菜单权限列表")
    @GetMapping("/list")
    @RequiresPermissions("system:sysMenu:list")
    public Result list(SysMenu sysMenu, @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit) {

        PageHelper.startPage(page, limit);
        List<SysMenu> consults = sysMenuService.list(new QueryWrapper<SysMenu>(sysMenu));
        PageInfo<SysMenu> pageInfo = new PageInfo<SysMenu>(consults);
        return Result.success(new Page<SysMenu>(pageInfo.getTotal(), limit, page, consults));
    }


    /**
     * 新增菜单权限
     *
     * @param sysMenu 菜单权限
     * @return 操作结果
     */
    @ApiOperation("新增菜单权限")
    @RequiresPermissions("system:sysMenu:add")
    @PostMapping("/add")
    public Result addSave(@RequestBody SysMenu sysMenu) {
        boolean save = sysMenuService.save(sysMenu);

        if (save) {
            log.info("新增tb_sys_menu表数据为------->" + JSONObject.toJSON(sysMenu) + "成功!");
            return Result.success();
        } else {
            log.info("新增tb_sys_menu表数据为------->" + JSONObject.toJSON(sysMenu) + "失败!");
            return Result.error();
        }

    }

    /**
     * 保存编辑菜单权限
     *
     * @param sysMenu 菜单权限
     * @return 操作结果
     */
    @ApiOperation("保存编辑菜单权限根据id")
    @RequiresPermissions("system:sysMenu:edit")
    @PutMapping("/edit")
    public Result editSave(@RequestBody SysMenu sysMenu) {
        boolean remove = sysMenuService.updateById(sysMenu);
        ;
        if (remove) {
            log.info("更新tb_sys_menu表中数据为------->" + JSONObject.toJSON(sysMenu) + "成功!");
            return Result.success();
        } else {
            log.info("更新tb_sys_menu表中数据为------->" + JSONObject.toJSON(sysMenu) + "失败!");
            return Result.error();
        }
    }

    /**
     * 删除菜单权限根据id
     *
     * @param sysMenu ID
     * @return 操作结果
     */
    @RequiresPermissions("system:sysMenu:remove")
    @DeleteMapping("/remove")
    @ApiOperation("删除菜单权限根据id")
    public Result remove(@RequestBody SysMenu sysMenu) {
        boolean remove = sysMenuService.remove(new UpdateWrapper<SysMenu>(sysMenu));
        if (remove) {
            log.info("删除tb_sys_menu表中数据为------->" + JSONObject.toJSON(sysMenu) + "成功!");
            return Result.success();
        } else {
            log.info("删除tb_sys_menu表中数据为------->" + JSONObject.toJSON(sysMenu) + "失败!");
            return Result.error();
        }
    }
}
