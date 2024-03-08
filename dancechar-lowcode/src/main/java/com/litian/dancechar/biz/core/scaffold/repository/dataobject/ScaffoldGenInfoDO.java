package com.litian.dancechar.biz.core.scaffold.repository.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import lombok.*;

import java.io.Serializable;

/**
 * 脚手架生成-基础信息(ScaffoldGenInfo)实体类
 *
 * @author 01406831
 * @since 2021-06-21 14:32:35
 */
@Data
@TableName("fcode_scaffold_gen_info")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScaffoldGenInfoDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = -45407410460668716L;
    /**
     * 主键
     */
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
     * 产生代码行
     */
    private Integer codeLines;
    /**
     * 模板Id
     */
    private Long tplInfoId;
    /**
     * 模板类型 1.应用层；2.微服务层；3.前端 4.单体应用
     */
    private Integer templateType;
    /**
     * 生成功能
     */
    private String genFunctions;
    /**
     * 中间件(多个使用#分隔),比如redis、kafka
     */
    private String middleware;
    /**
     * 目录名称(多个使用#分隔),比如service、manager、repository
     */
    private String dirNames;

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
