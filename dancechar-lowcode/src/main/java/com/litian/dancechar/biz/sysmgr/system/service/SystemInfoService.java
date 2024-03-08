package com.litian.dancechar.biz.sysmgr.system.service;

import com.litian.dancechar.biz.sysmgr.system.dto.SystemInfoDTO;
import com.litian.dancechar.biz.sysmgr.system.dto.SystemInfoQueryReqDTO;
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
 * 类描述：systemInfo 服务接口
 *
 * @author fcoder
 * @date 2021-06-26 18:49:31
 */
@Path("systemInfoService" )
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "systemInfoService", tags = "工程管理" )
public interface SystemInfoService {
    /**
     * 功能描述: 分页查询列表
     */
    @POST
    @Path("pageList" )
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表" )
    Result<PageResp<SystemInfoDTO>> listPage(SystemInfoQueryReqDTO req);

    /**
     * 功能描述: 查询列表
     */
    @POST
    @Path("findList" )
    @ApiOperation(value = "查询列表", notes = "查询列表" )
    Result<List<SystemInfoDTO>> findList(SystemInfoQueryReqDTO req);

    /**
     * 功能描述: 新增保存
     */
    @POST
    @Path("save" )
    @ApiOperation(value = "新增保存", notes = "新增保存" )
    Result<SystemInfoDTO> save(SystemInfoDTO systemInfoDTO);

    /**
     * 功能描述: 修改记录
     */
    @POST
    @Path("update" )
    @ApiOperation(value = "修改记录", notes = "修改记录" )
    Result<Boolean> update(SystemInfoDTO systemInfoDTO);

    /**
     * 功能描述: 根据id删除记录
     */
    @POST
    @Path("deleteById" )
    @ApiOperation(value = "根据id删除记录", notes = "根据id删除记录" )
    Result<Boolean> deleteById(SystemInfoDTO systemInfoDTO);

    /**
     * 功能描述: 根据id获取记录
     */
    @POST
    @Path("getById" )
    @ApiOperation(value = "根据id获取记录", notes = "根据id获取记录" )
    Result<SystemInfoDTO> getById(SystemInfoDTO systemInfoDTO);


    /**
     * 功能描述: 根据id获取记录
     */
    @POST
    @Path("getBySystemCode" )
    @ApiOperation(value = "根据SystemCode获取记录", notes = "根据SystemCode获取记录" )
    Result<SystemInfoDTO> getBySystemCode(SystemInfoDTO systemInfoDTO);
}
