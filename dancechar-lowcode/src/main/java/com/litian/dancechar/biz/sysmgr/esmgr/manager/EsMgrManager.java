package com.litian.dancechar.biz.sysmgr.esmgr.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.biz.sysmgr.esmgr.dto.EsMgrDTO;
import com.litian.dancechar.biz.sysmgr.esmgr.dto.EsMgrQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.esmgr.repository.dataobject.EsMgrDO;
import com.litian.dancechar.biz.sysmgr.esmgr.repository.mapper.EsMgrMapper;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类描述：ES连接信息管理manager
 *
 * @author 01396106
 * @date 2021-10-18 13:39:03
 */
@Component
@Slf4j
public class EsMgrManager extends ServiceImpl<EsMgrMapper, EsMgrDO> {

    /**
     * 功能：分页查询列表记录
     */
    public PageWrapperDTO<EsMgrDTO> listPage(EsMgrQueryReqDTO esMgrQueryReqDTO) {
        PageHelper.startPage(esMgrQueryReqDTO.getPageNo(), esMgrQueryReqDTO.getPageSize());
        LambdaQueryWrapper<EsMgrDO> queryWrapper = Wrappers.lambdaQuery();
        if (DCObjectUtil.isNotEmpty(esMgrQueryReqDTO.getName())) {
            queryWrapper.like(EsMgrDO::getName, esMgrQueryReqDTO.getName());
        }
        if (DCObjectUtil.isNotEmpty(esMgrQueryReqDTO.getClusterName())){
            queryWrapper.like(EsMgrDO::getClusterName, esMgrQueryReqDTO.getClusterName());
        }
        queryWrapper.orderByDesc(EsMgrDO::getUpdateDate);
        List<EsMgrDO> list = getBaseMapper().selectList(queryWrapper);
        PageWrapperDTO<EsMgrDTO> pageResp = PageResultUtil.setPageResult(list, EsMgrDTO.class);
        return pageResp;
    }

    public List<EsMgrDTO> selectByName(EsMgrQueryReqDTO esMgrQueryReqDTO){

        LambdaQueryWrapper<EsMgrDO> queryWrapper = Wrappers.lambdaQuery();
        if (DCObjectUtil.isNotEmpty(esMgrQueryReqDTO.getName())) {
            queryWrapper.eq(EsMgrDO::getName, esMgrQueryReqDTO.getName());
        }
        if (DCObjectUtil.isNotEmpty(esMgrQueryReqDTO.getClusterName())){
            queryWrapper.eq(EsMgrDO::getClusterName, esMgrQueryReqDTO.getClusterName());
        }
        queryWrapper.orderByDesc(EsMgrDO::getUpdateDate);
        List<EsMgrDO> list = getBaseMapper().selectList(queryWrapper);
        return DCBeanUtil.copyList(list,EsMgrDTO.class);
    }

    /**
     * 功能：新增保存记录
     * @return
     */
    public Result<Object> save(EsMgrDTO esMgrDTO) {
        EsMgrQueryReqDTO esMgrQueryReqDTO = EsMgrQueryReqDTO.builder().name(esMgrDTO.getName()).build();
        if (DCCollectionUtil.isNotEmpty(this.selectByName(esMgrQueryReqDTO))){
            return DCResultUtil.error("name属性不能重复");
        }
        esMgrQueryReqDTO =  EsMgrQueryReqDTO.builder().name(null).clusterName(esMgrDTO.getClusterName()).build();
        if (DCCollectionUtil.isNotEmpty(this.selectByName(esMgrQueryReqDTO))){
            return DCResultUtil.error("cluster name属性不能重复");
        }
        EsMgrDO esMgrDO = new EsMgrDO();
        DCBeanUtil.copyNotNull(esMgrDTO, esMgrDO);
        save(esMgrDO);
        return DCResultUtil.success("新增成功");
    }

    /**
     * 功能：修改记录
     */
    public Boolean update(EsMgrDTO esMgrDTO) {
        if (DCObjectUtil.isNotNull(esMgrDTO.getId())) {
            EsMgrDO esMgrDO = this.baseMapper.selectById(esMgrDTO.getId());
            if (DCObjectUtil.isNotNull(esMgrDO)) {
                DCBeanUtil.copyNotNull(esMgrDTO, esMgrDO);
                return this.baseMapper.updateById(esMgrDO) > 0;
            }
        }
        return false;
    }

    /**
     * 功能：根据id删除记录
     */
    public Boolean deleteById(EsMgrDTO esMgrDTO) {
        EsMgrDO esMgrDO = this.baseMapper.selectById(esMgrDTO.getId());
        if (DCObjectUtil.isNotNull(esMgrDO)) {
            return super.removeById(esMgrDO.getId());
        }
        return false;
    }

    /**
     * 功能：根据id获取记录
     * @return
     */
    public EsMgrDO getWrapperById(Long id) {
        return DCBeanUtil.copyNotNull(new EsMgrDTO(),this.baseMapper.selectById(id) );
    }
}
