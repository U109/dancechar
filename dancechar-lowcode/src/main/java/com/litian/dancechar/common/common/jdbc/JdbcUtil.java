package com.litian.dancechar.common.common.jdbc;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.google.common.collect.Maps;
import com.litian.dancechar.framework.common.util.DCStrUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * 类描述: jdbc工具类
 *
 * @author 01406831
 * @date 2021/08/05 15:22
 */
@Slf4j
public class JdbcUtil {

    /**
     * 验证select语句语法是否正确
     */
    public static boolean validSelectSqlSyntax(DbConfig dbConfig, String selectSql, Map<String, String> errorMap) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            // 创建连接
            if (DCStrUtil.equalsIgnoreCase(dbConfig.getDriverClass(), "mysql")) {
                dbConfig.setDriverClass("com.mysql.jdbc.Driver");
                conn = getMysqlConnetion(dbConfig.getIp(), dbConfig.getPort(), dbConfig.getDbName(), dbConfig.getUser(),
                        dbConfig.getCredit(), dbConfig.getDriverClass());
            } else if (DCStrUtil.equalsIgnoreCase(dbConfig.getDriverClass(), "oracle")) {
                dbConfig.setDriverClass("oracle.jdbc.driver.OracleDriver");
                conn = getOracleConnection(dbConfig.getOracleUrl(), dbConfig.getUser(),
                        dbConfig.getCredit(), dbConfig.getDriverClass());
            }
            // 创建prepareStatement对象，用于执行SQL
            ps = conn.prepareStatement(selectSql);
            // 获取查询结果集
            result = ps.executeQuery();
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            errorMap.put("errMsg", e.getMessage());
            return false;
        } finally {
            release(result, ps, conn);
        }
    }

    /**
     * 用于创建oracle jdbc链接的工具类对象
     */
    private static Connection getOracleConnection(String jdbcUrl, String user, String credit, String driverClass) {
        Connection conn = null;
        try {
            Class.forName(driverClass);
            conn = DriverManager.getConnection(jdbcUrl, user, credit);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return conn;
    }

    /**
     * 用于创建mysql jdbc链接的工具类对象
     */
    private static Connection getMysqlConnetion(String ip, String port, String dbName, String user, String credit, String driverClass) {
        String jdbcUrl = "jdbc:mysql://%s:%s/%s?useUnicode=true&amp;characterEncoding=utf8&allowMultiQueries=true&rewriteBatchedStatements=true";
        jdbcUrl = String.format(jdbcUrl, ip, port, dbName);
        Connection conn = null;
        try {
            Class.forName(driverClass);
            conn = DriverManager.getConnection(jdbcUrl, user, credit);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return conn;
    }

    private static boolean validBaseSqlSyntax(String sql) {
        try {
            MySqlStatementParser parser = new MySqlStatementParser(sql);
            List<SQLStatement> stmtList = parser.parseStatementList();
            // 将AST通过visitor输出
            StringBuilder out = new StringBuilder();
            MySqlOutputVisitor visitor = new MySqlOutputVisitor(out);

            for (SQLStatement stmt : stmtList) {
                stmt.accept(visitor);
                System.out.println(out + ";");
                out.setLength(0);
            }
            log.debug("sql base valid :{}", parser != null);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    private static void release(PreparedStatement ps, Connection conn) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private static void release(ResultSet result, PreparedStatement ps, Connection conn) {
        try {
            if (result != null) {
                result.close();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    public static void main(String[] args) {
       /* String ip = "cempbase-m.dbsit.sfcloud.local";
        String port = "3306";
        String dbName = "cempbase";
        String user = "cempbase";
        String credit = "qazwsx1234!";
        //String driverClass = "com.mysql.jdbc.Driver";
        String driverClass = "mysql";
        String sql = "select * from fcode_code_gen_user_db";
        DbConfig dbConfig = DbConfig.builder().ip(ip).port(port).dbName(dbName).user(user).credit(credit).driverClass(driverClass).build();
        System.out.println(validSelectSqlSyntax(dbConfig, sql, Maps.newConcurrentMap()));*/

        String oracleUrl = "jdbc:oracle:thin:@scssit.dbsit.sfdc.com.cn:1521:scssit";
        String user = "scs";
        String credit = "sfpwd12345#";
        String driverClass = "oracle";
        String sql = "SELECT * FROM SCS_VIRTUAL_COUPON_INFO T WHERE T.VIRTUAL_COUPON_NO='C1036767441732441'";
        DbConfig dbConfig = DbConfig.builder().oracleUrl(oracleUrl).user(user).credit(credit).driverClass(driverClass).build();
        System.out.println(validSelectSqlSyntax(dbConfig, sql, Maps.newConcurrentMap()));

        //System.out.println(validBaseSqlSyntax(sql));
    }
}