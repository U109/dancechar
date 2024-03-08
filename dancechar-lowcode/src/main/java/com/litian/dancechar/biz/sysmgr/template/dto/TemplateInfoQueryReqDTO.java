package com.litian.dancechar.biz.sysmgr.template.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 模板信息管理(TemplateInfo)查询请求DTO
 *
 * @author 01406831
 * @since 2021-06-21 13:48:15
 */
@Data
@ApiModel("工程模板查询参数")
public class TemplateInfoQueryReqDTO extends BasePage {
    private static final long serialVersionUID = -98031137433664791L;
    /**
     * 模板Id
     */
    @ApiModelProperty(value = "主键Id", name = "id", example = "1")
    private Long id;
    /**
     * 模板名称
     */
    @ApiModelProperty(value = "模板名称", name = "templateName", required = false, example = "Web前端开发")
    private String templateName;
    /**
     * 模板描述
     */
    @ApiModelProperty(value = "模板描述", name = "templateDesc", required = false, example = "前端模板React")
    private String templateDesc;
    /**
     * 模板类型 1.应用层；2.微服务层；3.前端
     */
    @ApiModelProperty(value = "模板类型 1.应用层；2.微服务层；3.前端", name = "templateType", required = false, example = "1")
    private Integer templateType;
    /**
     * 功能集(多个使用#分隔),比如service#manager
     */
    @ApiModelProperty(value = "功能集", name = "plugins", required = false, example = "service#manager#repository")
    private String plugins;
    /**
     * 中间件(多个使用#分隔),比如redis#kafka
     */
    @ApiModelProperty(value = "中间件", name = "middleware", required = false, example = "redis#kafka#sentinel#saturn")
    private String middleware;
}
