package com.kevin.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色菜单表 tb_sys_role_meun
 *
 * @author kevin
 * @date 2020/06/10
 */
@Data
@TableName(value = "tb_sys_role_meun")
@ApiModel(value = "com-kevin-system-domain-SysRoleMeun")
public class SysRoleMeun  {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色id
	 */
    @TableField(value = "roleID")
    @ApiModelProperty(value = "角色id")
	private Integer roleID;
	/**
	 * 菜单id
	 */
    @TableField(value = "meunID")
    @ApiModelProperty(value = "菜单id")
	private Integer meunID;

}
