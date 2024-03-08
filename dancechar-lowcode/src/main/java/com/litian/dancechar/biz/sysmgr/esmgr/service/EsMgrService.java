package com.litian.dancechar.biz.sysmgr.esmgr.service;

import com.litian.dancechar.biz.sysmgr.esmgr.dto.EsMgrDTO;
import com.litian.dancechar.biz.sysmgr.esmgr.dto.EsMgrQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.esmgr.repository.dataobject.EsMgrDO;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 类描述：ES连接信息管理服务接口
 *
 * @author 01396106
 * @date 2021-10-18 13:39:03
 */
@Path("esMgrService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "esMgrService", tags = "ES连接信息管理")
public interface EsMgrService {
    /**
     * 功能描述: 分页查询列表
     */
    @POST
    @Path("pageList")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    Result<PageWrapperDTO<EsMgrDTO>> listPage(EsMgrQueryReqDTO req);

    /**
     * 功能描述: 新增保存
     * @return
     */
    @POST
    @Path("save")
    @ApiOperation(value = "新增保存", notes = "新增保存")
    Result<Object> save(EsMgrDTO esMgrDTO);

    /**
     * 功能描述: 修改记录
     */
    @POST
    @Path("update")
    @ApiOperation(value = "修改记录", notes = "修改记录")
    Result<Boolean> update(EsMgrDTO esMgrDTO);

    /**
     * 功能描述: 根据id删除记录
     */
    @POST
    @Path("deleteById")
    @ApiOperation(value = "根据id删除记录", notes = "根据id删除记录")
    Result<Boolean> deleteById(EsMgrDTO esMgrDTO);

    /**
     * 功能描述: 根据id获取记录
     * @return
     */
    @POST
    @Path("getById")
    @ApiOperation(value = "根据id获取记录", notes = "根据id获取记录")
    Result<EsMgrDO> getWrapperById(EsMgrDTO esMgrDTO);
}