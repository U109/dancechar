package com.litian.dancechar.biz.sysmgr.sentinelmgr.service;

import com.litian.dancechar.biz.sysmgr.sentinelmgr.dto.FcodeSentinelInfoDTO;
import com.litian.dancechar.biz.sysmgr.sentinelmgr.dto.FcodeSentinelInfoQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.sentinelmgr.repository.dataobject.FcodeSentinelInfoDO;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 类描述：sentinel配置信息服务接口
 *
 * @author 01410001
 * @date 2021-10-12 14:08:05
 */
@Path("fcodeSentinelInfoService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "fcodeSentinelInfoService", tags = "sentinel配置信息")
public interface FcodeSentinelInfoService {
    /**
     * 功能描述: 分页查询列表
     * @return
     */
    @POST
    @Path("pageList")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    Result<PageWrapperDTO<FcodeSentinelInfoDTO>> listPage(FcodeSentinelInfoQueryReqDTO req);

    /**
     * 功能描述: 新增保存
     */
    @POST
    @Path("save")
    @ApiOperation(value = "新增保存", notes = "新增保存")
    Result<Long> save(FcodeSentinelInfoDTO fcodeSentinelInfoDTO);

    /**
     * 功能描述: 修改记录
     */
    @POST
    @Path("update")
    @ApiOperation(value = "修改记录", notes = "修改记录")
    Result<Boolean> update(FcodeSentinelInfoDTO fcodeSentinelInfoDTO);

    /**
     * 功能描述: 根据id删除记录
     */
    @POST
    @Path("deleteById")
    @ApiOperation(value = "根据id删除记录", notes = "根据id删除记录")
    Result<Boolean> deleteById(FcodeSentinelInfoDTO fcodeSentinelInfoDTO);

    /**
     * 功能描述: 根据id获取记录
     * @return
     */
    @POST
    @Path("getById")
    @ApiOperation(value = "根据id获取记录", notes = "根据id获取记录")
    Result<FcodeSentinelInfoDO> getWrapperById(FcodeSentinelInfoDTO fcodeSentinelInfoDTO);

    @GET
    @Path("getAllSentinelInfo")
    @ApiOperation(value = "获取所有sentinel配置信息列表", notes = "获取所有sentinel配置信息列表")
    Result<List<FcodeSentinelInfoDTO>> getAllSentinelInfo();
}