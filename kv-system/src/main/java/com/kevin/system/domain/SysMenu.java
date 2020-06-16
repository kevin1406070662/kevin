package com.kevin.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**
 * 菜单权限表 tb_sys_menu
 *
 * @author kevin
 * @date 2020/05/30
 *
 * @author kevin
 * @date 2020/06/10
 */
@Data
@TableName(value = "tb_sys_menu")
@ApiModel(value = "com-kevin-system-domain-SysMenu")
public class SysMenu {
	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "菜单ID")
	private Integer id;
	/**
	 * 菜单名称
	 */
    @TableField(value = "menuName")
    @ApiModelProperty(value = "菜单名称")
	private String menuName;
	/**
	 * 父菜单ID
	 */
    @TableField(value = "parentID")
    @ApiModelProperty(value = "父菜单ID")
	private Integer parentID;
	/**
	 * 显示顺序
	 */
    @TableField(value = "orderNum")
    @ApiModelProperty(value = "显示顺序")
	private Integer orderNum;
	/**
	 * 请求地址
	 */
    @TableField(value = "url")
    @ApiModelProperty(value = "请求地址")
	private String url;
	/**
	 * 菜单类型（M目录 C菜单 F按钮）
	 */
    @TableField(value = "menuType")
    @ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮）")
	private String menuType;
	/**
	 * 菜单状态（0显示 1隐藏）
	 */
    @TableField(value = "visible")
    @ApiModelProperty(value = "菜单状态（0显示 1隐藏）")
	private String visible;
	/**
	 * 权限标识
	 */
    @TableField(value = "perms")
    @ApiModelProperty(value = "权限标识")
	private String perms;
	/**
	 * 组件路径
	 */
    @TableField(value = "component")
    @ApiModelProperty(value = "组件路径")
	private String component;
	/**
	 * 默认跳转地址
	 */
    @TableField(value = "redirect")
    @ApiModelProperty(value = "默认跳转地址")
	private String redirect;
	/**
	 * 菜单图标
	 */
    @TableField(value = "icon")
    @ApiModelProperty(value = "菜单图标")
	private String icon;
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
