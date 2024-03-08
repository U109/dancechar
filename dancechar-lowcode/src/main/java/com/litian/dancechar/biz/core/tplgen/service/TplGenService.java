package com.litian.dancechar.biz.core.tplgen.service;

import com.litian.dancechar.biz.core.tplgen.dto.TplGenParamDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 类描述：代码生成基础配置服务接口
 *
 * @author 01402521
 * @date 2021/3/31 12:49
 */
@Path("tplGenService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "tplGenService", tags = "代码生成")
public interface TplGenService {

    /**
     * 代码生成基础配置生成CRUD
     */
    @POST
    @Path("tplGen")
    @ApiOperation(value = "生成操作", notes = "生成操作")
    void tplGen(TplGenParamDTO tplGenParamDTO, @Context HttpServletResponse response);

    void tplGenList(List<TplGenParamDTO> tplGenParamDTO, @Context HttpServletResponse response);


    void tplGenSql(TplGenParamDTO tplGenParamDTO, @Context HttpServletResponse response);

}