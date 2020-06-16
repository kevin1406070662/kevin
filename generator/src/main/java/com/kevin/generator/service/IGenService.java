package com.kevin.generator.service;


import com.kevin.generator.domain.ColumnInfo;
import com.kevin.generator.domain.TableInfo;

import java.util.List;

/**
 * 代码生成 服务层
 *
 * @author novel
 * @date 2020/3/25
 */
public interface IGenService {
    /**
     * 查询数据库表信息
     *
     * @param  tableName 表名
     * @return 数据库表列表
     */
    List<TableInfo> selectTableList(String  tableName);

    /**
     * 生成代码
     *
     * @param tableName 表名称
     * @return 数据
     */
    byte[] generatorCode(String tableName);

    /**
     * 批量生成代码
     *
     * @param tableNames 表数组
     * @return 数据
     */
    byte[] generatorCode(String[] tableNames);

    /**
     * 查询表下的字段
     * @param tableName 表名称
     * @return 数据
     */
    List<ColumnInfo>   selectTableColumn(String tableName);

}
