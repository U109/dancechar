package com.litian.dancechar.biz.core.scaffold.dto;

import com.litian.dancechar.common.common.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 脚手架生成-数据库信息(ScaffoldGenDbInfo)DTO
 *
 * @author 01406831
 * @since 2021-06-21 14:33:21
 */
@Data
@ApiModel("scaffoldGenDbInfoDTO")
public class ScaffoldGenDbInfoDTO extends BaseDTO implements Serializable {
    private static final long serialVersionUID = -97878327325449137L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 脚手架生成-基础信息Id
     */
    private Long scaffoldGenInfoId;


    private Long systemDbId;
    /**
     * 数据库驱动类型,例如mysql
     */
    private String driverClass;
    /**
     * ip/端口
     */
    private String ipPort;
    /**
     * ip/端口
     */
    private String ip;
    /**
     * ip/端口
     */
    private String port;
    /**
     * 数据库名
     */
    private String dbName;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 是否主库(0-否 1-是)
     */
    private Integer primaryDb;
    /**
     * 表前缀
     */
    private String tablePrefix;
    /**
     * 示例列表json串
     */
    private String scaffoldGenDbExampleInfoJson;
    /**
     * 示例列表
     */
    private List<ScaffoldGenDbExampleInfoDTO> scaffoldGenDbExampleInfoList;

    /**
     * 1: 脚手架工程生成 2: 单功能生成单表 3：单功能多表
     */
    private Integer scaffoldType;

    /**
     * 预览信息
     */
    private FunctionGenSqlDTO genSqlDTO;

}