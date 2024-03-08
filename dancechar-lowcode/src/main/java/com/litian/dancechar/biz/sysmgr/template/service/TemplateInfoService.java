package com.litian.dancechar.biz.sysmgr.template.service;

import com.litian.dancechar.biz.sysmgr.template.dto.TemplateInfoDTO;
import com.litian.dancechar.biz.sysmgr.template.dto.TemplateInfoQueryReqDTO;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.page.PageResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 模板信息管理(TemplateInfo)表服务接口
 *
 * @author 01406831
 * @since 2021-06-21 13:48:15
 */
@Path("templateInfoService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "templateInfoService", tags = "工程模板管理")
public interface TemplateInfoService {

    /**
     * 功能描述: 分页查询列表
     */
    @POST
    @Path("pageList")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    Result<PageResp<TemplateInfoDTO>> listPage(TemplateInfoQueryReqDTO req);


    /**
     * 功能描述: 查询列表
     */
    @POST
    @Path("findList")
    @ApiOperation(value = "查询列表", notes = "查询列表")
    Result<List<TemplateInfoDTO>> findList();

    /**
     * 功能描述: 新增保存
     */
    @POST
    @Path("save")
    @ApiOperation(value = "新增保存", notes = "新增保存")
    Result<Boolean> save(TemplateInfoDTO templateInfoDTO);

    /**
     * 功能描述: 修改记录
     */
    @POST
    @Path("update")
    @ApiOperation(value = "修改记录", notes = "修改记录")
    Result<Boolean> update(TemplateInfoDTO templateInfoDTO);

    /**
     * 功能描述: 根据id删除记录
     */
    @POST
    @Path("deleteById")
    @ApiOperation(value = "根据id删除记录", notes = "根据id删除记录")
    Result<Boolean> deleteById(TemplateInfoDTO templateInfoDTO);

    /**
     * 功能描述: 根据id获取记录
     */
    @POST
    @Path("getById")
    @ApiOperation(value = "根据id获取记录", notes = "根据id获取记录")
    Result<TemplateInfoDTO> getById(TemplateInfoDTO templateInfoDTO);
}