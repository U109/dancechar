package com.litian.dancechar.biz.sysmgr.dbmgr.service;

import com.litian.dancechar.biz.sysmgr.dbmgr.dto.*;
import com.litian.dancechar.biz.sysmgr.dbmgr.repository.dataobject.InformationSchemaTableColumn;
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
import java.util.Map;

/**
 * 类描述：systemDBInfo 服务接口
 *
 * @author terryhl
 * @date 2021-06-21 11:48:37
 */
@Path("systemDBInfoService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "systemDBInfoService", tags = "数据库管理")
public interface SystemDBInfoService {

    /**
     * 刷新列表
     */
    @POST
    @Path("refreshTableList")
    @ApiOperation(value = "刷新表", notes = "刷新表")
    Result<Void> refreshTableList(SystemDBInfoDTO systemDBInfoDTO);

    /**
     * 功能描述: 解析dburl
     */
    @POST
    @Path("resolveDBUrl")
    @ApiOperation(value = "解析dburl", notes = "解析dburl")
    Result<DynamicDBInfo> resolveDBUrl(SystemDBInfoDTO systemDBInfoDTO);

    /**
     * 功能描述: 分页查询列表
     */
    @POST
    @Path("pageList")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    Result<PageResp<SystemDBInfoDTO>> listPage(SystemDBInfoQueryReqDTO req);

    /**
     * 功能描述: 查询列表
     */
    @POST
    @Path("listAllSystemDB")
    @ApiOperation(value = "查询列表(不分页)", notes = "查询列表(不分页)")
    Result<List<SystemDBInfoDTO>> listAllSystemDB(SystemDBInfoReqDTO systemDBInfoReqDTO);

    /**
     * 功能描述: 新增保存
     */
    @POST
    @Path("save")
    @ApiOperation(value = "新增保存", notes = "新增保存")
    Result<SystemDBInfoDTO> save(SystemDBInfoDTO systemDBInfoDTO);

    /**
     * 功能描述: 新增修改
     */
    @POST
    @Path("saveOrUpdate")
    @ApiOperation(value = "新增修改保存", notes = "新增修改保存")
    Result<SystemDBInfoDTO> saveOrUpdate(SystemDBInfoDTO systemDBInfoDTO);

    /**
     * 功能描述: 修改记录
     */
    @POST
    @Path("update")
    @ApiOperation(value = "修改记录", notes = "修改记录")
    Result<Boolean> update(SystemDBInfoDTO systemDBInfoDTO);

    /**
     * 功能描述: 根据id删除记录
     */
    @POST
    @Path("deleteById")
    @ApiOperation(value = "根据id删除记录", notes = "根据id删除记录")
    Result<Boolean> deleteById(SystemDBInfoDTO systemDBInfoDTO);

    /**
     * 功能描述: 根据id获取记录
     */
    @POST
    @Path("getById")
    @ApiOperation(value = "根据id获取记录", notes = "根据id获取记录")
    Result<SystemDBInfoDTO> getById(SystemDBInfoDTO systemDBInfoDTO);

    /**
     * 功能描述: 获取所有表
     */
    @POST
    @Path("listDBTable")
    @ApiOperation(value = "获取所有表", notes = "获取所有表")
    Result<SystemDBInfoDTO> listDBTable(SystemDBInfoDTO systemDBInfoDTO);

    /**
     * 功能描述: 获取所有表的字段和描述
     */
    @POST
    @Path("listDBTableColumns")
    @ApiOperation(value = "获取所有表的字段和描述", notes = "获取所有表的字段和描述")
    Result<List<InformationSchemaTableColumn>> listDBTableColumns(DbColumnsQueryDTO dbColumnsQueryDTO);

    @POST
    @Path("batchSelectColumns")
    @ApiOperation(value = "批量获取表的字段和描述", notes = "批量获取表的字段和描述")
    Result<Map<String, List<InformationSchemaTableColumn>>> batchSelectColumns(SystemTableInfoDTO systemTableInfoDTO);
}