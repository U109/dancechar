package com.litian.dancechar.biz.sysmgr.kafkamgr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.biz.sysmgr.kafkamgr.dto.FcodeKafkaBaseInfoDTO;
import com.litian.dancechar.biz.sysmgr.kafkamgr.dto.FcodeKafkaInfoDTO;
import com.litian.dancechar.biz.sysmgr.kafkamgr.dto.FcodeKafkaInfoQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.kafkamgr.repository.dataobject.FcodeKafkaInfoDO;
import com.litian.dancechar.biz.sysmgr.kafkamgr.repository.mapper.FcodeKafkaInfoMapper;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCCollectionUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCStrUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.util.page.PageRespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：kafka配置信息manager
 *
 * @author 01410001
 * @date 2021-10-12 14:07:34
 */
@Component
@Slf4j
public class FcodeKafkaInfoManager extends ServiceImpl<FcodeKafkaInfoMapper, FcodeKafkaInfoDO> {

    /**
     * 功能：分页查询列表记录
     */
    public PageResp<FcodeKafkaInfoDTO> listPage(FcodeKafkaInfoQueryReqDTO fcodeKafkaInfoQueryReqDTO) {
        PageHelper.startPage(fcodeKafkaInfoQueryReqDTO.getPageNo(), fcodeKafkaInfoQueryReqDTO.getPageSize());
        LambdaQueryWrapper<FcodeKafkaInfoDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.likeRight(DCStrUtil.isNotEmpty(fcodeKafkaInfoQueryReqDTO.getKafkaName()),FcodeKafkaInfoDO::getKafkaName,fcodeKafkaInfoQueryReqDTO.getKafkaName());
        queryWrapper.likeRight(DCStrUtil.isNotEmpty(fcodeKafkaInfoQueryReqDTO.getMonitorUrl()),FcodeKafkaInfoDO::getMonitorUrl,fcodeKafkaInfoQueryReqDTO.getMonitorUrl());
        queryWrapper.orderByDesc(FcodeKafkaInfoDO::getUpdateDate);
        List<FcodeKafkaInfoDO> list = getBaseMapper().selectList(queryWrapper);
        PageResp<FcodeKafkaInfoDTO> pageResp = PageRespUtil.buildPageResult(list, FcodeKafkaInfoDTO.class);
        return pageResp;
    }

    /**
     * 功能：新增保存记录
     */
    public Long save(FcodeKafkaInfoDTO fcodeKafkaInfoDTO) {
        FcodeKafkaInfoDO fcodeKafkaInfoDO = new FcodeKafkaInfoDO();
        DCBeanUtil.copyNotNull(fcodeKafkaInfoDTO, fcodeKafkaInfoDO);
        save(fcodeKafkaInfoDO);
        return fcodeKafkaInfoDO.getId();
    }

    /**
     * 功能：修改记录
     */
    public Boolean update(FcodeKafkaInfoDTO fcodeKafkaInfoDTO) {
        if (DCObjectUtil.isNotNull(fcodeKafkaInfoDTO.getId())) {
            FcodeKafkaInfoDO fcodeKafkaInfoDO = this.baseMapper.selectById(fcodeKafkaInfoDTO.getId());
            if (DCObjectUtil.isNotNull(fcodeKafkaInfoDO)) {
                DCBeanUtil.copyNotNull(fcodeKafkaInfoDTO, fcodeKafkaInfoDO);
                return this.baseMapper.updateById(fcodeKafkaInfoDO) > 0;
            }
        }
        return false;
    }

    /**
     * 功能：根据id删除记录
     */
    public Boolean deleteById(FcodeKafkaInfoDTO fcodeKafkaInfoDTO) {
        FcodeKafkaInfoDO fcodeKafkaInfoDO = this.baseMapper.selectById(fcodeKafkaInfoDTO.getId());
        if (DCObjectUtil.isNotNull(fcodeKafkaInfoDO)) {
            return super.removeById(fcodeKafkaInfoDO.getId());
        }
        return false;
    }

    /**
     * 功能：根据id获取记录
     */
    public FcodeKafkaBaseInfoDTO getWrapperById(Long id) {
        return DCBeanUtil.copyNotNull(this.baseMapper.selectById(id), new FcodeKafkaBaseInfoDTO());
    }

    public List<FcodeKafkaBaseInfoDTO> getListByIds(List<Long> ids) {
        QueryWrapper<FcodeKafkaInfoDO> queryWrapper = Wrappers.query();
        queryWrapper.in("id",ids);
        return DCBeanUtil.copyList(this.baseMapper.selectList(queryWrapper),FcodeKafkaBaseInfoDTO.class);
    }

    /**
     * 获取所有kafka配置信息列表
     * @return
     */
    public List<FcodeKafkaInfoDTO> getAllKafkaInfo(){
        QueryWrapper<FcodeKafkaInfoDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("delete_flag",0);
        List<FcodeKafkaInfoDO> kafkaInfoList = this.baseMapper.selectList(queryWrapper);
        if(DCCollectionUtil.isEmpty(kafkaInfoList)){
            return new ArrayList<>();
        }
        return DCBeanUtil.copyList(kafkaInfoList,FcodeKafkaInfoDTO.class);
    }

    public Boolean checkName(String kafkaName,Long id){
        QueryWrapper<FcodeKafkaInfoDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("kafka_name",kafkaName);
        queryWrapper.eq("delete_flag",0);
        if(DCObjectUtil.isNotEmpty(id)){
            queryWrapper.ne("id",id);
        }
        return baseMapper.selectCount(queryWrapper)>0;
    }
}
