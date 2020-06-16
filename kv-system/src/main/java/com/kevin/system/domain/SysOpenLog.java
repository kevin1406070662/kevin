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
 * 操作日志表 tb_sys_open_log
 * 
 * @author kevin
 * @date 2020/05/30
 */
@Data
@TableName(value = "tb_sys_open_log")
@ApiModel(value = "com-kevin-system-domain-SysOpenLog")
public class SysOpenLog  {
	private static final long serialVersionUID = 1L;
	
	/**
	 * id 自增
	 */
	@TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id 自增")
	private Integer id;
	/**
	 * 操作标题
	 */
    @TableField(value = "title")
    @ApiModelProperty(value = "操作标题")
	private String title;
	/**
	 * 业务类型（0其它 1新增 2修改 3删除） 操作类型
	 */
    @TableField(value = "operationType")
    @ApiModelProperty(value = "业务类型（0其它 1新增 2修改 3删除） 操作类型")
	private Integer operationType;
	/**
	 * 方法名称
	 */
    @TableField(value = "method")
    @ApiModelProperty(value = "方法名称")
	private String method;
	/**
	 * 请求方式
	 */
    @TableField(value = "requestMethod")
    @ApiModelProperty(value = "请求方式")
	private String requestMethod;
	/**
	 * 操作人员
	 */
    @TableField(value = "operName")
    @ApiModelProperty(value = "操作人员")
	private String operName;
	/**
	 * 请求URL
	 */
    @TableField(value = "operUrl")
    @ApiModelProperty(value = "请求URL")
	private String operUrl;
	/**
	 * 请求者ip
	 */
    @TableField(value = "operIp")
    @ApiModelProperty(value = "请求者ip")
	private String operIp;
	/**
	 * 请求地址
	 */
    @TableField(value = "operLocation")
    @ApiModelProperty(value = "请求地址")
	private String operLocation;
	/**
	 * 请求参数
	 */
    @TableField(value = "operParam")
    @ApiModelProperty(value = "请求参数")
	private String operParam;
	/**
	 * 返回参数
	 */
    @TableField(value = "jsonResult")
    @ApiModelProperty(value = "返回参数")
	private String jsonResult;
	/**
	 * 操作状态（0正常 1异常）
	 */
    @TableField(value = "status")
    @ApiModelProperty(value = "操作状态（0正常 1异常）")
	private Integer status;
	/**
	 * 错误信息
	 */
    @TableField(value = "errorMsg")
    @ApiModelProperty(value = "错误信息")
	private String errorMsg;
	/**
	 * 处理耗时
	 */
    @TableField(value = "processingTime")
    @ApiModelProperty(value = "处理耗时")
	private Long processingTime;
	/**
	 * 操作时间
	 */
    @TableField(value = "createTime")
    @ApiModelProperty(value = "操作时间")
	private Date createTime;

}
