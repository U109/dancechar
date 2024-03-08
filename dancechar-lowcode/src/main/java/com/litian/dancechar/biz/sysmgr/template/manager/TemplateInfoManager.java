package com.litian.dancechar.biz.sysmgr.template.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.biz.sysmgr.template.dto.TemplateInfoDTO;
import com.litian.dancechar.biz.sysmgr.template.dto.TemplateInfoQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.template.repository.dataobject.TemplateInfoDO;
import com.litian.dancechar.biz.sysmgr.template.repository.mapper.TemplateInfoMapper;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCStrUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.util.page.PageRespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 模板信息管理(TemplateInfo)manager处理
 *
 * @author 01406831
 * @since 2021-06-21 13:48:15
 */
@Component
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class TemplateInfoManager extends ServiceImpl<TemplateInfoMapper, TemplateInfoDO> {

    /**
     * 功能：分页查询列表记录
     */
    public PageResp<TemplateInfoDTO> listPage(TemplateInfoQueryReqDTO templateInfoQueryReqDTO) {
        PageHelper.startPage(templateInfoQueryReqDTO.getPageNo(), templateInfoQueryReqDTO.getPageSize());
        QueryWrapper<TemplateInfoDO> queryWrapper = Wrappers.query();
        if (templateInfoQueryReqDTO.getId() !=null ) {
            queryWrapper.like("id", "%" + templateInfoQueryReqDTO.getId() + "%");
        }
        if (DCStrUtil.isNotEmpty(templateInfoQueryReqDTO.getTemplateName())) {
            queryWrapper.like("template_name", "%" + templateInfoQueryReqDTO.getTemplateName() + "%");
        }

        queryWrapper.orderByDesc("update_date");
        List<TemplateInfoDO> list = getBaseMapper().selectList(queryWrapper);
        return PageRespUtil.buildPageResult(list, TemplateInfoDTO.class);
    }

    /**
     * 功能: 查询列表记录
     */
    public List<TemplateInfoDTO> findList() {
        QueryWrapper<TemplateInfoDO> queryWrapper = Wrappers.query();
        queryWrapper.orderByDesc("update_date");
        return DCBeanUtil.copyList(getBaseMapper().selectList(queryWrapper), TemplateInfoDTO.class);
    }

    /**
     * 功能：新增保存记录
     */
    public Boolean save(TemplateInfoDTO templateInfoDTO) {
        TemplateInfoDO templateInfoDO = new TemplateInfoDO();
        DCBeanUtil.copyNotNull(templateInfoDTO, templateInfoDO);
        return save(templateInfoDO);
    }

    /**
     * 功能：修改记录
     */
    public Boolean update(TemplateInfoDTO templateInfoDTO) {
        TemplateInfoDO templateInfoDO = new TemplateInfoDO();
        DCBeanUtil.copyNotNull(templateInfoDTO, templateInfoDO);
        if (DCObjectUtil.isNotNull(templateInfoDO)) {
            return super.updateById(templateInfoDO);
        }
        return false;
    }

    /**
     * 功能：根据id删除记录
     */
    public Boolean deleteById(TemplateInfoDTO templateInfoDTO) {
        TemplateInfoDO templateInfoDO = this.baseMapper.selectById(templateInfoDTO.getId());
        if (DCObjectUtil.isNotNull(templateInfoDO)) {
            return this.removeById(templateInfoDTO.getId());
        }
        return false;
    }

    /**
     * 功能：根据id获取记录
     */
    public TemplateInfoDTO getById(Long id) {
        return DCBeanUtil.copyNotNull(this.baseMapper.selectById(id), new TemplateInfoDTO());
    }
}
