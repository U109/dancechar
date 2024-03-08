package com.litian.dancechar.biz.core.scaffold.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 脚手架生成-数据库信息(ScaffoldGenDbInfo)查询请求DTO
 *
 * @author 01406831
 * @since 2021-06-21 14:33:21
 */
@Data
@ApiModel("scaffoldGenDbInfo查询请求DTO")
public class ScaffoldGenDbInfoQueryReqDTO extends BasePage {
    private static final long serialVersionUID = -17624501505653804L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 脚手架工程生成-基础信息Id
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
}