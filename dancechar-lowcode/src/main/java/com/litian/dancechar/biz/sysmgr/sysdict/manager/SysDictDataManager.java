package com.litian.dancechar.biz.sysmgr.sysdict.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.biz.sysmgr.sysdict.dto.SysDictDataDTO;
import com.litian.dancechar.biz.sysmgr.sysdict.dto.SysDictDataQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.sysdict.dto.SysDictDataReqDTO;
import com.litian.dancechar.biz.sysmgr.sysdict.repository.dataobject.SysDictDataDO;
import com.litian.dancechar.biz.sysmgr.sysdict.repository.mapper.SysDictDataMapper;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCStrUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.util.page.PageRespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：数据字典数据manager
 *
 * @author 01407390
 * @date 2021-09-28 15:16:56
 */
@Component
@Slf4j
public class SysDictDataManager extends ServiceImpl<SysDictDataMapper, SysDictDataDO> {

    /**
     * 功能：分页查询列表记录
     */
    public PageResp<SysDictDataDTO> listPage(SysDictDataQueryReqDTO fcodeSysDictDataQueryReqDTO) {
        PageHelper.startPage(fcodeSysDictDataQueryReqDTO.getPageNo(), fcodeSysDictDataQueryReqDTO.getPageSize());
        LambdaQueryWrapper<SysDictDataDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysDictDataDO::getTypeId, fcodeSysDictDataQueryReqDTO.getTypeId());
        if (DCStrUtil.isNotEmpty(fcodeSysDictDataQueryReqDTO.getCode())) {
            queryWrapper.likeRight(SysDictDataDO::getCode, fcodeSysDictDataQueryReqDTO.getCode());
        }
        if (DCStrUtil.isNotEmpty(fcodeSysDictDataQueryReqDTO.getValue())) {
            queryWrapper.likeRight(SysDictDataDO::getValue, fcodeSysDictDataQueryReqDTO.getValue());
        }
        queryWrapper.orderByDesc(SysDictDataDO::getUpdateDate);
        List<SysDictDataDO> list = getBaseMapper().selectList(queryWrapper);
        PageResp<SysDictDataDTO> pageResp = PageRespUtil.buildPageResult(list, SysDictDataDTO.class);
        return pageResp;
    }

    /**
     * 功能：新增保存记录
     */
    public Long save(SysDictDataDTO sysDictDataDTO) {
        SysDictDataDO sysDictDataDO = new SysDictDataDO();
        DCBeanUtil.copyNotNull(sysDictDataDTO, sysDictDataDO);
        save(sysDictDataDO);
        return sysDictDataDO.getId();
    }

    /**
     * 功能：修改记录
     */
    public Boolean update(SysDictDataDTO sysDictDataDTO) {
        if (DCObjectUtil.isNotNull(sysDictDataDTO.getId())) {
            SysDictDataDO sysDictDataDO = this.baseMapper.selectById(sysDictDataDTO.getId());
            if (DCObjectUtil.isNotNull(sysDictDataDO)) {
                DCBeanUtil.copyNotNull(sysDictDataDTO, sysDictDataDO);
                return this.baseMapper.updateById(sysDictDataDO) > 0;
            }
        }
        return false;
    }

    /**
     * 功能：新增修改记录
     */
    public Boolean saveOrUpdate(SysDictDataReqDTO fcodeSysDictDataReqDTO) {
        List<SysDictDataDTO> sysDictDataDTOList = fcodeSysDictDataReqDTO.getSysDictDataDTOList();
        List<SysDictDataDO> saveList = new ArrayList<>();
        List<SysDictDataDO> updateList = new ArrayList<>();
        sysDictDataDTOList.forEach(sysDictDataDTO -> {
            SysDictDataDO dataDO = new SysDictDataDO();
            DCBeanUtil.copyNotNull(sysDictDataDTO, dataDO);
            dataDO.setTypeId(fcodeSysDictDataReqDTO.getTypeId());
            if (DCObjectUtil.isEmpty(dataDO.getId())) {
                saveList.add(dataDO);
            } else {
                updateList.add(dataDO);
            }
        });
        updateBatch(updateList);
        return saveBatch(saveList);
    }

    /**
     * 功能：修改记录
     */
    public Boolean updateBatch(List<SysDictDataDO> sysDictDataList) {
        sysDictDataList.forEach(sysDictData -> {
            baseMapper.updateById(sysDictData);
        });
        return true;
    }

    /**
     * 功能：根据id删除记录
     */
    public Boolean deleteById(SysDictDataDTO fcodeSysDictDataDTO) {
        SysDictDataDO fcodeSysDictDataDO = this.baseMapper.selectById(fcodeSysDictDataDTO.getId());
        if (DCObjectUtil.isNotNull(fcodeSysDictDataDO)) {
            return super.removeById(fcodeSysDictDataDO.getId());
        }
        return false;
    }

    /**
     * 功能：根据id获取记录
     */
    public SysDictDataDTO getWrapperById(Long id) {
        return DCBeanUtil.copyNotNull(this.baseMapper.selectById(id), new SysDictDataDTO());
    }

    public List<SysDictDataDO> getDataByTypeId(Long typeId) {
        QueryWrapper<SysDictDataDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("type_id", typeId);
        queryWrapper.eq("status", 1);
        queryWrapper.eq("delete_flag", 0);
        queryWrapper.orderByAsc("show_no");
        return baseMapper.selectList(queryWrapper);
    }

    public Boolean checkName(String value,Long typeId,Long id) {
        QueryWrapper<SysDictDataDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("value", value);
        queryWrapper.eq("type_id", typeId);
        queryWrapper.eq("delete_flag", 0);
        if (DCObjectUtil.isNotEmpty(id)) {
            queryWrapper.ne("id", id);
        }
        return baseMapper.selectCount(queryWrapper) > 0;
    }

    public Boolean checkCode(String code,Long typeId,Long id) {
        QueryWrapper<SysDictDataDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("code", code);
        queryWrapper.eq("type_id", typeId);
        queryWrapper.eq("delete_flag", 0);
        if (DCObjectUtil.isNotEmpty(id)) {
            queryWrapper.ne("id", id);
        }
        return baseMapper.selectCount(queryWrapper) > 0;
    }

    public Boolean checkDictHasData(Long typeId) {
        QueryWrapper<SysDictDataDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("type_id", typeId);
        queryWrapper.eq("delete_flag", 0);
        return baseMapper.selectCount(queryWrapper)>0;
    }
}
