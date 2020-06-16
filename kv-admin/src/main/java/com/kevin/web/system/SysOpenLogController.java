package com.kevin.web.system;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kevin.common.annotation.Log;
import com.kevin.common.enums.BusinessType;
import com.kevin.common.result.Page;
import com.kevin.common.result.Result;
import com.kevin.system.domain.SysUser;
import com.kevin.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.kevin.system.domain.SysOpenLog;
import com.kevin.system.service.SysOpenLogService;
import java.util.List;
/**
 * 操作日志 信息操作处理
 * 
 * @author kevin
 * @date 2020/05/30
 */
@RestController
@RequestMapping("/system/sysOpenLog")
@Slf4j
@Api(tags = "接口--------操作日志")
public class SysOpenLogController {

	@Autowired
	SysOpenLogService sysOpenLogService;
    @Autowired
	SysUserService sysUserService;

	/**
	 *
	 * 分页查询操作日志列表
	 */
	@ApiOperation("分页查询操作日志列表")
	@GetMapping("/list")
	@RequiresPermissions("system:sysOpenLog:list")
	public Result list(SysOpenLog sysOpenLog,@RequestParam(defaultValue = "1") Integer page,
												 @RequestParam(defaultValue = "10") Integer limit){

		PageHelper.startPage(page, limit);
		List<SysOpenLog> consults = sysOpenLogService.list(new QueryWrapper<SysOpenLog>(sysOpenLog));
		PageInfo<SysOpenLog> pageInfo = new PageInfo<SysOpenLog>(consults);
		return Result.success(new Page<SysOpenLog>(pageInfo.getTotal(),limit,page,consults));
	}


	/**
    * 新增操作日志
    *
    * @param sysOpenLog 操作日志
    * @return 操作结果
    */
	@ApiOperation("新增操作日志")
	@RequiresPermissions("system:sysOpenLog:add")
	@PostMapping("/add")
	public Result addSave(@RequestBody SysOpenLog sysOpenLog) {
		boolean save = sysOpenLogService.save(sysOpenLog);
		if (save){
			log.info("新增tb_sys_open_log表数据为------->"+ JSONObject.toJSON(sysOpenLog)+"成功!");
			return Result.success();
		}else {
			log.info("新增tb_sys_open_log表数据为------->"+JSONObject.toJSON(sysOpenLog)+"失败!");
			return Result.error();
		}

	}

	/**
	* 保存编辑操作日志
	*
	* @param sysOpenLog 操作日志
	* @return 操作结果
	*/
	@ApiOperation("保存编辑操作日志根据id")
	@RequiresPermissions("system:sysOpenLog:edit")
	@PutMapping("/edit")
	public Result editSave(@RequestBody SysOpenLog sysOpenLog) {
		boolean remove = sysOpenLogService.updateById(sysOpenLog);;
		if (remove){
			log.info("更新tb_sys_open_log表中数据为------->"+JSONObject.toJSON(sysOpenLog)+"成功!");
			return Result.success();
		}else {
			log.info("更新tb_sys_open_log表中数据为------->"+JSONObject.toJSON(sysOpenLog)+"失败!");
			return Result.error();
		}
	}

	/**
    * 删除操作日志根据id
    *
    * @param sysOpenLog
    * @return 操作结果
    */
	@RequiresPermissions("system:sysOpenLog:remove")
	@DeleteMapping("/remove")
	@ApiOperation("删除操作日志根据id")
	public Result remove(@RequestBody SysOpenLog sysOpenLog ) {
		boolean remove = sysOpenLogService.remove(new UpdateWrapper<SysOpenLog>(sysOpenLog));
		if (remove){
			log.info("删除tb_sys_open_log表中数据为------->"+JSONObject.toJSON(sysOpenLog)+"成功!");
			return Result.success();
		}else {
			log.info("删除tb_sys_open_log表中数据为------->"+JSONObject.toJSON(sysOpenLog)+"失败!");
			return Result.error();
		}
	}
	@PostMapping("addUser")
	@ApiOperation("新增系统用户")
	@Log(title = "新增系统用户", businessType = BusinessType.INSERT)
	public Result addUser(@RequestBody SysUser sysUser) {
		sysUser.setSalt(RandomUtil.randomString(10));
		// 获取盐值，即用id
		ByteSource salt = ByteSource.Util.bytes(sysUser.getSalt());
		//将原始密码加盐（上面生成的盐），并且用md5算法加密3次，将最后结果存入数据库中
		/*
		 * MD5加密：
		 * 使用SimpleHash类对原始密码进行加密。
		 * 第一个参数代表使用MD5方式加密
		 * 第二个参数为原始密码
		 * 第三个参数为盐值，即用户名
		 * 第四个参数为加密次数
		 * 最后用toHex()方法将加密后的密码转成String
		 * */
		String pwd = new SimpleHash("MD5", sysUser.getAccount(), salt, 3).toHex();
		sysUser.setPassword(pwd);
		boolean save = sysUserService.save(sysUser);
		if (save){
			log.info("新增表数据为------->"+JSONObject.toJSON(sysUser)+"成功!");
			return Result.success();
		}else {
			log.info("新增表数据为------->"+JSONObject.toJSON(sysUser)+"失败!");
			return Result.error();
		}
	}
}
