package com.litian.dancechar.biz.sysmgr.mongodbmgr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.biz.sysmgr.mongodbmgr.dto.FcodeMongodbBaseInfoDTO;
import com.litian.dancechar.biz.sysmgr.mongodbmgr.dto.FcodeMongodbInfoDTO;
import com.litian.dancechar.biz.sysmgr.mongodbmgr.dto.FcodeMongodbInfoQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.mongodbmgr.repository.dataobject.FcodeMongodbInfoDO;
import com.litian.dancechar.biz.sysmgr.mongodbmgr.repository.mapper.FcodeMongodbInfoMapper;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCStrUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.util.page.PageRespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类描述：mongodb配置信息manager
 *
 * @author 01410001
 * @date 2021-11-08 16:16:16
 */
@Component
@Slf4j
public class FcodeMongodbInfoManager extends ServiceImpl<FcodeMongodbInfoMapper, FcodeMongodbInfoDO> {

    /**
     * 功能：分页查询列表记录
     */
    public PageResp<FcodeMongodbInfoDTO> listPage(FcodeMongodbInfoQueryReqDTO fcodeMongodbInfoQueryReqDTO) {
        PageHelper.startPage(fcodeMongodbInfoQueryReqDTO.getPageNo(), fcodeMongodbInfoQueryReqDTO.getPageSize());
        LambdaQueryWrapper<FcodeMongodbInfoDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.likeRight(DCStrUtil.isNotBlank(fcodeMongodbInfoQueryReqDTO.getMongodbName()),FcodeMongodbInfoDO::getMongodbName,fcodeMongodbInfoQueryReqDTO.getMongodbName());
        queryWrapper.likeRight(DCStrUtil.isNotBlank(fcodeMongodbInfoQueryReqDTO.getMongodbHost()),FcodeMongodbInfoDO::getMongodbHost,fcodeMongodbInfoQueryReqDTO.getMongodbHost());
        queryWrapper.likeRight(DCStrUtil.isNotBlank(fcodeMongodbInfoQueryReqDTO.getMongodbUsername()),FcodeMongodbInfoDO::getMongodbUsername,fcodeMongodbInfoQueryReqDTO.getMongodbUsername());
        queryWrapper.likeRight(DCStrUtil.isNotBlank(fcodeMongodbInfoQueryReqDTO.getMongodbDatabase()),FcodeMongodbInfoDO::getMongodbDatabase,fcodeMongodbInfoQueryReqDTO.getMongodbDatabase());
        queryWrapper.likeRight(DCStrUtil.isNotBlank(fcodeMongodbInfoQueryReqDTO.getMongodbUrl()),FcodeMongodbInfoDO::getMongodbUrl,fcodeMongodbInfoQueryReqDTO.getMongodbUrl());
        queryWrapper.orderByDesc(FcodeMongodbInfoDO::getUpdateDate);
        List<FcodeMongodbInfoDO> list = getBaseMapper().selectList(queryWrapper);
        PageResp<FcodeMongodbInfoDTO> pageResp = PageRespUtil.buildPageResult(list, FcodeMongodbInfoDTO.class);
        return pageResp;
    }

    /**
     * 功能：新增保存记录
     */
    public Long save(FcodeMongodbInfoDTO fcodeMongodbInfoDTO) {
        FcodeMongodbInfoDO fcodeMongodbInfoDO = new FcodeMongodbInfoDO();
        DCBeanUtil.copyNotNull(fcodeMongodbInfoDTO, fcodeMongodbInfoDO);
        save(fcodeMongodbInfoDO);
        return fcodeMongodbInfoDO.getId();
    }

    /**
     * 功能：修改记录
     */
    public Boolean update(FcodeMongodbInfoDTO fcodeMongodbInfoDTO) {
        if (DCObjectUtil.isNotNull(fcodeMongodbInfoDTO.getId())) {
            FcodeMongodbInfoDO fcodeMongodbInfoDO = this.baseMapper.selectById(fcodeMongodbInfoDTO.getId());
            if (DCObjectUtil.isNotNull(fcodeMongodbInfoDO)) {
                DCBeanUtil.copyNotNull(fcodeMongodbInfoDTO, fcodeMongodbInfoDO);
                return this.baseMapper.updateById(fcodeMongodbInfoDO) > 0;
            }
        }
        return false;
    }

    /**
     * 功能：根据id删除记录
     */
    public Boolean deleteById(FcodeMongodbInfoDTO fcodeMongodbInfoDTO) {
        FcodeMongodbInfoDO fcodeMongodbInfoDO = this.baseMapper.selectById(fcodeMongodbInfoDTO.getId());
        if (DCObjectUtil.isNotNull(fcodeMongodbInfoDO)) {
            return super.removeById(fcodeMongodbInfoDO.getId());
        }
        return false;
    }

    /**
     * 功能：根据id获取记录
     */
    public FcodeMongodbInfoDTO getWrapperById(Long id) {
        return DCBeanUtil.copyNotNull(this.baseMapper.selectById(id), new FcodeMongodbInfoDTO());

    }

    public FcodeMongodbBaseInfoDTO getInfoById(Long id) {
        return DCBeanUtil.copyNotNull(this.baseMapper.selectById(id), new FcodeMongodbBaseInfoDTO());

    }

    public Boolean getInfoByName(FcodeMongodbInfoDTO fcodeMongodbInfoDTO) {
        QueryWrapper<FcodeMongodbInfoDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("mongodb_name",fcodeMongodbInfoDTO.getMongodbName());
        if(DCObjectUtil.isNotEmpty(fcodeMongodbInfoDTO.getId())){
            queryWrapper.ne("id",fcodeMongodbInfoDTO.getId());
        }
        return baseMapper.selectCount(queryWrapper)>0;

    }

    public List<FcodeMongodbBaseInfoDTO> getMongodbList() {
        return DCBeanUtil.copyList(this.baseMapper.selectList(Wrappers.query()), FcodeMongodbBaseInfoDTO.class);

    }
}
