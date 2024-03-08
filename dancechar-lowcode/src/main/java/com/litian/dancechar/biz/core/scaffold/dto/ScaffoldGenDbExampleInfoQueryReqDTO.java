package com.litian.dancechar.biz.core.scaffold.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 脚手架生成-数据库-示例信息(ScaffoldGenDbExampleInfo)查询请求DTO
 *
 * @author 01406831
 * @since 2021-06-21 14:33:55
 */
@Data
@ApiModel("scaffoldGenDbExampleInfo查询请求DTO")
public class ScaffoldGenDbExampleInfoQueryReqDTO extends BasePage {
    private static final long serialVersionUID = -79036166697308383L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 脚手架工程生成-数据库信息Id
     */
    private Long scaffoldGenDbInfoId;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 表描述
     */
    private String tableDesc;
    /**
     * 实例类名
     */
    private String className;
    /**
     * 实例目录名
     */
    private String catalog;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 作者
     */
    private String author;
}