package com.litian.dancechar.biz.sysmgr.mongodbmgr.service;

import com.litian.dancechar.biz.sysmgr.mongodbmgr.dto.FcodeMongodbBaseInfoDTO;
import com.litian.dancechar.biz.sysmgr.mongodbmgr.dto.FcodeMongodbInfoDTO;
import com.litian.dancechar.biz.sysmgr.mongodbmgr.dto.FcodeMongodbInfoQueryReqDTO;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.page.PageResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 类描述：mongodb配置信息服务接口
 *
 * @author 01410001
 * @date 2021-11-08 16:16:16
 */
@Path("fcodeMongodbInfoService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "fcodeMongodbInfoService", tags = "mongodb配置信息")
public interface FcodeMongodbInfoService {
    /**
     * 功能描述: 分页查询列表
     */
    @POST
    @Path("pageList")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    Result<PageResp<FcodeMongodbInfoDTO>> listPage(FcodeMongodbInfoQueryReqDTO req);

    /**
     * 功能描述: 新增保存
     */
    @POST
    @Path("save")
    @ApiOperation(value = "新增保存", notes = "新增保存")
    Result<Long> save(FcodeMongodbInfoDTO fcodeMongodbInfoDTO);

    /**
     * 功能描述: 修改记录
     */
    @POST
    @Path("update")
    @ApiOperation(value = "修改记录", notes = "修改记录")
    Result<Boolean> update(FcodeMongodbInfoDTO fcodeMongodbInfoDTO);

    /**
     * 功能描述: 根据id删除记录
     */
    @POST
    @Path("deleteById")
    @ApiOperation(value = "根据id删除记录", notes = "根据id删除记录")
    Result<Boolean> deleteById(FcodeMongodbInfoDTO fcodeMongodbInfoDTO);

    /**
     * 功能描述: 根据id获取记录
     */
    @POST
    @Path("getById")
    @ApiOperation(value = "根据id获取记录", notes = "根据id获取记录")
    Result<FcodeMongodbInfoDTO> getWrapperById(FcodeMongodbInfoDTO fcodeMongodbInfoDTO);


    @GET
    @Path("getMongodbList")
    @ApiOperation(value = "获取mongodb配置列表", notes = "获取mongodb配置列表")
    Result<List<FcodeMongodbBaseInfoDTO>> getMongodbList();
}