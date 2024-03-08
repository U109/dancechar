package com.litian.dancechar.biz.core.codegenlog.manager;

import com.litian.dancechar.biz.core.codegenlog.dto.CodeGenLogDTO;
import com.litian.dancechar.biz.core.codegenlog.dto.CodeGenLogQueryReqDTO;
import com.litian.dancechar.biz.core.codegenlog.repository.dataobject.CodeGenLogDO;
import com.litian.dancechar.biz.core.codegenlog.repository.mapper.CodeGenLogMapper;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.util.page.PageRespUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import com.baomidou.dynamic.datasource.annotation.DS;

/**
 * 类描述：codeGenLog manager
 *
 * @author fcoder
 * @date 2021-07-05 10:07:23
 */
@Component
@Slf4j
@DS("cempfcode")
public class CodeGenLogManager extends ServiceImpl<CodeGenLogMapper, CodeGenLogDO> {

    /**
     * 功能：分页查询列表记录
     */
    public PageResp<CodeGenLogDTO> listPage(CodeGenLogQueryReqDTO codeGenLogQueryReqDTO) {
        PageHelper.startPage(codeGenLogQueryReqDTO.getPageNo(), codeGenLogQueryReqDTO.getPageSize());
        QueryWrapper<CodeGenLogDO> queryWrapper = Wrappers.query();
        // cloumn query example:
        // queryWrapper.eq("id", 1);
        queryWrapper.orderByDesc("create_date");
        List<CodeGenLogDO> list = getBaseMapper().selectList(queryWrapper);
        PageResp<CodeGenLogDTO> pageResp = PageRespUtil.buildPageResult(list, CodeGenLogDTO.class);
        return pageResp;
    }

    /**
     * 功能：新增保存记录
     */
    public Boolean save(CodeGenLogDTO codeGenLogDTO) {
        CodeGenLogDO codeGenLogDO = new CodeGenLogDO();
        DCBeanUtil.copyNotNull(codeGenLogDTO, codeGenLogDO);
        return save(codeGenLogDO);
    }

    /**
     * 功能：修改记录
     */
    public Boolean update(CodeGenLogDTO codeGenLogDTO) {
        if (DCObjectUtil.isNotNull(codeGenLogDTO.getId())) {
            CodeGenLogDO codeGenLogDO = this.baseMapper.selectById(codeGenLogDTO.getId());
            if (DCObjectUtil.isNotNull(codeGenLogDO)) {
                DCBeanUtil.copyNotNull(codeGenLogDTO, codeGenLogDO);
                return this.baseMapper.updateById(codeGenLogDO) > 0;
            }
        }
        return false;
    }

    /**
     * 功能：根据id删除记录
     */
    public Boolean deleteById(CodeGenLogDTO codeGenLogDTO) {
        CodeGenLogDO codeGenLogDO = this.baseMapper.selectById(codeGenLogDTO.getId());
        if (DCObjectUtil.isNotNull(codeGenLogDO)) {
            return super.removeById(codeGenLogDO.getId());
        }
        return false;
    }

    /**
     * 功能：根据id获取记录
     */
    public CodeGenLogDTO getById(Long id) {
        return DCBeanUtil.copyNotNull(this.baseMapper.selectById(id), new CodeGenLogDTO());
    }
}
