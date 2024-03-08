package com.litian.dancechar.biz.core.scaffold.service;

import com.litian.dancechar.biz.core.scaffold.dto.*;
import com.litian.dancechar.biz.sysmgr.dbmgr.dto.SystemDBInfoDTO;
import com.litian.dancechar.biz.sysmgr.dbmgr.dto.SystemTableInfoDTO;
import com.litian.dancechar.biz.sysmgr.dbmgr.repository.dataobject.InformationSchemaTable;
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
 * 脚手架生成-数据库信息(ScaffoldGenDbInfo)表服务接口
 *
 * @author 01406831
 * @since 2021-06-21 14:33:21
 */
@Path("scaffoldGenDbInfoService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "scaffoldGenDbInfoService", tags = "代码生成(数据库配置)")
public interface ScaffoldGenDbInfoService {

    /**
     * scaffoldGenDbInfoService
     * 功能描述: 分页查询列表
     */
    @POST
    @Path("pageList")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    Result<PageResp<ScaffoldGenDbInfoDTO>> listPage(ScaffoldGenDbInfoQueryReqDTO req);

    /**
     * 功能描述: 新增/修改保存
     */
    @POST
    @Path("save")
    @ApiOperation(value = "新增/修改保存", notes = "新增/修改保存")
    Result<ChoosedTableColumnsRespDTO> save(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO);

    /**
     * 功能描述: 根据id删除记录
     */
    @POST
    @Path("deleteById")
    @ApiOperation(value = "根据id删除记录", notes = "根据id删除记录")
    Result<Boolean> deleteById(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO);

    /**
     * 功能描述: 根据id获取记录
     */
    @POST
    @Path("getById")
    @ApiOperation(value = "根据id获取记录", notes = "根据id获取记录")
    Result<ScaffoldGenDbInfoDTO> getById(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO);

    /**
     * 功能描述: 校验数据库连接
     */
    @POST
    @Path("checkDBConnection")
    @ApiOperation(value = "校验数据库连接", notes = "校验数据库连接")
    Result<String> checkDBConnection(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO);

    /**
     * 功能描述: 查询数据库下所有表
     */
    @POST
    @Path("listDBTable")
    @ApiOperation(value = "根据数据库ID查询数据库下所有表", notes = "根据数据库ID查询数据库下所有表")
    Result<List<String>> listDBTable(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO);

    /**
     * 功能描述: 根据dbname查询db信息
     */
    @POST
    @Path("listByDBName")
    @ApiOperation(value = "根据dbname查询db信息", notes = "根据dbname查询db信息")
    Result<List<ScaffoldGenDbInfoDTO>> listByDBName(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO);

    /**
     * 根据dbname和表名查询表信息
     */
    @POST
    @Path("listTablesComment")
    @ApiOperation(value = "根据dbname和表名查询表信息", notes = "根据dbname和表名查询表信息")
    Result<List<InformationSchemaTable>> listTablesComment(SystemTableInfoDTO systemTableInfoDTO);

    @POST
    @Path("pageListTables")
    @ApiOperation(value = "分页获取数据库表信息", notes = "分页获取数据库表信息")
    Result<PageResp<TableInfoRespDTO>> pageListTables(SystemTableInfoDTO systemTableInfoDTO);

    @POST
    @Path("listTables")
    @ApiOperation(value = "不分页获取数据库表信息", notes = "不分页获取数据库表信息")
    Result<List<TableInfoRespDTO>> listTables(SystemTableInfoDTO systemTableInfoDTO);


    @POST
    @Path("getDbInfoByGenInfoId")
    @ApiOperation(value = "获取配置的db信息", notes = "获取配置的db信息")
    Result<ScaffoldMultiTableRespDTO> getDbInfoByGenInfoId(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO);

    @POST
    @Path("listByGenInfoId")
    @ApiOperation(value = "根据scaffoldGenInfoId获取配置的db信息列表", notes = "根据scaffoldGenInfoId获取配置的db信息列表")
    Result<List<SystemDBInfoDTO>> listByGenInfoId(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO);

    @POST
    @Path("saveBatchTable")
    @ApiOperation(value = "保存多张独立的表数据", notes = "保存多张独立的表数据")
    Result<List<ChoosedTableColumnsRespDTO>> saveBatchTable(ScaffoldMultiTableDbDTO scaffoldMultiTableDbDTO);

    @POST
    @Path("getBatchDbInfoByGenInfoId")
    @ApiOperation(value = "获取配置的db信息", notes = "获取配置的db信息")
    Result<List<ScaffoldMultiTableRespDTO>> getBatchDbInfoByGenInfoId(ScaffoldMultiTableReqDTO multiTableReqDTO);

}
