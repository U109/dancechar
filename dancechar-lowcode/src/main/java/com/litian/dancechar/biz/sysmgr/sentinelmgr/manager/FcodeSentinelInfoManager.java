package com.litian.dancechar.biz.sysmgr.sentinelmgr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.biz.sysmgr.sentinelmgr.dto.FcodeSentinelBaseInfoDTO;
import com.litian.dancechar.biz.sysmgr.sentinelmgr.dto.FcodeSentinelInfoDTO;
import com.litian.dancechar.biz.sysmgr.sentinelmgr.dto.FcodeSentinelInfoQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.sentinelmgr.repository.dataobject.FcodeSentinelInfoDO;
import com.litian.dancechar.biz.sysmgr.sentinelmgr.repository.mapper.FcodeSentinelInfoMapper;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：sentinel配置信息manager
 *
 * @author 01410001
 * @date 2021-10-12 14:08:05
 */
@Component
@Slf4j
public class FcodeSentinelInfoManager extends ServiceImpl<FcodeSentinelInfoMapper, FcodeSentinelInfoDO> {

    /**
     * 功能：分页查询列表记录
     */
    public PageWrapperDTO<FcodeSentinelInfoDTO> listPage(FcodeSentinelInfoQueryReqDTO fcodeSentinelInfoQueryReqDTO) {
        PageHelper.startPage(fcodeSentinelInfoQueryReqDTO.getPageNo(), fcodeSentinelInfoQueryReqDTO.getPageSize());
        LambdaQueryWrapper<FcodeSentinelInfoDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.likeRight(DCStrUtil.isNotEmpty(fcodeSentinelInfoQueryReqDTO.getSentinelName()), FcodeSentinelInfoDO::getSentinelName,fcodeSentinelInfoQueryReqDTO.getSentinelName());
        queryWrapper.likeRight(DCStrUtil.isNotEmpty(fcodeSentinelInfoQueryReqDTO.getConsoleUrl()),FcodeSentinelInfoDO::getConsoleUrl,fcodeSentinelInfoQueryReqDTO.getConsoleUrl());
        queryWrapper.likeRight(DCStrUtil.isNotEmpty(fcodeSentinelInfoQueryReqDTO.getZkUrl()),FcodeSentinelInfoDO::getZkUrl,fcodeSentinelInfoQueryReqDTO.getZkUrl());
        queryWrapper.orderByDesc(FcodeSentinelInfoDO::getUpdateDate);
        List<FcodeSentinelInfoDO> list = getBaseMapper().selectList(queryWrapper);
        PageWrapperDTO<FcodeSentinelInfoDTO> RespResult = PageResultUtil.setPageResult(list, FcodeSentinelInfoDTO.class);
        return RespResult;
    }

    /**
     * 功能：新增保存记录
     */
    public Long save(FcodeSentinelInfoDTO fcodeSentinelInfoDTO) {
        FcodeSentinelInfoDO fcodeSentinelInfoDO = new FcodeSentinelInfoDO();
        DCBeanUtil.copyNotNull(fcodeSentinelInfoDO,fcodeSentinelInfoDTO );
        save(fcodeSentinelInfoDO);
        return fcodeSentinelInfoDO.getId();
    }

    /**
     * 功能：修改记录
     */
    public Boolean update(FcodeSentinelInfoDTO fcodeSentinelInfoDTO) {
        if (DCObjectUtil.isNotNull(fcodeSentinelInfoDTO.getId())) {
            FcodeSentinelInfoDO fcodeSentinelInfoDO = this.baseMapper.selectById(fcodeSentinelInfoDTO.getId());
            if (DCObjectUtil.isNotNull(fcodeSentinelInfoDO)) {
                DCBeanUtil.copyNotNull(fcodeSentinelInfoDO,fcodeSentinelInfoDTO );
                return this.baseMapper.updateById(fcodeSentinelInfoDO) > 0;
            }
        }
        return false;
    }

    /**
     * 功能：根据id删除记录
     */
    public Boolean deleteById(FcodeSentinelInfoDTO fcodeSentinelInfoDTO) {
        FcodeSentinelInfoDO fcodeSentinelInfoDO = this.baseMapper.selectById(fcodeSentinelInfoDTO.getId());
        if (DCObjectUtil.isNotNull(fcodeSentinelInfoDO)) {
            return super.removeById(fcodeSentinelInfoDO.getId());
        }
        return false;
    }

    /**
     * 功能：根据id获取记录
     * @return
     */
    public FcodeSentinelInfoDO getWrapperById(Long id) {
        return DCBeanUtil.copyNotNull(new FcodeSentinelBaseInfoDTO(),this.baseMapper.selectById(id));
    }

    /**
     * 获取所有sentinel配置信息列表
     * @return
     */
    public List<FcodeSentinelInfoDTO> getAllSentinelInfo(){
        QueryWrapper<FcodeSentinelInfoDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("delete_flag",0);
        List<FcodeSentinelInfoDO> sentinelInfoList = this.baseMapper.selectList(queryWrapper);
        if(DCCollectionUtil.isEmpty(sentinelInfoList)){
            return new ArrayList<>();
        }
        return DCBeanUtil.copyList(sentinelInfoList,FcodeSentinelInfoDTO.class);
    }

    public Boolean checkName(String sentinelName,Long id){
        QueryWrapper<FcodeSentinelInfoDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("sentinel_name",sentinelName);
        queryWrapper.eq("delete_flag",0);
        if(DCObjectUtil.isNotEmpty(id)){
            queryWrapper.ne("id",id);
        }
        return baseMapper.selectCount(queryWrapper)>0;
    }
}
