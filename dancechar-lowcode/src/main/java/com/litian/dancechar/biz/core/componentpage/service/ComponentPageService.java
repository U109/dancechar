package com.litian.dancechar.biz.core.componentpage.service;

import com.litian.dancechar.biz.core.componentpage.dto.GenFileInfoDTO;
import com.litian.dancechar.biz.core.componentpage.dto.GenFileMultiTableDTO;
import com.litian.dancechar.biz.core.scaffold.dto.ScaffoldMultiTableReqDTO;
import com.litian.dancechar.biz.core.tplgen.dto.TplGenParamDTO;
import com.litian.dancechar.framework.common.base.Result;
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
 * @date 2021/3/31 12:50
 */
@Path("componentPageService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "componentPageService", tags = "组件页管理")
public interface ComponentPageService {

    /**
     * initTplGenParamDTO
     */
    @POST
    @Path("initTplGenParamDTO")
    @ApiOperation(value = "初始化数据", notes = "初始化数据")
    Result<TplGenParamDTO> initTplGenParamDTO(TplGenParamDTO tplGenParamDTO)throws Exception;

    /**
     * 显示目录树
     */
    @POST
    @Path("loadPage")
    @ApiOperation(value = "组件页展示", notes = "组件页展示")
    Result<List<GenFileInfoDTO>> loadPage(TplGenParamDTO tplGenParamDTO, @Context HttpServletResponse response)throws Exception;

    /**
     * 预览文件
     */
    @POST
    @Path("previewFile")
    @ApiOperation(value = "预览文件", notes = "预览文件")
    Result<String> previewFile(TplGenParamDTO tplGenParamDTO, @Context HttpServletResponse response)throws Exception;


    /**
     * 预览文件
     */
    @POST
    @Path("previewProjectFile")
    @ApiOperation(value = "预览文件", notes = "预览文件")
    Result<String> previewProjectFile(TplGenParamDTO tplGenParamDTO, @Context HttpServletResponse response)throws Exception;


    /**
     * 重命名文件
     */
    @POST
    @Path("renameClassName")
    @ApiOperation(value = "重命名", notes = "重命名")
    Result<Boolean> renameClassName(GenFileInfoDTO genFileInfoDTO, @Context HttpServletResponse response)throws Exception;

    @POST
    @Path("loadMultiTableFile")
    @ApiOperation(value = "预览多个工程文件", notes = "预览多个工程文件")
    Result<List<GenFileInfoDTO>> loadMultiTableFile(ScaffoldMultiTableReqDTO scaffoldMultiTableReqDTO, @Context HttpServletResponse response)throws Exception;

    @POST
    @Path("newPreviewFile")
    @ApiOperation(value = "预览文件", notes = "预览文件")
    Result<String> newPreviewFile(GenFileMultiTableDTO multiTableDTO, @Context HttpServletResponse response)throws Exception;
}