package com.litian.dancechar.biz.core.codegen.service;

import com.litian.dancechar.biz.core.codegen.common.response.ResponseData;
import com.litian.dancechar.biz.core.codegen.dto.SysCodeGenerateConfigParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 类描述：代码生成详细配置服务接口
 *
 * @author 01402521
 * @date 2021/3/31 12:49
 */
@Path("codeGenDetailConfigService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "codeGenDetailConfigService", tags = "代码生成详细配置")
public interface CodeGenDetailConfigService {

    /**
     * 编辑代码生成详细配置
     */
    @POST
    @Path("edit")
    @ApiOperation(value = "编辑", notes = "编辑")
    ResponseData edit(SysCodeGenerateConfigParam sysCodeGenerateConfigParam);

    /**
     * 查看详情
     */
    @GET
    @Path("detail")
    @ApiOperation(value = "查看详情", notes = "查看详情")
    ResponseData detail(SysCodeGenerateConfigParam sysCodeGenerateConfigParam);

    /**
     * 代码生成详细配置列表
     */
    @GET
    @Path("list")
    @ApiOperation(value = "查询列表", notes = "查询详细配置列表")
    ResponseData list(SysCodeGenerateConfigParam sysCodeGenerateConfigParam);
}