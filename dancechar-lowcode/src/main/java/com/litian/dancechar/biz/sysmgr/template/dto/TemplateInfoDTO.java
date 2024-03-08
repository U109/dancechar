package com.litian.dancechar.biz.sysmgr.template.dto;

import com.litian.dancechar.common.common.dto.BaseDTO;
import com.litian.dancechar.common.common.dto.BaseParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 模板信息管理(TemplateInfo)DTO
 *
 * @author 01406831
 * @since 2021-06-21 13:48:15
 */
@Data
@ApiModel("templateInfoDTO")
public class TemplateInfoDTO extends BaseDTO implements Serializable {
    private static final long serialVersionUID = -11151712503339697L;
    /**
     * 模板编码
     */
    private Long id;

    /**
     * 模板名称
     */
    @NotNull(message = "模板名称不能为空", groups = {BaseParam.add.class})
    private String templateName;
    /**
     * 模板描述
     */
    @NotNull(message = "模板描述不能为空", groups = {BaseParam.add.class})
    private String templateDesc;
    /**
     * 模板类型 1.应用层；2.微服务层；3.前端
     */
    @NotNull(message = "模板类型不能为空", groups = {BaseParam.add.class})
    private Integer templateType;
    /**
     * 包含组件(多个使用#分隔),比如service#manager
     */
    @NotNull(message = "组件不能为空", groups = {BaseParam.add.class})
    private String plugins;
    /**
     * 中间件(多个使用#分隔),比如redis#kafka
     */
    private String middleware;
}
