package com.applications.mybatisgen;

import java.io.File;


public class MyBatisGenConst {

    // 包名
    public static final String MAIN_PACKAGE = "com.mini.dal";
    public static final String DO_PACKAGE = "com.mini.dal.model";
    public static final String QUERY_PACKAGE = "com.mini.dal.query";
    public static final String MAPPER_PACKAGE = "com.mini.dal.mapper";
    public static final String MAPPER_EXT_PACKAGE = "com.mini.dal.mapper.ext";
    public static final String TABLE_PREFIX = "mini_";//系统所有表名前缀
    public static final String TABLE_PREFIX_MHC = "b2b_";//系统所有买好车表名前缀
    public static final String QUERY_PREFIX = "Query";
    public static final String MAPPER_SUFFIX = "Mapper";
    public static final String MAPPER_EXT_SUFFIX = "ExtMapper";
    public static final String DO_SUFFIX = "DO";

    // 工作目录
    public static final String WORK_DIR = System.getProperty("user.dir") + File.separator;
    // mapper xml 输出目录
    public static final String MAPPER_XML_DIR = WORK_DIR + "mini-dal/src/main/resources/mybatis/mapper/";
    // mapper-ext xml 输出目录
    public static final String MAPPER_EXT_XML_DIR = WORK_DIR + "mini-dal/src/main/resources/mybatis/mapper/ext/";
    // do/model 输出目录
    public static final String MAPPER_DO_DIR = WORK_DIR + "mini-dal/src/main/java/com/mini/dal/model";

    // query/query 输出目录
    public static final String MAPPER_QUERY_DIR = WORK_DIR + "mini-dal/src/main/java/com/mini/dal/query";

    // mapper java 输出目录
    public static final String MAPPER_JAVA_DIR = WORK_DIR + "mini-dal/src/main/java/com/mini/dal/mapper";
    // mapper-ext java 输出目录
    public static final String MAPPER_EXT_JAVA_DIR = WORK_DIR + "mini-dal/src/main/java/com/mini/dal/mapper/ext";
    // java数据对象类模板
    public static final String DO_TEMPLATE = ClassLoader.getSystemResource("template/do.txt").getPath();//  "resources/template/do.txt";
    // query 模板
    public static final String QUERY_TEMPLATE = ClassLoader.getSystemResource("template/query.txt").getPath();
    // sqlmap模板
    public static final String SQLMAP_TEMPLATE = ClassLoader.getSystemResource("template/sqlmap.txt").getPath();
    // mapper模板
    public static final String MAPPER_TEMPLATE = ClassLoader.getSystemResource("template/mapper.txt").getPath();
    // mapper-ext模板
    public static final String MAPPER_EXT_TEMPLATE = ClassLoader.getSystemResource("template/mapper-ext.txt").getPath();
    // sqlmap-ext模板
    public static final String SQLMAP_EXT_TEMPLATE = ClassLoader.getSystemResource("template/sqlmap-ext.txt").getPath();

    public static final String COMMON_COLUMN_STR = "";
//    public static final String COMMON_COLUMN_STR = "id,gmt_create,gmt_modified,";

    // jdbc result set metadata collumn name
    public static final String RSMD_COLUMN_NAME = "rsmdColumnName";
    public static final String RSMD_COLUMN_CLASS_NAME = "columnClassName";
    public static final String RSMD_COLUMN_TYPE_NAME = "columnTypeName";
    public static final String RSMD_COLUMN_PRECISION = "Precision";
    public static final String RSMD_COLUMN_SCALE = "Scale";

    // velocity param
    public static final String VP_LIST = "list";
    public static final String VP_QUERY_PREFIX = "queryPrefix";
    public static final String VP_DO_SUFFIX = "doSuffix";
    public static final String VP_MAPPER_SUFFIX = "mapperSuffix";
    public static final String VP_MAPPER_EXT_SUFFIX = "extMapperSuffix";
    public static final String VP_CLASS_NAME = "className";
    public static final String VP_MAIN_PACKAGE = "mainPackage";
    public static final String VP_DO_PACKAGE = "doPackage";
    public static final String VP_QUERY_PACKAGE = "queryPackage";
    public static final String VP_MAPPER_PACKAGE = "mapperPackage";
    public static final String VP_MAPPER_EXT_PACKAGE = "mapperExtPackage";
    public static final String VP_JAVA_TYPE = "javaType";
    public static final String VP_PROP_NAME = "propName";
    public static final String VP_GET_METHOD = "getMethod";
    public static final String VP_SET_METHOD = "setMethod";

    public static final String VP_COLUMN_NAME = "columnName";
    public static final String VP_TABLE_NAME = "tableName";
    public static final String VP_JDBC_TYPE = "jdbcType";
    public static final String VP_COLS = "cols";
    public static final String VP_COLUMN_PK_NAME = "columnPkName";
    public static final String VP_COLS_WITHOUT_COMMON_COLUMNS = "colsWithoutCommColumns";
    public static final String VP_SERIAL_VERSION_UID = "serialVersionUID";
    public static final String VP_SERIAL_VERSION_UID2 = "serialVersionUID2";

    //分库分表 表后缀regex
    public static final String SHARDING_SUFFIX_REG = "_[\\d]{4}";
}
