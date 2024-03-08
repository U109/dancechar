package com.litian.dancechar.biz.sysmgr.esmgr.service;

import com.litian.dancechar.biz.sysmgr.esmgr.dto.EsOprDTO;
import com.litian.dancechar.framework.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * 类描述：ES7.6.0操作入口
 *
 * @author 01396106
 * @date 2021-10-18 13:39:03
 */
@Path("esOprService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "esOprService", tags = "ES操作入口")
public interface EsOprService {

    /**
     * 功能描述: 分页查询列表
     */
    @POST
    @Path("createIndex")
    @ApiOperation(value = "添加索引", notes = "添加索引")
    Result<Boolean> createIndex(EsOprDTO req);

    @POST
    @Path("deleteIndex")
    @ApiOperation(value = "删除索引", notes = "删除索引")
    Result<Boolean> deleteIndex(EsOprDTO req);

    @POST
    @Path("addInJson")
    @ApiOperation(value = "添加json格式数据", notes = "添加json格式数据")
    Result<Boolean> addInJson(EsOprDTO req);

    @POST
    @Path("updateInJson")
    @ApiOperation(value = "修改json格式数据", notes = "修改json格式数据")
    Result<Boolean> updateInJson(EsOprDTO req);

    @POST
    @Path("deleteById")
    @ApiOperation(value = "根据id删除数据", notes = "根据id删除数据")
    Result<Boolean> deleteById(EsOprDTO req);

    @POST
    @Path("getById")
    @ApiOperation(value = "根据id查询数据", notes = "根据id查询数据")
    Result<Map<String, Object>> getById(EsOprDTO req);
}