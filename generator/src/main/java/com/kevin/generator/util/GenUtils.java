package com.kevin.generator.util;

import com.kevin.common.utils.DateUtils;
import com.kevin.common.utils.StringUtils;
import com.kevin.generator.constant.CommonConstant;
import com.kevin.generator.constant.CommonMap;
import com.kevin.generator.domain.ColumnInfo;
import com.kevin.generator.domain.TableInfo;
import com.kevin.generator.config.GenConfig;
import org.apache.velocity.VelocityContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成器 工具类
 *
 * @author novel
 * @date 2020/3/25
 */
public class GenUtils {
    /**
     * 项目空间路径
     */
    private static final String projectPath = "main/java/com/kevin";

    /**
     * mybatis空间路径
     */
    private static final String myBatisPath = "main/resources/mapper";

    /**
     * vue空间路径
     */
    private static final String vuePath = "vue/view";

    /**
     * api空间路径
     */
    private static final String apiPath = "vue/api";

    /**
     * 设置列信息
     */
    public static List<ColumnInfo> transColums(List<ColumnInfo> columns) {
        // 列信息
        List<ColumnInfo> columnList = new ArrayList<>();
        for (ColumnInfo column : columns) {
            // 列名转换成Java属性名
            String attrName = StringUtils.convertToCamelCase(column.getColumnName());
            column.setAttrName(attrName);
            column.setAttrname(StringUtils.uncapitalize(attrName));

            // 列的数据类型，转换成Java类型
            String attrType = CommonMap.javaTypeMap.get(column.getDataType());
            column.setAttrType(attrType);

            columnList.add(column);
        }
        return columnList;
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static VelocityContext getVelocityContext(TableInfo table) {
        // java对象数据传递到模板文件vm
        VelocityContext velocityContext = new VelocityContext();
        String packageName = GenConfig.getPackageName();
        velocityContext.put("tableName", table.getTableName());
        // TODO 生成信息待完善
        velocityContext.put("apiModel", packageName.replace(".","-"));
        velocityContext.put("tableComment", replaceKeyword(table.getTableComment()));
        velocityContext.put("primaryKey", table.getSetPrimaryKey());
        velocityContext.put("tableNameDB", table.getTableNameDB());
        velocityContext.put("className", table.getClassName());
        velocityContext.put("classname", table.getTableName());
        velocityContext.put("moduleName", GenUtils.getModuleName(packageName));
        velocityContext.put("columns", table.getColumns());
        velocityContext.put("package", packageName);
        velocityContext.put("author", GenConfig.getAuthor());
        velocityContext.put("datetime", DateUtils.getGenDate());
        velocityContext.put("hasDate", isHasDate(table.getColumns()));
        return velocityContext;
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add("vm/java/domain.java.vm");
        templates.add("vm/java/Mapper.java.vm");
        templates.add("vm/java/Service.java.vm");
        templates.add("vm/java/ServiceImpl.java.vm");
        templates.add("vm/java/Controller.java.vm");
        templates.add("vm/xml/Mapper.xml.vm");
        templates.add("vm/vue/index.vue.vm");
        templates.add("vm/vue/index.ts.vm");
        templates.add("vm/sql/sql.vm");
        return templates;
    }

    /**
     * 表名转换成Java类名
     *
     * @param tableName 表名
     * @return java类名
     */
    public static String tableToJava(String tableName) {
        if (CommonConstant.AUTO_REOMVE_PRE.equals(GenConfig.getAutoRemovePre())) {
            tableName = tableName.substring(tableName.indexOf("_") + 1);
        }
        if (StringUtils.isNotEmpty(GenConfig.getTablePrefix())) {
            tableName = tableName.replace(GenConfig.getTablePrefix(), "");
        }
        return StringUtils.convertToCamelCase(tableName);
    }

    /**
     * 获取文件名
     *
     * @param template   模板
     * @param table      表
     * @param moduleName 模块
     * @return 文件名
     */
    public static String getFileName(String template, TableInfo table, String moduleName) {
        // TODO 生成信息待完善
//        // 小写类名
        String classname = table.getTableName();
        // 大写类名
        String className = table.getClassName();

        String javaPath = projectPath + "/" + moduleName + "/";
        String mybatisPath = myBatisPath + "/" + className;

    /*    if (StringUtils.isNotEmpty(classname)) {
            javaPath += classname.replace(".", "/") + "/";
        }*/

        if (template.contains("domain.java.vm")) {
            return javaPath + "domain" + "/" + className + ".java";
        }

        if (template.contains("Mapper.java.vm")) {
            return javaPath + "mapperxml" + "/" + className + "Mapper.java";
        }

        if (template.contains("Service.java.vm")) {
            return javaPath + "service" + "/" + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return javaPath + "service" + "/impl/" + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm")) {
            return javaPath + "controller" + "/" + className + "Controller.java";
        }

        if (template.contains("Mapper.xml.vm")) {
            return mybatisPath + "Mapper.xml";
        }

        if (template.contains("index.vue.vm")) {
            return vuePath + "/" + className + ".vue";
        }
        if (template.contains("index.ts.vm")) {
            return apiPath + "/" + classname + ".ts";
        }
        if (template.contains("sql.vm")) {
            return classname + "Menu.sql";
        }
        return null;
    }

    /**
     * 获取模块名
     *
     * @param packageName 包名
     * @return 模块名
     */
    public static String getModuleName(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        int nameLength = packageName.length();
        return StringUtils.substring(packageName, lastIndex + 1, nameLength);
    }

    /**
     * 判断是否存在date类型
     *
     * @param columnInfoList 列信息
     * @return true：存在，false：不存在
     */
    public static boolean isHasDate(List<ColumnInfo> columnInfoList) {
        for (ColumnInfo columnInfo : columnInfoList) {
            if ("Date".equalsIgnoreCase(columnInfo.getAttrType())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 替换数据库表的表名
     *
     * @param keyword 表名
     * @return 替换后的名称
     */
    public static String replaceKeyword(String keyword) {
        return keyword.replaceAll("(?:表|信息)", "");
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.convertToCamelCase("user_name"));
        System.out.println(replaceKeyword("岗位信息表"));
        System.out.println(getModuleName("com.ruoyi.project.system"));
    }
}
