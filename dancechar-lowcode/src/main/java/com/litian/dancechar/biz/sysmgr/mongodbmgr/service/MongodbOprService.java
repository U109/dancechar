package com.litian.dancechar.biz.sysmgr.mongodbmgr.service;

import com.litian.dancechar.biz.sysmgr.mongodbmgr.dto.MongodbDemoDTO;
import com.litian.dancechar.framework.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("mongodbServiceDemo")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "mongodbServiceDemo", tags = "mongodb测试")
public interface MongodbOprService {

    @POST
    @Path("save")
    @ApiOperation(value = "保存", notes = "保存")
    Result<Boolean> save(MongodbDemoDTO demo);

    @POST
    @Path("queryById")
    @ApiOperation(value = "根据id获取数据", notes = "根据id获取数据")
    Result<MongodbDemoDTO> queryById(MongodbDemoDTO demo);

    @POST
    @Path("deleteById")
    @ApiOperation(value = "根据id删除数据", notes = "根据id删除数据")
    Result<Boolean> deleteById(MongodbDemoDTO demo);

    @POST
    @Path("updateFirst")
    @ApiOperation(value = "修改第一个匹配到的数据", notes = "根据id删除数据")
    Result<Boolean> updateFirst(MongodbDemoDTO demo);

}
