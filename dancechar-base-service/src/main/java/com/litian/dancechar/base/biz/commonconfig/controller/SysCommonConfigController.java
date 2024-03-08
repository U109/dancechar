package com.litian.dancechar.base.biz.commonconfig.controller;

import com.litian.dancechar.base.biz.commonconfig.dto.SysCommonConfigAddOrEditDTO;
import com.litian.dancechar.base.biz.commonconfig.dto.SysCommonConfigReqDTO;
import com.litian.dancechar.base.biz.commonconfig.dto.SysCommonConfigRespDTO;
import com.litian.dancechar.base.biz.commonconfig.service.SysCommonConfigService;
import com.litian.dancechar.framework.cache.redis.constants.LockConstant;
import com.litian.dancechar.framework.cache.redis.distributelock.annotation.Lock;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.validator.ValidatorUtil;
import com.litian.dancechar.framework.common.validator.groups.Update;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统公共配置业务处理
 *
 * @author tojson
 * @date 2021/6/19 11:17
 */
@Api(tags = "系统公共配置相关api")
@RestController
@Slf4j
@RequestMapping(value = "/sys/common/config")
public class SysCommonConfigController {
    @Resource
    private SysCommonConfigService sysCommonConfigService;

    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    @PostMapping("listPaged")
    public RespResult<PageWrapperDTO<SysCommonConfigRespDTO>> listPaged(@RequestBody SysCommonConfigReqDTO req) {
        return sysCommonConfigService.listPaged(req);
    }

    @ApiOperation(value = "查询列表", notes = "查询列表")
    @PostMapping("findList")
    public RespResult<List<SysCommonConfigRespDTO>> findList(@RequestBody SysCommonConfigReqDTO req) {
        return sysCommonConfigService.findList(req);
    }

    @ApiOperation(value = "新增保存", notes = "新增保存")
    @PostMapping("add")
    @Lock(keyPrefix = "sysCommConfg", lockFailMsg = LockConstant.REPEATED_SUBMIT, value = "#req.configKey")
    public RespResult<Boolean> add(@Validated @RequestBody SysCommonConfigAddOrEditDTO req) {
        log.info("进入新增公共配置....");
        return sysCommonConfigService.save(req);
    }

    @ApiOperation(value = "修改保存", notes = "修改保存")
    @PostMapping("update")
    public RespResult<Boolean> update(@RequestBody SysCommonConfigAddOrEditDTO req) {
        log.info("进入修改公共配置....");
        ValidatorUtil.validate(req, Update.class);
        return sysCommonConfigService.save(req);
    }
}