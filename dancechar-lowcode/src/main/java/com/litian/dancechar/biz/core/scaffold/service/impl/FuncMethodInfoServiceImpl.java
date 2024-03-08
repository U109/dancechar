package com.litian.dancechar.biz.core.scaffold.service.impl;

import com.litian.dancechar.biz.core.scaffold.dto.FuncMethodListRespDTO;
import com.litian.dancechar.biz.core.scaffold.dto.FuncMethodReqDTO;
import com.litian.dancechar.biz.core.scaffold.manager.FuncMethodInfoManager;
import com.litian.dancechar.biz.core.scaffold.service.FuncMethodInfoService;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.DCResultUtil;
import com.litian.dancechar.framework.common.validator.DCValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 类描述：方法详情
 *
 * @author 01410001
 * @date 2021/09/10 16:09
 */
@Slf4j
@Service
public class FuncMethodInfoServiceImpl implements FuncMethodInfoService {

    @Autowired
    private FuncMethodInfoManager funcMethodInfoManager;

    @Override
    public Result<FuncMethodListRespDTO> getMethodInfoList(FuncMethodReqDTO funcMethodReqDTO) {
        try{
            DCValidatorUtil.validateModel(funcMethodReqDTO);
            return DCResultUtil.success(funcMethodInfoManager.getMethodInfoList(funcMethodReqDTO));
        }catch (Exception e){
            log.error("获取方法列表异常:{}",e);
            return DCResultUtil.error("获取方法列表异常");
        }
    }
}
