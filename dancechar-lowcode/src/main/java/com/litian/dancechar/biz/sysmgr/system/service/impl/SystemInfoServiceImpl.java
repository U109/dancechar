package com.litian.dancechar.biz.sysmgr.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.litian.dancechar.biz.core.scaffold.manager.ScaffoldGenInfoManager;
import com.litian.dancechar.biz.sysmgr.system.dto.SystemInfoDTO;
import com.litian.dancechar.biz.sysmgr.system.dto.SystemInfoQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.system.manager.SystemInfoManager;
import com.litian.dancechar.biz.sysmgr.system.repository.dataobject.SystemInfoDO;
import com.litian.dancechar.biz.sysmgr.system.service.SystemInfoService;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.DCCollectionUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCResultUtil;
import com.litian.dancechar.framework.common.util.DCStrUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述：systemInfo 服务实现
 *
 * @author fcoder
 * @date 2021-06-26 18:49:31
 */
@Component
@Slf4j
public class SystemInfoServiceImpl implements SystemInfoService {
    @Resource
    private SystemInfoManager systemInfoManager;
    @Resource
    private ScaffoldGenInfoManager scaffoldGenInfoManager;

    private static final String ID_TIPS = "id不能为空";

    private static final String CODE_TIPS = "systemCode不能为空";

    @Override
    public Result<PageResp<SystemInfoDTO>> listPage(SystemInfoQueryReqDTO req) {
        PageResp<SystemInfoDTO> pageResp = systemInfoManager.listPage(req);
        return DCResultUtil.success(pageResp);
    }

    @Override
    public Result<List<SystemInfoDTO>> findList(SystemInfoQueryReqDTO req) {
        //如果是新建工程，则获取没有被使用的工程返回
        if(req != null && DCObjectUtil.isNotEmpty(req.getScaffoldType()) && (req.getScaffoldType()==1 || req.getScaffoldType()==5 || req.getScaffoldType()==6)){
            return DCResultUtil.success(systemInfoManager.getUnusedSystemList(req.getScaffoldGenInfoId()));
        }
        return DCResultUtil.success(systemInfoManager.findList(req));
    }

    @Override
    public Result<SystemInfoDTO> save(SystemInfoDTO systemInfoDTO) {
        if (DCStrUtil.hasBlank(systemInfoDTO.getSystemCode(),
                systemInfoDTO.getSystemName(), systemInfoDTO.getLeader(),
                systemInfoDTO.getPackageName(),systemInfoDTO.getContextPath(),systemInfoDTO.getArtifactId())) {
            return DCResultUtil.error("系统编码、系统名称、系统负责人、包名等不能为空");
        }
        if (!matchPackageName(systemInfoDTO.getPackageName())){
            return DCResultUtil.error("包名格式不匹配，标准示例格式：com.sf.cemp.goods");
        }
        QueryWrapper<SystemInfoDO> queryCodeWrapper = Wrappers.query();
        queryCodeWrapper.eq("system_code", systemInfoDTO.getSystemCode());
        if (DCCollectionUtil.isNotEmpty(systemInfoManager.list(queryCodeWrapper))) {
            return DCResultUtil.error("系统编码不能重复");
        }
        QueryWrapper<SystemInfoDO> queryNameWrapper = Wrappers.query();
        queryNameWrapper.eq("system_name", systemInfoDTO.getSystemName());
        if (DCCollectionUtil.isNotEmpty(systemInfoManager.list(queryNameWrapper))) {
            return DCResultUtil.error("系统名称不能重复");
        }
        return DCResultUtil.success(systemInfoManager.save(systemInfoDTO));
    }

    @Override
    public Result<Boolean> update(SystemInfoDTO systemInfoDTO) {
        if (DCObjectUtil.isNull(systemInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        if (DCStrUtil.hasBlank(systemInfoDTO.getSystemCode(), systemInfoDTO.getSystemName(), systemInfoDTO.getLeader(),systemInfoDTO.getPackageName(),systemInfoDTO.getContextPath(),systemInfoDTO.getArtifactId())) {
            return DCResultUtil.error("系统编码、系统名称、系统负责人、包名等不能为空");
        }
        if (!matchPackageName(systemInfoDTO.getPackageName())){
            return DCResultUtil.error("包名格式不匹配，标准示例格式：com.sf.cemp.goods");
        }
        QueryWrapper<SystemInfoDO> queryCodeWrapper = Wrappers.query();
        queryCodeWrapper.eq("system_code", systemInfoDTO.getSystemCode());
        queryCodeWrapper.ne("id", systemInfoDTO.getId());
        if (DCCollectionUtil.isNotEmpty(systemInfoManager.list(queryCodeWrapper))) {
            return DCResultUtil.error("系统编码不能重复");
        }
        QueryWrapper<SystemInfoDO> queryNameWrapper = Wrappers.query();
        queryNameWrapper.eq("system_name", systemInfoDTO.getSystemName());
        queryNameWrapper.ne("id", systemInfoDTO.getId());
        if (DCCollectionUtil.isNotEmpty(systemInfoManager.list(queryNameWrapper))) {
            return DCResultUtil.error("系统名称不能重复");
        }
        return DCResultUtil.success(systemInfoManager.update(systemInfoDTO));
    }

    @Override
    public Result<Boolean> deleteById(SystemInfoDTO systemInfoDTO) {
        if (DCObjectUtil.isNull(systemInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        if(scaffoldGenInfoManager.getGenInfoBySystemInfoId(systemInfoDTO.getId()) > 0){
            return DCResultUtil.error("该工程已被使用,不能删除");
        }
        return DCResultUtil.success(systemInfoManager.deleteById(systemInfoDTO));
    }

    @Override
    public Result<SystemInfoDTO> getById(SystemInfoDTO systemInfoDTO) {
        if (DCObjectUtil.isNull(systemInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(systemInfoManager.getByPrimaryId(systemInfoDTO.getId()));
    }

    @Override
    public Result<SystemInfoDTO> getBySystemCode(SystemInfoDTO systemInfoDTO) {
        if (DCObjectUtil.isNull(systemInfoDTO.getSystemCode())) {
            return DCResultUtil.error(CODE_TIPS);
        }
        return DCResultUtil.success(systemInfoManager.getBySystemCode(systemInfoDTO.getSystemCode()));
    }

    /**
     * 判断输入的包名格式是否正确
     * @param packageName
     * @return
     */
    private boolean matchPackageName(String packageName){
        String regex = "^([a-z]+(\\.)+){1,}[a-z]+$";
        return packageName.matches(regex);
    }
}
