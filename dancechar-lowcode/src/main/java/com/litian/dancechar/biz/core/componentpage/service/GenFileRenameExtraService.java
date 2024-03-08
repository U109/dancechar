package com.litian.dancechar.biz.core.componentpage.service;

import com.litian.dancechar.biz.core.componentpage.dto.GenFileRenameExtraDTO;
import com.litian.dancechar.biz.core.componentpage.dto.GenFileRenameExtraQueryReqDTO;
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
 * 类描述：genFileRenameExtra 服务接口
 *
 * @author fcoder
 * @date 2021-06-30 16:42:34
 */
@Path("genFileRenameExtraService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "genFileRenameExtraService", tags = "生成文件重命名")
public interface GenFileRenameExtraService {
    /**
     * 功能描述: 分页查询列表
     */
    @POST
    @Path("pageList")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    Result<PageResp<GenFileRenameExtraDTO>> listPage(GenFileRenameExtraQueryReqDTO req);

    /**
     * 功能描述: 新增保存
     */
    @POST
    @Path("save")
    @ApiOperation(value = "新增保存", notes = "新增保存")
    Result<Boolean> save(GenFileRenameExtraDTO genFileRenameExtraDTO);

    /**
     * 功能描述: 修改记录
     */
    @POST
    @Path("update")
    @ApiOperation(value = "修改记录", notes = "修改记录")
    Result<Boolean> update(GenFileRenameExtraDTO genFileRenameExtraDTO);

    /**
     * 功能描述: 根据id删除记录
     */
    @POST
    @Path("deleteById")
    @ApiOperation(value = "根据id删除记录", notes = "根据id删除记录")
    Result<Boolean> deleteById(GenFileRenameExtraDTO genFileRenameExtraDTO);

    /**
     * 功能描述: 根据id获取记录
     */
    @POST
    @Path("getById")
    @ApiOperation(value = "根据id获取记录", notes = "根据id获取记录")
    Result<GenFileRenameExtraDTO> getById(GenFileRenameExtraDTO genFileRenameExtraDTO);
}