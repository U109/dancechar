package com.litian.dancechar.biz.sysmgr.redismgr.service.impl;

import com.litian.dancechar.biz.sysmgr.redismgr.dto.FcodeRedisInfoDTO;
import com.litian.dancechar.biz.sysmgr.redismgr.dto.FcodeRedisInfoQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.redismgr.manager.FcodeRedisInfoManager;
import com.litian.dancechar.biz.sysmgr.redismgr.service.FcodeRedisInfoService;
import com.litian.dancechar.common.common.dto.BaseParam;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.DCCollectionUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCResultUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.validator.DCValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述：Redis信息管理服务实现
 *
 * @author 01396106
 * @date 2021-10-12 11:05:08
 */
@Component
@Slf4j
public class FcodeRedisInfoServiceImpl implements FcodeRedisInfoService {
    @Resource
    private FcodeRedisInfoManager fcodeRedisInfoManager;

    private static final String ID_TIPS = "id不能为空";


    @Override
    public Result<List<FcodeRedisInfoDTO>> dropdownlist(FcodeRedisInfoQueryReqDTO req) {
        FcodeRedisInfoDTO fcodeRedisInfoDTO = new FcodeRedisInfoDTO();
        fcodeRedisInfoDTO.setInstallType(req.getInstallType());
        return DCResultUtil.success(fcodeRedisInfoManager.selectList(fcodeRedisInfoDTO));
    }

    @Override
    public Result<com.litian.dancechar.framework.common.util.page.PageResp<FcodeRedisInfoDTO>> listPage(FcodeRedisInfoQueryReqDTO req) {
        PageResp<FcodeRedisInfoDTO> pageResp = fcodeRedisInfoManager.listPage(req);
        return DCResultUtil.success(pageResp);
    }

    @Override
    public Result<String> save(FcodeRedisInfoDTO fcodeRedisInfoDTO) {
        DCValidatorUtil.validateModel(fcodeRedisInfoDTO, BaseParam.addUpdateRequired.class);
        FcodeRedisInfoDTO fcodeRedisInfoDTOSearchReq = new FcodeRedisInfoDTO();
        fcodeRedisInfoDTOSearchReq.setName(fcodeRedisInfoDTO.getName());
        if (DCCollectionUtil.isNotEmpty(fcodeRedisInfoManager.selectList(fcodeRedisInfoDTOSearchReq))){
            return DCResultUtil.error("名称不能重复");
        }
        try {
            if (fcodeRedisInfoManager.save(fcodeRedisInfoDTO)) {
                return DCResultUtil.success(fcodeRedisInfoDTO.getName());
            }
        }catch (Exception e){
            log.error("新增redis信息异常,{}",e.getMessage(),e);
        }
        return DCResultUtil.error("新增异常");
    }

    @Override
    public Result<Boolean> update(FcodeRedisInfoDTO fcodeRedisInfoDTO) {
        if (DCObjectUtil.isNull(fcodeRedisInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        DCValidatorUtil.validateModel(fcodeRedisInfoDTO, BaseParam.addUpdateRequired.class);
        return DCResultUtil.success(fcodeRedisInfoManager.update(fcodeRedisInfoDTO));
    }

    @Override
    public Result<Boolean> deleteById(FcodeRedisInfoDTO fcodeRedisInfoDTO) {
        if (DCObjectUtil.isNull(fcodeRedisInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(fcodeRedisInfoManager.deleteById(fcodeRedisInfoDTO));
    }

    @Override
    public Result<FcodeRedisInfoDTO> getWrapperById(FcodeRedisInfoDTO fcodeRedisInfoDTO) {
        if (DCObjectUtil.isNull(fcodeRedisInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(fcodeRedisInfoManager.getWrapperById(fcodeRedisInfoDTO.getId()));
    }
}
