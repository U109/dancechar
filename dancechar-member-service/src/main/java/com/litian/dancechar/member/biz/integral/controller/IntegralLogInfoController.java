package com.litian.dancechar.member.biz.integral.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.litian.dancechar.framework.cache.redis.distributelock.annotation.Lock;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.validator.ValidatorUtil;
import com.litian.dancechar.member.biz.integral.dto.IntegralLogInfoReqDTO;
import com.litian.dancechar.member.biz.integral.dto.IntegralLogInfoRespDTO;
import com.litian.dancechar.member.biz.integral.dto.IntegralLogInfoSaveDTO;
import com.litian.dancechar.member.biz.integral.service.IntegralLogInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 会员积分流水业务处理
 *
 * @author tojson
 * @date 2022/7/9 06:26
 */
@Api(tags = "会员积分相关api")
@RestController
@Slf4j
@RequestMapping(value = "/member/integral/")
public class IntegralLogInfoController {
    @Resource
    private IntegralLogInfoService integralInfoService;

    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    @PostMapping("listPaged")
    public RespResult<PageWrapperDTO<IntegralLogInfoRespDTO>> listPaged(@RequestBody IntegralLogInfoReqDTO req) {
        return integralInfoService.listPaged(req);
    }

    @ApiOperation(value = "根据Id查询积分信息", notes = "根据Id查询积分信息")
    @PostMapping("findById")
    public RespResult<IntegralLogInfoRespDTO> findById(@RequestBody IntegralLogInfoReqDTO req) {
        return RespResult.success(DCBeanUtil.copyNotNull(integralInfoService.findById(req.getId()), new IntegralLogInfoRespDTO()));
    }

    @ApiOperation(value = "根据业务Id-查询积分信息", notes = "根据业务Id-查询积分信息")
    @PostMapping("findByBusinessId")
    public RespResult<IntegralLogInfoRespDTO> findByBusinessId(@RequestBody IntegralLogInfoReqDTO req) {
        if(StrUtil.hasBlank(req.getBusinessType(), req.getBusinessId())){
            return RespResult.error("businessType或businessId不能为空！");
        }
        return RespResult.success(DCBeanUtil.copyNotNull(integralInfoService.findByCondition(req),
                new IntegralLogInfoRespDTO()));
    }

    @ApiOperation(value = "批量根据Id查询积分信息", notes = "批量根据Id查询积分信息")
    @PostMapping("findByIds")
    public RespResult<List<IntegralLogInfoRespDTO>> findByIds(@RequestBody IntegralLogInfoReqDTO req) {
        if(CollUtil.isEmpty(req.getIds())){
            return RespResult.error("ids不能为空！");
        }
        return RespResult.success(DCBeanUtil.copyList(integralInfoService.findByIds(req.getIds()), IntegralLogInfoRespDTO.class));
    }

    @ApiOperation(value = "新增积分", notes = "新增积分")
    @PostMapping("add")
    @Lock(value = "#integralInfoSaveDTO.customerId,#integralInfoSaveDTO.businessType,#integralInfoSaveDTO.businessId",
            lockFailMsg = "请勿重复提交", expireTime = 3000)
    public RespResult<String> add(@RequestBody IntegralLogInfoSaveDTO integralInfoSaveDTO) {
        log.info("新增保存积分....");
        ValidatorUtil.validate(integralInfoSaveDTO);
        return integralInfoService.saveWithInsert(integralInfoSaveDTO);
    }
}