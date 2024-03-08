package com.litian.dancechar.biz.sysmgr.esmgr.service.impl;

import com.litian.dancechar.biz.core.scaffold.manager.ScaffoldGenInfoManager;
import com.litian.dancechar.biz.sysmgr.esmgr.dto.EsInfoDTO;
import com.litian.dancechar.biz.sysmgr.esmgr.dto.EsInfoQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.esmgr.manager.EsInfoManager;
import com.litian.dancechar.biz.sysmgr.esmgr.service.EsInfoService;
import com.litian.dancechar.common.common.dto.BaseParam;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCResultUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.validator.DCValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述：es连接信息管理服务实现
 *
 * @author 01406831
 * @date 2021-11-04 15:28:33
 */
@Component
@Slf4j
public class EsInfoServiceImpl implements EsInfoService {
    @Resource
    private EsInfoManager esInfoManager;
    @Resource
    private ScaffoldGenInfoManager scaffoldGenInfoManager;

    @Override
    public Result<PageResp<EsInfoDTO>> listPage(EsInfoQueryReqDTO req) {
        PageResp<EsInfoDTO> pageResp = esInfoManager.listPage(req);
        return DCResultUtil.success(pageResp);
    }

    @Override
    public Result<Long> save(EsInfoDTO esInfoDTO) {
        DCValidatorUtil.validateModel(esInfoDTO, BaseParam.addUpdateRequired.class);
        if (esInfoManager.checkName(esInfoDTO.getName(), null)) {
            return DCResultUtil.error("es名称已存在");
        }
        if (esInfoManager.checkClusterName(esInfoDTO.getClusterName(), null)) {
            return DCResultUtil.error("es系统名称已存在");
        }
        if (esInfoManager.checkEsAddr(esInfoDTO.getEsAddr(), null)) {
            return DCResultUtil.error("es地址已存在");
        }
        return DCResultUtil.success(esInfoManager.save(esInfoDTO));
    }

    @Override
    public Result<Boolean> update(EsInfoDTO esInfoDTO) {
        if (DCObjectUtil.isNull(esInfoDTO.getId())) {
            return DCResultUtil.error("主键不能为空");
        }
        DCValidatorUtil.validateModel(esInfoDTO, BaseParam.addUpdateRequired.class);
        if (esInfoManager.checkName(esInfoDTO.getName(), esInfoDTO.getId())) {
            return DCResultUtil.error("es名称已存在");
        }
        if (esInfoManager.checkClusterName(esInfoDTO.getClusterName(), esInfoDTO.getId())) {
            return DCResultUtil.error("es系统名称已存在");
        }
        if (esInfoManager.checkEsAddr(esInfoDTO.getEsAddr(), esInfoDTO.getId())) {
            return DCResultUtil.error("es地址已存在");
        }
        return DCResultUtil.success(esInfoManager.update(esInfoDTO));
    }

    @Override
    public Result<Boolean> deleteById(EsInfoDTO esInfoDTO) {
        if (DCObjectUtil.isNull(esInfoDTO.getId())) {
            return DCResultUtil.error("主键不能为空");
        }
        if (scaffoldGenInfoManager.checkEsHasUsed(esInfoDTO.getId())) {
            return DCResultUtil.error("该配置已被使用,不能删除");
        }
        return DCResultUtil.success(esInfoManager.deleteById(esInfoDTO));
    }

    @Override
    public Result<EsInfoDTO> getWrapperById(EsInfoDTO esInfoDTO) {
        if (DCObjectUtil.isNull(esInfoDTO.getId())) {
            return DCResultUtil.error("主键不能为空");
        }
        return DCResultUtil.success(esInfoManager.getWrapperById(esInfoDTO.getId()));
    }

    @Override
    public Result<List<EsInfoDTO>> listByVersion(EsInfoDTO esInfoDTO) {
        if (DCObjectUtil.isNull(esInfoDTO.getEsVersion())) {
            return DCResultUtil.error("es版本不能为空");
        }
        return DCResultUtil.success(esInfoManager.listByVersion(esInfoDTO.getEsVersion()));
    }
}
