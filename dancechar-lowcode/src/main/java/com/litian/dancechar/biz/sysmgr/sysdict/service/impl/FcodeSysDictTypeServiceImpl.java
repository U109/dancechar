package com.litian.dancechar.biz.sysmgr.sysdict.service.impl;

import com.litian.dancechar.biz.sysmgr.sysdict.dto.SysDictTypeDTO;
import com.litian.dancechar.biz.sysmgr.sysdict.dto.SysDictTypeQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.sysdict.dto.SysDictValueDTO;
import com.litian.dancechar.biz.sysmgr.sysdict.manager.SysDictDataManager;
import com.litian.dancechar.biz.sysmgr.sysdict.manager.SysDictTypeManager;
import com.litian.dancechar.biz.sysmgr.sysdict.repository.dataobject.SysDictTypeDO;
import com.litian.dancechar.biz.sysmgr.sysdict.service.FcodeSysDictTypeService;
import com.litian.dancechar.common.common.dto.BaseParam;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCResultUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.validator.DCValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述：数据字典类型服务实现
 *
 * @author 01407390
 * @date 2021-09-28 15:47:15
 */
@Component
@Slf4j
public class FcodeSysDictTypeServiceImpl implements FcodeSysDictTypeService {
    @Resource
    private SysDictTypeManager fcodeSysDictTypeManager;
    @Resource
    private SysDictDataManager sysDictDataManager;


    private static final String ID_TIPS = "id不能为空";

    @Override
  //  @SentinelResource(value = "DictTypeListPage", blockHandler = "blockHandlerError", fallback = "fallBackError")
    public Result<PageResp<SysDictTypeDTO>> listPage(SysDictTypeQueryReqDTO req) {
        PageResp<SysDictTypeDTO> pageResp = fcodeSysDictTypeManager.listPage(req);
        return DCResultUtil.success(pageResp);
    }

    @Override
    public Result<Long> save(SysDictTypeDTO fcodeSysDictTypeDTO) {
        DCValidatorUtil.validateModel(fcodeSysDictTypeDTO, BaseParam.addUpdateRequired.class);
        if (fcodeSysDictTypeManager.checkName(fcodeSysDictTypeDTO.getTypeName(), null)) {
            return DCResultUtil.error("该类型名已存在");
        }
        if (fcodeSysDictTypeManager.checkCode(fcodeSysDictTypeDTO.getTypeCode(), null)) {
            return DCResultUtil.error("该编码已存在");
        }
        return DCResultUtil.success(fcodeSysDictTypeManager.save(fcodeSysDictTypeDTO));
    }

    @Override
    public Result<Boolean> update(SysDictTypeDTO fcodeSysDictTypeDTO) {
        if (DCObjectUtil.isNull(fcodeSysDictTypeDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        DCValidatorUtil.validateModel(fcodeSysDictTypeDTO, BaseParam.addUpdateRequired.class);
        if (fcodeSysDictTypeManager.checkName(fcodeSysDictTypeDTO.getTypeName(), fcodeSysDictTypeDTO.getId())) {
            return DCResultUtil.error("该类型名已存在");
        }
        if (fcodeSysDictTypeManager.checkCode(fcodeSysDictTypeDTO.getTypeCode(), fcodeSysDictTypeDTO.getId())) {
            return DCResultUtil.error("该编码已存在");
        }
        return DCResultUtil.success(fcodeSysDictTypeManager.update(fcodeSysDictTypeDTO));
    }

    @Override
    public Result<Boolean> deleteById(SysDictTypeDTO fcodeSysDictTypeDTO) {
        if (DCObjectUtil.isNull(fcodeSysDictTypeDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        SysDictTypeDO fcodeSysDictTypeDO = fcodeSysDictTypeManager.getBaseMapper().selectById(fcodeSysDictTypeDTO.getId());
        if (DCObjectUtil.isEmpty(fcodeSysDictTypeDO)) {
            return DCResultUtil.error("该数据不存在");
        }
        if (fcodeSysDictTypeDO.getTypeSource() == 1 && sysDictDataManager.checkDictHasData(fcodeSysDictTypeDTO.getId())) {
            return DCResultUtil.error("请先删除字典数据");
        }
        return DCResultUtil.success(fcodeSysDictTypeManager.deleteById(fcodeSysDictTypeDTO));
    }

    @Override
    public Result<List<SysDictValueDTO>> getTypeAndDictByTypeId(SysDictTypeDTO fcodeSysDictTypeDTO) {
        if (DCObjectUtil.isNull(fcodeSysDictTypeDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(fcodeSysDictTypeManager.getTypeAndDictByTypeId(fcodeSysDictTypeDTO.getId()));
    }

    @Override
    public Result<SysDictTypeDTO> getById(SysDictTypeDTO fcodeSysDictTypeDTO) {
        if (DCObjectUtil.isNull(fcodeSysDictTypeDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(DCBeanUtil.copyNotNull(fcodeSysDictTypeManager.getById(fcodeSysDictTypeDTO.getId()),
                new SysDictTypeDTO()));
    }

    @Override
    public Result<List<SysDictTypeDTO>> getSysDictList(SysDictTypeDTO fcodeSysDictTypeDTO) {
        return DCResultUtil.success(fcodeSysDictTypeManager.getSysDictList(fcodeSysDictTypeDTO));
    }

    /*public Result<Void> blockHandlerError(SysDictTypeQueryReqDTO req, BlockException e) {
        log.error("{}===FcodeSysDictTypeService-findList-触发限流机制", Thread.currentThread().getName(), e);
        return DCResultUtil.error("blockHandlerError", "触发限流，返回为空");
    }*/

    public Result<Void> fallBackError(SysDictTypeQueryReqDTO req) {
        log.error("{}===FcodeSysDictTypeService-findList-触发熔断机制", Thread.currentThread().getName());
        return DCResultUtil.error("fallBackError", "触发熔断，返回为空");
    }
}
