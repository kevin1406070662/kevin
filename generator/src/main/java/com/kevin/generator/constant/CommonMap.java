package com.kevin.generator.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用Map数据
 *
 * @author kevin
 * @date 2020/5/25
 */
public class CommonMap {
    /**
     * 状态编码转换
     */
    public static Map<String, String> javaTypeMap = new HashMap<>(19);

    static {
        initJavaTypeMap();
    }

    /**
     * 返回状态映射
     */
    public static void initJavaTypeMap() {
        javaTypeMap.put("tinyint", "Integer");
        javaTypeMap.put("smallint", "Integer");
        javaTypeMap.put("mediumint", "Integer");
        javaTypeMap.put("int", "Integer");
        javaTypeMap.put("bigint", "Long");
        javaTypeMap.put("integer", "integer");
        javaTypeMap.put("float", "Float");
        javaTypeMap.put("double", "Double");
        javaTypeMap.put("decimal", "BigDecimal");
        javaTypeMap.put("bit", "Boolean");
        javaTypeMap.put("char", "String");
        javaTypeMap.put("varchar", "String");
        javaTypeMap.put("tinytext", "String");
        javaTypeMap.put("text", "String");
        javaTypeMap.put("mediumtext", "String");
        javaTypeMap.put("longtext", "String");
        javaTypeMap.put("date", "Date");
        javaTypeMap.put("datetime", "Date");
        javaTypeMap.put("timestamp", "Date");
        javaTypeMap.put("json", "String");
    }
}
