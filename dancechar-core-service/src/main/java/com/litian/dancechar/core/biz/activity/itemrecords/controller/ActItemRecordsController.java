package com.litian.dancechar.core.biz.activity.itemrecords.controller;

import com.litian.dancechar.core.biz.activity.itemrecords.dto.ActItemRecordReqDTO;
import com.litian.dancechar.core.biz.activity.itemrecords.dto.ActItemRecordsRespDTO;
import com.litian.dancechar.core.biz.activity.itemrecords.service.ActItemRecordService;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 活动领取信息业务处理
 *
 * @author tojson
 * @date 2022/7/9 11:26
 */
@Api(tags = "活动领取信息相关api")
@RestController
@Slf4j
@RequestMapping(value = "/act/item/records")
public class ActItemRecordsController {
    @Resource
    private ActItemRecordService actItemRecordService;

    @ApiOperation(value = "分页查询活动领取列表", notes = "分页查询活动领取列表")
    @PostMapping("listPaged")
    public RespResult<PageWrapperDTO<ActItemRecordsRespDTO>> listPaged(@RequestBody ActItemRecordReqDTO req) {
        return actItemRecordService.listPaged(req);
    }

    @ApiOperation(value = "根据Id查询活动领取信息", notes = "根据Id查询活动领取信息")
    @PostMapping("findById")
    public RespResult<ActItemRecordsRespDTO> findById(@RequestBody ActItemRecordReqDTO req) {
        return RespResult.success(DCBeanUtil.copyNotNull(actItemRecordService.findById(req.getId()),
                new ActItemRecordsRespDTO()));
    }
}