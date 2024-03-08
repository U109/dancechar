package com.litian.dancechar.biz.sysmgr.esmgr.service;

import com.litian.dancechar.biz.sysmgr.esmgr.dto.EsInfoDTO;
import com.litian.dancechar.biz.sysmgr.esmgr.dto.EsInfoQueryReqDTO;
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
 * 类描述：es连接信息管理服务接口
 *
 * @author 01406831
 * @date 2021-11-04 15:28:33
 */
@Path("esInfoService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "esInfoService", tags = "es连接信息管理")
public interface EsInfoService {
    /**
     * 功能描述: 分页查询列表
     */
    @POST
    @Path("pageList")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    Result<PageResp<EsInfoDTO>> listPage(EsInfoQueryReqDTO req);

    /**
     * 功能描述: 新增保存
     */
    @POST
    @Path("save")
    @ApiOperation(value = "新增保存", notes = "新增保存")
    Result<Long> save(EsInfoDTO esInfoDTO);

    /**
     * 功能描述: 修改记录
     */
    @POST
    @Path("update")
    @ApiOperation(value = "修改记录", notes = "修改记录")
    Result<Boolean> update(EsInfoDTO esInfoDTO);

    /**
     * 功能描述: 根据id删除记录
     */
    @POST
    @Path("deleteById")
    @ApiOperation(value = "根据id删除记录", notes = "根据id删除记录")
    Result<Boolean> deleteById(EsInfoDTO esInfoDTO);

    /**
     * 功能描述: 根据id获取记录
     */
    @POST
    @Path("getById")
    @ApiOperation(value = "根据id获取记录", notes = "根据id获取记录")
    Result<EsInfoDTO> getWrapperById(EsInfoDTO esInfoDTO);

    /**
     * 功能描述: 根据版本号-获取记录列表
     */
    @POST
    @Path("listByVersion")
    @ApiOperation(value = "根据版本号-获取记录列表", notes = "根据版本号-获取记录列表")
    Result<List<EsInfoDTO>> listByVersion(EsInfoDTO esInfoDTO);
}