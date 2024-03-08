package com.litian.dancechar.biz.core.codegen.service;

import com.litian.dancechar.biz.core.codegen.common.response.ResponseData;
import com.litian.dancechar.biz.core.codegen.dto.CodeGenerateParam;
import com.litian.dancechar.framework.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 类描述：代码生成基础配置服务接口
 *
 * @author 01402521
 * @date 2021/3/31 12:49
 */
@Path("codeGenBaseConfigService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "codeGenBaseConfigService", tags = "代码生成基础配置服务接口")
public interface CodeGenBaseConfigService {

    /**
     * 代码生成基础数据
     */
    @GET
    @Path("page")
    @ApiOperation(value = "分页", notes = "分页")
    ResponseData page(CodeGenerateParam codeGenerateParam);

    /**
     * 代码生成基础配置保存
     */
    @POST
    @Path("add")
    @ApiOperation(value = "新增", notes = "新增")
    Result<Long> add(@RequestBody CodeGenerateParam codeGenerateParam);

    /**
     * 代码生成基础配置编辑
     */
    @POST
    @Path("edit")
    @ApiOperation(value = "编辑", notes = "编辑")
    Result<Boolean> edit(@RequestBody CodeGenerateParam codeGenerateParam);

    /**
     * 删除代码生成基础配置
     */
    @POST
    @Path("delete")
    @ApiOperation(value = "删除", notes = "删除")
    ResponseData delete(@RequestBody List<CodeGenerateParam> codeGenerateParamList);


    /**
     * 代码生成基础配置生成
     */
    @POST
    @Path("runLocal")
    @ApiOperation(value = "生成本地", notes = "生成本地")
    ResponseData runLocal(@RequestBody CodeGenerateParam codeGenerateParam);

    /**
     * 代码生成基础配置生成
     */
    @GET
    @Path("codeGenerate/runDown")
    @ApiOperation(value = "生成本地配置", notes = "生成本地配置")
    void runDown(CodeGenerateParam codeGenerateParam, HttpServletResponse response);

    @POST
    @Path("codeGenerate/runDown4J")
    @ApiOperation(value = "生成本地", notes = "生成本地")
    void runDown4J(CodeGenerateParam codeGenerateParam, HttpServletResponse response);
}
