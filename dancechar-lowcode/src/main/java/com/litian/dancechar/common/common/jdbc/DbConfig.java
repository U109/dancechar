package com.litian.dancechar.common.common.jdbc;

import lombok.Builder;
import lombok.Data;

/**
 * 类描述: DB配置信息
 *
 * @author 01406831
 * @date 2021/08/05 15:59
 */
@Data
@Builder
public class DbConfig {
    private String ip;
    private String port;
    private String dbName;
    private String oracleUrl;
    private String user;
    private String credit;
    private String driverClass;
}
