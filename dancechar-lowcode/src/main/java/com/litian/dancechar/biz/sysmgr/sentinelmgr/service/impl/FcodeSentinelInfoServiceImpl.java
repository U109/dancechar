package com.litian.dancechar.biz.sysmgr.sentinelmgr.service.impl;
import com.litian.dancechar.biz.core.scaffold.manager.ScaffoldGenInfoManager;
import com.litian.dancechar.biz.sysmgr.sentinelmgr.dto.FcodeSentinelInfoDTO;
import com.litian.dancechar.biz.sysmgr.sentinelmgr.dto.FcodeSentinelInfoQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.sentinelmgr.manager.FcodeSentinelInfoManager;
import com.litian.dancechar.biz.sysmgr.sentinelmgr.repository.dataobject.FcodeSentinelInfoDO;
import com.litian.dancechar.biz.sysmgr.sentinelmgr.service.FcodeSentinelInfoService;
import com.litian.dancechar.common.common.dto.BaseParam;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCResultUtil;
import com.litian.dancechar.framework.common.util.DCStrUtil;
import com.litian.dancechar.framework.common.validator.DCValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述：sentinel配置信息服务实现
 *
 * @author 01410001
 * @date 2021-10-12 14:08:05
 */
@Component
@Slf4j
public class FcodeSentinelInfoServiceImpl implements FcodeSentinelInfoService {
    @Resource
    private FcodeSentinelInfoManager fcodeSentinelInfoManager;
    @Resource
    private ScaffoldGenInfoManager scaffoldGenInfoManager;

    private static final String ID_TIPS = "id不能为空";

    @Override
    public Result<PageWrapperDTO<FcodeSentinelInfoDTO>> listPage(FcodeSentinelInfoQueryReqDTO req) {
        PageWrapperDTO<FcodeSentinelInfoDTO> pageResp = fcodeSentinelInfoManager.listPage(req);
        return DCResultUtil.success(pageResp);
    }

    @Override
    public Result<Long> save(FcodeSentinelInfoDTO fcodeSentinelInfoDTO) {
        DCValidatorUtil.validateModel(fcodeSentinelInfoDTO, BaseParam.addUpdateRequired.class);
        if(fcodeSentinelInfoManager.checkName(fcodeSentinelInfoDTO.getSentinelName(),null)){
            return DCResultUtil.error("sentinel名称已存在");
        }
        return DCResultUtil.success(fcodeSentinelInfoManager.save(fcodeSentinelInfoDTO));
    }

    @Override
    public Result<Boolean> update(FcodeSentinelInfoDTO fcodeSentinelInfoDTO) {
        if (DCObjectUtil.isNull(fcodeSentinelInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        DCValidatorUtil.validateModel(fcodeSentinelInfoDTO, BaseParam.addUpdateRequired.class);
        if(DCStrUtil.isNotBlank(fcodeSentinelInfoDTO.getSentinelName()) && fcodeSentinelInfoManager.checkName(fcodeSentinelInfoDTO.getSentinelName(),fcodeSentinelInfoDTO.getId())){
            return DCResultUtil.error("sentinel名称已存在");
        }
        return DCResultUtil.success(fcodeSentinelInfoManager.update(fcodeSentinelInfoDTO));
    }

    @Override
    public Result<Boolean> deleteById(FcodeSentinelInfoDTO fcodeSentinelInfoDTO) {
        if (DCObjectUtil.isNull(fcodeSentinelInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        if(scaffoldGenInfoManager.checkSentinelHasUsed(fcodeSentinelInfoDTO.getId())){
            return DCResultUtil.error("该sentinel配置已被使用，不能删除");
        }
        return DCResultUtil.success(fcodeSentinelInfoManager.deleteById(fcodeSentinelInfoDTO));
    }

    @Override
    public Result<FcodeSentinelInfoDO> getWrapperById(FcodeSentinelInfoDTO fcodeSentinelInfoDTO) {
        if (DCObjectUtil.isNull(fcodeSentinelInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(fcodeSentinelInfoManager.getWrapperById(fcodeSentinelInfoDTO.getId()));
    }

    @Override
    public Result<List<FcodeSentinelInfoDTO>> getAllSentinelInfo(){
        return DCResultUtil.success(fcodeSentinelInfoManager.getAllSentinelInfo());
    }
}
