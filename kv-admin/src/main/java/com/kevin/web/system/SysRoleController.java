package com.kevin.web.system;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kevin.common.result.Page;
import com.kevin.common.result.Result;
import com.kevin.system.domain.SysRole;
import com.kevin.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色 信息操作处理
 *
 * @author kevin
 * @date 2020/05/30
 */
@RestController
@RequestMapping("/system/sysRole")
@Slf4j
@Api(tags = "接口--------角色管理")
public class SysRoleController {

	@Autowired
	SysRoleService sysRoleService;


	/**
	 *
	 * 分页查询角色列表
	 */
	@ApiOperation("分页查询角色列表")
	@GetMapping("/list")
	@RequiresPermissions("system:sysRole:list")
	public Result list(SysRole sysRole,@RequestParam(defaultValue = "1") Integer page,
												 @RequestParam(defaultValue = "10") Integer limit){

		PageHelper.startPage(page, limit);
		List<SysRole> consults = sysRoleService.list(new QueryWrapper<SysRole>(sysRole));
		PageInfo<SysRole> pageInfo = new PageInfo<SysRole>(consults);
		return Result.success(new Page<SysRole>(pageInfo.getTotal(),limit,page,consults));
	}


	/**
    * 新增角色
    *
    * @param sysRole 角色
    * @return 操作结果
    */
	@ApiOperation("新增角色")
	@RequiresPermissions("system:sysRole:add")
	@PostMapping("/add")
	public Result addSave(@RequestBody SysRole sysRole) {
		boolean save = sysRoleService.save(sysRole);
		if (save){
			log.info("新增tb_sys_role表数据为------->"+JSONObject.toJSON(sysRole)+"成功!");
			return Result.success();
		}else {
			log.info("新增tb_sys_role表数据为------->"+JSONObject.toJSON(sysRole)+"失败!");
			return Result.error();
		}

	}

	/**
	* 保存编辑角色
	*
	* @param sysRole 角色
	* @return 操作结果
	*/
	@ApiOperation("保存编辑角色根据id")
	@RequiresPermissions("system:sysRole:edit")
	@PutMapping("/edit")
	public Result editSave(@RequestBody SysRole sysRole) {
		boolean remove = sysRoleService.updateById(sysRole);;
		if (remove){
			log.info("更新tb_sys_role表中数据为------->"+JSONObject.toJSON(sysRole)+"成功!");
			return Result.success();
		}else {
			log.info("更新tb_sys_role表中数据为------->"+JSONObject.toJSON(sysRole)+"失败!");
			return Result.error();
		}
	}

	/**
    * 删除角色根据id
    *
    * @param sysRole ID
    * @return 操作结果
    */
	@RequiresPermissions("system:sysRole:remove")
	@DeleteMapping("/remove")
	@ApiOperation("删除角色根据id")
	public Result remove(@RequestBody SysRole sysRole ) {
		boolean remove = sysRoleService.remove(new UpdateWrapper<SysRole>(sysRole));
		if (remove){
			log.info("删除tb_sys_role表中数据为------->"+JSONObject.toJSON(sysRole)+"成功!");
			return Result.success();
		}else {
			log.info("删除tb_sys_role表中数据为------->"+JSONObject.toJSON(sysRole)+"失败!");
			return Result.error();
		}
	}
}
