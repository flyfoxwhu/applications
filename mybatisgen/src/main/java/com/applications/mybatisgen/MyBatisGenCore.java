package com.applications.mybatisgen;

import com.applications.common.utils.ChangeUtil;
import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

public class MyBatisGenCore {

    /**
     * 根据表名获取字段信息
     *
     * @param cn
     * @param table
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> getColInfoList(Connection cn, String table) throws Exception {
        String sql = "select * from " + table + " where 1>2";
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = cn.createStatement();
            rs = stmt.executeQuery(sql);
            // 获取结果集元数据信息
            ResultSetMetaData rsmd = rs.getMetaData();
            int num = rsmd.getColumnCount();
            Map<String, String> map;
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            for (int i = 1; i <= num; i++) {
                map = Maps.newHashMap();
                map.put(MyBatisGenConst.RSMD_COLUMN_NAME, rsmd.getColumnName(i));
                map.put(MyBatisGenConst.RSMD_COLUMN_CLASS_NAME, rsmd.getColumnClassName(i));
                map.put(MyBatisGenConst.RSMD_COLUMN_TYPE_NAME, rsmd.getColumnTypeName(i));
                map.put(MyBatisGenConst.RSMD_COLUMN_PRECISION, rsmd.getPrecision(i) + "");
                map.put(MyBatisGenConst.RSMD_COLUMN_SCALE, rsmd.getScale(i) + "");
                list.add(map);
            }
            return list;
        } catch (Exception e) {
            throw new Exception(e + ",table=" + table, e);
        } finally {
            try {
                stmt.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                rs.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * 获取列信息。
     *
     * @param table
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> getColInfoList(String table) throws Exception {
        Connection cn = getConnection();
        try {
            return getColInfoList(cn, table);
        } finally {
            try {
                cn.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * 获取参数列表
     *
     * @param colInfoList
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> makeParamList(List<Map<String, String>> colInfoList) throws Exception {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map;
        int num = colInfoList.size();
        Map<String, String> mapNew;
        for (int i = 0; i < num; i++) {
            map = colInfoList.get(i);
            mapNew = Maps.newHashMap();
            String columnName = map.get(MyBatisGenConst.RSMD_COLUMN_NAME);
            String columnClassName = map.get(MyBatisGenConst.RSMD_COLUMN_CLASS_NAME);
            String columnTypeName = map.get(MyBatisGenConst.RSMD_COLUMN_TYPE_NAME);
            String scaleStr = map.get(MyBatisGenConst.RSMD_COLUMN_SCALE);
            int scale = NumberUtils.toInt(scaleStr);
            String javaType = getJavaType(columnClassName, columnTypeName, scale);
            String jdbcType = getJdbcType(columnClassName, columnTypeName);
            String propName = getPropName(columnName);
            String setMethod = getSetMethod(propName);
            String getMethod = getGetMethod(propName);
            mapNew.put(MyBatisGenConst.VP_COLUMN_NAME, columnName.toLowerCase());
            mapNew.put(MyBatisGenConst.VP_PROP_NAME, propName);
            mapNew.put(MyBatisGenConst.VP_JAVA_TYPE, javaType);
            mapNew.put(MyBatisGenConst.VP_JDBC_TYPE, jdbcType);
            mapNew.put(MyBatisGenConst.VP_GET_METHOD, getMethod);
            mapNew.put(MyBatisGenConst.VP_SET_METHOD, setMethod);
            list.add(mapNew);
        }
        return list;
    }

    /**
     * 获取字段的java类型
     *
     * @param columnClassName 字段类名
     * @param columnTypeName  字段类型名称
     * @param scale           精度 小数位数
     * @return
     */
    public static String getJavaType(String columnClassName, String columnTypeName, int scale) {
        if (columnClassName.equals("java.sql.Timestamp")) {
            return "Date";
        }
        if (columnClassName.equals("java.lang.String")) {
            return "String";
        }
        if (columnTypeName.equals("DECIMAL") && scale < 1) {
            return "Long";
        }
        if (columnTypeName.equals("DECIMAL") && scale > 0) {
            return "java.math.BigDecimal";
        }
        if (columnTypeName.startsWith("BIGINT")) {
            return "Long";
        }
        if (columnTypeName.startsWith("INT")) {
            return "Integer";
        }
        if (columnTypeName.startsWith("TINYINT")) {
            return "Integer";
        }
        if (columnTypeName.startsWith("SMALLINT")) {
            return "Integer";
        }
        return columnClassName;
    }

    /**
     * 获取jdbc类型
     *
     * @param columnClassName 字段类名
     * @param columnTypeName  字段类型名称
     * @return
     */
    public static String getJdbcType(String columnClassName, String columnTypeName) {
        if (columnClassName.equals("java.lang.String")) {
            return "VARCHAR";
        }
        if (columnClassName.startsWith("java.sql.")) {
            return "TIMESTAMP";
        }
        if (columnTypeName.startsWith("NUMBER")) {
            return "DECIMAL";
        }
        if (columnTypeName.startsWith("INT")) {
            return "INTEGER";
        }
        if (columnTypeName.startsWith("BIGINT")) {
            return "BIGINT";
        }
        return columnTypeName;
    }

    /**
     * 根据表名获取java类型
     *
     * @param tableName 表名
     * @return
     */
    public static String getClassName(String tableName) {
        String t = tableName.toLowerCase();
        t = t.replace(MyBatisGenConst.TABLE_PREFIX, "");
        t = t.replace(MyBatisGenConst.TABLE_PREFIX_MHC, "");
        String[] arr = t.split("_");
        int num = arr.length;
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < num; i++) {
            s.append(StringUtils.capitalize(arr[i]));
        }
        return s.toString();
    }

    /**
     * 根据字段名获取java数据对象属性名
     *
     * @param columnName 字段名
     * @return
     */
    public static String getPropName(String columnName) {
        String t = columnName.toLowerCase();
        String[] arr = t.split("_");
        int num = arr.length;
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < num; i++) {
            if (i > 0) {
                s.append(StringUtils.capitalize(arr[i]));
            } else {
                s.append(arr[i]);
            }
        }
        return s.toString();
    }

    public static String getSetMethod(String propName) {
        return "set" + StringUtils.capitalize(propName);
    }

    public static String getGetMethod(String propName) {
        return "get" + StringUtils.capitalize(propName);
    }

    public static String getColsStr(List<Map<String, String>> list) {
        int num = list.size();
        Map<String, String> map;
        String colName;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < num; i++) {
            map = list.get(i);
            colName = map.get(MyBatisGenConst.VP_COLUMN_NAME);
            if (i > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(colName);
        }
        return stringBuilder.toString();
    }

    /**
     * velocity模板合并
     *
     * @param template 模板字符串 如 hello,${name}
     * @param paramMap 参数
     * @return
     * @throws Exception
     */
    public static String merge(String template, Map<String, Object> paramMap) throws Exception {
        VelocityContext vc = new VelocityContext(paramMap);
        StringWriter writer = new StringWriter();
        Velocity.evaluate(vc, writer, "mybatis_code_gen", template);
        return writer.getBuffer().toString();
    }

    /**
     * 获取sqlmap 参数列表 去掉 ID GMT_CREATE GMT_MODIFIED 字段
     *
     * @param paramList
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> getSqlmapParamList(List<Map<String, String>> paramList) throws Exception {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> tmp;
        Map<String, String> map;
        int num = paramList.size();
        for (int i = 0; i < num; i++) {
            tmp = paramList.get(i);
            String columnName = tmp.get(MyBatisGenConst.VP_COLUMN_NAME);
//            if (columnName.equalsIgnoreCase("ID")) {
//                continue;
//            }
//            if (columnName.equalsIgnoreCase("GMT_CREATE")) {
//                continue;
//            }
//            if (columnName.equalsIgnoreCase("GMT_MODIFIED")) {
//                continue;
//            }
            map = Maps.newHashMap();
            map.putAll(tmp);
            list.add(map);
        }
        return list;
    }

    /**
     * 根据表名生成java数据对象类文件和sqlmap文件
     *
     * @param table 表名
     * @throws Exception
     */
    public static void gen(String table) throws Exception {
        List<Map<String, String>> colInfoList = getColInfoList(table);
        List<Map<String, String>> paramList = makeParamList(colInfoList);

        boolean isSharding = Pattern.compile(MyBatisGenConst.SHARDING_SUFFIX_REG).matcher(table).find();

        if (isSharding) {
            // 去掉分库分表后面的表后缀，如_0001
            table = table.replaceAll(MyBatisGenConst.SHARDING_SUFFIX_REG, "");
        }

        String className = getClassName(table);

        String doTemplate = FileUtils.readFileToString(new File(MyBatisGenConst.DO_TEMPLATE));
        String queryTemplate = FileUtils.readFileToString(new File(MyBatisGenConst.QUERY_TEMPLATE));
        String sqlmapTemplate = FileUtils.readFileToString(new File(MyBatisGenConst.SQLMAP_TEMPLATE));
        String mapperTemplate = FileUtils.readFileToString(new File(MyBatisGenConst.MAPPER_TEMPLATE));
        String sqlmapExtTemplate = FileUtils.readFileToString(new File(MyBatisGenConst.SQLMAP_EXT_TEMPLATE));
        String mapperExtTemplate = FileUtils.readFileToString(new File(MyBatisGenConst.MAPPER_EXT_TEMPLATE));

        Map<String, Object> param = Maps.newHashMap();

        param.put(MyBatisGenConst.VP_MAIN_PACKAGE, MyBatisGenConst.MAIN_PACKAGE);
        param.put(MyBatisGenConst.VP_DO_PACKAGE, MyBatisGenConst.DO_PACKAGE);
        param.put(MyBatisGenConst.VP_QUERY_PACKAGE, MyBatisGenConst.QUERY_PACKAGE);
        param.put(MyBatisGenConst.VP_MAPPER_PACKAGE, MyBatisGenConst.MAPPER_PACKAGE);
        param.put(MyBatisGenConst.VP_MAPPER_EXT_PACKAGE, MyBatisGenConst.MAPPER_EXT_PACKAGE);
        param.put(MyBatisGenConst.VP_CLASS_NAME, className);
        param.put(MyBatisGenConst.VP_LIST, paramList);
        param.put(MyBatisGenConst.VP_QUERY_PREFIX, MyBatisGenConst.QUERY_PREFIX);
        param.put(MyBatisGenConst.VP_DO_SUFFIX, MyBatisGenConst.DO_SUFFIX);
        param.put(MyBatisGenConst.VP_MAPPER_SUFFIX, MyBatisGenConst.MAPPER_SUFFIX);
        param.put(MyBatisGenConst.VP_MAPPER_EXT_SUFFIX, MyBatisGenConst.MAPPER_EXT_SUFFIX);

        String vpTableName = table.toLowerCase();
        if (isSharding) {
            vpTableName += "_$tabNum$";
        }

        param.put(MyBatisGenConst.VP_TABLE_NAME, vpTableName);
        param.put(MyBatisGenConst.VP_SERIAL_VERSION_UID, "" + (long) (Math.random() * 1000000000000000000L));

        param.put(MyBatisGenConst.VP_SERIAL_VERSION_UID2, "" + (long) (Math.random() * 1000000000000000000L));

        String doResult = merge(doTemplate, param);

        List<Map<String, String>> sqlmapParamList = getSqlmapParamList(paramList);
        // 获取字段名不包含 id gmt_create gmt_modified
        param.put(MyBatisGenConst.VP_LIST, sqlmapParamList);

        String colsWithoutCommColumns = getColsStr(sqlmapParamList);
        param.put(MyBatisGenConst.VP_COLS_WITHOUT_COMMON_COLUMNS, colsWithoutCommColumns);
        String cols = MyBatisGenConst.COMMON_COLUMN_STR + colsWithoutCommColumns;
        param.put(MyBatisGenConst.VP_COLS, cols);
        param.put(MyBatisGenConst.VP_COLUMN_PK_NAME, ChangeUtil.getList(colsWithoutCommColumns, ",").get(0));

        String sqlmapResult = merge(sqlmapTemplate, param);
        String mapperResult = merge(mapperTemplate, param);
        String queryResult = merge(queryTemplate, param);
        String mapperExtResult = merge(mapperExtTemplate, param);
        String sqlmapExtResult = merge(sqlmapExtTemplate, param);

        String doOutFilePath = MyBatisGenConst.MAPPER_DO_DIR + "/" + className + MyBatisGenConst.DO_SUFFIX + ".java";
        String queryOutFilePath = MyBatisGenConst.MAPPER_QUERY_DIR + "/" + className + MyBatisGenConst.QUERY_PREFIX + ".java";
        String sqlmapOutFilePath = MyBatisGenConst.MAPPER_XML_DIR + "/" + className + MyBatisGenConst.MAPPER_SUFFIX + ".xml";
        String mapperOutFilePath = MyBatisGenConst.MAPPER_JAVA_DIR + "/" + className + MyBatisGenConst.MAPPER_SUFFIX + ".java";
        String sqlmapExtOutFilePath = MyBatisGenConst.MAPPER_EXT_XML_DIR + "/" + className + MyBatisGenConst.MAPPER_EXT_SUFFIX + ".xml";
        String mapperExtOutFilePath = MyBatisGenConst.MAPPER_EXT_JAVA_DIR + "/" + className + MyBatisGenConst.MAPPER_EXT_SUFFIX + ".java";

        boolean success = new File(MyBatisGenConst.MAPPER_DO_DIR).mkdirs();
        if (!success) {
            System.out.println(String.format("mkdir %s error", MyBatisGenConst.MAPPER_DO_DIR));
        }
        success = new File(MyBatisGenConst.MAPPER_QUERY_DIR).mkdirs();
        if (!success) {
            System.out.println(String.format("mkdir %s error", MyBatisGenConst.MAPPER_QUERY_DIR));
        }
        success = new File(MyBatisGenConst.MAPPER_XML_DIR).mkdirs();
        if (!success) {
            System.out.println(String.format("mkdir %s error", MyBatisGenConst.MAPPER_XML_DIR));
        }
        success = new File(MyBatisGenConst.MAPPER_JAVA_DIR).mkdirs();
        if (!success) {
            System.out.println(String.format("mkdir %s error", MyBatisGenConst.MAPPER_JAVA_DIR));
        }
        success = new File(MyBatisGenConst.MAPPER_EXT_XML_DIR).mkdirs();
        if (!success) {
            System.out.println(String.format("mkdir %s error", MyBatisGenConst.MAPPER_EXT_XML_DIR));
        }
        success = new File(MyBatisGenConst.MAPPER_EXT_JAVA_DIR).mkdirs();
        if (!success) {
            System.out.println(String.format("mkdir %s error", MyBatisGenConst.MAPPER_EXT_JAVA_DIR));
        }

        File sqlmapOutFile = new File(sqlmapOutFilePath);
        if (!sqlmapOutFile.exists()) {
            success = sqlmapOutFile.createNewFile();
            if (!success) {
                System.out.println(String.format("createNewFile %s error", sqlmapOutFilePath));
            }
        }
        File doOutFile = new File(doOutFilePath);
        if (!doOutFile.exists()) {
            success = doOutFile.createNewFile();
            if (!success) {
                System.out.println(String.format("createNewFile %s error", doOutFilePath));
            }
        }
        File mapperOutFile = new File(mapperOutFilePath);
        if (!mapperOutFile.exists()) {
            success = mapperOutFile.createNewFile();
            if (!success) {
                System.out.println(String.format("createNewFile %s error", mapperOutFilePath));
            }
        }
        File queryOutFile = new File(queryOutFilePath);
        if (!queryOutFile.exists()) {
            success = queryOutFile.createNewFile();
            if (!success) {
                System.out.println(String.format("createNewFile %s error", queryOutFilePath));
            }
        }

        FileUtils.writeStringToFile(sqlmapOutFile, sqlmapResult);
        FileUtils.writeStringToFile(doOutFile, doResult);
        FileUtils.writeStringToFile(queryOutFile, queryResult);
        FileUtils.writeStringToFile(mapperOutFile, mapperResult);

        File mapperExtOutFile = new File(mapperExtOutFilePath);
        if (!mapperExtOutFile.exists()) {
            mapperExtOutFile.createNewFile();
            FileUtils.writeStringToFile(mapperExtOutFile, mapperExtResult);
        }

        File sqlmapExtOutFile = new File(sqlmapExtOutFilePath);
        if (!sqlmapExtOutFile.exists()) {
            sqlmapExtOutFile.createNewFile();
            FileUtils.writeStringToFile(sqlmapExtOutFile, sqlmapExtResult);
        }
    }

    /**
     * 获取数据库连接
     *
     * @return
     * @throws ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    private static Connection getConnection() throws ClassNotFoundException, SQLException, FileNotFoundException,
            IOException {
        Properties prop = new Properties();
        prop.load(ClassLoader.getSystemResourceAsStream("db.config"));
        Class.forName(prop.getProperty("driver"));
        String url = prop.getProperty("url");
        String user = prop.getProperty("user");
        String psw = prop.getProperty("pwd");
        return DriverManager.getConnection(url, user, psw);
    }

    /**
     * 批量生成java数据对象类文件和sqlmap文件
     *
     * @param tables 表 多个表用逗号分隔 table1,table2,table3
     * @throws Exception
     */
    public static void batchGen(List<String> tables) throws Exception {
        System.out.println("table num " + tables.size());
        Connection cn = getConnection();
        try {
            for (String table : tables) {
                MyBatisGenCore.gen(table.trim());
                System.out.println(table.trim() + " done");
            }
        } finally {
            try {
                cn.close();
            } catch (Exception e) {
            }
        }
    }
}
