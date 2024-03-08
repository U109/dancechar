package com.litian.dancechar.biz.sysmgr.esmgr.service.impl;

import com.litian.dancechar.biz.sysmgr.esmgr.dto.EsMgrDTO;
import com.litian.dancechar.biz.sysmgr.esmgr.dto.EsMgrQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.esmgr.manager.EsMgrManager;
import com.litian.dancechar.biz.sysmgr.esmgr.repository.dataobject.EsMgrDO;
import com.litian.dancechar.biz.sysmgr.esmgr.service.EsMgrService;
import com.litian.dancechar.common.common.dto.BaseParam;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCResultUtil;
import com.litian.dancechar.framework.common.validator.DCValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 类描述：ES连接信息管理服务实现
 *
 * @author 01396106
 * @date 2021-10-18 13:39:03
 */
@Component
@Slf4j
public class EsMgrServiceImpl implements EsMgrService {
    @Resource
    private EsMgrManager esMgrManager;

    private static final String ID_TIPS = "id不能为空";

    @Override
    public Result<PageWrapperDTO<EsMgrDTO>> listPage(EsMgrQueryReqDTO req) {
        PageWrapperDTO<EsMgrDTO> pageResp = esMgrManager.listPage(req);
        return DCResultUtil.success(pageResp);
    }

    @Override
    public Result<Object> save(EsMgrDTO esMgrDTO) {
        DCValidatorUtil.validateModel(esMgrDTO, BaseParam.addUpdateRequired.class);
        return esMgrManager.save(esMgrDTO);
    }

    @Override
    public Result<Boolean> update(EsMgrDTO esMgrDTO) {
        if (DCObjectUtil.isNull(esMgrDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        DCValidatorUtil.validateModel(esMgrDTO, BaseParam.addUpdateRequired.class);
        return DCResultUtil.success(esMgrManager.update(esMgrDTO));
    }

    @Override
    public Result<Boolean> deleteById(EsMgrDTO esMgrDTO) {
        if (DCObjectUtil.isNull(esMgrDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(esMgrManager.deleteById(esMgrDTO));
    }

    @Override
    public Result<EsMgrDO> getWrapperById(EsMgrDTO esMgrDTO) {
        if (DCObjectUtil.isNull(esMgrDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(esMgrManager.getWrapperById(esMgrDTO.getId()));
    }
}
