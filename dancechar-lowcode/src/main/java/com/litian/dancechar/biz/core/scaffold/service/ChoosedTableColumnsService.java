package com.litian.dancechar.biz.core.scaffold.service;

import com.litian.dancechar.biz.core.scaffold.dto.ChoosedTableColumnsReqDTO;
import com.litian.dancechar.biz.core.scaffold.dto.FunctionGenSqlDTO;
import com.litian.dancechar.framework.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 类描述：已选择的库表字段
 *
 * @author 01410001
 * @date 2021/08/07 18:12
 */
@Path("choosedTableColumnsService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "choosedTableColumnsService", tags = "选择表字段保存")
public interface ChoosedTableColumnsService {

    @POST
    @Path("saveChoosedTable")
    @ApiOperation(value = "保存单功能字段及sql生成", notes = "保存单功能字段及sql生成")
    Result<Boolean> saveChoosedTable(ChoosedTableColumnsReqDTO choosedTableColumnsReqDTO);

    @POST
    @Path("previewSql")
    @ApiOperation(value = "预览sql", notes = "预览sql")
    Result<String> previewSql(FunctionGenSqlDTO genSqlDTO);
}
