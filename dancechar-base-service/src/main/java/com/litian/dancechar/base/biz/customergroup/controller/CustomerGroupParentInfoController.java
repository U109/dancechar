package com.litian.dancechar.base.biz.customergroup.controller;

import com.litian.dancechar.base.biz.customergroup.dto.CustomerGroupParentInfoReqDTO;
import com.litian.dancechar.base.biz.customergroup.dto.CustomerGroupParentInfoRespDTO;
import com.litian.dancechar.base.biz.customergroup.service.CustomerGroupParentInfoService;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 父客群业务处理
 *
 * @author tojson
 * @date 2022/7/9 06:26
 */
@Api(tags = "拆分客群相关api")
@RestController
@Slf4j
@RequestMapping(value = "/customerGroup/split")
public class CustomerGroupParentInfoController {
    @Resource
    private CustomerGroupParentInfoService customerGroupParentInfoService;

    @ApiOperation(value = "查看拆分客群页面", notes = "查看拆分客群页面")
    @PostMapping("findList")
    public RespResult<PageWrapperDTO<CustomerGroupParentInfoRespDTO>> findList(@RequestBody CustomerGroupParentInfoReqDTO req) {
        return customerGroupParentInfoService.listPaged(req);
    }
}