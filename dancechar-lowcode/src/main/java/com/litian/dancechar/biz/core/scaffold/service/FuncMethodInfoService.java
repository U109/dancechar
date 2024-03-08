package com.litian.dancechar.biz.core.scaffold.service;

import com.litian.dancechar.biz.core.scaffold.dto.FuncMethodListRespDTO;
import com.litian.dancechar.biz.core.scaffold.dto.FuncMethodReqDTO;
import com.litian.dancechar.framework.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 类描述：方法详情
 *
 * @author 01410001
 * @date 2021/09/10 16:08
 */
@Path("methodInfoService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "methodInfoService", tags = "获取前端代码生成数据")
public interface FuncMethodInfoService {

    @POST
    @Path("getMethodInfoList")
    @ApiOperation(value = "方法列表信息查询", notes = "方法列表信息查询")
    Result<FuncMethodListRespDTO> getMethodInfoList(FuncMethodReqDTO funcMethodReqDTO);
}
