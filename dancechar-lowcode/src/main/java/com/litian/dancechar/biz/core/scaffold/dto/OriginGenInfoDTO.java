package com.litian.dancechar.biz.core.scaffold.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class OriginGenInfoDTO implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 1: 脚手架生成 2: 单功能生成
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
     * 接入工程id
     */
    private Long systemInfoId;

    /**
     * 模板Id
     */
    private Long tplInfoId;
    /**
     * 模板类型 1.应用层；2.微服务层；3.前端 4.单体应用
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
     * 工程中的功能集
     */
    private String functionCollect;

}
