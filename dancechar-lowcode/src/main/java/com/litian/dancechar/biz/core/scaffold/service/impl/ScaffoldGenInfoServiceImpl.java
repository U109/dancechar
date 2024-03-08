package com.litian.dancechar.biz.core.scaffold.service.impl;

import com.litian.dancechar.biz.core.componentpage.manager.GenFileInfoManager;
import com.litian.dancechar.biz.core.scaffold.dto.ScaffoldGenInfoDTO;
import com.litian.dancechar.biz.core.scaffold.dto.ScaffoldGenInfoQueryReqDTO;
import com.litian.dancechar.biz.core.scaffold.dto.ScaffoldMultiTableReqDTO;
import com.litian.dancechar.biz.core.scaffold.manager.ScaffoldGenDbExampleInfoManager;
import com.litian.dancechar.biz.core.scaffold.manager.ScaffoldGenDbInfoManager;
import com.litian.dancechar.biz.core.scaffold.manager.ScaffoldGenInfoManager;
import com.litian.dancechar.biz.core.scaffold.repository.dataobject.ScaffoldGenDbInfoDO;
import com.litian.dancechar.biz.core.scaffold.service.ScaffoldGenInfoService;
import com.litian.dancechar.biz.core.tplgen.dto.TplGenParamDTO;
import com.litian.dancechar.biz.core.tplgen.manager.TplGenManager;
import com.litian.dancechar.biz.core.tplgen.service.TplGenService;
import com.litian.dancechar.biz.sysmgr.dbmgr.dto.SystemDBInfoDTO;
import com.litian.dancechar.biz.sysmgr.dbmgr.manager.SystemDBInfoManager;
import com.litian.dancechar.biz.sysmgr.system.dto.SystemInfoDTO;
import com.litian.dancechar.biz.sysmgr.system.manager.SystemInfoManager;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.context.ContextHoldUtil;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCCollectionUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCResultUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 脚手架生成-基础信息(ScaffoldGenInfo)服务实现
 *
 * @author 01406831
 * @since 2021-06-21 14:32:35
 */
@Component
@Slf4j
public class ScaffoldGenInfoServiceImpl implements ScaffoldGenInfoService {
    @Resource
    private ScaffoldGenInfoManager scaffoldGenInfoManager;
    @Resource
    private TplGenService tplGenService;
    @Resource
    private SystemInfoManager systemInfoManager;
    @Resource
    private SystemDBInfoManager systemDBInfoManager;
    @Resource
    private TplGenManager tplGenManager;
    @Resource
    private ScaffoldGenDbInfoManager scaffoldGenDbInfoManager;
    @Resource
    private ScaffoldGenDbExampleInfoManager scaffoldGenDbExampleInfoManager;
    @Resource
    private GenFileInfoManager genFileInfoManager;

    private static final String ID_TIPS = "id不能为空";

    @Override
    public Result<PageResp<ScaffoldGenInfoDTO>> listPage(ScaffoldGenInfoQueryReqDTO req) {
        PageResp<ScaffoldGenInfoDTO> pageResp = scaffoldGenInfoManager.listPage(req);
        return DCResultUtil.success(pageResp);
    }

    @Override
    public Result<Long> save(ScaffoldGenInfoDTO scaffoldGenInfoDTO) {
        if (DCObjectUtil.isNull(scaffoldGenInfoDTO.getTplInfoId())) {
            return DCResultUtil.error("模板Id不能为空");
        }
        //1、单功能生成中第四步重新refresh模板
        if ((scaffoldGenInfoDTO.getScaffoldType() == 2 || scaffoldGenInfoDTO.getScaffoldType() == 3) && scaffoldGenInfoDTO.getStep() == 4) {
            commonSave(scaffoldGenInfoDTO);
        }
        //2、生成脚手架的时候，第二步直接从SystemInfo中查询基础信息
        if (scaffoldGenInfoDTO.getStep() == 2 && scaffoldGenInfoDTO.getSystemInfoId() != null) {
            SystemInfoDTO systemInfoDTO = systemInfoManager.getByPrimaryId(scaffoldGenInfoDTO.getSystemInfoId());
            if (systemInfoDTO != null) {
                scaffoldGenInfoDTO.setProjectPackageName(systemInfoDTO.getPackageName());
                scaffoldGenInfoDTO.setGroupId(systemInfoDTO.getGroupId());
                scaffoldGenInfoDTO.setArtifactId(systemInfoDTO.getArtifactId());
                scaffoldGenInfoDTO.setContextPath(systemInfoDTO.getContextPath());
                scaffoldGenInfoDTO.setVersionNo(systemInfoDTO.getVersion());
                scaffoldGenInfoDTO.setSystemCode(systemInfoDTO.getSystemCode());
                scaffoldGenInfoDTO.setSystemName(systemInfoDTO.getSystemName());
            }
        }
        // 3、单工程、第三步、直接选取数据库即可
        if ((scaffoldGenInfoDTO.getScaffoldType() == 1 || scaffoldGenInfoDTO.getScaffoldType() == 6) && scaffoldGenInfoDTO.getStep() == 3) {
            // 3.1 选取数据库后，先删除ScaffoldGenDBInfo保存数据信息
            scaffoldGenDbInfoManager.deleteByScaffoldGenId(scaffoldGenInfoDTO.getId());
            // 3.2 将选择systemDB信息存入ScaffoldGenDBInfo

            if (CollectionUtils.isNotEmpty(scaffoldGenInfoDTO.getSystemDBList())) {
                if (scaffoldGenInfoDTO.getSystemDBList().stream().filter(db -> db.getPrimaryDb() == 1).count() > 1) {
                    return DCResultUtil.error("只能有一个主库");
                }
                scaffoldGenInfoDTO.getSystemDBList().forEach(systemDBDTO -> {
                    SystemDBInfoDTO systemDBInfoDTO = systemDBInfoManager.getById(systemDBDTO.getSystemDbId());
                    if (DCObjectUtil.isNotNull(systemDBInfoDTO)) {
                        ScaffoldGenDbInfoDO scaffoldGenDbInfoDO = new ScaffoldGenDbInfoDO();
                        DCBeanUtil.copyNotNull(systemDBInfoDTO, scaffoldGenDbInfoDO, "id");
                        scaffoldGenDbInfoDO.setScaffoldGenInfoId(scaffoldGenInfoDTO.getId());
                        scaffoldGenDbInfoDO.setDriverClass(systemDBInfoDTO.getDbDriver());
                        scaffoldGenDbInfoDO.setUserName(systemDBInfoDTO.getDbUsername());
                        scaffoldGenDbInfoDO.setPassword(systemDBInfoDTO.getDbPassword());
                        scaffoldGenDbInfoDO.setIpPort(systemDBInfoDTO.getDbHost() + "/" + systemDBInfoDTO.getDbPort());
                        scaffoldGenDbInfoDO.setUpdateUser(ContextHoldUtil.getEmpNum());
                        scaffoldGenDbInfoDO.setCreateUser(ContextHoldUtil.getEmpNum());
                        scaffoldGenDbInfoDO.setSystemDbId(systemDBInfoDTO.getId());
                        scaffoldGenDbInfoDO.setPrimaryDb(systemDBDTO.getPrimaryDb());
                        scaffoldGenDbInfoManager.save(scaffoldGenDbInfoDO);
                    }
                });
            }
        }
        //保存工程第二步，需要校验工程模板是否被其他工程使用
        if (DCObjectUtil.isEmpty(scaffoldGenInfoDTO.getId()) && scaffoldGenInfoDTO.getScaffoldType() == 1 && scaffoldGenInfoDTO.getStep() == 2) {
            Integer count = scaffoldGenInfoManager.getGenInfoBySystemInfoId(scaffoldGenInfoDTO.getSystemInfoId());
            if(count > 0){
                return DCResultUtil.error("该工程模板已被其他工程使用");
            }
        }
        //工程第四步，重新生成最新文件
        if (scaffoldGenInfoDTO.getScaffoldType() == 1 && scaffoldGenInfoDTO.getStep() == 4) {
            tplGenManager.rebuildGenFileInfo(scaffoldGenInfoDTO);
        }
        return DCResultUtil.success(scaffoldGenInfoManager.save(scaffoldGenInfoDTO));
    }

    private void commonSave(ScaffoldGenInfoDTO scaffoldGenInfoDTO) {
        //还需要更新类名和包名
        scaffoldGenDbExampleInfoManager.updateMultiTableClassName(scaffoldGenInfoDTO.getClassName(), scaffoldGenInfoDTO.getCatalog(),
                scaffoldGenInfoDTO.getScaffoldGenDbInfoId());
        ScaffoldGenInfoDTO genInfoDTO = new ScaffoldGenInfoDTO();
        genInfoDTO.setId(scaffoldGenInfoDTO.getId());
        genInfoDTO.setGenFunctions(scaffoldGenInfoDTO.getGenFunctions());
        scaffoldGenInfoManager.updateById(genInfoDTO);
        tplGenManager.rebuildGenFileInfo(scaffoldGenInfoDTO);
    }

    @Override
    public Result<List<Long>> saveMultiTableGenInfo(ScaffoldMultiTableReqDTO multiTableReqDTO){
        if(DCCollectionUtil.isEmpty(multiTableReqDTO.getGenInfoList())){
            return DCResultUtil.error("必传参数不能为空");
        }
        List<Long> ids = new ArrayList<>();
        multiTableReqDTO.getGenInfoList().forEach(genInfoDTO->{
            commonSave(genInfoDTO);
            ids.add(scaffoldGenInfoManager.save(genInfoDTO));
        });
        return DCResultUtil.success(ids);
    }

    @Override
    public Result<Boolean> deleteById(ScaffoldGenInfoDTO scaffoldGenInfoDTO) {
        if (DCObjectUtil.isNull(scaffoldGenInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(scaffoldGenInfoManager.deleteById(scaffoldGenInfoDTO));
    }

    @Override
    public Result<ScaffoldGenInfoDTO> getById(ScaffoldGenInfoDTO scaffoldGenInfoDTO) {
        if (DCObjectUtil.isNull(scaffoldGenInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(scaffoldGenInfoManager.getById(scaffoldGenInfoDTO.getId()));
    }

    @Override
    public void downloadWithZip(ScaffoldGenInfoDTO scaffoldGenInfoDTO, @Context HttpServletResponse response) {
        if (DCObjectUtil.isNull(scaffoldGenInfoDTO.getId())) {
            return;
        }
        // 通过id查询代码生成基础信息
        ScaffoldGenInfoDTO dbScaffoldGenInfoDTO = scaffoldGenInfoManager.getById(scaffoldGenInfoDTO.getId());
        if (DCObjectUtil.isNull(dbScaffoldGenInfoDTO)) {
            return;
        }
        dbScaffoldGenInfoDTO.setCreateUser(ContextHoldUtil.getEmpNum());
        dbScaffoldGenInfoDTO.setUpdateUser(ContextHoldUtil.getEmpNum());
        //代码生成基础配置生成
        tplGenService.tplGen(scaffoldGenInfoManager.convertScaffoldGenInfoDTOToTplGenParamDTO(dbScaffoldGenInfoDTO), response);
    }

    @Override
    public Result<Boolean> updateByCodeLine(ScaffoldGenInfoDTO scaffoldGenInfoDTO) {
        if(DCObjectUtil.isNull(scaffoldGenInfoDTO.getId())){
            return DCResultUtil.error("id不能为空");
        }
        ScaffoldGenInfoDTO dbScaffoldGenInfoDTO = scaffoldGenInfoManager.getById(scaffoldGenInfoDTO.getId());
        if (DCObjectUtil.isNull(dbScaffoldGenInfoDTO)) {
            return DCResultUtil.error("获取不到工程信息");
        }
        dbScaffoldGenInfoDTO.setCreateUser(ContextHoldUtil.getEmpNum());
        dbScaffoldGenInfoDTO.setUpdateUser(ContextHoldUtil.getEmpNum());
        TplGenParamDTO tplGenParamDTO = scaffoldGenInfoManager.convertScaffoldGenInfoDTOToTplGenParamDTO(dbScaffoldGenInfoDTO);
        tplGenManager.updateCodeCalculate(tplGenParamDTO,scaffoldGenInfoDTO.getCodeLines());
        return DCResultUtil.success(true);
    }

    @Override
    public Result<Boolean> deleteNoGenData(){
        //1.获取需要被清除的数据
        List<ScaffoldGenInfoDTO> dataList = scaffoldGenInfoManager.getNoGenDataList();
        log.info("需要被删除的工程数据有:{}",dataList.size());
        if(DCCollectionUtil.isEmpty(dataList)){
            return DCResultUtil.success(true);
        }
        List<Long> ids = dataList.stream().map(ScaffoldGenInfoDTO::getId).collect(Collectors.toList());
        //2.删除工程基础信息数据
        scaffoldGenInfoManager.deleteByIds(ids);
        return DCResultUtil.success(true);
    }

    @Override
    public void downloadWithMultiZip(ScaffoldMultiTableReqDTO scaffoldMultiTableReqDTO, @Context HttpServletResponse response) {
        if (DCCollectionUtil.isEmpty(scaffoldMultiTableReqDTO.getIds())) {
            return;
        }
        // 通过id查询代码生成基础信息
        List<ScaffoldGenInfoDTO>  genInfoList = scaffoldGenInfoManager.getListByIds(scaffoldMultiTableReqDTO.getIds());
        if (DCObjectUtil.isNull(genInfoList)) {
            return;
        }
        List<TplGenParamDTO> tplGenParamList = new ArrayList<>();
        genInfoList.forEach(genInfo->{
            TplGenParamDTO tplGenParamDTO = scaffoldGenInfoManager.convertScaffoldGenInfoDTOToTplGenParamDTO(genInfo);
            tplGenParamList.add(tplGenParamDTO);
        });
        //代码生成基础配置生成
        tplGenService.tplGenList(tplGenParamList, response);
    }

    @Override
    public Result<Boolean> deleteGenInfo(ScaffoldMultiTableReqDTO multiTableReqDTO){
        if(DCCollectionUtil.isEmpty(multiTableReqDTO.getIds())){
            return DCResultUtil.error("id不能为空");
        }
        List<Long> ids = multiTableReqDTO.getIds();
        //1.删除工程基础信息数据
        scaffoldGenInfoManager.deleteByIds(ids);
        //2.删除数据库数据
        List<ScaffoldGenDbInfoDO> dbList = scaffoldGenDbInfoManager.getDbListByGenInfoIds(ids);
        log.info("需要被删除的数据库数据有:{}",dbList.size());
        if(DCCollectionUtil.isEmpty(dbList)){
            return DCResultUtil.success(true);
        }
        List<Long> dbIds = dbList.stream().map(ScaffoldGenDbInfoDO::getId).collect(Collectors.toList());
        scaffoldGenDbInfoManager.deleteByIds(dbIds);
        //3.删除关联表数据
        scaffoldGenDbExampleInfoManager.deleteByScaffoldGenDbInfoId(dbIds);
        //4.删除文件数据
        genFileInfoManager.deleteFileByGenInfoIds(ids);
        return DCResultUtil.success(true);
    }
}
