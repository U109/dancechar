package com.litian.dancechar.biz.sysmgr.sysdict.service.impl;

import cn.hutool.core.lang.Dict;
import com.litian.dancechar.biz.sysmgr.sysdict.dto.SysDictDataDTO;
import com.litian.dancechar.biz.sysmgr.sysdict.dto.SysDictDataQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.sysdict.manager.SysDictDataManager;
import com.litian.dancechar.biz.sysmgr.sysdict.manager.SysDictTypeManager;
import com.litian.dancechar.biz.sysmgr.sysdict.repository.dataobject.SysDictTypeDO;
import com.litian.dancechar.biz.sysmgr.sysdict.service.FcodeSysDictDataService;
import com.litian.dancechar.common.common.dto.BaseParam;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCResultUtil;
import com.litian.dancechar.framework.common.util.DCStrUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.validator.DCValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述：数据字典数据服务实现
 *
 * @author 01407390
 * @date 2021-09-28 15:16:56
 */
@Component
@Slf4j
public class FcodeSysDictDataServiceImpl implements FcodeSysDictDataService {
    @Resource
    private SysDictDataManager sysDictDataManager;
    @Resource
    private SysDictTypeManager sysDictTypeManager;

    private static final String ID_TIPS = "id不能为空";

    @Override
    public Result<PageResp<SysDictDataDTO>> listPage(SysDictDataQueryReqDTO req) {
        if (DCObjectUtil.isNull(req.getTypeId())) {
            return DCResultUtil.error("typeId不能为空");
        }
        PageResp<SysDictDataDTO> pageResp = sysDictDataManager.listPage(req);
        return DCResultUtil.success(pageResp);
    }

    @Override
    public Result<Long> save(SysDictDataDTO sysDictDataDTO) {
        DCValidatorUtil.validateModel(sysDictDataDTO, BaseParam.addUpdateRequired.class);
        if (sysDictDataManager.checkName(sysDictDataDTO.getValue(),sysDictDataDTO.getTypeId(), null)) {
            return DCResultUtil.error("该字典中名称已存在");
        }
        if (sysDictDataManager.checkCode(sysDictDataDTO.getCode(),sysDictDataDTO.getTypeId(), null)) {
            return DCResultUtil.error("该字典中code已存在");
        }
        return DCResultUtil.success(sysDictDataManager.save(sysDictDataDTO));
    }

    @Override
    public Result<Boolean> update(SysDictDataDTO sysDictDataDTO) {
        if (DCObjectUtil.isNull(sysDictDataDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        DCValidatorUtil.validateModel(sysDictDataDTO, BaseParam.addUpdateRequired.class);
        if (sysDictDataManager.checkName(sysDictDataDTO.getValue(),sysDictDataDTO.getTypeId(), sysDictDataDTO.getId())) {
            return DCResultUtil.error("该字典中名称已存在");
        }
        if (sysDictDataManager.checkCode(sysDictDataDTO.getCode(),sysDictDataDTO.getTypeId(), sysDictDataDTO.getId())) {
            return DCResultUtil.error("该字典中code已存在");
        }
        return DCResultUtil.success(sysDictDataManager.update(sysDictDataDTO));
    }

    @Override
    public Result<Boolean> deleteById(SysDictDataDTO fcodeSysDictDataDTO) {
        if (DCObjectUtil.isNull(fcodeSysDictDataDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(sysDictDataManager.deleteById(fcodeSysDictDataDTO));
    }

    @Override
    public Result<SysDictDataDTO> getWrapperById(SysDictDataDTO fcodeSysDictDataDTO) {
        if (DCObjectUtil.isNull(fcodeSysDictDataDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(sysDictDataManager.getWrapperById(fcodeSysDictDataDTO.getId()));
    }

    @Override
    public Result<List<SysDictDataDTO>> listByTypeCode(Dict dict) {
        String typeCode = dict.getStr("typeCode");
        if (DCStrUtil.isEmpty(typeCode)) {
            return DCResultUtil.error("字典类型code不能为空");
        }
        SysDictTypeDO sysDictTypeDO = sysDictTypeManager.getByCode(typeCode);
        if (DCObjectUtil.isNull(sysDictTypeDO)) {
            return DCResultUtil.error("字典类型不存在");
        }
        return DCResultUtil.success(DCBeanUtil.copyList(sysDictDataManager.getDataByTypeId(sysDictTypeDO.getId()), SysDictDataDTO.class));
    }
}
