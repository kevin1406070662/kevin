package com.kevin.generator.mapper;

import com.kevin.generator.domain.ColumnInfo;
import com.kevin.generator.domain.TableInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 代码生成 数据层
 *
 * @author kevin
 * @date 2020/3/25
 */

public interface GenMapper {
    /**
     * 查询数据库表信息
     *
     * @param tableName 表信息 tableName
     * @return 数据库表列表
     */
    List<TableInfo> selectTableList(@Param("tableName") String tableName);

    /**
     * 根据表名称查询信息
     *
     * @param tableName 表名称
     * @return 表信息
     */
    TableInfo selectTableByName(@Param("tableName") String tableName);

    /**
     * 根据表名称查询列信息
     *
     * @param tableName 表名称
     * @return 列信息
     */
    List<ColumnInfo> selectTableColumnsByName(@Param("tableName")String tableName);
}
