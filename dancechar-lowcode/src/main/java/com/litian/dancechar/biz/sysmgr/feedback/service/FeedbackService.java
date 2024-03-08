package com.litian.dancechar.biz.sysmgr.feedback.service;

import com.litian.dancechar.biz.sysmgr.feedback.dto.FeedbackDTO;
import com.litian.dancechar.biz.sysmgr.feedback.dto.FeedbackQueryReqDTO;
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
 * 意见反馈信息服务接口
 *
 * @author 01406831
 * @since 2021-06-21 10:44:48
 */
@Path("feedbackService")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Api(value = "feedbackService", tags = "意见反馈")
public interface FeedbackService {

    /**
     * 功能描述: 分页查询列表
     */
    @POST
    @Path("pageList")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    Result<PageResp<FeedbackDTO>> listPage(FeedbackQueryReqDTO req);

    /**
     * 功能描述: 新增保存
     */
    @POST
    @Path("save")
    @ApiOperation(value = "新增保存", notes = "新增保存")
    Result<Boolean> save(FeedbackDTO feedbackDTO);

    /**
     * 功能描述: 处理记录
     */
    @POST
    @Path("deal")
    @ApiOperation(value = "处理记录", notes = "处理记录")
    Result<Boolean> deal(FeedbackDTO feedbackDTO);

    /**
     * 功能描述: 根据id删除记录
     */
    @POST
    @Path("deleteById")
    @ApiOperation(value = "根据id删除记录", notes = "根据id删除记录")
    Result<Boolean> deleteById(FeedbackDTO feedbackDTO);

    /**
     * 功能描述: 根据id获取记录
     */
    @POST
    @Path("getById")
    @ApiOperation(value = "根据id获取记录", notes = "根据id获取记录")
    Result<FeedbackDTO> getById(FeedbackDTO feedbackDTO);
}