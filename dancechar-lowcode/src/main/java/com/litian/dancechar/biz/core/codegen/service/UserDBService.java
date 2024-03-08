package com.litian.dancechar.biz.core.codegen.service;

import com.litian.dancechar.biz.core.codegen.dto.UserDBDTO;
import com.litian.dancechar.biz.core.codegen.dto.UserDBQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.dbmgr.dto.DynamicDBInfo;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.page.PageResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 类描述：userDB 服务接口
 *
 * @author terryhl
 * @date 2021-06-15 14:43:47
 */
@Path("userDBService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "userDBService", tags = "数据库管理")
public interface UserDBService {


    /**
     * 功能描述: 查询我的数据库信息列表
     */
    @POST
    @Path("listMyUserDB")
    @ApiOperation(value = "查询我的数据库信息列表", notes = "查询我的数据库信息列表")
    Result<PageResp<UserDBDTO>> listMyUserDB(UserDBQueryReqDTO req);

    /**
     * 功能描述: 查询db信息
     */
    @POST
    @Path("getByDBCode")
    @ApiOperation(value = "查询db信息", notes = "查询db信息")
    Result<UserDBDTO> getByDBCode(UserDBDTO userDBDTO);

    /**
     * 功能描述: 删除db配置信息
     */
    @POST
    @Path("deleteByDBCode")
    @ApiOperation(value = "删除db配置信息", notes = "删除db配置信息")
    Result<Boolean> deleteByDBCode(UserDBDTO userDBDTO);

    /**
     * 功能描述: 校验数据库连接
     */
    @POST
    @Path("checkConnection")
    @ApiOperation(value = "校验数据库连接", notes = "校验数据库连接")
    Result<Void> checkConnection(UserDBDTO userDBDTO);

    /**
     * 功能描述: 解析dburl
     */
    @POST
    @Path("resolveDBUrl")
    @ApiOperation(value = "解析dburl", notes = "解析dburl")
    Result<DynamicDBInfo> resolveDBUrl(UserDBDTO userDBDTO);

    /**
     * 功能描述: 分页查询列表
     */
    @POST
    @Path("listPage")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    Result<PageResp<UserDBDTO>> listPage(UserDBQueryReqDTO req);

    /**
     * 功能描述: 新增保存
     */
    @POST
    @Path("save")
    @ApiOperation(value = "新增保存", notes = "新增保存")
    Result<Boolean> save(UserDBDTO userDBDTO);

    /**
     * 功能描述: 修改记录
     */
    @POST
    @Path("update")
    @ApiOperation(value = "修改记录", notes = "修改记录")
    Result<Boolean> update(UserDBDTO userDBDTO);

    /**
     * 功能描述: 根据id删除记录
     */
    @POST
    @Path("deleteById")
    @ApiOperation(value = "根据id删除记录", notes = "根据id删除记录")
    Result<Boolean> deleteById(UserDBDTO userDBDTO);

    /**
     * 功能描述: 根据id获取记录
     */
    @POST
    @Path("getById")
    @ApiOperation(value = "根据id获取记录", notes = "根据id获取记录")
    Result<UserDBDTO> getById(UserDBDTO userDBDTO);
}