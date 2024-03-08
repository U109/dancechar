package com.litian.dancechar.biz.core.codegen.manager.conn;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.litian.dancechar.biz.sysmgr.dbmgr.common.enums.SystemDBInfoEnums;
import com.litian.dancechar.biz.sysmgr.dbmgr.dto.DynamicDBInfo;
import com.litian.dancechar.framework.common.exception.BusinessException;

import javax.sql.DataSource;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionPoolUtil {

    /**
     * 存储连接池映射 key为ipport
     */
    public static Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();

    public static Map<String, List<String>> tableMap = new ConcurrentHashMap<>();

//    public Connection getConnection(String dbUrl, String dbUsername,
//                                    String dbPassword) {
//        Pair<String, DataSource> pair = getDataSource(dbUrl, dbUsername, dbPassword);
//        Connection connection = null;
//        try {
//            connection = pair.getValue().getConnection();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return connection;
//    }

//    /**
//     * 获取ds 加锁
//     * @param dbUrl
//     * @param dbUsername
//     * @param dbPassword
//     * @return
//     */
//    public static Pair<String, DataSource> getDataSource(String dbUrl, String dbUsername, String dbPassword) {
//        String dsKey = getDSKey(dbUrl);
//        DataSource dataSource = dataSourceMap.get(dsKey);
//        if(Objects.nonNull(dataSource)){
//            return Pair.of(dsKey, dataSource);
//        }
//        dataSource = createDataSource(dbUrl, dbUsername, dbPassword);
//        if(Objects.nonNull(dataSource)){
//            dataSourceMap.put(dsKey, dataSource);
//            return Pair.of(dsKey, dataSource);
//        }
//        return null;
//    }


    /**
     * 获取ds
     * @param dbUrl
     * @param dbUsername
     * @param dbPassword
     * @return
     */
    public static DataSource getDataSource(String dbUrl, String dbUsername, String dbPassword) {
        String dsKey = getDSKey(dbUrl);
        DataSource dataSource = createDataSource(dbUrl, dbUsername, dbPassword);
        return null;
    }

    /**
     * 创建ds
     * @param dbUrl
     * @param dbUsername
     * @param dbPassword
     * @return
     */
    public static DataSource createDataSource(String dbUrl, String dbUsername, String dbPassword){
        Properties properties = new Properties();
        properties.setProperty("url", dbUrl);
        properties.setProperty("username", dbUsername);
        properties.setProperty("password", dbPassword);
        // <!-- 配置初始化大小、最小、最大 -->
        properties.setProperty("initialSize", "1");
        properties.setProperty("minIdle", "1");
        properties.setProperty("maxActive", "5");
        properties.setProperty("maxWait", "5000");
        // <!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
        properties.setProperty("timeBetweenEvictionRunsMillis", "60000");
        // <!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
        properties.setProperty("minEvictableIdleTimeMillis", "300000");
        // 配置一个连接在池中最大生存的时间
        properties.setProperty("maxEvictableIdleTimeMillis", "900000");
        properties.setProperty("testWhileIdle", "true");
        properties.setProperty("testOnBorrow", "false");
        properties.setProperty("testOnReturn", "false");
        DataSource ds = null;
        try {
            if (dbUrl.contains(SystemDBInfoEnums.DriverEnum.ORACLE.getCode())) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                properties.setProperty("validationQuery", "SELECT 'x' FROM DUAL");
            } else {
                Class.forName("com.mysql.jdbc.Driver");
                properties.setProperty("validationQuery", "SELECT 'x'");
            }
            //从德鲁伊数据库连接池中获取连接
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new BusinessException("创建数据库连接失败");
        }
        return ds;
    }


    /**
     * 创建ds
     * @return
     */
    public static DataSource createDataSource(DynamicDBInfo dynamicDBInfo){
        Properties properties = new Properties();
        properties.setProperty("url", dynamicDBInfo.getDbUrl());
        properties.setProperty("username", dynamicDBInfo.getUsername());
        properties.setProperty("password", dynamicDBInfo.getPassword());
        // <!-- 配置初始化大小、最小、最大 -->
        properties.setProperty("initialSize", "1");
        properties.setProperty("minIdle", "1");
        properties.setProperty("maxActive", "5");
        properties.setProperty("maxWait", "5000");
        // <!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
        properties.setProperty("timeBetweenEvictionRunsMillis", "60000");
        // <!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
        properties.setProperty("minEvictableIdleTimeMillis", "300000");
        // 配置一个连接在池中最大生存的时间
        properties.setProperty("maxEvictableIdleTimeMillis", "900000");
        properties.setProperty("validationQuery", "SELECT 'x' FROM DUAL");
        properties.setProperty("testWhileIdle", "true");
        properties.setProperty("testOnBorrow", "false");
        properties.setProperty("testOnReturn", "false");

        DataSource ds = null;
        try {
            if (dynamicDBInfo.getDbUrl().contains(SystemDBInfoEnums.DriverEnum.ORACLE.getCode())) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                properties.setProperty("validationQuery", "SELECT 'x' FROM DUAL");
            } else {
                Class.forName("com.mysql.jdbc.Driver");
                properties.setProperty("validationQuery", "SELECT 'x'");
            }
            //从德鲁伊数据库连接池中获取连接
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new BusinessException("创建数据库连接失败");
        }
        return ds;
    }

    /**
     * 获取DataSource缓存key
     * @param dbUrl
     * @return
     */
    public static String getDSKey(String dbUrl){
        DynamicDBInfo dynamicDBInfo = resolveDBUrl(dbUrl);
        return dynamicDBInfo.getHost()+"|"+dynamicDBInfo.getPort()+"|"+dynamicDBInfo.getDbName();
    }

    /**
     * 解析url
     * @param dbUrl
     * @return
     */
    public static DynamicDBInfo resolveDBUrl(String dbUrl){
        // 解析url ip port
        String cleanURI = dbUrl.substring(5);
        URI uri = URI.create(cleanURI);
        // 提取dbname
        String dbName = uri.getPath().replace("/", "");
        String driverName = uri.getScheme();
        return DynamicDBInfo.builder().driverName(driverName).host(uri.getHost()).port(uri.getPort()).dbName(dbName).build();
    }

}
