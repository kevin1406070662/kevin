package com.kevin.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**
 * 角色表 tb_sys_role
 * 
 * @author kevin
 * @date 2020/05/30
 */
@Data
@TableName(value = "tb_sys_role")
@ApiModel(value = "com-kevin-system-domain-SysRole")
public class SysRole {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 角色ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "角色ID")
	private Integer id;
	/**
	 * 角色名称
	 */
    @TableField(value = "roleName")
    @ApiModelProperty(value = "角色名称")
	private String roleName;
	/**
	 * 角色权限字符串
	 */
    @TableField(value = "roleKey")
    @ApiModelProperty(value = "角色权限字符串")
	private String roleKey;
	/**
	 * 显示顺序
	 */
    @TableField(value = "roleSort")
    @ApiModelProperty(value = "显示顺序")
	private Integer roleSort;
	/**
	 * 角色状态（0正常 1停用）
	 */
    @TableField(value = "status")
    @ApiModelProperty(value = "角色状态（0正常 1停用）")
	private String status;
	/**
	 * 删除标志（0代表存在 1代表删除）
	 */
    @TableField(value = "delFlag")
    @ApiModelProperty(value = "删除标志（0代表存在 1代表删除）")
	private String delFlag;
	/**
	 * 创建者
	 */
    @TableField(value = "createBy")
    @ApiModelProperty(value = "创建者")
	private String createBy;
	/**
	 * 创建时间
	 */
    @TableField(value = "createTime")
    @ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**
	 * 更新者
	 */
    @TableField(value = "updateBy")
    @ApiModelProperty(value = "更新者")
	private String updateBy;
	/**
	 * 更新时间
	 */
    @TableField(value = "updateTime")
    @ApiModelProperty(value = "更新时间")
	private Date updateTime;
	/**
	 * 备注
	 */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
	private String remark;

}
