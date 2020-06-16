package com.kevin.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
 * 系统用户表
 */
@ApiModel(value = "com-kevin-system-domain-SysUser")
@Data
@TableName(value = "tb_sys_user")
public class SysUser  {
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "")
    private String id;

    /**
     * 用户名
     */
    @TableField(value = "userName")
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 账号
     */
    @TableField(value = "account")
    @ApiModelProperty(value = "账号")
    private String account;

    /**
     * 密码
     */
    @TableField(value = "password")
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 0.男 1.女
     */
    @TableField(value = "sex")
    @ApiModelProperty(value = "0.男 1.女")
    private Integer sex;

    /**
     * 部门id
     */
    @TableField(value = "deptid")
    @ApiModelProperty(value = "部门id")
    private Integer deptid;

    /**
     * 角色id
     */
    @TableField(value = "roleid")
    @ApiModelProperty(value = "角色id")
    private Integer roleid;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 头像
     */
    @TableField(value = "portrait")
    @ApiModelProperty(value = "头像")
    private String portrait;

    /**
     * 盐值
     */
    @TableField(value = "salt")
    @ApiModelProperty(value = "盐值")
    private String salt;

    /**
     * 创建者
     */
    @TableField(value = "createBy")
    @ApiModelProperty(value = "创建者")
    private String createby;

    /**
     * 创建时间
     */
    @TableField(value = "createTime")
    @ApiModelProperty(value = "创建时间")
    private Date createtime;

    /**
     * 更新者
     */
    @TableField(value = "updateBy")
    @ApiModelProperty(value = "更新者")
    private String updateby;

    /**
     * 更新时间
     */
    @TableField(value = "updateTime")
    @ApiModelProperty(value = "更新时间")
    private Date updatetime;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;
}