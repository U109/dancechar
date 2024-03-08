package com.litian.dancechar.biz.core.codegen.service.impl;

import com.litian.dancechar.biz.core.codegen.common.response.ResponseData;
import com.litian.dancechar.biz.core.codegen.common.response.SuccessResponseData;
import com.litian.dancechar.biz.core.codegen.dto.SysCodeGenerateConfigParam;
import com.litian.dancechar.biz.core.codegen.manager.CodeGenDetailConfigManager;
import com.litian.dancechar.biz.core.codegen.service.CodeGenDetailConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 类描述：代码生成详细配置服务实现
 *
 * @author 01406831
 * @date 2021/3/31 11:18
 */
@Component
@Slf4j
public class CodeGenDetailConfigServiceImpl implements CodeGenDetailConfigService {
    @Resource
    private CodeGenDetailConfigManager codeGenDetailConfigManager;

    @Override
    public ResponseData edit(SysCodeGenerateConfigParam sysCodeGenerateConfigParam) {
        codeGenDetailConfigManager.edit(sysCodeGenerateConfigParam.getSysCodeGenerateConfigParamList());
        return new SuccessResponseData();
    }

    @Override
    public ResponseData detail(SysCodeGenerateConfigParam sysCodeGenerateConfigParam) {
        return new SuccessResponseData(codeGenDetailConfigManager.detail(sysCodeGenerateConfigParam));
    }

    @Override
    public ResponseData list(SysCodeGenerateConfigParam sysCodeGenerateConfigParam) {
        return new SuccessResponseData(codeGenDetailConfigManager.list(sysCodeGenerateConfigParam));
    }
}
