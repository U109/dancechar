package com.litian.dancechar.core.biz.activity.act20220910.service;

import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.litian.dancechar.core.biz.activity.act20220910.dto.Act20220910ReqDTO;
import com.litian.dancechar.core.biz.activity.act20220910.enums.ItemTypeEnum;
import com.litian.dancechar.core.biz.activity.act20220910.mq.ProduceIntegralAddKafkaMsg;
import com.litian.dancechar.core.biz.activity.acttask.dao.entity.ActTaskConfigInfoDO;
import com.litian.dancechar.core.biz.activity.acttask.service.ActTaskConfigInfoService;
import com.litian.dancechar.core.biz.activity.custfinishtask.dto.CustomerFinishTaskInfoSaveDTO;
import com.litian.dancechar.core.biz.activity.custfinishtask.service.CustomerFinishTaskInfoService;
import com.litian.dancechar.core.biz.activity.info.dto.ActBaseInfoDTO;
import com.litian.dancechar.core.biz.activity.info.service.ActInfoService;
import com.litian.dancechar.core.biz.activity.itemrecords.dao.entity.ActItemRecordsDO;
import com.litian.dancechar.core.biz.activity.itemrecords.service.ActItemRecordService;
import com.litian.dancechar.core.biz.task.dao.entity.TaskInfoDO;
import com.litian.dancechar.core.biz.task.service.TaskInfoService;
import com.litian.dancechar.core.biz.transactionmsg.dto.SysTransactionMsgReqDTO;
import com.litian.dancechar.core.biz.transactionmsg.service.TransactionMsgService;
import com.litian.dancechar.core.feign.member.integral.client.IntegralClient;
import com.litian.dancechar.core.feign.member.integral.dto.IntegralLogInfoSaveDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 中秋活动信息服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class Act20220910Service {
    @Resource
    private ActInfoService actInfoService;
    @Resource
    private ActTaskConfigInfoService actTaskConfigInfoService;
    @Resource
    private ActItemRecordService actItemRecordService;
    @Resource
    private IntegralClient integralClient;
    @Resource
    private TransactionMsgService transactionMsgService;
    @Resource
    private CustomerFinishTaskInfoService customerFinishTaskInfoService;
    @Resource
    private TaskInfoService taskInfoService;
    @Resource
    private ProduceIntegralAddKafkaMsg produceIntegralAddKafkaMsg;

    /**
     * 功能：完成任务
     */
    public RespResult<Boolean> finishTask(Act20220910ReqDTO act20220910ReqDTO) {
        log.info("开始完成任务!param:{}", JSONUtil.toJsonStr(act20220910ReqDTO));
        RespResult<ActBaseInfoDTO> validResult = validParam(act20220910ReqDTO);
        if(validResult.isNotOk()){
            return RespResult.error(validResult.getMessage());
        }
        TaskInfoDO taskInfoDO = taskInfoService.findByCode(act20220910ReqDTO.getTaskCode());
        if(ObjectUtil.isNull(taskInfoDO)){
            return RespResult.error("任务对应的code不存在，请检查");
        }
        ActTaskConfigInfoDO actTaskConfigInfoDO = actTaskConfigInfoService.getByActNoAndTaskCode(
                                                  act20220910ReqDTO.getActNo(), act20220910ReqDTO.getTaskCode());
        if(ObjectUtil.isNull(actTaskConfigInfoDO)){
            return RespResult.error("活动对应的任务不存在，请检查");
        }
        // step1：保存用户完成的任务
        CustomerFinishTaskInfoSaveDTO customerFinishTaskInfoSaveDTO = new CustomerFinishTaskInfoSaveDTO();
        customerFinishTaskInfoSaveDTO.setCustomerId(act20220910ReqDTO.getCustomerId());
        customerFinishTaskInfoSaveDTO.setActNo(act20220910ReqDTO.getActNo());
        customerFinishTaskInfoSaveDTO.setTaskCode(actTaskConfigInfoDO.getTaskCode());
        RespResult<Boolean> finishTaskR = customerFinishTaskInfoService.insert(customerFinishTaskInfoSaveDTO);
        if(finishTaskR.isNotOk()){
            return RespResult.error(finishTaskR.getMessage());
        }
        // step2: 保存用户发放记录
        ActItemRecordsDO actItemRecords = new ActItemRecordsDO();
        actItemRecords.setCustomerId(act20220910ReqDTO.getCustomerId());
        actItemRecords.setActId(validResult.getData().getId());
        actItemRecords.setActNo(actTaskConfigInfoDO.getActNo());
        actItemRecords.setOperateItemType(ItemTypeEnum.INTEGRAL.getCode());
        actItemRecords.setOperateItemNum(actTaskConfigInfoDO.getItemNum());
        actItemRecords.setTaskId(taskInfoDO.getId());
        actItemRecords.setTaskCode(taskInfoDO.getTaskCode());
        actItemRecords.setTaskName(taskInfoDO.getTaskName());
        actItemRecordService.insert(actItemRecords);
        String itemType = actTaskConfigInfoDO.getItemType();
        // step2：当前给用户加积分
        if(StrUtil.equals(ItemTypeEnum.INTEGRAL.getCode(),itemType)){
            IntegralLogInfoSaveDTO integralInfoSaveDTO = new IntegralLogInfoSaveDTO();
            integralInfoSaveDTO.setCustomerId(act20220910ReqDTO.getCustomerId());
            integralInfoSaveDTO.setBusinessId(actItemRecords.getId());
            integralInfoSaveDTO.setBusinessType(ItemTypeEnum.INTEGRAL.getCode());
            integralInfoSaveDTO.setOperateType(1);
            integralInfoSaveDTO.setOperateNum(actTaskConfigInfoDO.getItemNum());
            try{
                if(act20220910ReqDTO.getSendMQ()){
                    // 通过mq通知积分服务
                    produceIntegralAddKafkaMsg.sendMessage(JSONUtil.toJsonStr(integralInfoSaveDTO));
                }else{
                    // 通过http调用积分服务
                    RespResult<String>  integralR = integralClient.add(integralInfoSaveDTO);
                    if(integralR.isOk()){
                        // 更新客户发放记录
                        actItemRecords.setItemSerialNo(integralR.getData());
                        actItemRecordService.updateById(actItemRecords);
                    }else{
                        // 抛出运行时异常，回滚事务
                        log.error("扣减积分失败！");
                        throw new BusinessException("扣减积分失败");
                    }
                }
            } catch (Exception e){
                log.error("给用户加积分发生异常！errMsg:{}", e.getMessage(), e);
                // 网络抖动引起的异常，记录本地消息表，进行重试
                SysTransactionMsgReqDTO sysTransactionMsgReqDTO = new SysTransactionMsgReqDTO();
                sysTransactionMsgReqDTO.setBusinessType(ItemTypeEnum.INTEGRAL.getCode());
                sysTransactionMsgReqDTO.setBusinessId(actItemRecords.getId());
                sysTransactionMsgReqDTO.setBusinessContent(JSONUtil.toJsonStr(integralInfoSaveDTO));
                sysTransactionMsgReqDTO.setRemark("客户完成任务加积分异常");
                transactionMsgService.insertOrUpdate(sysTransactionMsgReqDTO);
                log.info("完成记录用户加积分异常信息到本地表补偿处理!");
            }
        }
        return RespResult.success(true);

    }

    /**
     * 验证任务参数是否合法
     */
    private RespResult<ActBaseInfoDTO> validParam(Act20220910ReqDTO act20220910ReqDTO){
        String actNo = act20220910ReqDTO.getActNo();
        if(StrUtil.isEmpty(actNo)){
            return RespResult.error("actNo不能为空");
        }
        if(StrUtil.isEmpty(act20220910ReqDTO.getCustomerId())){
            return RespResult.error("customerId不能为空");
        }
        if(StrUtil.isEmpty(act20220910ReqDTO.getTaskCode())){
            return RespResult.error("taskCode不能为空");
        }
        ActBaseInfoDTO actBaseInfoDTO = actInfoService.getByActNo(actNo);
        if(ObjectUtil.isNull(actBaseInfoDTO)){
            return RespResult.error("活动不存在");
        }
        long curDate = new Date().getTime();
        if(CompareUtil.compare(actBaseInfoDTO.getStartTime().getTime(), curDate) > 0
                || CompareUtil.compare(actBaseInfoDTO.getEndTime().getTime(), curDate) < 0){
            return RespResult.error("活动未开始或活动已结束");
        }
        return RespResult.success(actBaseInfoDTO);
    }
}