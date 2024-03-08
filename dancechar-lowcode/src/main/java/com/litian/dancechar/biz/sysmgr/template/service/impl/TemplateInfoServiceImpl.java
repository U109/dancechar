package com.litian.dancechar.biz.sysmgr.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.litian.dancechar.biz.core.scaffold.manager.ScaffoldGenInfoManager;
import com.litian.dancechar.biz.sysmgr.template.dto.TemplateInfoDTO;
import com.litian.dancechar.biz.sysmgr.template.dto.TemplateInfoQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.template.manager.TemplateInfoManager;
import com.litian.dancechar.biz.sysmgr.template.repository.dataobject.TemplateInfoDO;
import com.litian.dancechar.biz.sysmgr.template.service.TemplateInfoService;
import com.litian.dancechar.common.common.dto.BaseParam;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.DCCollectionUtil;
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
 * 模板信息管理(TemplateInfo)服务实现
 *
 * @author 01406831
 * @since 2021-06-21 13:48:15
 */
@Component
@Slf4j
public class TemplateInfoServiceImpl implements TemplateInfoService {
    @Resource
    private TemplateInfoManager templateInfoManager;
    @Resource
    private ScaffoldGenInfoManager scaffoldGenInfoManager;

    private static final String ID_TIPS = "id不能为空";

    @Override
    public Result<PageResp<TemplateInfoDTO>> listPage(TemplateInfoQueryReqDTO req) {
        PageResp<TemplateInfoDTO> pageResp = templateInfoManager.listPage(req);
        return DCResultUtil.success(pageResp);
    }

    @Override
    public Result<List<TemplateInfoDTO>> findList() {
        return DCResultUtil.success(templateInfoManager.findList());
    }

    @Override
    public Result<Boolean> save(TemplateInfoDTO templateInfoDTO) {
        DCValidatorUtil.validateModel(templateInfoDTO, BaseParam.add.class);
        if (templateInfoDTO.getTemplateType() != 3 && DCStrUtil.isBlank(templateInfoDTO.getMiddleware())) {
            return DCResultUtil.error("中间件不能为空");
        }
        QueryWrapper<TemplateInfoDO> queryNameWrapper = Wrappers.query();
        queryNameWrapper.eq("template_name", templateInfoDTO.getTemplateName());
        if (DCCollectionUtil.isNotEmpty(templateInfoManager.list(queryNameWrapper))) {
            return DCResultUtil.error("模板名称不能重复");
        }
        return DCResultUtil.success(templateInfoManager.save(templateInfoDTO));
    }

    @Override
    public Result<Boolean> update(TemplateInfoDTO templateInfoDTO) {
        DCValidatorUtil.validateModel(templateInfoDTO, BaseParam.add.class);
        if (DCObjectUtil.isNull(templateInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        if (templateInfoDTO.getTemplateType() != 3 && DCStrUtil.isBlank(templateInfoDTO.getMiddleware())) {
            return DCResultUtil.error("中间件不能为空");
        }
        QueryWrapper<TemplateInfoDO> queryNameWrapper = Wrappers.query();
        queryNameWrapper.eq("template_name", templateInfoDTO.getTemplateName());
        queryNameWrapper.ne("id", templateInfoDTO.getId());
        if (DCCollectionUtil.isNotEmpty(templateInfoManager.list(queryNameWrapper))) {
            return DCResultUtil.error("模板名称不能重复");
        }
        return DCResultUtil.success(templateInfoManager.update(templateInfoDTO));
    }

    @Override
    public Result<Boolean> deleteById(TemplateInfoDTO templateInfoDTO) {
        if (DCObjectUtil.isNull(templateInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        if(scaffoldGenInfoManager.getGenInfoByTplInfoId(templateInfoDTO.getId()) > 0){
            return DCResultUtil.error("该工程模板已被使用,不能删除");
        }
        return DCResultUtil.success(templateInfoManager.deleteById(templateInfoDTO));
    }

    @Override
    public Result<TemplateInfoDTO> getById(TemplateInfoDTO templateInfoDTO) {
        if (DCObjectUtil.isNull(templateInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(templateInfoManager.getById(templateInfoDTO.getId()));
    }
}