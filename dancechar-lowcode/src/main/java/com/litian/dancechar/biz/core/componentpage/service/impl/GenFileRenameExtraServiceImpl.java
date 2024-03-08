package com.litian.dancechar.biz.core.componentpage.service.impl;

import com.litian.dancechar.biz.core.componentpage.dto.GenFileRenameExtraDTO;
import com.litian.dancechar.biz.core.componentpage.dto.GenFileRenameExtraQueryReqDTO;
import com.litian.dancechar.biz.core.componentpage.manager.GenFileRenameExtraManager;
import com.litian.dancechar.biz.core.componentpage.service.GenFileRenameExtraService;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCResultUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * 类描述：genFileRenameExtra 服务实现
 *
 * @author fcoder
 * @date 2021-06-30 16:42:34
 */
@Component
@Slf4j
public class GenFileRenameExtraServiceImpl implements GenFileRenameExtraService {
    @Resource
    private GenFileRenameExtraManager genFileRenameExtraManager;

    private static final String ID_TIPS = "id不能为空";

    @Override
    public Result<PageResp<GenFileRenameExtraDTO>> listPage(GenFileRenameExtraQueryReqDTO req) {
        PageResp<GenFileRenameExtraDTO> pageResp = genFileRenameExtraManager.listPage(req);
        return DCResultUtil.success(pageResp);
    }

    @Override
    public Result<Boolean> save(GenFileRenameExtraDTO genFileRenameExtraDTO) {
        return DCResultUtil.success(genFileRenameExtraManager.save(genFileRenameExtraDTO));
    }

    @Override
    public Result<Boolean> update(GenFileRenameExtraDTO genFileRenameExtraDTO) {
        if (DCObjectUtil.isNull(genFileRenameExtraDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(genFileRenameExtraManager.update(genFileRenameExtraDTO));
    }

    @Override
    public Result<Boolean> deleteById(GenFileRenameExtraDTO genFileRenameExtraDTO) {
        if (DCObjectUtil.isNull(genFileRenameExtraDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(genFileRenameExtraManager.deleteById(genFileRenameExtraDTO));
    }

    @Override
    public Result<GenFileRenameExtraDTO> getById(GenFileRenameExtraDTO genFileRenameExtraDTO) {
        if (DCObjectUtil.isNull(genFileRenameExtraDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(genFileRenameExtraManager.getById(genFileRenameExtraDTO.getId()));
    }
}
