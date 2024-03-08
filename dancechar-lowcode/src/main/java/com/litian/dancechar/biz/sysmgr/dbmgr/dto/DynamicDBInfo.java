package com.litian.dancechar.biz.sysmgr.dbmgr.dto;

import com.litian.dancechar.biz.sysmgr.dbmgr.common.enums.SystemDBInfoEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.net.URI;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DynamicDBInfo implements Serializable {

    private static final long serialVersionUID = 935288243233531322L;

    /**
     * 数据库url
     */
    private String dbUrl;
    /**
     * host ip
     */
    private String host;
    /**
     * 数据库端口
     */
    private Integer port;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 数据库名
     */
    private String dbName;
    /**
     * 驱动名
     */
    private String driverName;
    /**
     * 动态dskey
     */
    private String dsKey;
    /**
     * 表名
     */
    private String tableName;
//    /**
//     * 表名列表
//     */
//    private List<String> tableNameList;

    /**
     * 解析dburl构造属性
     */
    public DynamicDBInfo initDBInfo(String dbUrl, String username, String password) {
        this.dbUrl = dbUrl;
        this.username = username;
        this.password = password;

//        String driverNameIndex = uri.getScheme();
        if (dbUrl.contains(SystemDBInfoEnums.DriverEnum.MYSQL.getCode())) {
            // 解析url ip port
            String cleanURI = dbUrl.substring(5);
            URI uri = URI.create(cleanURI);
            this.host = uri.getHost();
            this.port = uri.getPort();
            // 提取dbname
            int length = dbUrl.indexOf("?") > 0? dbUrl.indexOf("?"):uri.getPath().length();
            this.dbName = uri.getPath().substring(1, length);
            this.dsKey  = host+"|"+port+"|"+dbName;
            driverName = SystemDBInfoEnums.DriverEnum.MYSQL.name();
        } else if (dbUrl.contains(SystemDBInfoEnums.DriverEnum.ORACLE.getCode())) {
            // 解析url ip port
            int lengthFirst = dbUrl.lastIndexOf("@") ;
            int lengthMiddle = dbUrl.substring(lengthFirst).indexOf(":")+lengthFirst ;
            int lengthLast = dbUrl.lastIndexOf(":") ;

            this.host = dbUrl.substring(lengthFirst+1,lengthMiddle);
            this.port = Integer.parseInt(dbUrl.substring(lengthMiddle+1,lengthLast));
            // 提取dbname

            this.dbName = dbUrl.substring(lengthLast+1);
            this.dsKey  = host+"|"+port+"|"+dbName;
            driverName = SystemDBInfoEnums.DriverEnum.ORACLE.name();
        }
        return this;
    }
}
