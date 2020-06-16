package com.kevin.web.system;

import io.swagger.annotations.ApiOperation;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kevin.system.domain.SysUser;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kevin.common.result.Page;
import com.kevin.common.result.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.kevin.system.service.SysUserService;

import java.util.List;

/**
 * 系统用户 信息操作处理
 *
 * @author kevin
 * @date 2020/05/30
 */
@RestController
@RequestMapping("/system/sysUser")
@Slf4j
@Api(tags = "接口--------系统用户管理")
public class SysUserController {

	@Autowired
	SysUserService sysUserService;

	/**
	 *
	 * 分页查询系统用户列表
	 */
	@ApiOperation("分页查询系统用户列表")
	@GetMapping("/list")
	@RequiresPermissions("system:sysUser:list")
	public Result list(SysUser sysUser,@RequestParam(defaultValue = "1") Integer page,
					                   @RequestParam(defaultValue = "10") Integer limit){
		PageHelper.startPage(page, limit);
		List<SysUser> consults = sysUserService.list(new QueryWrapper<SysUser>(sysUser));
		PageInfo<SysUser> pageInfo = new PageInfo<SysUser>(consults);
		return Result.success(new Page<SysUser>(pageInfo.getTotal(),limit,page,consults));
	}


	/**
    * 新增系统用户
    *
    * @param sysUser 系统用户
    * @return 操作结果
    */
	@ApiOperation("新增系统用户")
	@RequiresPermissions("system:sysUser:add")
	@PostMapping("/add")
	public Result addSave(@RequestBody SysUser sysUser) {
		boolean save = sysUserService.save(sysUser);
		if (save){
			log.info("新增tb_sys_user表数据为------->"+JSONObject.toJSON(sysUser)+"成功!");
			return Result.success();
		}else {
			log.info("新增tb_sys_user表数据为------->"+JSONObject.toJSON(sysUser)+"失败!");
			return Result.error();
		}

	}

	/**
	* 保存编辑系统用户
	*
	* @param sysUser 系统用户
	* @return 操作结果
	*/
	@ApiOperation("保存编辑系统用户根据id")
	@RequiresPermissions("system:sysUser:edit")
	@PutMapping("/edit")
	public Result editSave(@RequestBody SysUser sysUser) {
		boolean remove = sysUserService.updateById(sysUser);;
		if (remove){
			log.info("更新tb_sys_user表中数据为------->"+JSONObject.toJSON(sysUser)+"成功!");
			return Result.success();
		}else {
			log.info("更新tb_sys_user表中数据为------->"+JSONObject.toJSON(sysUser)+"失败!");
			return Result.error();
		}
	}

	/**
    * 删除系统用户根据id
    *
    * @param sysUser ID
    * @return 操作结果
    */
	@RequiresPermissions("system:sysUser:remove")
	@DeleteMapping("/remove")
	@ApiOperation("删除系统用户根据id")
	public Result remove(@RequestBody SysUser sysUser ) {
		boolean remove = sysUserService.remove(new UpdateWrapper<SysUser>(sysUser));
		if (remove){
			log.info("删除tb_sys_user表中数据为------->"+JSONObject.toJSON(sysUser)+"成功!");
			return Result.success();
		}else {
			log.info("删除tb_sys_user表中数据为------->"+JSONObject.toJSON(sysUser)+"失败!");
			return Result.error();
		}
	}
}
