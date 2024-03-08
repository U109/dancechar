package com.litian.dancechar.biz.core.tabledetailinfo.service;

import com.litian.dancechar.biz.core.tabledetailinfo.dto.TableDetailInfoDTO;
import com.litian.dancechar.biz.core.tabledetailinfo.dto.TableDetailInfoQueryReqDTO;
import com.litian.dancechar.biz.core.tplgen.dto.TplGenTableColumnDTO;
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
 * 类描述：代码生成-表关联字段信息表服务接口
 *
 * @author 853523
 * @date 2021-09-22 10:30:53
 */
@Path("tableDetailInfoService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "tableDetailInfoService", tags = "代码生成-表关联字段信息表")
public interface TableDetailInfoService {
    /**
     * 功能描述: 分页查询列表
     */
    @POST
    @Path("pageList")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    Result<PageResp<TableDetailInfoDTO>> listPage(TableDetailInfoQueryReqDTO req);

    /**
     * 功能描述: 新增保存
     */
    @POST
    @Path("save")
    @ApiOperation(value = "新增保存", notes = "新增保存")
    Result<Boolean> save(List<TableDetailInfoDTO> tableDetailInfos);

    /**
     * 功能描述: 修改记录
     */
    @POST
    @Path("update")
    @ApiOperation(value = "修改记录", notes = "修改记录")
    Result<Boolean> update(List<TableDetailInfoDTO> tableDetailInfos);

    /**
     * 功能描述: 根据id删除记录
     */
    @POST
    @Path("deleteById")
    @ApiOperation(value = "根据id删除记录", notes = "根据id删除记录")
    Result<Boolean> deleteById(TableDetailInfoDTO tableDetailInfoDTO);

    /**
     * 功能描述: 根据id获取记录
     */
    @POST
    @Path("getById")
    @ApiOperation(value = "根据id获取记录", notes = "根据id获取记录")
    Result<TableDetailInfoDTO> getWrapperById(TableDetailInfoDTO tableDetailInfoDTO);

    @POST
    @Path("getConfigListByGenInfoId")
    @ApiOperation(value = "获取工程的配置列表", notes = "获取工程的配置列表")
    Result<List<TplGenTableColumnDTO>> getConfigListByGenInfoId(TableDetailInfoDTO tableDetailInfoDTO);
}