package com.litian.dancechar.biz.sysmgr.redismgr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.biz.sysmgr.redismgr.dto.FcodeRedisInfoDTO;
import com.litian.dancechar.biz.sysmgr.redismgr.dto.FcodeRedisInfoQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.redismgr.repository.dataobject.FcodeRedisInfoDO;
import com.litian.dancechar.biz.sysmgr.redismgr.repository.mapper.FcodeRedisInfoMapper;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.util.page.PageRespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类描述：Redis信息管理manager
 *
 * @author 01396106
 * @date 2021-10-12 11:05:08
 */
@Component
@Slf4j
public class FcodeRedisInfoManager extends ServiceImpl<FcodeRedisInfoMapper, FcodeRedisInfoDO> {

    /**
     * 功能：分页查询列表记录
     */
    public PageResp<FcodeRedisInfoDTO> listPage(FcodeRedisInfoQueryReqDTO fcodeRedisInfoQueryReqDTO) {
        PageHelper.startPage(fcodeRedisInfoQueryReqDTO.getPageNo(), fcodeRedisInfoQueryReqDTO.getPageSize());
        LambdaQueryWrapper<FcodeRedisInfoDO> queryWrapper = Wrappers.lambdaQuery();
        if (DCObjectUtil.isNotEmpty(fcodeRedisInfoQueryReqDTO.getName())) {
            queryWrapper.like(FcodeRedisInfoDO::getName, fcodeRedisInfoQueryReqDTO.getName());
        }
        if (DCObjectUtil.isNotEmpty(fcodeRedisInfoQueryReqDTO.getInstallType())){
            queryWrapper.eq(FcodeRedisInfoDO::getInstallType, fcodeRedisInfoQueryReqDTO.getInstallType());
        }
        queryWrapper.orderByDesc(FcodeRedisInfoDO::getUpdateDate);
        List<FcodeRedisInfoDO> list = getBaseMapper().selectList(queryWrapper);
        PageResp<FcodeRedisInfoDTO> pageResp = PageRespUtil.buildPageResult(list, FcodeRedisInfoDTO.class);
        return pageResp;
    }

    /**
     * 功能：新增保存记录
     */
    public Boolean save(FcodeRedisInfoDTO fcodeRedisInfoDTO) {
        FcodeRedisInfoDO fcodeRedisInfoDO = new FcodeRedisInfoDO();
        DCBeanUtil.copyNotNull(fcodeRedisInfoDTO, fcodeRedisInfoDO);
        return save(fcodeRedisInfoDO);
    }

    public List<FcodeRedisInfoDTO> selectList(FcodeRedisInfoDTO fcodeRedisInfoDTO) {
        LambdaQueryWrapper<FcodeRedisInfoDO> queryWrapper = Wrappers.lambdaQuery();
        if (DCObjectUtil.isNotEmpty(fcodeRedisInfoDTO.getName())) {
            queryWrapper.eq(FcodeRedisInfoDO::getName, fcodeRedisInfoDTO.getName());
        }
        if (DCObjectUtil.isNotEmpty(fcodeRedisInfoDTO.getInstallType())) {
            queryWrapper.eq(FcodeRedisInfoDO::getInstallType, fcodeRedisInfoDTO.getInstallType());
        }
        return DCBeanUtil.copyList(getBaseMapper().selectList(queryWrapper),FcodeRedisInfoDTO.class);
    }

    /**
     * 功能：修改记录
     */
    public Boolean update(FcodeRedisInfoDTO fcodeRedisInfoDTO) {
        if (DCObjectUtil.isNotNull(fcodeRedisInfoDTO.getId())) {
            FcodeRedisInfoDO fcodeRedisInfoDO = this.baseMapper.selectById(fcodeRedisInfoDTO.getId());
            if (DCObjectUtil.isNotNull(fcodeRedisInfoDO)) {
                DCBeanUtil.copyNotNull(fcodeRedisInfoDTO, fcodeRedisInfoDO);
                return this.baseMapper.updateById(fcodeRedisInfoDO) > 0;
            }
        }
        return false;
    }

    /**
     * 功能：根据id删除记录
     */
    public Boolean deleteById(FcodeRedisInfoDTO fcodeRedisInfoDTO) {
        FcodeRedisInfoDO fcodeRedisInfoDO = this.baseMapper.selectById(fcodeRedisInfoDTO.getId());
        if (DCObjectUtil.isNotNull(fcodeRedisInfoDO)) {
            return super.removeById(fcodeRedisInfoDO.getId());
        }
        return false;
    }

    /**
     * 功能：根据id获取记录
     */
    public FcodeRedisInfoDTO getWrapperById(Long id) {
        return DCBeanUtil.copyNotNull(this.baseMapper.selectById(id), new FcodeRedisInfoDTO());
    }
}
