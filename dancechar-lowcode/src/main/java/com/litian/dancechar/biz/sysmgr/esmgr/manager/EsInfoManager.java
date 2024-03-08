package com.litian.dancechar.biz.sysmgr.esmgr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.biz.sysmgr.esmgr.dto.EsBaseInfoDTO;
import com.litian.dancechar.biz.sysmgr.esmgr.dto.EsInfoDTO;
import com.litian.dancechar.biz.sysmgr.esmgr.dto.EsInfoQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.esmgr.repository.dataobject.EsInfoDO;
import com.litian.dancechar.biz.sysmgr.esmgr.repository.mapper.EsInfoMapper;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCStrUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.util.page.PageRespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类描述：es连接信息管理manager
 *
 * @author 01406831
 * @date 2021-11-04 15:28:33
 */
@Component
@Slf4j
public class EsInfoManager extends ServiceImpl<EsInfoMapper, EsInfoDO> {

    /**
     * 功能：分页查询列表记录
     */
    public PageResp<EsInfoDTO> listPage(EsInfoQueryReqDTO esInfoQueryReqDTO) {
        PageHelper.startPage(esInfoQueryReqDTO.getPageNo(), esInfoQueryReqDTO.getPageSize());
        LambdaQueryWrapper<EsInfoDO> queryWrapper = Wrappers.lambdaQuery();
        if (DCStrUtil.isNotEmpty(esInfoQueryReqDTO.getName())) {
            queryWrapper.like(EsInfoDO::getName, esInfoQueryReqDTO.getName());
        }
        if (DCStrUtil.isNotEmpty(esInfoQueryReqDTO.getClusterName())) {
            queryWrapper.like(EsInfoDO::getClusterName, esInfoQueryReqDTO.getClusterName());
        }
        if (DCStrUtil.isNotEmpty(esInfoQueryReqDTO.getEsVersion())) {
            queryWrapper.eq(EsInfoDO::getEsVersion, esInfoQueryReqDTO.getEsVersion());
        }
        if (DCStrUtil.isNotEmpty(esInfoQueryReqDTO.getEsAddr())) {
            queryWrapper.like(EsInfoDO::getEsAddr, esInfoQueryReqDTO.getEsAddr());
        }
        List<EsInfoDO> list = getBaseMapper().selectList(queryWrapper.orderByDesc(EsInfoDO::getUpdateDate));
        PageResp<EsInfoDTO> pageResp = PageRespUtil.buildPageResult(list, EsInfoDTO.class);
        return pageResp;
    }

    /**
     * 功能：新增保存记录
     */
    public Long save(EsInfoDTO esInfoDTO) {
        EsInfoDO esInfoDO = new EsInfoDO();
        DCBeanUtil.copyNotNull(esInfoDTO, esInfoDO);
        save(esInfoDO);
        return esInfoDO.getId();
    }

    /**
     * 功能：修改记录
     */
    public Boolean update(EsInfoDTO esInfoDTO) {
        if (DCObjectUtil.isNotNull(esInfoDTO.getId())) {
            EsInfoDO esInfoDO = this.baseMapper.selectById(esInfoDTO.getId());
            if (DCObjectUtil.isNotNull(esInfoDO)) {
                DCBeanUtil.copyNotNull(esInfoDTO, esInfoDO);
                return this.baseMapper.updateById(esInfoDO) > 0;
            }
        }
        return false;
    }

    /**
     * 功能：根据id删除记录
     */
    public Boolean deleteById(EsInfoDTO esInfoDTO) {
        EsInfoDO esInfoDO = this.baseMapper.selectById(esInfoDTO.getId());
        if (DCObjectUtil.isNotNull(esInfoDO)) {
            return super.removeById(esInfoDO.getId());
        }
        return false;
    }

    /**
     * 功能：根据id获取记录
     */
    public EsInfoDTO getWrapperById(Long id) {
        return DCBeanUtil.copyNotNull(this.baseMapper.selectById(id), new EsInfoDTO());
    }

    public EsBaseInfoDTO getBaseInfoById(Long id) {
        return DCBeanUtil.copyNotNull(this.baseMapper.selectById(id), new EsBaseInfoDTO());
    }

    /**
     * 功能：根据版本号-获取记录列表
     */
    public List<EsInfoDTO> listByVersion(String esVersion) {
        QueryWrapper<EsInfoDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("es_version", esVersion);
        return DCBeanUtil.copyList(baseMapper.selectList(queryWrapper), EsInfoDTO.class);
    }

    /**
     * 功能：获取所有的记录列表
     */
    public List<EsInfoDTO> listAll() {
        return DCBeanUtil.copyList(baseMapper.selectList(Wrappers.query()), EsInfoDTO.class);
    }

    public Boolean checkName(String name, Long id) {
        QueryWrapper<EsInfoDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("name", name);
        if (DCObjectUtil.isNotEmpty(id)) {
            queryWrapper.ne("id", id);
        }
        return baseMapper.selectCount(queryWrapper) > 0;
    }

    public Boolean checkClusterName(String clusterName, Long id) {
        QueryWrapper<EsInfoDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("cluster_name", clusterName);
        if (DCObjectUtil.isNotEmpty(id)) {
            queryWrapper.ne("id", id);
        }
        return baseMapper.selectCount(queryWrapper) > 0;
    }

    public Boolean checkEsAddr(String esAddr, Long id) {
        QueryWrapper<EsInfoDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("es_addr", esAddr);
        if (DCObjectUtil.isNotEmpty(id)) {
            queryWrapper.ne("id", id);
        }
        return baseMapper.selectCount(queryWrapper) > 0;
    }
}