package com.kevin.web.system;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kevin.common.result.Page;
import com.kevin.common.result.Result;
import com.kevin.system.domain.SysRoleMeun;
import com.kevin.system.service.SysRoleMeunService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色菜单 信息操作处理
 *
 * @author kevin
 * @date 2020/06/10
 */
@RestController
@RequestMapping("/system/sysRoleMeun")
@Slf4j
@Api(tags = "接口--------角色菜单关联")
public class SysRoleMeunController {

	@Autowired
	SysRoleMeunService sysRoleMeunService;


	/**
	 *
	 * 分页查询角色菜单列表
	 */
	@ApiOperation("分页查询角色菜单列表")
	@GetMapping("/list")
	@RequiresPermissions("system:sysRoleMeun:list")
	public Result list(SysRoleMeun sysRoleMeun,@RequestParam(defaultValue = "1") Integer page,
												 @RequestParam(defaultValue = "10") Integer limit){

		PageHelper.startPage(page, limit);
		List<SysRoleMeun> consults = sysRoleMeunService.list(new QueryWrapper<SysRoleMeun>(sysRoleMeun));
		PageInfo<SysRoleMeun> pageInfo = new PageInfo<SysRoleMeun>(consults);
		return Result.success(new Page<SysRoleMeun>(pageInfo.getTotal(),limit,page,consults));
	}


	/**
    * 新增角色菜单
    *
    * @param sysRoleMeun 角色菜单
    * @return 操作结果
    */
	@ApiOperation("新增角色菜单")
	//@RequiresPermissions("system:sysRoleMeun:add")
	@PostMapping("/add")
	public Result addSave(@RequestBody SysRoleMeun sysRoleMeun) {
		boolean save = sysRoleMeunService.save(sysRoleMeun);
		if (save){
			log.info("新增tb_sys_role_meun表数据为------->"+JSONObject.toJSON(sysRoleMeun)+"成功!");
			return Result.success();
		}else {
			log.info("新增tb_sys_role_meun表数据为------->"+JSONObject.toJSON(sysRoleMeun)+"失败!");
			return Result.error();
		}

	}

	/**
	* 保存编辑角色菜单
	*
	* @param sysRoleMeun 角色菜单
	* @return 操作结果
	*/
	@ApiOperation("保存编辑角色菜单根据id")
	//@RequiresPermissions("system:sysRoleMeun:edit")
	@PutMapping("/edit")
	public Result editSave(@RequestBody SysRoleMeun sysRoleMeun) {
		boolean remove = sysRoleMeunService.updateById(sysRoleMeun);;
		if (remove){
			log.info("更新tb_sys_role_meun表中数据为------->"+JSONObject.toJSON(sysRoleMeun)+"成功!");
			return Result.success();
		}else {
			log.info("更新tb_sys_role_meun表中数据为------->"+JSONObject.toJSON(sysRoleMeun)+"失败!");
			return Result.error();
		}
	}

	/**
    * 删除角色菜单根据id
    *
    * @param sysRoleMeun ID
    * @return 操作结果
    */
//	@RequiresPermissions("system:sysRoleMeun:remove")
	@DeleteMapping("/remove")
	@ApiOperation("删除角色菜单根据id")
	public Result remove(@RequestBody SysRoleMeun sysRoleMeun ) {
		boolean remove = sysRoleMeunService.remove(new UpdateWrapper<SysRoleMeun>(sysRoleMeun));
		if (remove){
			log.info("删除tb_sys_role_meun表中数据为------->"+JSONObject.toJSON(sysRoleMeun)+"成功!");
			return Result.success();
		}else {
			log.info("删除tb_sys_role_meun表中数据为------->"+JSONObject.toJSON(sysRoleMeun)+"失败!");
			return Result.error();
		}
	}
}
