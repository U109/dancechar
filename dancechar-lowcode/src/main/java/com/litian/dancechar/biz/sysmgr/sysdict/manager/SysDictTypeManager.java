package com.litian.dancechar.biz.sysmgr.sysdict.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.biz.sysmgr.sysdict.dto.SysDictTypeDTO;
import com.litian.dancechar.biz.sysmgr.sysdict.dto.SysDictTypeQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.sysdict.dto.SysDictValueDTO;
import com.litian.dancechar.biz.sysmgr.sysdict.repository.dataobject.SysDictTypeDO;
import com.litian.dancechar.biz.sysmgr.sysdict.repository.mapper.SysDictTypeMapper;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCStrUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.util.page.PageRespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：数据字典类型manager
 *
 * @author 01407390
 * @date 2021-09-28 15:47:15
 */
@Component
@Slf4j
public class SysDictTypeManager extends ServiceImpl<SysDictTypeMapper, SysDictTypeDO> {

    @Autowired
    private SysDictDataManager fcodeSysDictDataManager;

    /**
     * 功能：分页查询列表记录
     */
    public PageResp<SysDictTypeDTO> listPage(SysDictTypeQueryReqDTO fcodeSysDictTypeQueryReqDTO) {
        PageHelper.startPage(fcodeSysDictTypeQueryReqDTO.getPageNo(), fcodeSysDictTypeQueryReqDTO.getPageSize());
        LambdaQueryWrapper<SysDictTypeDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(DCObjectUtil.isNotNull(fcodeSysDictTypeQueryReqDTO.getTypeSource()), SysDictTypeDO::getTypeSource, fcodeSysDictTypeQueryReqDTO.getTypeSource());
        queryWrapper.likeRight(DCStrUtil.isNotEmpty(fcodeSysDictTypeQueryReqDTO.getTypeName()), SysDictTypeDO::getTypeName, fcodeSysDictTypeQueryReqDTO.getTypeName());
        queryWrapper.likeRight(DCStrUtil.isNotEmpty(fcodeSysDictTypeQueryReqDTO.getTypeCode()), SysDictTypeDO::getTypeCode, fcodeSysDictTypeQueryReqDTO.getTypeCode());
        queryWrapper.orderByDesc(SysDictTypeDO::getUpdateDate);
        List<SysDictTypeDO> list = getBaseMapper().selectList(queryWrapper);
        PageResp<SysDictTypeDTO> pageResp = PageRespUtil.buildPageResult(list, SysDictTypeDTO.class);
        return pageResp;
    }

    /**
     * 功能：新增保存记录
     */
    public Long save(SysDictTypeDTO fcodeSysDictTypeDTO) {
        SysDictTypeDO fcodeSysDictTypeDO = new SysDictTypeDO();
        DCBeanUtil.copyNotNull(fcodeSysDictTypeDTO, fcodeSysDictTypeDO);
        save(fcodeSysDictTypeDO);
        return fcodeSysDictTypeDO.getId();
    }

    /**
     * 功能：修改记录
     */
    public Boolean update(SysDictTypeDTO fcodeSysDictTypeDTO) {
        if (DCObjectUtil.isNotNull(fcodeSysDictTypeDTO.getId())) {
            SysDictTypeDO fcodeSysDictTypeDO = this.baseMapper.selectById(fcodeSysDictTypeDTO.getId());
            if (DCObjectUtil.isNotNull(fcodeSysDictTypeDO)) {
                DCBeanUtil.copyNotNull(fcodeSysDictTypeDTO, fcodeSysDictTypeDO);
                return this.baseMapper.updateById(fcodeSysDictTypeDO) > 0;
            }
        }
        return false;
    }

    /**
     * 功能：根据id删除记录
     */
    public Boolean deleteById(SysDictTypeDTO fcodeSysDictTypeDTO) {
        return super.removeById(fcodeSysDictTypeDTO.getId());
    }

    /**
     * 功能：根据id获取记录
     */
    public List<SysDictValueDTO> getTypeAndDictByTypeId(Long typeId) {
        List<SysDictValueDTO> list = new ArrayList<>();
        SysDictTypeDO dictTypeDO = this.baseMapper.selectById(typeId);
        if (DCObjectUtil.isEmpty(dictTypeDO)) {
            return list;
        }
        if (dictTypeDO.getTypeSource() == 1) {
            return DCBeanUtil.copyList(fcodeSysDictDataManager.getDataByTypeId(dictTypeDO.getId()), SysDictValueDTO.class);
        }
        SysDictValueDTO dto = new SysDictValueDTO();
        dto.setRequestType(dictTypeDO.getRequestType());
        dto.setRequestParams(dictTypeDO.getRequestParams());
        dto.setValue(dictTypeDO.getRequestUrl());
        list.add(dto);
        return list;
    }

    public List<SysDictTypeDTO> getSysDictList(SysDictTypeDTO sysDictTypeDTO) {
        QueryWrapper<SysDictTypeDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("status", 1);
        queryWrapper.eq("delete_flag", 0);
        if (DCStrUtil.isNotBlank(sysDictTypeDTO.getTypeName())) {
            queryWrapper.eq("type_name", sysDictTypeDTO.getTypeName());
        }
        queryWrapper.orderByAsc("show_no");
        return DCBeanUtil.copyList(baseMapper.selectList(queryWrapper), SysDictTypeDTO.class);
    }

    public SysDictTypeDO getByCode(String code) {
        LambdaQueryWrapper<SysDictTypeDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysDictTypeDO::getTypeCode, code);
        queryWrapper.eq(SysDictTypeDO::getStatus, 1);
        return getBaseMapper().selectOne(queryWrapper);
    }

    public Boolean checkName(String typeName, Long id) {
        QueryWrapper<SysDictTypeDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("type_name", typeName);
        queryWrapper.eq("delete_flag", 0);
        if (DCObjectUtil.isNotEmpty(id)) {
            queryWrapper.ne("id", id);
        }
        return baseMapper.selectCount(queryWrapper) > 0;
    }

    public Boolean checkCode(String typeCode, Long id) {
        QueryWrapper<SysDictTypeDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("type_code", typeCode);
        queryWrapper.eq("delete_flag", 0);
        if (DCObjectUtil.isNotEmpty(id)) {
            queryWrapper.ne("id", id);
        }
        return baseMapper.selectCount(queryWrapper) > 0;
    }

    public SysDictTypeDO getById(Long id){
        return this.baseMapper.selectById(id);
    }

}
