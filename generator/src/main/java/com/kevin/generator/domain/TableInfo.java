package com.kevin.generator.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 数据库表
 *
 * @author kevin
 * @date 2020/5/28
 */
@Data
@ApiModel(value = "com-kevin-system-generator-TableInfo")
public class TableInfo  {


    /**
     * 表名称
     */
    @ApiModelProperty(value = "表名称")
    private String tableName;

    /**
     * 表描述
     */
    @ApiModelProperty(value = "表描述")
    private String tableComment;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "主键")
    private String setPrimaryKey;

    private List<ColumnInfo> Columns;

    /**
     * 表名称
     */
    @ApiModelProperty(value = "数据库表名称")
    private String tableNameDB;
    /**
     * 类名
     */
    @ApiModelProperty(value = "类名")
    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTableNameDB() {
        return tableNameDB;
    }

    public void setTableNameDB(String tableNameDB) {
        this.tableNameDB = tableNameDB;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getSetPrimaryKey() {
        return setPrimaryKey;
    }

    public void setSetPrimaryKey(String setPrimaryKey) {
        this.setPrimaryKey = setPrimaryKey;
    }

    public List<ColumnInfo> getColumns() {
        return Columns;
    }

    public void setColumns(List<ColumnInfo> columns) {
        Columns = columns;
    }


}
