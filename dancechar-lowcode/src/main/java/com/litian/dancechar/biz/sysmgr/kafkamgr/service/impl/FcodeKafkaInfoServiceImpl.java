package com.litian.dancechar.biz.sysmgr.kafkamgr.service.impl;

import com.litian.dancechar.biz.core.scaffold.manager.ScaffoldGenInfoManager;
import com.litian.dancechar.biz.sysmgr.kafkamgr.dto.FcodeKafkaBaseInfoDTO;
import com.litian.dancechar.biz.sysmgr.kafkamgr.dto.FcodeKafkaInfoDTO;
import com.litian.dancechar.biz.sysmgr.kafkamgr.dto.FcodeKafkaInfoQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.kafkamgr.manager.FcodeKafkaInfoManager;
import com.litian.dancechar.biz.sysmgr.kafkamgr.service.FcodeKafkaInfoService;
import com.litian.dancechar.common.common.dto.BaseParam;
import com.litian.dancechar.framework.common.base.Result;
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
 * 类描述：kafka配置信息服务实现
 *
 * @author 01410001
 * @date 2021-10-12 14:07:34
 */
@Component
@Slf4j
public class FcodeKafkaInfoServiceImpl implements FcodeKafkaInfoService {
    @Resource
    private FcodeKafkaInfoManager fcodeKafkaInfoManager;
    @Resource
    private ScaffoldGenInfoManager scaffoldGenInfoManager;

    private static final String ID_TIPS = "id不能为空";

    @Override
    public Result<PageResp<FcodeKafkaInfoDTO>> listPage(FcodeKafkaInfoQueryReqDTO req) {
        PageResp<FcodeKafkaInfoDTO> pageResp = fcodeKafkaInfoManager.listPage(req);
        return DCResultUtil.success(pageResp);
    }

    @Override
    public Result<Long> save(FcodeKafkaInfoDTO fcodeKafkaInfoDTO) {
        DCValidatorUtil.validateModel(fcodeKafkaInfoDTO, BaseParam.addUpdateRequired.class);
        if(fcodeKafkaInfoManager.checkName(fcodeKafkaInfoDTO.getKafkaName(),null)){
            return DCResultUtil.error("kafka名称已存在");
        }
        return DCResultUtil.success(fcodeKafkaInfoManager.save(fcodeKafkaInfoDTO));
    }

    @Override
    public Result<Boolean> update(FcodeKafkaInfoDTO fcodeKafkaInfoDTO) {
        if (DCObjectUtil.isNull(fcodeKafkaInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        DCValidatorUtil.validateModel(fcodeKafkaInfoDTO, BaseParam.addUpdateRequired.class);
        if(DCStrUtil.isNotBlank(fcodeKafkaInfoDTO.getKafkaName()) && fcodeKafkaInfoManager.checkName(fcodeKafkaInfoDTO.getKafkaName(),fcodeKafkaInfoDTO.getId())){
            return DCResultUtil.error("kafka名称已存在");
        }
        return DCResultUtil.success(fcodeKafkaInfoManager.update(fcodeKafkaInfoDTO));
    }

    @Override
    public Result<Boolean> deleteById(FcodeKafkaInfoDTO fcodeKafkaInfoDTO) {
        if (DCObjectUtil.isNull(fcodeKafkaInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        if(scaffoldGenInfoManager.checkKafkaHasUsed(fcodeKafkaInfoDTO.getId())){
            return DCResultUtil.error("该kafka配置已被使用，不能删除");
        }
        return DCResultUtil.success(fcodeKafkaInfoManager.deleteById(fcodeKafkaInfoDTO));
    }

    @Override
    public Result<FcodeKafkaBaseInfoDTO> getWrapperById(FcodeKafkaInfoDTO fcodeKafkaInfoDTO) {
        if (DCObjectUtil.isNull(fcodeKafkaInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(fcodeKafkaInfoManager.getWrapperById(fcodeKafkaInfoDTO.getId()));
    }

    @Override
    public Result<List<FcodeKafkaInfoDTO>> getAllKafkaInfo(){
        return DCResultUtil.success(fcodeKafkaInfoManager.getAllKafkaInfo());
    }
}
