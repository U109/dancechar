package com.litian.dancechar.common.common.jdbc.detail;

import cn.hutool.json.JSONUtil;
import com.litian.dancechar.framework.common.util.DCStrUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * jdbc工具类
 */
@Slf4j
public class JdbcUtil {

    public static void main(String[] args) {
        DatasourceConfig datasourceConfig = new DatasourceConfig();
        datasourceConfig.setUrl("jdbc:mysql://localhost:3306/zeus?useUnicode=true&characterEncoding=UTF8&serverTimezone=CTT");
        datasourceConfig.setUsername("root");
        datasourceConfig.setPassword("root");
        datasourceConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        System.out.println(getTablesColumn(getConnection(datasourceConfig), "admin_role"));
        System.out.println(getTablesName(getConnection(datasourceConfig), "zeus"));

        Bean bean = new Bean();
        AutoCodeConfig autoCodeConfig = new AutoCodeConfig();
        autoCodeConfig.setDatasourceConfig(datasourceConfig);
        autoCodeConfig.setBean(bean);
        bean.setDataName("admin_role");

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setParentPack("com.litian.dancechar.lowcode");
        autoCodeConfig.setGlobalConfig(globalConfig);
        bean = saxTable(getConnection(datasourceConfig), autoCodeConfig);
        System.out.println(JSONUtil.toJsonStr(bean));
    }


    /**
     * 获取jdbc连接
     */
    public static Connection getConnection(DatasourceConfig datasourceConfig) {
        try {
            Class.forName(datasourceConfig.getDriverClassName());
            return DriverManager.getConnection(datasourceConfig.getUrl(), datasourceConfig.getUsername(), datasourceConfig.getPassword());
        } catch (Exception e) {
            throw new RuntimeException("获取jdbc连接异常", e);
        }
    }

    /**
     * 获取表的列
     */
    public static List<String> getTablesColumn(Connection connection, String tableName) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            List<String> columns = new ArrayList<>();
            if (metaData.getDriverName().toLowerCase().contains("oracle")) {
                ResultSet rs = metaData.getColumns(connection.getCatalog(), "%", tableName.toUpperCase(), "%");
                while (rs.next()) {
                    columns.add(rs.getString("COLUMN_NAME").toLowerCase());
                }
            } else {
                ResultSet rs = metaData.getColumns(connection.getCatalog(), "%", tableName, "%");
                while (rs.next()) {
                    columns.add(rs.getString("COLUMN_NAME"));
                }
            }
            return columns;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 获取所有的表名称
     */
    public static List<String> getTablesName(Connection connection) {
        return getTablesName(connection, null);
    }

    /**
     * 获取所有的表名称
     */
    public static List<String> getTablesName(Connection connection, String catalog) {
        List<String> tables = new ArrayList<>();
        try {
            DatabaseMetaData meta = connection.getMetaData();
            if (DCStrUtil.isEmpty(catalog)) {
                catalog = connection.getCatalog();
            }
            ResultSet rs;
            if (meta.getDriverName().toLowerCase().contains("oracle")) {
                rs = meta.getTables(null, catalog, null,
                        new String[]{"TABLE"});
                while (rs.next()) {
                    tables.add(rs.getString(3).toLowerCase());
                }
            } else {
                rs = meta.getTables(catalog, null, null,
                        new String[]{"TABLE"});
                while (rs.next()) {
                    tables.add(rs.getString(3));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return tables;
    }

    /**
     * 获取table相关数据
     */
    public static Bean saxTable(Connection connection, AutoCodeConfig autoCodeConfig) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            if (metaData.getDriverName().toLowerCase().contains("oracle")) {
                return saxOracle(connection, autoCodeConfig);
            } else {
                return saxMysql(connection, autoCodeConfig);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Bean saxOracle(Connection connection, AutoCodeConfig autoCodeConfig) {
        Statement statement = null;
        ResultSet rs = null;
        Bean bean = autoCodeConfig.getBean();
        DatasourceConfig datasourceConfig = autoCodeConfig.getDatasourceConfig();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            String tableName = bean.getDataName().toUpperCase();
            ResultSet tables = metaData.getTables(null, datasourceConfig.getName(), tableName, new String[]{"TABLE"});
            if (tables.next()) {
                //获取表名
                statement = connection.createStatement();
                StringBuffer tableNameb = new StringBuffer("select * from all_tab_comments c where 1=1");
                String catalog = datasourceConfig.getName();
                if (!DCStrUtil.isEmpty(catalog)) {
                    tableNameb.append(String.format(" and c.owner='%s'", catalog));
                }
                tableNameb.append(String.format(" and c.table_name='%s'", tableName));
                rs = statement.executeQuery(tableNameb.toString());
                if (rs != null && rs.next()) {
                    String comment = rs.getString("COMMENTS");
                    if (DCStrUtil.isEmpty(comment)) {
                        comment = tableName;
                    }
                    bean.setTableRemarks(comment);
                }
                //获取列注释
                Map<String, String> remarks = new HashMap<>();
                StringBuffer cols = new StringBuffer("select * from all_col_comments c where 1=1");
                if (!DCStrUtil.isEmpty(catalog)) {
                    cols.append(String.format(" and c.owner='%s'", catalog));
                }
                cols.append(String.format(" and c.table_name='%s'", tableName));
                rs = statement.executeQuery(cols.toString());
                while (rs != null && rs.next()) {
                    remarks.put(rs.getString("COLUMN_NAME").toLowerCase(), rs.getString("COMMENTS"));
                }
                //获取主键
                ResultSet primaryKeys = metaData.getPrimaryKeys(null, catalog, tableName);
                Map<String, String> pk = new HashMap<>();
                if (primaryKeys.next()) {
                    String column_name = primaryKeys.getString("COLUMN_NAME").toLowerCase();
                    pk.put(column_name, column_name);
                }
                //获取列名
                List<BeanColumn> beans = new ArrayList<>();
                List<BeanColumn> keys = new ArrayList<>();

                rs = metaData.getColumns(null, catalog, tableName, "%");
                while (rs.next()) {
                    BeanColumn beanColumn = new BeanColumn();
                    beanColumn.setJdbcName(rs.getString("COLUMN_NAME").toLowerCase());
                    beanColumn.setJdbcType(rs.getString("DATA_TYPE"));
                    if (!DCStrUtil.isEmpty(rs.getString("IS_NULLABLE")) && rs.getString("IS_NULLABLE").equalsIgnoreCase("yes")) {
                        beanColumn.setNullable(true);
                    }
                    beanColumn.setLength(rs.getString("COLUMN_SIZE"));
                    if (!DCStrUtil.isEmpty(rs.getString("IS_AUTOINCREMENT")) && rs.getString("IS_AUTOINCREMENT").equalsIgnoreCase("yes")) {
                        beanColumn.setIdentity(true);
                    }
                    String remark = remarks.get(beanColumn.getJdbcName());
                    if (DCStrUtil.isEmpty(remark)) {
                        beanColumn.setRemarks(beanColumn.getJdbcName());
                    } else {
                        beanColumn.setRemarks(remark);
                    }
                    beanColumn.setDefaultValue("");

                    if (pk.get(beanColumn.getJdbcName()) != null) {
                        beanColumn.setKey(true);
                        keys.add(beanColumn);
                    }
                    beans.add(beanColumn);
                }
                bean.setPrimaryKey(keys);
                bean.setAllColumns(beans);
                bean.setParentPack(autoCodeConfig.getGlobalConfig().getParentPack());
                return bean;
            } else {
                log.info("数据库表不存在->{}", tableName);
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static Bean saxMysql(Connection connection, AutoCodeConfig autoCodeConfig) {
        Statement statement = null;
        ResultSet rs = null;
        Bean bean = autoCodeConfig.getBean();
        DatasourceConfig datasourceConfig = autoCodeConfig.getDatasourceConfig();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            String tableName = bean.getDataName();
            ResultSet tables = metaData.getTables(datasourceConfig.getName(), "%", tableName, new String[]{"TABLE"});
            if (tables.next()) {
                //获取表名
                statement = connection.createStatement();
                try {
                    rs = statement.executeQuery("SHOW CREATE TABLE " + tableName);
                    if (rs != null && rs.next()) {
                        String createDDL = rs.getString(2);
                        String comment = parse(createDDL);
                        log.info("解析表-{}->{}", tableName, comment);
                        if (DCStrUtil.isEmpty(comment)) {
                            comment = tableName;
                        }
                        bean.setTableRemarks(comment);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                //获取主键
                ResultSet primaryKeys = metaData.getPrimaryKeys(datasourceConfig.getName(), "%", tableName);
                Map<String, String> pk = new HashMap<>();
                if (primaryKeys.next()) {
                    String column_name = primaryKeys.getString("COLUMN_NAME");
                    pk.put(column_name, column_name);
                }

                //获取列名
                List<BeanColumn> beans = new ArrayList<>();
                List<BeanColumn> keys = new ArrayList<>();

                rs = metaData.getColumns(datasourceConfig.getName(), "%", tableName, "%");
                while (rs.next()) {
                    BeanColumn beanColumn = new BeanColumn();
                    beanColumn.setJdbcName(rs.getString("COLUMN_NAME"));
                    beanColumn.setJdbcType(rs.getString("DATA_TYPE"));
                    if (!DCStrUtil.isEmpty(rs.getString("IS_NULLABLE")) && rs.getString("IS_NULLABLE").equalsIgnoreCase("yes")) {
                        beanColumn.setNullable(true);
                    }
                    beanColumn.setLength(rs.getString("COLUMN_SIZE") == null ? "1" : rs.getString("COLUMN_SIZE"));
                    if (!DCStrUtil.isEmpty(rs.getString("IS_AUTOINCREMENT")) && rs.getString("IS_AUTOINCREMENT").equalsIgnoreCase("yes")) {
                        beanColumn.setIdentity(true);
                    }
                    String remarks = rs.getString("REMARKS");
                    if (DCStrUtil.isEmpty(remarks)) {
                        beanColumn.setRemarks(beanColumn.getJdbcName());
                    } else {
                        beanColumn.setRemarks(remarks);
                    }
                    beanColumn.setDefaultValue(rs.getString("COLUMN_DEF"));

                    if (pk.get(beanColumn.getJdbcName()) != null) {
                        beanColumn.setKey(true);
                        keys.add(beanColumn);
                    }
                    beans.add(beanColumn);
                }
                bean.setPrimaryKey(keys);
                bean.setAllColumns(beans);
                bean.setParentPack(autoCodeConfig.getGlobalConfig().getParentPack());
                return bean;
            } else {
                log.info("数据库表不存在->{}", tableName);
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 返回表注释信息
     */
    private static String parse(String all) {
        String comment = null;
        int index = all.indexOf("COMMENT='");
        if (index < 0) {
            return "";
        }
        comment = all.substring(index + 9);
        comment = comment.substring(0, comment.length() - 1);
        return comment;
    }
}
