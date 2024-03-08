package com.litian.dancechar.core.biz.activity.act20220910.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.litian.dancechar.core.biz.activity.act20220910.dto.Act20220910ReqDTO;
import com.litian.dancechar.core.biz.activity.act20220910.dto.CustomerInfoReqDTO;
import com.litian.dancechar.core.biz.activity.act20220910.mq.ProduceCustomerAddKafkaMsg;
import com.litian.dancechar.core.biz.activity.act20220910.mq.ProduceIntegralAddKafkaMsg;
import com.litian.dancechar.core.biz.activity.act20220910.service.Act20220910Service;
import com.litian.dancechar.core.feign.member.integral.dto.IntegralLogInfoSaveDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.es.util.ElasticsearchUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 中秋活动业务处理
 *
 * @author tojson
 * @date 2022/7/9 11:26
 */
@Api(tags = "中秋活动相关api")
@RestController
@Slf4j
@RequestMapping(value = "/act/20220910")
public class Act20220910Controller {
    @Resource
    private Act20220910Service act20220910Service;
    @Resource
    private ProduceCustomerAddKafkaMsg produceCustomerAddKafkaMsg;
    @Resource
    private ProduceIntegralAddKafkaMsg produceIntegralAddKafkaMsg;

    @Resource
    private ElasticsearchUtil elasticsearchUtil;

    @ApiOperation(value = "完成任务", notes = "完成任务")
    @PostMapping("finishTask")
    public RespResult<Boolean> finishTask(@RequestBody Act20220910ReqDTO req) {
        return act20220910Service.finishTask(req);
    }

    @ApiOperation(value = "发送客户新增消息", notes = "发送客户新增消息")
    @PostMapping("sendCustomerAddKafkaMsg")
    public RespResult<Boolean> sendCustomerAddKafkaMsg(){
        for (int i = 0; i < 10; i++) {
            CustomerInfoReqDTO customerInfoReqDTO = new CustomerInfoReqDTO();
            customerInfoReqDTO.setMobile(RandomUtil.randomString(11));
            customerInfoReqDTO.setRealName(RandomUtil.randomString(5));
            produceCustomerAddKafkaMsg.sendMessage(JSONUtil.toJsonStr(customerInfoReqDTO));
        }
        return RespResult.success(true);
    }

    @ApiOperation(value = "发送积分新增消息", notes = "发送积分新增消息")
    @PostMapping("sendIntegralAddKafkaMsg")
    public RespResult<Boolean> sendIntegralAddKafkaMsg(@RequestBody IntegralLogInfoSaveDTO integralInfoSaveDTO){
        produceIntegralAddKafkaMsg.sendMessage(JSONUtil.toJsonStr(integralInfoSaveDTO));
        return RespResult.success(true);
    }
}