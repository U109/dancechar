package com.litian.dancechar.biz.sysmgr.redismgr.service;

import com.litian.dancechar.biz.sysmgr.redismgr.dto.FcodeRedisInfoDTO;
import com.litian.dancechar.biz.sysmgr.redismgr.dto.FcodeRedisInfoQueryReqDTO;
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
 * 类描述：Redis信息管理服务接口
 *
 * @author 01396106
 * @date 2021-10-12 11:05:08
 */
@Path("fcodeRedisInfoService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "fcodeRedisInfoService", tags = "Redis信息管理")
public interface FcodeRedisInfoService {

    /**
     * 功能描述: 分页查询列表
     */
    @POST
    @Path("dropdownlist")
    @ApiOperation(value = "下拉框选择列表", notes = "下拉框选择列表")
    Result<List<FcodeRedisInfoDTO>> dropdownlist(FcodeRedisInfoQueryReqDTO req);


    /**
     * 功能描述: 分页查询列表
     */
    @POST
    @Path("pageList")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    Result<PageResp<FcodeRedisInfoDTO>> listPage(FcodeRedisInfoQueryReqDTO req);

    /**
     * 功能描述: 新增保存
     */
    @POST
    @Path("save")
    @ApiOperation(value = "新增保存", notes = "新增保存")
    Result<String> save(FcodeRedisInfoDTO fcodeRedisInfoDTO);

    /**
     * 功能描述: 修改记录
     */
    @POST
    @Path("update")
    @ApiOperation(value = "修改记录", notes = "修改记录")
    Result<Boolean> update(FcodeRedisInfoDTO fcodeRedisInfoDTO);

    /**
     * 功能描述: 根据id删除记录
     */
    @POST
    @Path("deleteById")
    @ApiOperation(value = "根据id删除记录", notes = "根据id删除记录")
    Result<Boolean> deleteById(FcodeRedisInfoDTO fcodeRedisInfoDTO);

    /**
     * 功能描述: 根据id获取记录
     */
    @POST
    @Path("getById")
    @ApiOperation(value = "根据id获取记录", notes = "根据id获取记录")
    Result<FcodeRedisInfoDTO> getWrapperById(FcodeRedisInfoDTO fcodeRedisInfoDTO);
}