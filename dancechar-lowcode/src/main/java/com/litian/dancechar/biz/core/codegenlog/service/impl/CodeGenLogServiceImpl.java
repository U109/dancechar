package com.litian.dancechar.biz.core.codegenlog.service.impl;

import com.litian.dancechar.biz.core.codegenlog.dto.CodeGenLogDTO;
import com.litian.dancechar.biz.core.codegenlog.dto.CodeGenLogQueryReqDTO;
import com.litian.dancechar.biz.core.codegenlog.manager.CodeGenLogManager;
import com.litian.dancechar.biz.core.codegenlog.service.CodeGenLogService;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCResultUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * 类描述：codeGenLog 服务实现
 *
 * @author fcoder
 * @date 2021-07-05 10:07:23
 */
@Component
@Slf4j
public class CodeGenLogServiceImpl implements CodeGenLogService {
    @Resource
    private CodeGenLogManager codeGenLogManager;

    private static final String ID_TIPS = "id不能为空";

    @Override
    public Result<PageResp<CodeGenLogDTO>> listPage(CodeGenLogQueryReqDTO req) {
        PageResp<CodeGenLogDTO> pageResp = codeGenLogManager.listPage(req);
        return DCResultUtil.success(pageResp);
    }

    @Override
    public Result<Boolean> save(CodeGenLogDTO codeGenLogDTO) {
        return DCResultUtil.success(codeGenLogManager.save(codeGenLogDTO));
    }

    @Override
    public Result<Boolean> update(CodeGenLogDTO codeGenLogDTO) {
        if (DCObjectUtil.isNull(codeGenLogDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(codeGenLogManager.update(codeGenLogDTO));
    }

    @Override
    public Result<Boolean> deleteById(CodeGenLogDTO codeGenLogDTO) {
        if (DCObjectUtil.isNull(codeGenLogDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(codeGenLogManager.deleteById(codeGenLogDTO));
    }

    @Override
    public Result<CodeGenLogDTO> getById(CodeGenLogDTO codeGenLogDTO) {
        if (DCObjectUtil.isNull(codeGenLogDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(codeGenLogManager.getById(codeGenLogDTO.getId()));
    }
}
