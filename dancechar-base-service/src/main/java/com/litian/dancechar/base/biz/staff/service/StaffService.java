package com.litian.dancechar.base.biz.staff.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.litian.dancechar.base.biz.staff.dao.entity.StaffDO;
import com.litian.dancechar.base.biz.staff.dao.inf.StaffDao;
import com.litian.dancechar.base.biz.staff.dto.StaffRespDTO;
import com.litian.dancechar.base.common.constants.RedisKeyConstants;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 员工服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class StaffService extends ServiceImpl<StaffDao, StaffDO> {
    @Resource
    private StaffDao staffDao;
    @Resource
    private RedisHelper redisHelper;

    public StaffRespDTO getByNo(String no){
        StaffDO employeeDO = redisHelper.getBean(RedisKeyConstants.Employee.EMPLOYEE_INFO_KEY +  no,
                StaffDO.class);
        if(ObjectUtil.isNull(employeeDO)){
            return null;
        }
        return DCBeanUtil.copyNotNull(employeeDO, new StaffRespDTO());
    }

    /**
     * 将员工表db中的数据写入到redis(预计有100w左右)
     */
    public RespResult<Boolean> refreshDBToRedis(){
        int pageSize = 500;
        List<StaffDO> employeeList = staffDao.findList("0L", pageSize);
        if(CollUtil.isEmpty(employeeList)){
            log.warn("员工信息表没有记录！");
            return RespResult.success(true);
        }
        while (employeeList.size() >= pageSize){
            addDataToRedis(employeeList);
            String maxId = employeeList.get(employeeList.size()-1).getId();
            long start = System.currentTimeMillis();
            employeeList = staffDao.findList(maxId , pageSize);
            log.info("本次db查询耗时:{}ms", System.currentTimeMillis()-start);
        }
        if(CollUtil.isNotEmpty(employeeList)){
            addDataToRedis(employeeList);
        }
        return RespResult.success(true);
    }

    private void addDataToRedis(List<StaffDO> employeeList){
        List<List<StaffDO>>  employeePartitionList = Lists.partition(employeeList, 500);
        employeePartitionList.forEach(partition->{
            Map<String, Object> map = Maps.newHashMap();
            partition.forEach(employeeDO -> {
                map.put(RedisKeyConstants.Employee.EMPLOYEE_INFO_KEY + employeeDO.getNo(), employeeDO);
            });
            redisHelper.executeAsyncPipeLinedSetString(map, 24*3600L, TimeUnit.SECONDS);
            try{
                // 这里睡眠100ms，防止redis cpu飙高
                Thread.sleep(100);
            }catch (Exception e){
                log.error(e.getMessage() ,e);
            }
        });
    }
}