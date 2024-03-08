package com.litian.dancechar.biz.core.scaffold.service;

import com.litian.dancechar.biz.core.scaffold.dto.ScaffoldGenInfoDTO;
import com.litian.dancechar.biz.core.scaffold.dto.ScaffoldGenInfoQueryReqDTO;
import com.litian.dancechar.biz.core.scaffold.dto.ScaffoldMultiTableReqDTO;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.page.PageResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 脚手架生成-基础信息(ScaffoldGenInfo)表服务接口
 *
 * @author 01406831
 * @since 2021-06-21 14:32:35
 */
@Path("scaffoldGenInfoService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "scaffoldGenInfoService", tags = "代码生成")
public interface ScaffoldGenInfoService {

    /**
     * 功能描述: 分页查询列表
     */
    @POST
    @Path("pageList")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    Result<PageResp<ScaffoldGenInfoDTO>> listPage(ScaffoldGenInfoQueryReqDTO req);

    /**
     * 功能描述: 新增/修改保存
     */
    @POST
    @Path("save")
    @ApiOperation(value = "新增/修改保存", notes = "新增/修改保存")
    Result<Long> save(ScaffoldGenInfoDTO scaffoldGenInfoDTO);

    @POST
    @Path("saveMultiTableGenInfo")
    @ApiOperation(value = "多张独立的表保存", notes = "多张独立的表保存")
    Result<List<Long>> saveMultiTableGenInfo(ScaffoldMultiTableReqDTO multiTableReqDTO);

    /**
     * 功能描述: 根据id删除记录
     */
    @POST
    @Path("deleteById")
    @ApiOperation(value = "根据id删除记录", notes = "根据id删除记录")
    Result<Boolean> deleteById(ScaffoldGenInfoDTO scaffoldGenInfoDTO);

    /**
     * 功能描述: 根据id获取记录
     */
    @POST
    @Path("getById")
    @ApiOperation(value = "根据id获取记录", notes = "根据id获取记录")
    Result<ScaffoldGenInfoDTO> getById(ScaffoldGenInfoDTO scaffoldGenInfoDTO);

    /**
     * 功能描述: 下载zip格式
     */
    @POST
    @Produces({MediaType.APPLICATION_OCTET_STREAM + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
    @Path("downloadWithZip")
    void downloadWithZip(ScaffoldGenInfoDTO scaffoldGenInfoDTO, @Context HttpServletResponse response);

    @POST
    @Path("updateByCodeLine")
    @ApiOperation(value = "根据id修改代码行", notes = "根据id修改代码行")
    Result<Boolean> updateByCodeLine(ScaffoldGenInfoDTO scaffoldGenInfoDTO);

    @POST
    @Path("deleteNoGenData")
    @ApiOperation(value = "清理没有生成功能的数据", notes = "清理没有生成功能的数据")
    Result<Boolean> deleteNoGenData();

    @POST
    @Produces({MediaType.APPLICATION_OCTET_STREAM + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
    @Path("downloadWithMultiZip")
    void downloadWithMultiZip(ScaffoldMultiTableReqDTO scaffoldMultiTableReqDTO, @Context HttpServletResponse response) ;

    @POST
    @Path("deleteGenInfo")
    @ApiOperation(value = "删除工程相关信息", notes = "删除工程相关信息")
    Result<Boolean> deleteGenInfo(ScaffoldMultiTableReqDTO multiTableReqDTO);

}