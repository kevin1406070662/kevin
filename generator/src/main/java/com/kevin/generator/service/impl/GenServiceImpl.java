package com.kevin.generator.service.impl;

import com.kevin.common.exception.base.BaseException;
import com.kevin.common.utils.StringUtils;
import com.kevin.generator.domain.ColumnInfo;
import com.kevin.generator.domain.TableInfo;
import com.kevin.generator.mapper.GenMapper;

import com.kevin.generator.config.GenConfig;
import com.kevin.generator.service.IGenService;
import com.kevin.generator.util.GenUtils;
import com.kevin.generator.util.VelocityInitializer;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成 服务层处理
 *
 * @author novel
 * @date 2020/3/25
 */
@Service("genService")
public class GenServiceImpl implements IGenService {
    @Resource
    private  GenMapper genMapper;

    /**
     * 查询ry数据库表信息
     *
     * @param tableName 表名称
     * @return 数据库表列表
     */
    @Override
    public List<TableInfo> selectTableList(String  tableName) {
        return genMapper.selectTableList(tableName);
    }

    /**
     * 生成代码
     *
     * @param tableName 表名称
     * @return 数据
     */
    @Override
    public byte[] generatorCode(String tableName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        // 查询表信息
        TableInfo table = genMapper.selectTableByName(tableName);
        // 查询列信息
        List<ColumnInfo> columns = genMapper.selectTableColumnsByName(tableName);
        // 生成代码
        generatorCode(table, columns, zip);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 批量生成代码
     *
     * @param tableNames 表数组
     * @return 数据
     */
    @Override
    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames) {
            // 查询表信息
            TableInfo table = genMapper.selectTableByName(tableName);
            // 查询列信息
            List<ColumnInfo> columns = genMapper.selectTableColumnsByName(tableName);
            // 生成代码
            generatorCode(table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    @Override
    public List<ColumnInfo> selectTableColumn(String tableName) {
        return genMapper.selectTableColumnsByName(tableName);
    }

    /**
     * 生成代码
     */
    public void generatorCode(TableInfo table, List<ColumnInfo> columns, ZipOutputStream zip) {
        table.setTableNameDB(table.getTableName());
        // 表名转换成Java属性名
        String className = GenUtils.tableToJava(table.getTableName());
        table.setClassName(className);
        //第一个字母大写
        table.setTableName(className);
        //第一个字母小写
        table.setTableName(StringUtils.uncapitalize(className));
        // 列信息
        table.setColumns(GenUtils.transColums(columns));
        for (ColumnInfo columnInfo : columns) {
            if ("PRI".equals(columnInfo.getColumnKey())){
                // 设置主键
                table.setSetPrimaryKey(columnInfo.getColumnName());
            }
        }
        //初始化
        VelocityInitializer.initVelocity();
        //包名
        String packageName = GenConfig.getPackageName();
        //模块名
        String moduleName = GenUtils.getModuleName(packageName);
        //获取模板信息
        VelocityContext context = GenUtils.getVelocityContext(table);

        // 获取模板列表
        List<String> templates = GenUtils.getTemplates();
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);
            try {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(Objects.requireNonNull(GenUtils.getFileName(template, table, moduleName))));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new BaseException("渲染模板失败，表名：" + table.getTableName(), e.getMessage());
            }
        }
    }
}
