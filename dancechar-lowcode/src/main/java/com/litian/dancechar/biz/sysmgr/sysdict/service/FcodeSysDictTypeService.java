package com.litian.dancechar.biz.sysmgr.sysdict.service;

import com.litian.dancechar.biz.sysmgr.sysdict.dto.SysDictTypeDTO;
import com.litian.dancechar.biz.sysmgr.sysdict.dto.SysDictTypeQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.sysdict.dto.SysDictValueDTO;
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
 * 类描述：数据字典类型服务接口
 *
 * @author 01407390
 * @date 2021-09-28 15:47:15
 */
@Path("fcodeSysDictTypeService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "fcodeSysDictTypeService", tags = "数据字典类型")
public interface FcodeSysDictTypeService {
    /**
     * 功能描述: 分页查询列表
     */
    @POST
    @Path("pageList")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    Result<PageResp<SysDictTypeDTO>> listPage(SysDictTypeQueryReqDTO req);

    /**
     * 功能描述: 新增保存
     */
    @POST
    @Path("save")
    @ApiOperation(value = "新增保存", notes = "新增保存")
    Result<Long> save(SysDictTypeDTO fcodeSysDictTypeDTO);

    /**
     * 功能描述: 修改记录
     */
    @POST
    @Path("update")
    @ApiOperation(value = "修改记录", notes = "修改记录")
    Result<Boolean> update(SysDictTypeDTO fcodeSysDictTypeDTO);

    /**
     * 功能描述: 根据id删除记录
     */
    @POST
    @Path("deleteById")
    @ApiOperation(value = "根据id删除记录", notes = "根据id删除记录")
    Result<Boolean> deleteById(SysDictTypeDTO fcodeSysDictTypeDTO);

    /**
     * 功能描述: 根据id获取包装记录
     */
    @POST
    @Path("getTypeAndDictByTypeId")
    @ApiOperation(value = "根据id获取包装记录", notes = "根据id获取包装记录")
    Result<List<SysDictValueDTO>> getTypeAndDictByTypeId(SysDictTypeDTO fcodeSysDictTypeDTO);

    /**
     * 功能描述: 根据id获取包装记录
     */
    @POST
    @Path("getById")
    @ApiOperation(value = "根据id获取记录", notes = "根据id获取记录")
    Result<SysDictTypeDTO> getById(SysDictTypeDTO fcodeSysDictTypeDTO);

    @POST
    @Path("getSysDictList")
    @ApiOperation(value = "获取所有下拉框", notes = "根据id获取记录")
    Result<List<SysDictTypeDTO>> getSysDictList(SysDictTypeDTO fcodeSysDictTypeDTO);
}