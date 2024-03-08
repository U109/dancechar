package com.litian.dancechar.biz.sysmgr.kafkamgr.service;

import com.litian.dancechar.biz.sysmgr.kafkamgr.dto.FcodeKafkaBaseInfoDTO;
import com.litian.dancechar.biz.sysmgr.kafkamgr.dto.FcodeKafkaInfoDTO;
import com.litian.dancechar.biz.sysmgr.kafkamgr.dto.FcodeKafkaInfoQueryReqDTO;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.page.PageResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 类描述：kafka配置信息服务接口
 *
 * @author 01410001
 * @date 2021-10-12 14:07:34
 */
@Path("fcodeKafkaInfoService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "fcodeKafkaInfoService", tags = "kafka配置信息")
public interface FcodeKafkaInfoService {
    /**
     * 功能描述: 分页查询列表
     */
    @POST
    @Path("pageList")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    Result<PageResp<FcodeKafkaInfoDTO>> listPage(FcodeKafkaInfoQueryReqDTO req);

    /**
     * 功能描述: 新增保存
     */
    @POST
    @Path("save")
    @ApiOperation(value = "新增保存", notes = "新增保存")
    Result<Long> save(FcodeKafkaInfoDTO fcodeKafkaInfoDTO);

    /**
     * 功能描述: 修改记录
     */
    @POST
    @Path("update")
    @ApiOperation(value = "修改记录", notes = "修改记录")
    Result<Boolean> update(FcodeKafkaInfoDTO fcodeKafkaInfoDTO);

    /**
     * 功能描述: 根据id删除记录
     */
    @POST
    @Path("deleteById")
    @ApiOperation(value = "根据id删除记录", notes = "根据id删除记录")
    Result<Boolean> deleteById(FcodeKafkaInfoDTO fcodeKafkaInfoDTO);

    /**
     * 功能描述: 根据id获取记录
     */
    @POST
    @Path("getById")
    @ApiOperation(value = "根据id获取记录", notes = "根据id获取记录")
    Result<FcodeKafkaBaseInfoDTO> getWrapperById(FcodeKafkaInfoDTO fcodeKafkaInfoDTO);

    @GET
    @Path("getAllKafkaInfo")
    @ApiOperation(value = "获取所有kafka配置信息列表", notes = "获取所有kafka配置信息列表")
    Result<List<FcodeKafkaInfoDTO>> getAllKafkaInfo();
}