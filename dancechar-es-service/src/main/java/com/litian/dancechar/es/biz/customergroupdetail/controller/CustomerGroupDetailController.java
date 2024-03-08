package com.litian.dancechar.es.biz.customergroupdetail.controller;

import com.litian.dancechar.es.biz.customergroupdetail.dao.document.CustomerGroupDetailDocument;
import com.litian.dancechar.es.biz.customergroupdetail.dto.CustomerGroupDetailDTO;
import com.litian.dancechar.es.biz.customergroupdetail.dto.CustomerGroupDetailListDTO;
import com.litian.dancechar.es.biz.customergroupdetail.service.CustomerGroupDocumentService;
import com.litian.dancechar.framework.common.base.RespResult;
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
 * 客群详情es业务处理
 *
 * @author tojson
 * @date 2021/6/19 11:17
 */
@Api(tags = "客群详情es相关api")
@RestController
@Slf4j
@RequestMapping(value = "/customergroup/detail")
public class CustomerGroupDetailController {
    @Resource
    private CustomerGroupDocumentService customerGroupDocumentService;

    @ApiOperation(value = "插入客群明细数据到es", notes = "插入明细数据到es")
    @PostMapping("insert")
    public RespResult<Boolean> insert(@RequestBody CustomerGroupDetailDTO detailDTO) {
        return RespResult.success(customerGroupDocumentService.insert(detailDTO));
    }

    @ApiOperation(value = "批量插入客群明细数据到es", notes = "插入明细数据到es")
    @PostMapping("batchInsert")
    public RespResult<Boolean> batchInsert(@RequestBody CustomerGroupDetailListDTO customerGroupDetailDTO) {
        return RespResult.success(customerGroupDocumentService.batchInsert(customerGroupDetailDTO.getDetailDTOS()));
    }

    @ApiOperation(value = "根据code删除客群明细数据", notes = "根据code删除客群明细数据")
    @PostMapping("deleteByCode")
    public RespResult<Boolean> deleteByCode(@RequestBody CustomerGroupDetailDTO detailDTO) {
        return RespResult.success(customerGroupDocumentService.delete(detailDTO.getCustomerGroupCode()));
    }

    @ApiOperation(value = "根据客群code查询客群明细列表", notes = "根据客群code查询客群明细列表")
    @PostMapping("listByCode")
    public RespResult<List<CustomerGroupDetailDocument>> listByCode(@RequestBody CustomerGroupDetailDTO detailDTO) {
        return RespResult.success(customerGroupDocumentService
                .listByCode(detailDTO.getCustomerGroupCode()));
    }

    @ApiOperation(value = "根据客群code和mobile查询详情", notes = "根据客群code和mobile查询详情")
    @PostMapping("queryByCodeAndMobile")
    public RespResult<CustomerGroupDetailDocument> queryByCodeAndMobile(@RequestBody CustomerGroupDetailDTO detailDTO) {
        return RespResult.success(customerGroupDocumentService
                .queryByCodeAndMobile(detailDTO.getCustomerGroupCode(), detailDTO.getMobile()));
    }

    @ApiOperation(value = "客群列表", notes = "清理过期的客群列表")
    @PostMapping("clearExpireData")
    public RespResult<Boolean> clearExpireData(@RequestBody CustomerGroupDetailDTO detailDTO) {
        return RespResult.success(customerGroupDocumentService.clearExpireData(detailDTO));
    }
}