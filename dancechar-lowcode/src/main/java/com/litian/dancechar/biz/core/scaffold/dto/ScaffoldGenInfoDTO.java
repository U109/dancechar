package com.litian.dancechar.biz.core.scaffold.dto;

import com.litian.dancechar.framework.common.util.page.BaseDTO;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 脚手架生成-基础信息(ScaffoldGenInfo)DTO
 *
 * @author 01406831
 * @since 2021-06-21 14:32:35
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("scaffoldGenInfoDTO")
public class ScaffoldGenInfoDTO extends BaseDTO implements Serializable {
    private static final long serialVersionUID = -58215107792787391L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 1: 脚手架工程生成 2: 单功能生成单表 3：单功能多表
     */
    private Integer scaffoldType;

    /**
     * 系统编码
     */
    private String systemCode;
    /**
     * 系统名称
     */
    private String systemName;

    /**
     * 接入工程id
     */
    private Long systemInfoId;
    /**
     * 工程的groupId
     */
    private String groupId;

    /**
     * 访问路径
     */
    private String contextPath;

    /**
     * 工程的artifactId
     */
    private String artifactId;
    /**
     * 版本号
     */
    private String versionNo;
    /**
     * 工程包名
     */
    private String projectPackageName;
    /**
     * 产生代码行
     */
    private Integer codeLines;
    /**
     * 模板Id
     */
    private Long tplInfoId;
    /**
     * 模板类型 1.应用层；2.微服务层；3.前端；4单体应用
     */
    private Integer templateType;
    /**
     * 中间件(多个使用#分隔),比如redis、kafka
     */
    private String middleware;

    /**
     * 目录名称(多个使用#分隔),比如service、manager、repository
     */
    private String dirNames;
    /**
     * 生成功能
     */
    private String genFunctions;
    /**
     * 进行到第几步
     */
    private Integer step;

    private String desc;

    private List<SystemDBDTO> systemDBList;

    /**
     * 数据库基本信息id
     */
    private Long scaffoldGenDbInfoId;
    /**
     * 实例类名
     */
    private String className;
    /**
     * 实例目录名
     */
    private String catalog;

    private String sql;

    /**
     * kafka配置表id,多个逗号分隔
     */
    private String kafkaId;

    /**
     * sentinel配置表id
     */
    private Long sentinelId;

    /**
     * 工程中的功能集
     */
    private String functionCollect;

    /**
     * es版本
     */
    private Long esId;

    /**
     * mongodb配置表id
     */
    private Long mongodbId;
}

