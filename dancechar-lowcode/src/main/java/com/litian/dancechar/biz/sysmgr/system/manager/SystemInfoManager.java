package com.litian.dancechar.biz.sysmgr.system.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.biz.sysmgr.system.dto.SystemInfoDTO;
import com.litian.dancechar.biz.sysmgr.system.dto.SystemInfoQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.system.repository.dataobject.SystemInfoDO;
import com.litian.dancechar.biz.sysmgr.system.repository.mapper.SystemInfoMapper;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCStrUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.util.page.PageRespUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类描述：systemInfo manager
 *
 * @author fcoder
 * @date 2021-06-26 18:49:31
 */
@Component
@Slf4j
public class SystemInfoManager extends ServiceImpl<SystemInfoMapper, SystemInfoDO> {

    public PageResp<SystemInfoDTO> getPersonalSystemInfo(List<String> systemCodeForSelf) {
        QueryWrapper<SystemInfoDO> queryWrapperSystemInfo = Wrappers.query();
        List<SystemInfoDO> systemInfoDOS = this.list(queryWrapperSystemInfo);
        List<SystemInfoDO> systemInfoDORst = new com.github.pagehelper.Page<>();
        systemInfoDOS.stream().forEach(systemInfoDO -> {
            if (systemCodeForSelf.contains(systemInfoDO.getSystemCode())) {
                systemInfoDORst.add(systemInfoDO);
            }
        });
        return PageRespUtil.buildPageResult(systemInfoDORst, SystemInfoDTO.class);
    }

    /**
     * 功能：分页查询列表记录
     */
    public PageResp<SystemInfoDTO> listPage(SystemInfoQueryReqDTO systemInfoQueryReqDTO) {
        PageHelper.startPage(systemInfoQueryReqDTO.getPageNo(), systemInfoQueryReqDTO.getPageSize());
        QueryWrapper<SystemInfoDO> queryWrapper = Wrappers.query();
        if (DCStrUtil.isNotEmpty(systemInfoQueryReqDTO.getSystemCode())) {
            queryWrapper.like("system_code", "%" + systemInfoQueryReqDTO.getSystemCode() + "%");
        }
        if (DCStrUtil.isNotEmpty(systemInfoQueryReqDTO.getSystemName())) {
            queryWrapper.like("system_name", "%" + systemInfoQueryReqDTO.getSystemName() + "%");
        }
       /* if (SfStrUtil.isNotEmpty(ContextHoldUtil.getEmpNum())){
            queryWrapper.eq("update_user",  ContextHoldUtil.getEmpNum());
        }*/
        queryWrapper.orderByDesc("update_date");
        List<SystemInfoDO> list = getBaseMapper().selectList(queryWrapper);
        return PageRespUtil.buildPageResult(list, SystemInfoDTO.class);
    }

    /**
     * 功能：查询列表记录
     */
    public List<SystemInfoDTO> findList(SystemInfoQueryReqDTO systemInfoQueryReqDTO) {
        QueryWrapper<SystemInfoDO> queryWrapper = Wrappers.query();
        if (DCStrUtil.isNotEmpty(systemInfoQueryReqDTO.getSystemCode())) {
            queryWrapper.like("system_code", systemInfoQueryReqDTO.getSystemCode());
        }
        if (DCStrUtil.isNotEmpty(systemInfoQueryReqDTO.getSystemName())) {
            queryWrapper.like("system_name", systemInfoQueryReqDTO.getSystemName());
        }
        queryWrapper.orderByDesc("update_date");
        List<SystemInfoDO> list = getBaseMapper().selectList(queryWrapper);
        return DCBeanUtil.copyList(list, SystemInfoDTO.class);
    }

    public List<SystemInfoDTO> getUnusedSystemList(Long scaffoldGenInfoId){
        return baseMapper.getUnusedSystemList(scaffoldGenInfoId);
    }

    /**
     * 功能：新增保存记录
     */
    public SystemInfoDTO save(SystemInfoDTO systemInfoDTO) {
        SystemInfoDO systemInfoDO = new SystemInfoDO();
        DCBeanUtil.copyNotNull(systemInfoDTO, systemInfoDO);
        save(systemInfoDO);
        return DCBeanUtil.copyNotNull(systemInfoDO, new SystemInfoDTO());
    }

    /**
     * 功能：修改记录
     */
    public Boolean update(SystemInfoDTO systemInfoDTO) {
        if (DCObjectUtil.isNotNull(systemInfoDTO.getId())) {
            SystemInfoDO systemInfoDO = this.baseMapper.selectById(systemInfoDTO.getId());
            if (DCObjectUtil.isNotNull(systemInfoDO)) {
                DCBeanUtil.copyNotNull(systemInfoDTO, systemInfoDO);
                return this.baseMapper.updateById(systemInfoDO) > 0;
            }
        }
        return false;
    }

    /**
     * 功能：根据id删除记录
     */
    public Boolean deleteById(SystemInfoDTO systemInfoDTO) {
        SystemInfoDO systemInfoDO = this.baseMapper.selectById(systemInfoDTO.getId());
        if (DCObjectUtil.isNotNull(systemInfoDO)) {
            return this.removeById(systemInfoDO.getId());
        }
        return false;
    }

    /**
     * 功能：根据id获取记录
     */
    public SystemInfoDTO getByPrimaryId(Long id) {
        return DCBeanUtil.copyNotNull(this.baseMapper.selectById(id), new SystemInfoDTO());
    }

    /**
     * 功能：根据systemCode获取记录
     */
    public SystemInfoDTO getBySystemCode(String systemCode) {
        QueryWrapper<SystemInfoDO> queryWrapper = Wrappers.query();
        if (DCStrUtil.isNotEmpty(systemCode)) {
            queryWrapper.like("system_code", systemCode);
        }
        List<SystemInfoDO> systemInfoDOS = this.baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(systemInfoDOS)) {
            return null;
        }
        return DCBeanUtil.copyNotNull(systemInfoDOS.get(0), new SystemInfoDTO());
    }


    public SystemInfoDTO getInfoBySystemCode(String systemCode) {
        QueryWrapper<SystemInfoDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("system_code", systemCode);
        return DCBeanUtil.copyNotNull(baseMapper.selectOne(queryWrapper), new SystemInfoDTO());
    }

}
