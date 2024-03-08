package com.litian.dancechar.base.biz.customergroup.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.aliyun.oss.model.OSSObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.litian.dancechar.base.biz.customergroup.dao.entity.CustomerGroupDO;
import com.litian.dancechar.base.biz.customergroup.dao.entity.CustomerGroupParentInfoDO;
import com.litian.dancechar.base.biz.customergroup.dao.inf.CustomerGroupDao;
import com.litian.dancechar.base.biz.customergroup.dao.inf.CustomerGroupParentInfoDao;
import com.litian.dancechar.base.biz.customergroup.dto.CustomerGroupReqDTO;
import com.litian.dancechar.base.biz.customergroup.dto.CustomerGroupRespDTO;
import com.litian.dancechar.base.biz.customergroup.dto.CustomerGroupSaveDTO;
import com.litian.dancechar.base.biz.customergroup.dto.CustomerMessageDTO;
import com.litian.dancechar.base.biz.customergroup.enums.ImportTypeEnum;
import com.litian.dancechar.base.biz.customergroup.enums.UploadStatusEnum;
import com.litian.dancechar.base.biz.snowflake.util.SnowflakeHelper;
import com.litian.dancechar.base.common.constants.CommConstants;
import com.litian.dancechar.base.common.constants.RedisKeyConstants;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.PageResultUtil;
import com.litian.dancechar.framework.kafka.util.KafkaProducerUtil;
import com.litian.dancechar.framework.oss.util.OssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 客群服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CustomerGroupService extends ServiceImpl<CustomerGroupDao, CustomerGroupDO> {
    @Resource
    private CustomerGroupDao customerGroupDao;
    @Resource
    private CustomerGroupParentInfoService customerGroupParentInfoService;
    @Resource
    private KafkaProducerUtil kafkaProducerUtil;
    @Resource
    private OssUtil ossUtil;
    @Resource
    private RedisHelper redisHelper;
    @Resource
    private CustomerGroupParentInfoDao customerGroupParentInfoDao;


    // 这里线程大小默认设置为2，超过了，就放队列
    private final ExecutorService executorService = new ThreadPoolExecutor(2, 2,
                                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    /**
     * 功能: 分页查询客群列表
     */
    public RespResult<PageWrapperDTO<CustomerGroupRespDTO>> listPaged(CustomerGroupReqDTO req) {
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        PageWrapperDTO<CustomerGroupRespDTO> pageCommon = new PageWrapperDTO<>();
        PageResultUtil.setPageResult(customerGroupDao.findList(req.getCode(), req.getName()), pageCommon);
        return RespResult.success(pageCommon);
    }

    /**
     * 删除客群
     */
    public Boolean deleteCustomerGroup(CustomerGroupReqDTO reqDTO){
        customerGroupDao.deleteById(reqDTO.getId());
        // 如果没有子客群，连带删除父客群
        LambdaQueryWrapper<CustomerGroupDO> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(CustomerGroupDO::getCustomerGroupParentId, reqDTO.getCustomerGroupParentId());
        if(CollUtil.isEmpty(customerGroupDao.selectList(lambdaQueryWrapper))){
            customerGroupParentInfoDao.deleteById(reqDTO.getCustomerGroupParentId());
        }
        return true;
    }

    /**
     * 更新成功上传人数
     */
    public void updateSuccessTotalCount(String code){
        LambdaQueryWrapper<CustomerGroupDO> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(CustomerGroupDO::getCode, code);
        CustomerGroupDO customerGroupDO =  this.getOne(lambdaQueryWrapper);
        if(ObjectUtil.isNotNull(customerGroupDO)){
            customerGroupDO.setSuccessTotalCount(customerGroupDO.getUploadTotalCount());
        }
        this.updateById(customerGroupDO);
    }

    /**
     * 功能：新增客群
     */
    public RespResult<Boolean> saveCustomerGroup(CustomerGroupSaveDTO customerGroupSaveDTO) {
        if(ImportTypeEnum.ONE.getCode().equals(customerGroupSaveDTO.getImportType())){
            CustomerGroupDO customerGroupDO = new CustomerGroupDO();
            DCBeanUtil.copyNotNull(customerGroupSaveDTO, customerGroupDO);
            customerGroupDO.setCode(SnowflakeHelper.genString("kq"));
            customerGroupDO.setUploadStatus(UploadStatusEnum.WAIT_DO.getCode());
            customerGroupDao.insert(customerGroupDO);
            executorService.execute(()-> productSingleCustomerGroupMsg(customerGroupDO,
                    customerGroupSaveDTO.getShortUploadPath()));
            return RespResult.success(true);
        }
        // 如果是拆分客群，处理逻辑不同
        CustomerGroupParentInfoDO customerGroupParentInfoDO = new CustomerGroupParentInfoDO();
        customerGroupParentInfoDO.setCode(SnowflakeHelper.genString("kqc"));
        customerGroupParentInfoDO.setName(customerGroupSaveDTO.getName());
        DCBeanUtil.copyNotNull(customerGroupSaveDTO, customerGroupParentInfoDO);
        List<CustomerGroupDO> childList = Lists.newArrayList();
        // 拆分客群的总数，比如550w客户数据，100w客户一个客群，供550w/100w=6,向上舍入
        long splitGroupNum = (long)Math.ceil((double)customerGroupSaveDTO.getUploadTotalCount()/customerGroupSaveDTO.getSplitTotalCount());
        customerGroupParentInfoDO.setSplitGroupNum(splitGroupNum);
        customerGroupParentInfoService.save(customerGroupParentInfoDO);
        for(int i=0; i<splitGroupNum; i++){
            CustomerGroupDO customerGroupDO = new  CustomerGroupDO();
            DCBeanUtil.copyNotNull(customerGroupSaveDTO, customerGroupDO);
            customerGroupDO.setCustomerGroupParentId(customerGroupParentInfoDO.getId());
            customerGroupDO.setCode(SnowflakeHelper.genString("qc"));
            customerGroupDO.setName(customerGroupSaveDTO.getName()+"-"+(i+1));
            customerGroupDO.setUploadStatus(UploadStatusEnum.WAIT_DO.getCode());
            if(i == splitGroupNum-1){
                // 拆分客群计算最后一个客群的客户数
                long lastCustomerGroupTotalCount = (customerGroupSaveDTO.getUploadTotalCount() % customerGroupSaveDTO.getSplitTotalCount());
                customerGroupDO.setUploadTotalCount(lastCustomerGroupTotalCount);
            } else{
                customerGroupDO.setUploadTotalCount(customerGroupSaveDTO.getSplitTotalCount());
            }
            childList.add(customerGroupDO);
        }
        if(CollUtil.isNotEmpty(childList)){
            this.saveBatch(childList);
            executorService.execute(()-> productBatchCustomerGroupMsg(childList,
                    customerGroupSaveDTO.getSplitTotalCount(), customerGroupSaveDTO.getShortUploadPath()));
        }
        return RespResult.success(true);
    }

    /**
     * 不拆分，发送MQ消息
     */
    private void productSingleCustomerGroupMsg(CustomerGroupDO customerGroupDO, String shortUploadPath){
        try{
            OSSObject ossObject = ossUtil.getObject(shortUploadPath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
            long count = 0;
            while (true) {
                String line = reader.readLine();
                if (line == null){
                    break;
                }
                CustomerMessageDTO customerMessageDTO = new CustomerMessageDTO();
                customerMessageDTO.setMsgId(SnowflakeHelper.genString());
                customerMessageDTO.setMobile(line);
                customerMessageDTO.setCustomerGroupCode(customerGroupDO.getCode());
                customerMessageDTO.setExpireTime(customerGroupDO.getExpireTime());
                kafkaProducerUtil.sendMessage(CommConstants.KafkaTopic.TOPIC_CUSTOMER_GROUP_INFO, JSONUtil.toJsonStr(customerMessageDTO));
                count ++;
            }
            redisHelper.incrByLong(RedisKeyConstants.CustomerGroup.CUSTOMER_GROUP_COUNT+customerGroupDO.getCode(), count);
            reader.close();
            ossObject.close();
        } catch (Exception e){
            log.error(e.getMessage() ,e);
        }
    }

    /**
     * 拆分客群，发送MQ消息
     */
    private void productBatchCustomerGroupMsg(List<CustomerGroupDO> customerGroupCodeList, long eachTotalSize,
                                             String shortUploadPath){
        List<String> recordList = Lists.newArrayList();
        try {
            OSSObject ossObject = ossUtil.getObject(shortUploadPath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                recordList.add(line);
            }
            reader.close();
            ossObject.close();
            log.info("预计发送总记录数:{}", recordList.size());
            if (CollUtil.isNotEmpty(recordList)) {
                List<List<String>> splitList = Lists.partition(recordList, Convert.toInt(eachTotalSize));
                for (int i = 0; i < splitList.size(); i++) {
                    List<String> mobileList = splitList.get(i);
                    long customerGroupCodeCount = 0;
                    String customerGroupCode = customerGroupCodeList.get(i).getCode();
                    Date expireTime  = customerGroupCodeList.get(i).getExpireTime();
                    for (int j=0;j<mobileList.size();j++){
                        CustomerMessageDTO customerMessageDTO = new CustomerMessageDTO();
                        customerMessageDTO.setMsgId(SnowflakeHelper.genString());
                        customerMessageDTO.setMobile(mobileList.get(j));
                        customerMessageDTO.setCustomerGroupCode(customerGroupCode);
                        customerMessageDTO.setExpireTime(expireTime);
                        kafkaProducerUtil.sendMessage(CommConstants.KafkaTopic.TOPIC_CUSTOMER_GROUP_INFO, JSONUtil.toJsonStr(customerMessageDTO));
                        redisHelper.increment(RedisKeyConstants.CustomerGroup.CUSTOMER_GROUP_COUNT+customerGroupCode);
                        customerGroupCodeCount ++;
                    }
                    redisHelper.incrByLong(RedisKeyConstants.CustomerGroup.CUSTOMER_GROUP_COUNT+customerGroupCode, customerGroupCodeCount);
                }
            }
        } catch (Exception e){
            log.error(e.getMessage() ,e);
        }
    }

    /**
     * 判断某人是否在客群
     */
    public Boolean isInCustomerGroup(String customerGroupCode, String mobile) {
        return StrUtil.equals(redisHelper.getString(customerGroupCode+mobile), "1");
    }
}