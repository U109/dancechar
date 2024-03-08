package com.litian.dancechar.core.biz.activity.acttask.controller;

import com.litian.dancechar.core.biz.activity.acttask.dto.ActTaskConfigInfoReqDTO;
import com.litian.dancechar.core.biz.activity.acttask.dto.ActTaskConfigInfoRespDTO;
import com.litian.dancechar.core.biz.activity.acttask.dto.ActTaskConfigInfoSaveDTO;
import com.litian.dancechar.core.biz.activity.acttask.service.ActTaskConfigInfoService;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.validator.ValidatorUtil;
import com.litian.dancechar.framework.cache.redis.distributelock.annotation.Lock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 活动任务配置信息业务处理
 *
 * @author tojson
 * @date 2022/7/9 11:26
 */
@Api(tags = "活动任务配置信息相关api")
@RestController
@Slf4j
@RequestMapping(value = "/act/task/config")
public class ActTaskConfigInfoController {
    @Resource
    private ActTaskConfigInfoService actTaskConfigInfoService;

    @ApiOperation(value = "分页查询活动任务配置列表", notes = "分页查询活动任务配置列表")
    @PostMapping("listPaged")
    public RespResult<PageWrapperDTO<ActTaskConfigInfoRespDTO>> listPaged(@RequestBody ActTaskConfigInfoReqDTO req) {
        return actTaskConfigInfoService.listPaged(req);
    }

    @ApiOperation(value = "根据Id查询活动任务配置信息", notes = "根据Id查询活动任务配置信息")
    @PostMapping("findById")
    public RespResult<ActTaskConfigInfoRespDTO> findById(@RequestBody ActTaskConfigInfoReqDTO req) {
        return RespResult.success(DCBeanUtil.copyNotNull(actTaskConfigInfoService.findById(req.getId()),
                new ActTaskConfigInfoRespDTO()));
    }

    @ApiOperation(value = "新增保存活动任务信息", notes = "新增保存活动任务配置信息")
    @PostMapping("saveWithInsert")
    @Lock(value = "#actInfoSaveDTO.actNo", lockFailMsg = "请勿重复提交", expireTime = 3000)
    public RespResult<Boolean> saveWithInsert(@RequestBody ActTaskConfigInfoSaveDTO actInfoSaveDTO) {
        log.info("新增保存活动任务配置....");
        ValidatorUtil.validate(actInfoSaveDTO);
        return RespResult.success(actTaskConfigInfoService.insert(actInfoSaveDTO));
    }

    @ApiOperation(value = "修改保存活动任务信息", notes = "修改保存活动任务配置信息")
    @PostMapping("saveWithUpdate")
    public RespResult<Boolean> saveWithUpdate(@RequestBody ActTaskConfigInfoSaveDTO actInfoSaveDTO) {
        log.info("新增保存活动任务配置....");
        ValidatorUtil.validate(actInfoSaveDTO);
        return RespResult.success(actTaskConfigInfoService.update(actInfoSaveDTO));
    }
}