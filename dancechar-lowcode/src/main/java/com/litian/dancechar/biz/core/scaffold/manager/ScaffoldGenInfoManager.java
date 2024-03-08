package com.litian.dancechar.biz.core.scaffold.manager;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.litian.dancechar.biz.core.codegenlog.manager.CodeGenLogManager;
import com.litian.dancechar.biz.core.scaffold.dto.ScaffoldGenInfoDTO;
import com.litian.dancechar.biz.core.scaffold.dto.ScaffoldGenInfoQueryReqDTO;
import com.litian.dancechar.biz.core.scaffold.repository.dataobject.ScaffoldGenInfoDO;
import com.litian.dancechar.biz.core.scaffold.repository.mapper.ScaffoldGenInfoMapper;
import com.litian.dancechar.biz.core.tplgen.common.enums.TplGenParamEnums;
import com.litian.dancechar.biz.core.tplgen.dto.TplGenDBInfoDTO;
import com.litian.dancechar.biz.core.tplgen.dto.TplGenParamDTO;
import com.litian.dancechar.biz.core.tplgen.manager.TplGenManager;
import com.litian.dancechar.biz.sysmgr.dbmgr.dto.SystemDBInfoDTO;
import com.litian.dancechar.biz.sysmgr.dbmgr.manager.SystemDBInfoManager;
import com.litian.dancechar.biz.sysmgr.system.dto.SystemInfoDTO;
import com.litian.dancechar.biz.sysmgr.system.manager.SystemInfoManager;
import com.litian.dancechar.biz.sysmgr.template.dto.TemplateInfoDTO;
import com.litian.dancechar.biz.sysmgr.template.manager.TemplateInfoManager;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCCollectionUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCStrUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.util.page.PageRespUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 脚手架生成-基础信息manager处理
 *
 * @author 01406831
 * @since 2021-06-21 14:32:35
 */
@Component
@Slf4j
public class ScaffoldGenInfoManager extends ServiceImpl<ScaffoldGenInfoMapper, ScaffoldGenInfoDO> {
    @Resource
    private ScaffoldGenDbInfoManager scaffoldGenDbInfoManager;
    @Resource
    private CodeGenLogManager codeGenLogManager;
    @Resource
    private SystemInfoManager systemInfoManager;
    @Resource
    private TplGenManager tplGenManager;
    @Resource
    private SystemDBInfoManager systemDBInfoManager;
    @Resource
    private TemplateInfoManager templateInfoManager;


    /**
     * 功能：分页查询列表记录
     */
    public PageResp<ScaffoldGenInfoDTO> listPage(ScaffoldGenInfoQueryReqDTO scaffoldGenInfoQueryReqDTO) {
        PageHelper.startPage(scaffoldGenInfoQueryReqDTO.getPageNo(), scaffoldGenInfoQueryReqDTO.getPageSize());
        QueryWrapper<ScaffoldGenInfoDO> queryWrapper = Wrappers.query();
        if (DCCollectionUtil.isNotEmpty(scaffoldGenInfoQueryReqDTO.getScaffoldType())) {
            queryWrapper.in("scaffold_type", scaffoldGenInfoQueryReqDTO.getScaffoldType());
        }
        if (DCStrUtil.isNotEmpty(scaffoldGenInfoQueryReqDTO.getSystemCode())) {
            queryWrapper.likeRight("system_code", scaffoldGenInfoQueryReqDTO.getSystemCode());
        }
        if (DCStrUtil.isNotEmpty(scaffoldGenInfoQueryReqDTO.getSystemName())) {
            queryWrapper.likeRight("system_name", scaffoldGenInfoQueryReqDTO.getSystemName());
        }
        if (DCStrUtil.isNotEmpty(scaffoldGenInfoQueryReqDTO.getGenFunctions())) {
            queryWrapper.likeRight("gen_functions", scaffoldGenInfoQueryReqDTO.getGenFunctions());
        }
        queryWrapper.orderByDesc("update_date");
        List<ScaffoldGenInfoDO> list = getBaseMapper().selectList(queryWrapper);
        return PageRespUtil.buildPageResult(list, ScaffoldGenInfoDTO.class);
    }

    /**
     * 查询已使用的系统列表
     */
    public List<String> findUseSystemList() {
        return baseMapper.findUseSystemList();
    }

    /**
     * 功能: 修改脚手架基础信息
     */
    public Boolean updateById(ScaffoldGenInfoDTO scaffoldGenInfoDTO) {
        ScaffoldGenInfoDO scaffoldGenInfoDO = new ScaffoldGenInfoDO();
        DCBeanUtil.copyNotNull(scaffoldGenInfoDTO, scaffoldGenInfoDO);
        return this.baseMapper.updateById(scaffoldGenInfoDO) > 0;
    }

    public Boolean updateByCodeLine(ScaffoldGenInfoDTO scaffoldGenInfoDTO) {
        ScaffoldGenInfoDO genInfoDO = baseMapper.selectById(scaffoldGenInfoDTO.getId());
        if (DCObjectUtil.isEmpty(genInfoDO)) {
            return false;
        }
        Integer codeLine = DCObjectUtil.isEmpty(genInfoDO.getCodeLines()) ? 0 : genInfoDO.getCodeLines();
        genInfoDO.setCodeLines(codeLine + scaffoldGenInfoDTO.getCodeLines());
        return this.baseMapper.updateById(genInfoDO) > 0;
    }

    /**
     * 功能: 新增或修改保存记录
     */
    @Transactional(rollbackFor = Exception.class)
    public Long save(ScaffoldGenInfoDTO scaffoldGenInfoDTO) {
        if (DCObjectUtil.isNotNull(scaffoldGenInfoDTO.getId())) {
            ScaffoldGenInfoDO scaffoldGenInfoDO = this.baseMapper.selectById(scaffoldGenInfoDTO.getId());
            if (DCObjectUtil.isNotNull(scaffoldGenInfoDO)) {
                DCBeanUtil.copyNotNull(scaffoldGenInfoDTO, scaffoldGenInfoDO);
                if (DCStrUtil.isNotBlank(scaffoldGenInfoDTO.getMiddleware())) {
                    List<String> middlewareList = Arrays.asList(scaffoldGenInfoDTO.getMiddleware().split("#"));
                    if (!middlewareList.contains("kafka")) {
                        scaffoldGenInfoDO.setKafkaId(null);
                    }
                    if (!middlewareList.contains("sentinel")) {
                        scaffoldGenInfoDO.setSentinelId(null);
                    }
                    if (!middlewareList.contains("mongodb")) {
                        scaffoldGenInfoDO.setMongodbId(null);
                    }
                    if (!middlewareList.contains("elasticsearch")) {
                        scaffoldGenInfoDO.setEsId(null);
                    }
                }
                if (DCStrUtil.isNotEmpty(scaffoldGenInfoDO.getSystemCode())) {
                    SystemInfoDTO systemInfoDTO = systemInfoManager.getInfoBySystemCode(scaffoldGenInfoDO.getSystemCode());
                    copySystemInfoToScaffoldGenInfoDO(systemInfoDTO, scaffoldGenInfoDO);
                }
                this.baseMapper.updateGenInfoById(scaffoldGenInfoDO);
                return scaffoldGenInfoDO.getId();
            }
            return null;
        } else {
            ScaffoldGenInfoDO scaffoldGenInfoDO = new ScaffoldGenInfoDO();
            DCBeanUtil.copyNotNull(scaffoldGenInfoDTO, scaffoldGenInfoDO);
            if (DCStrUtil.isNotEmpty(scaffoldGenInfoDO.getSystemCode())) {
                SystemInfoDTO systemInfoDTO = systemInfoManager.getInfoBySystemCode(scaffoldGenInfoDO.getSystemCode());
                copySystemInfoToScaffoldGenInfoDO(systemInfoDTO, scaffoldGenInfoDO);
            }
            TemplateInfoDTO templateInfoDO = templateInfoManager.getById(scaffoldGenInfoDO.getTplInfoId());
            if (DCObjectUtil.isNotNull(templateInfoDO)) {
                scaffoldGenInfoDO.setTemplateType(templateInfoDO.getTemplateType());
            }
            save(scaffoldGenInfoDO);
            return scaffoldGenInfoDO.getId();
        }
    }

    /**
     * 功能：根据id删除记录
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteById(ScaffoldGenInfoDTO scaffoldGenInfoDTO) {
        ScaffoldGenInfoDO scaffoldGenInfoDO = this.baseMapper.selectById(scaffoldGenInfoDTO.getId());
        if (DCObjectUtil.isNotNull(scaffoldGenInfoDO)) {
            scaffoldGenDbInfoManager.deleteByScaffoldGenInfoId(scaffoldGenInfoDTO.getId());
            // 删除下载日志
            Map<String, Object> columnMap = new HashMap<>();
            columnMap.put("system_code", scaffoldGenInfoDO.getSystemCode());
            codeGenLogManager.removeByMap(columnMap);
            return super.removeById(scaffoldGenInfoDTO.getId());
        }
        return false;
    }

    /**
     * 功能：根据id获取记录
     */
    public ScaffoldGenInfoDTO getById(Long id) {
        return DCBeanUtil.copyNotNull(this.baseMapper.selectById(id), new ScaffoldGenInfoDTO());
    }

    public List<ScaffoldGenInfoDTO> getListByIds(List<Long> ids) {
        return DCBeanUtil.copyList(this.baseMapper.selectBatchIds(ids), ScaffoldGenInfoDTO.class);
    }


    /**
     * 组装TplGenParamDTO
     */
    public TplGenParamDTO initTplGenParamDTO(Long genInfoId) {
        ScaffoldGenInfoDTO dbScaffoldGenInfoDTO = getById(genInfoId);
        return convertScaffoldGenInfoDTOToTplGenParamDTO(dbScaffoldGenInfoDTO);
    }

    /**
     * 组装TplGenParamDTO
     *
     * @param dbScaffoldGenInfoDTO
     * @return
     */
    public TplGenParamDTO convertScaffoldGenInfoDTOToTplGenParamDTO(ScaffoldGenInfoDTO dbScaffoldGenInfoDTO) {
        TplGenParamDTO tplGenParamDTO = new TplGenParamDTO();
        tplGenParamDTO.setSql(dbScaffoldGenInfoDTO.getSql());
        tplGenParamDTO.setGenInfoId(dbScaffoldGenInfoDTO.getId());
        tplGenParamDTO.setSysCode(dbScaffoldGenInfoDTO.getSystemCode());
        tplGenParamDTO.setSysName(dbScaffoldGenInfoDTO.getSystemName());
        tplGenParamDTO.setAuthorName(dbScaffoldGenInfoDTO.getUpdateUser());
        tplGenParamDTO.setVersionNo(dbScaffoldGenInfoDTO.getVersionNo());
        tplGenParamDTO.setPackageName(dbScaffoldGenInfoDTO.getProjectPackageName());
        tplGenParamDTO.setContextPath(dbScaffoldGenInfoDTO.getContextPath());
        tplGenParamDTO.setGroupId(dbScaffoldGenInfoDTO.getGroupId());
        tplGenParamDTO.setArtifactId(dbScaffoldGenInfoDTO.getArtifactId());
        tplGenParamDTO.setGenFunctions(dbScaffoldGenInfoDTO.getGenFunctions());
        tplGenParamDTO.setFunctionCollect(dbScaffoldGenInfoDTO.getFunctionCollect());
        tplGenParamDTO.setDirList(DCCollectionUtil.toList(DCStrUtil.split(dbScaffoldGenInfoDTO.getDirNames(), "#")));
        if (DCCollectionUtil.isEmpty(tplGenParamDTO.getDirList())) {
            tplGenParamDTO.setDirList(Arrays.stream(TplGenParamEnums.SingleFucPluginEnum.values()).map(TplGenParamEnums.SingleFucPluginEnum::getCode).collect(Collectors.toList()));
        }
        tplGenParamDTO.setMiddleareList(DCCollectionUtil.toList(DCStrUtil.split(StringUtils.upperCase(dbScaffoldGenInfoDTO.getMiddleware()), "#")));
        tplGenParamDTO.setCreateUser(dbScaffoldGenInfoDTO.getCreateUser());
        tplGenParamDTO.setUpdateUser(dbScaffoldGenInfoDTO.getUpdateUser());
        // 设置模板类型
        setTemplateTypeForDTO(dbScaffoldGenInfoDTO,tplGenParamDTO);
        // 设置数据库信息
        tplGenManager.setDBInfoList(dbScaffoldGenInfoDTO.getId(), tplGenParamDTO);
        return tplGenParamDTO;
    }

    private void setTemplateTypeForDTO(ScaffoldGenInfoDTO dbScaffoldGenInfoDTO,TplGenParamDTO tplGenParamDTO){
        // 1: 脚手架工程生成 2: 单功能生成 3：单功能中的多表关联的生成
        Integer scaffoldType = dbScaffoldGenInfoDTO.getScaffoldType();
        if(DCObjectUtil.isEmpty(scaffoldType)){
            return ;
        }
        switch (scaffoldType){
            case 1:
                tplGenParamDTO.setTemplateType(TplGenParamEnums.TemplateTypeEnum.FNEW.getCode());
                if (ObjectUtil.isNotNull(dbScaffoldGenInfoDTO.getMiddleware())) {
                    tplGenManager.setMiddleware(dbScaffoldGenInfoDTO, tplGenParamDTO);
                }
                break;
            case 2:
                tplGenParamDTO.setTemplateType(TplGenParamEnums.TemplateTypeEnum.DFUNC.getCode());
                if (DCObjectUtil.equals(dbScaffoldGenInfoDTO.getTemplateType(), 4)) {
                    tplGenParamDTO.setTemplateType(TplGenParamEnums.TemplateTypeEnum.BOOTDFUNC.getCode());
                }
                break;
            case 3:
                tplGenParamDTO.setTemplateType(TplGenParamEnums.TemplateTypeEnum.WDFUNC.getCode());
                if (DCObjectUtil.equals(dbScaffoldGenInfoDTO.getTemplateType(), 4)) {
                    tplGenParamDTO.setTemplateType(TplGenParamEnums.TemplateTypeEnum.BOOTWDFUNC.getCode());
                }
                break;
            case 4:
                tplGenParamDTO.setTemplateType(TplGenParamEnums.TemplateTypeEnum.SQLFUNC.getCode());
                break;
            case 5:
                tplGenParamDTO.setTemplateType(TplGenParamEnums.TemplateTypeEnum.MANAGEMENTNEW.getCode());
                break;
            case 6:
                tplGenParamDTO.setTemplateType(TplGenParamEnums.TemplateTypeEnum.BOOTNEW.getCode());
                if (ObjectUtil.isNotNull(dbScaffoldGenInfoDTO.getMiddleware())) {
                    tplGenManager.setMiddleware(dbScaffoldGenInfoDTO, tplGenParamDTO);
                }
                break;
            case 7:
                tplGenParamDTO.setTemplateType(TplGenParamEnums.TemplateTypeEnum.BOOTDFUNC.getCode());
                break;
            case 8:
                tplGenParamDTO.setTemplateType(TplGenParamEnums.TemplateTypeEnum.BOOTWDFUNC.getCode());
                break;
            default: tplGenParamDTO.setTemplateType(TplGenParamEnums.TemplateTypeEnum.DFUNC.getCode());
        }
    }


    public TplGenParamDTO convertScaffoldGenInfoDTOToTplGenParamDTOSqlTool(ScaffoldGenInfoDTO dbScaffoldGenInfoDTO) {
        TplGenParamDTO tplGenParamDTO = new TplGenParamDTO();
        tplGenParamDTO.setSql(dbScaffoldGenInfoDTO.getSql());
        tplGenParamDTO.setGenInfoId(dbScaffoldGenInfoDTO.getId());
        tplGenParamDTO.setSysCode(dbScaffoldGenInfoDTO.getSystemCode());
        tplGenParamDTO.setSysName(dbScaffoldGenInfoDTO.getSystemName());
        tplGenParamDTO.setAuthorName(dbScaffoldGenInfoDTO.getUpdateUser());
        tplGenParamDTO.setVersionNo(dbScaffoldGenInfoDTO.getVersionNo());
        tplGenParamDTO.setPackageName(dbScaffoldGenInfoDTO.getProjectPackageName());
        tplGenParamDTO.setContextPath(dbScaffoldGenInfoDTO.getContextPath());
        tplGenParamDTO.setGroupId(dbScaffoldGenInfoDTO.getGroupId());
        tplGenParamDTO.setArtifactId(dbScaffoldGenInfoDTO.getArtifactId());
        tplGenParamDTO.setGenFunctions(dbScaffoldGenInfoDTO.getGenFunctions());
        tplGenParamDTO.setDirList(DCCollectionUtil.toList(DCStrUtil.split(dbScaffoldGenInfoDTO.getDirNames(), "#")));
        if (DCCollectionUtil.isEmpty(tplGenParamDTO.getDirList())) {
            tplGenParamDTO.setDirList(Arrays.stream(TplGenParamEnums.SingleFucPluginEnum.values()).map(TplGenParamEnums.SingleFucPluginEnum::getCode).collect(Collectors.toList()));
        }
        tplGenParamDTO.setMiddleareList(DCCollectionUtil.toList(DCStrUtil.split(StringUtils.upperCase(dbScaffoldGenInfoDTO.getMiddleware()), "#")));
        tplGenParamDTO.setCreateUser(dbScaffoldGenInfoDTO.getCreateUser());
        tplGenParamDTO.setUpdateUser(dbScaffoldGenInfoDTO.getUpdateUser());
        // 1: 脚手架工程生成 2: 单功能生成 3：单功能中的多表关联的生成
        if (DCObjectUtil.equals(dbScaffoldGenInfoDTO.getScaffoldType(), 1)) {
            tplGenParamDTO.setTemplateType(TplGenParamEnums.TemplateTypeEnum.FNEW.getCode());
            if (ObjectUtil.isNotNull(dbScaffoldGenInfoDTO.getMiddleware())) {
                tplGenManager.setMiddleware(dbScaffoldGenInfoDTO, tplGenParamDTO);
            }
        } else if (DCObjectUtil.equals(dbScaffoldGenInfoDTO.getScaffoldType(), 3)) {
            tplGenParamDTO.setTemplateType(TplGenParamEnums.TemplateTypeEnum.WDFUNC.getCode());
        } else if (DCObjectUtil.equals(dbScaffoldGenInfoDTO.getScaffoldType(), 4)) {
            tplGenParamDTO.setTemplateType(TplGenParamEnums.TemplateTypeEnum.SQLFUNC.getCode());
        } else {
            tplGenParamDTO.setTemplateType(TplGenParamEnums.TemplateTypeEnum.DFUNC.getCode());
        }

        SystemDBInfoDTO systemDBInfoDTO = systemDBInfoManager.getById(dbScaffoldGenInfoDTO.getSystemInfoId());
        if (ObjectUtil.isNotEmpty(systemDBInfoDTO)) {
            List<TplGenDBInfoDTO> tplGenTOList = Lists.newArrayList();
            TplGenDBInfoDTO tplGenDBInfoDTO = new TplGenDBInfoDTO();
            tplGenDBInfoDTO.setHost(systemDBInfoDTO.getDbHost());
            tplGenDBInfoDTO.setPort(Integer.parseInt(systemDBInfoDTO.getDbPort()));
            tplGenDBInfoDTO.setGenDbId(systemDBInfoDTO.getId());
            tplGenDBInfoDTO.setUsername(systemDBInfoDTO.getDbUsername());
            tplGenDBInfoDTO.setPassword(systemDBInfoDTO.getDbPassword());
            tplGenDBInfoDTO.setDbName(systemDBInfoDTO.getDbName());
            tplGenDBInfoDTO.setDbTag(systemDBInfoDTO.getDbName());
            tplGenDBInfoDTO.setDriverName(systemDBInfoDTO.getDbDriver());
            tplGenDBInfoDTO.setPrimary(DCObjectUtil.equals(systemDBInfoDTO.getPrimaryDb(), 1));
            tplGenTOList.add(tplGenDBInfoDTO);

            tplGenParamDTO.setTplGenDBInfoDTOList(tplGenTOList);
        }
        return tplGenParamDTO;
    }

    /**
     * 复制系统信息到脚手架
     */
    private void copySystemInfoToScaffoldGenInfoDO(SystemInfoDTO systemInfoDTO, ScaffoldGenInfoDO scaffoldGenInfoDO) {
        scaffoldGenInfoDO.setProjectPackageName(systemInfoDTO.getPackageName());
        scaffoldGenInfoDO.setVersionNo(systemInfoDTO.getVersion());
        scaffoldGenInfoDO.setSystemInfoId(systemInfoDTO.getId());
        scaffoldGenInfoDO.setArtifactId(systemInfoDTO.getArtifactId());
        scaffoldGenInfoDO.setSystemCode(systemInfoDTO.getSystemCode());
        scaffoldGenInfoDO.setContextPath(systemInfoDTO.getContextPath());
        scaffoldGenInfoDO.setGroupId(systemInfoDTO.getGroupId());
        scaffoldGenInfoDO.setSystemName(systemInfoDTO.getSystemName());
    }

    public Integer getGenInfoByTplInfoId(Long tplInfoId) {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.eq("tpl_info_id", tplInfoId);
        queryWrapper.eq("delete_flag", 0);
        return baseMapper.selectCount(queryWrapper);
    }

    public Integer getGenInfoBySystemInfoId(Long systemInfoId) {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.eq("system_info_id", systemInfoId);
        queryWrapper.eq("delete_flag", 0);
        return baseMapper.selectCount(queryWrapper);
    }

    public List<ScaffoldGenInfoDO> getGenInfoListBySystemInfoId(Long systemInfoId) {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.eq("system_info_id", systemInfoId);
        queryWrapper.eq("delete_flag", 0);
        return baseMapper.selectList(queryWrapper);
    }

    public Boolean checkKafkaHasUsed(Long kafkaId) {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.eq("kafka_id", kafkaId);
        queryWrapper.eq("delete_flag", 0);
        return baseMapper.selectCount(queryWrapper) > 0;
    }

    public Boolean checkSentinelHasUsed(Long sentinelId) {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.eq("sentinel_id", sentinelId);
        queryWrapper.eq("delete_flag", 0);
        return baseMapper.selectCount(queryWrapper) > 0;
    }

    public Boolean checkEsHasUsed(Long esId) {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.eq("es_id", esId);
        queryWrapper.eq("delete_flag", 0);
        return baseMapper.selectCount(queryWrapper) > 0;
    }

    public Boolean checkMongodbHasUsed(Long mongodbId) {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.eq("mongodb_id", mongodbId);
        queryWrapper.eq("delete_flag", 0);
        return baseMapper.selectCount(queryWrapper) > 0;
    }

    public List<ScaffoldGenInfoDTO> getNoGenDataList() {
        QueryWrapper<ScaffoldGenInfoDO> queryWrapper = Wrappers.query();
        queryWrapper.isNull("gen_functions");
        queryWrapper.in("scaffold_type", Arrays.asList("2,3"));
        return DCBeanUtil.copyList(baseMapper.selectList(queryWrapper), ScaffoldGenInfoDTO.class);
    }

    public int deleteByIds(List<Long> ids) {
        return baseMapper.deleteByIds(ids);
    }

}
