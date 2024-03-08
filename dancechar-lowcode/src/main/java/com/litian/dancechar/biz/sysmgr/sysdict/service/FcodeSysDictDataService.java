package com.litian.dancechar.biz.sysmgr.sysdict.service;

import cn.hutool.core.lang.Dict;
import com.litian.dancechar.biz.sysmgr.sysdict.dto.SysDictDataDTO;
import com.litian.dancechar.biz.sysmgr.sysdict.dto.SysDictDataQueryReqDTO;
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
 * 类描述：数据字典数据服务接口
 *
 * @author 01407390
 * @date 2021-09-28 15:16:56
 */
@Path("fcodeSysDictDataService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "fcodeSysDictDataService", tags = "数据字典数据")
public interface FcodeSysDictDataService {
    /**
     * 功能描述: 分页查询列表
     */
    @POST
    @Path("pageList")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    Result<PageResp<SysDictDataDTO>> listPage(SysDictDataQueryReqDTO req);

    /**
     * 功能描述: 新增保存
     */
    @POST
    @Path("save")
    @ApiOperation(value = "新增保存", notes = "新增保存")
    Result<Long> save(SysDictDataDTO sysDictDataDTO);

    /**
     * 功能描述: 修改保存
     */
    @POST
    @Path("update")
    @ApiOperation(value = "修改保存", notes = "修改保存")
    Result<Boolean> update(SysDictDataDTO sysDictDataDTO);

    /**
     * 功能描述: 根据id删除记录
     */
    @POST
    @Path("deleteById")
    @ApiOperation(value = "根据id删除记录", notes = "根据id删除记录")
    Result<Boolean> deleteById(SysDictDataDTO fcodeSysDictDataDTO);

    /**
     * 功能描述: 根据id获取记录
     */
    @POST
    @Path("getById")
    @ApiOperation(value = "根据id获取记录", notes = "根据id获取记录")
    Result<SysDictDataDTO> getWrapperById(SysDictDataDTO fcodeSysDictDataDTO);

    @POST
    @Path("listByTypeCode")
    @ApiOperation(value = "根据字典类型code-获取字典值列表", notes = "根据字典类型code-获取字典值列表")
    Result<List<SysDictDataDTO>> listByTypeCode(Dict dict);
}