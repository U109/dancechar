package com.litian.dancechar.biz.sysmgr.mongodbmgr.service.impl;

import com.litian.dancechar.biz.core.scaffold.manager.ScaffoldGenInfoManager;
import com.litian.dancechar.biz.sysmgr.mongodbmgr.dto.FcodeMongodbBaseInfoDTO;
import com.litian.dancechar.biz.sysmgr.mongodbmgr.dto.FcodeMongodbInfoDTO;
import com.litian.dancechar.biz.sysmgr.mongodbmgr.dto.FcodeMongodbInfoQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.mongodbmgr.manager.FcodeMongodbInfoManager;
import com.litian.dancechar.biz.sysmgr.mongodbmgr.service.FcodeMongodbInfoService;
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
 * 类描述：mongodb配置信息服务实现
 *
 * @author 01410001
 * @date 2021-11-08 16:16:16
 */
@Component
@Slf4j
public class FcodeMongodbInfoServiceImpl implements FcodeMongodbInfoService {
    @Resource
    private FcodeMongodbInfoManager fcodeMongodbInfoManager;
    @Resource
    private ScaffoldGenInfoManager scaffoldGenInfoManager;

    @Override
    public Result<com.litian.dancechar.framework.common.util.page.PageResp<FcodeMongodbInfoDTO>> listPage(FcodeMongodbInfoQueryReqDTO req) {
        PageResp<FcodeMongodbInfoDTO> pageResp = fcodeMongodbInfoManager.listPage(req);
        return DCResultUtil.success(pageResp);
    }

    @Override
    public Result<Long> save(FcodeMongodbInfoDTO fcodeMongodbInfoDTO) {
        DCValidatorUtil.validateModel(fcodeMongodbInfoDTO, BaseParam.addUpdateRequired.class);
        if(fcodeMongodbInfoManager.getInfoByName(fcodeMongodbInfoDTO)){
            return DCResultUtil.error("mongodb名称已存在");
        }
        return DCResultUtil.success(fcodeMongodbInfoManager.save(fcodeMongodbInfoDTO));
    }

    @Override
    public Result<Boolean> update(FcodeMongodbInfoDTO fcodeMongodbInfoDTO) {
        if (DCObjectUtil.isNull(fcodeMongodbInfoDTO.getId())) {
            return DCResultUtil.error("主键不能为空");
        }
        DCValidatorUtil.validateModel(fcodeMongodbInfoDTO, BaseParam.addUpdateRequired.class);
        if(fcodeMongodbInfoManager.getInfoByName(fcodeMongodbInfoDTO)){
            return DCResultUtil.error("mongodb名称已存在");
        }
        return DCResultUtil.success(fcodeMongodbInfoManager.update(fcodeMongodbInfoDTO));
    }

    @Override
    public Result<Boolean> deleteById(FcodeMongodbInfoDTO fcodeMongodbInfoDTO) {
        if (DCObjectUtil.isNull(fcodeMongodbInfoDTO.getId())) {
            return DCResultUtil.error("主键不能为空");
        }
        if (scaffoldGenInfoManager.checkMongodbHasUsed(fcodeMongodbInfoDTO.getId())) {
            return DCResultUtil.error("该配置已被使用,不能删除");
        }
        return DCResultUtil.success(fcodeMongodbInfoManager.deleteById(fcodeMongodbInfoDTO));
    }

    @Override
    public Result<FcodeMongodbInfoDTO> getWrapperById(FcodeMongodbInfoDTO fcodeMongodbInfoDTO) {
        if (DCObjectUtil.isNull(fcodeMongodbInfoDTO.getId())) {
            return DCResultUtil.error("主键不能为空");
        }
        return DCResultUtil.success(fcodeMongodbInfoManager.getWrapperById(fcodeMongodbInfoDTO.getId()));
    }

    @Override
    public Result<List<FcodeMongodbBaseInfoDTO>> getMongodbList() {
        return DCResultUtil.success(fcodeMongodbInfoManager.getMongodbList());
    }
}
