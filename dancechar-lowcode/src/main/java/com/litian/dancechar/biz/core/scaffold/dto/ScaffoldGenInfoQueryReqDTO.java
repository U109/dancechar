package com.litian.dancechar.biz.core.scaffold.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * 脚手架生成-基础信息(ScaffoldGenInfo)查询请求DTO
 *
 * @author 01406831
 * @since 2021-06-21 14:32:35
 */
@Data
@ApiModel("scaffoldGenInfo查询请求DTO")
public class ScaffoldGenInfoQueryReqDTO extends BasePage {
    private static final long serialVersionUID = -49582016997087772L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 系统编码
     */
    private String systemCode;

    private Long systemInfoId;
    /**
     * 系统名称
     */
    private String systemName;
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
     * 中间件(多个使用#分隔),比如redis、kafka
     */
    private String middleware;
    /**
     * 1: 脚手架生成 2: 单功能生成
     */
    private List<Integer> scaffoldType;

    /**
     * 功能名
     */
    private String genFunctions;
}
