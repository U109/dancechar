package com.litian.dancechar.biz.core.codegen.manager.conn;//package com.sf.cemp.fcode.biz.core.codegen.manager.conn;
//
//
//import com.alibaba.druid.pool.DruidDataSourceFactory;
//import org.apache.commons.lang.StringUtils;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.Map;
//import java.util.Properties;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * 连接池 工具类
// * @author zhangqingzhou
// *
// */
//public class PoolUtil{
//
//
//    private static ConnectionPool connPool = null;
//    // 设置成单例模式 防止 多次实例化连接池
//    private static PoolUtil instance = new PoolUtil();
//    private PoolUtil(){};
//    public static PoolUtil getInstance(){
//
//        return instance;
//    }
//
//    /**
//     * 存储连接映射
//     */
//    public static Map<String, ConnectionPool> connectionPoolMap = new ConcurrentHashMap<>();
//
//    public static void createConnectionPool(){
//        connPool = new ConnectionPool("jdbcDriver", "dbUrl", "dbUsername", "dbPassword");
//    }
//
//    public int getInitialConnections() {
//        return connPool.getInitialConnections();
//    }
//
//    public void setInitialConnections(int initialConnections) {
//        connPool.setInitialConnections(initialConnections);
//    }
//
//    public int getIncrementalConnections() {
//        return connPool.getIncrementalConnections();
//    }
//
//    public void setIncrementalConnections(int incrementalConnections) {
//        connPool.setIncrementalConnections(incrementalConnections);
//    }
//
//    public int getMaxConnections() {
//        return connPool.getMaxConnections();
//    }
//
//    public void setMaxConnections(int maxConnections) {
//        connPool.setMaxConnections(maxConnections);
//    }
//
//    public void initPool() {
//        try {
//            connPool.createPool();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public void returnConnection(Connection conn) {
//        connPool.returnConnection(conn);
//    }
//
//    public void refreshConnections() {
//        try {
//            connPool.refreshConnections();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void closeConnectionPool() {
//        try {
//            connPool.closeConnectionPool();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
