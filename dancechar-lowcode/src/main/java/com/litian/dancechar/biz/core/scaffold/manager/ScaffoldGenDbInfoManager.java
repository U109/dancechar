package com.litian.dancechar.biz.core.scaffold.manager;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.litian.dancechar.biz.core.scaffold.dto.*;
import com.litian.dancechar.biz.core.scaffold.repository.dataobject.ChoosedTableColumnsDO;
import com.litian.dancechar.biz.core.scaffold.repository.dataobject.ScaffoldGenDbInfoDO;
import com.litian.dancechar.biz.core.scaffold.repository.dataobject.ScaffoldGenInfoDO;
import com.litian.dancechar.biz.core.scaffold.repository.mapper.ScaffoldGenDbInfoMapper;
import com.litian.dancechar.biz.core.tplgen.common.enums.TplGenParamEnums;
import com.litian.dancechar.biz.core.tplgen.dto.TplGenDBInfoDTO;
import com.litian.dancechar.biz.core.tplgen.dto.TplGenParamDTO;
import com.litian.dancechar.biz.core.tplgen.manager.TplGenManager;
import com.litian.dancechar.biz.sysmgr.dbmgr.dto.SystemDBInfoDTO;
import com.litian.dancechar.biz.sysmgr.dbmgr.manager.SystemDBInfoManager;
import com.litian.dancechar.biz.sysmgr.system.dto.SystemInfoDTO;
import com.litian.dancechar.biz.sysmgr.system.manager.SystemInfoManager;
import com.litian.dancechar.framework.common.context.ContextHoldUtil;
import com.litian.dancechar.framework.common.util.*;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.util.page.PageRespUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 脚手架生成-数据库信息(ScaffoldGenDbInfo)manager处理
 *
 * @author 01406831
 * @since 2021-06-21 14:33:21
 */
@Component
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ScaffoldGenDbInfoManager extends ServiceImpl<ScaffoldGenDbInfoMapper, ScaffoldGenDbInfoDO> {
    @Resource
    private ScaffoldGenDbExampleInfoManager scaffoldGenDbExampleInfoManager;
    @Resource
    private ChoosedTableColumnsManager choosedTableColumnsManager;
    @Resource
    private ScaffoldGenInfoManager scaffoldGenInfoManager;
    @Resource
    private FunctionGenSqlManager functionGenSqlManager;
    @Resource
    private SystemDBInfoManager systemDBInfoManager;
  //  @Resource
  //  private TableDetailInfoManager tableDetailInfoManager;
    @Resource
    private TplGenManager tplGenManager;
    @Resource
    private SystemInfoManager systemInfoManager;


    /**
     * 功能：分页查询列表记录
     */
    public PageResp<ScaffoldGenDbInfoDTO> listPage(ScaffoldGenDbInfoQueryReqDTO scaffoldGenDbInfoQueryReqDTO) {
        PageHelper.startPage(scaffoldGenDbInfoQueryReqDTO.getPageNo(), scaffoldGenDbInfoQueryReqDTO.getPageSize());
        QueryWrapper<ScaffoldGenDbInfoDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("scaffold_gen_info_id", scaffoldGenDbInfoQueryReqDTO.getScaffoldGenInfoId());
        if (StrUtil.isNotEmpty(scaffoldGenDbInfoQueryReqDTO.getIpPort())) {
            queryWrapper.like("ip_port", "%" + scaffoldGenDbInfoQueryReqDTO.getIpPort() + "%");
        }
        if (StrUtil.isNotEmpty(scaffoldGenDbInfoQueryReqDTO.getDbName())) {
            queryWrapper.like("db_name", "%" + scaffoldGenDbInfoQueryReqDTO.getDbName() + "%");
        }
        if (StrUtil.isNotEmpty(scaffoldGenDbInfoQueryReqDTO.getUserName())) {
            queryWrapper.like("user_name", "%" + scaffoldGenDbInfoQueryReqDTO.getUserName() + "%");
        }
        List<ScaffoldGenDbInfoDO> list = getBaseMapper().selectList(queryWrapper);
        return PageRespUtil.buildPageResult(list, ScaffoldGenDbInfoDTO.class);
    }

    /**
     * 功能：新增/修改保存记录
     */
    public ChoosedTableColumnsRespDTO save(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO,SystemDBInfoDTO systemDBInfoDTO) {
        //设置数据库连接信息
        systemDbTransation(scaffoldGenDbInfoDTO,systemDBInfoDTO);
        ChoosedTableColumnsRespDTO respDTO = new ChoosedTableColumnsRespDTO();
        String exampleInfoJson = scaffoldGenDbInfoDTO.getScaffoldGenDbExampleInfoJson();
        if (StrUtil.isNotEmpty(exampleInfoJson)) {
            scaffoldGenDbInfoDTO.setScaffoldGenDbExampleInfoList(DCJSONUtil.toList(exampleInfoJson, ScaffoldGenDbExampleInfoDTO.class));
        }
        //1.获取功能描述，如果存在就拿已有的值（编辑），不存在获取默认值（新增）
        ScaffoldGenInfoDTO genInfoDTO = scaffoldGenInfoManager.getById(scaffoldGenDbInfoDTO.getScaffoldGenInfoId());
        String functions = genInfoDTO.getGenFunctions();
        ScaffoldGenInfoDTO scaffoldGenInfoDTO = new ScaffoldGenInfoDTO();
        scaffoldGenInfoDTO.setId(scaffoldGenDbInfoDTO.getScaffoldGenInfoId());
        if (CollectionUtils.isNotEmpty(scaffoldGenDbInfoDTO.getScaffoldGenDbExampleInfoList()) && DCStrUtil.isBlank(functions)) {
            functions = scaffoldGenDbInfoDTO.getScaffoldGenDbExampleInfoList().stream().filter(example -> DCStrUtil.isNotEmpty(example.getTableDesc()))
                    .map(example -> example.getTableDesc()).collect(Collectors.joining());
        }
        scaffoldGenInfoDTO.setGenFunctions(functions);
        scaffoldGenInfoDTO.setScaffoldType(scaffoldGenDbInfoDTO.getScaffoldType());
        scaffoldGenInfoManager.updateById(scaffoldGenInfoDTO);
        ScaffoldGenInfoDTO scaffoldGenInfo =
                scaffoldGenInfoManager.getById(scaffoldGenDbInfoDTO.getScaffoldGenInfoId());
        if (scaffoldGenInfo != null && CollectionUtils.isNotEmpty(scaffoldGenDbInfoDTO.getScaffoldGenDbExampleInfoList())) {
            scaffoldGenDbInfoDTO.getScaffoldGenDbExampleInfoList().forEach(dbExample -> {
                dbExample.setPackageName(scaffoldGenInfo.getProjectPackageName());
            });
        }
        String className = null;
        String catalog = null;
        //先删除dbExample表数据，再删除db info数据, 然后再保存数据
        List<ScaffoldGenDbInfoDTO> genDbList = listByScaffoldGenInfoId(scaffoldGenDbInfoDTO.getScaffoldGenInfoId());
        if (DCCollectionUtil.isNotEmpty(genDbList)) {
            if (DCCollectionUtil.isNotEmpty(genDbList.get(0).getScaffoldGenDbExampleInfoList())) {
                ScaffoldGenDbExampleInfoDTO dbExample = genDbList.get(0).getScaffoldGenDbExampleInfoList().get(0);
                className = dbExample.getClassName();
                catalog = dbExample.getCatalog();
            }
            List<Long> genDbIdList = genDbList.stream().map(ScaffoldGenDbInfoDTO::getId).collect(Collectors.toList());
            scaffoldGenDbExampleInfoManager.deleteByScaffoldGenDbInfoId(genDbIdList);
        }
        //删除字段信息
       // tableDetailInfoManager.deleteByGenInfoId(scaffoldGenDbInfoDTO.getScaffoldGenInfoId());
        this.baseMapper.deleteByScaffoldGenId(scaffoldGenDbInfoDTO.getScaffoldGenInfoId());
        ScaffoldGenDbInfoDO scaffoldGenDbInfoDO = new ScaffoldGenDbInfoDO();
        DCBeanUtil.copyNotNull(scaffoldGenDbInfoDTO, scaffoldGenDbInfoDO, "id");
        save(scaffoldGenDbInfoDO);
        if (scaffoldGenDbInfoDTO.getScaffoldType() == 3) {
            //多表处理
            respDTO = scaffoldGenDbExampleInfoManager.saveMultiTable(scaffoldGenDbInfoDTO, scaffoldGenDbInfoDO.getId());
        } else {
            //单表处理
            respDTO = scaffoldGenDbExampleInfoManager.saveScaffoldGenDbExampleInfoList(scaffoldGenDbInfoDTO.getScaffoldGenDbExampleInfoList(),
                    scaffoldGenDbInfoDO.getId());
        }
        respDTO.setId(scaffoldGenDbInfoDO.getId());
        if (DCStrUtil.isNotBlank(className) && DCStrUtil.isNotBlank(catalog)) {
            respDTO.setCatalog(StringUtils.lowerCase(catalog));
            respDTO.setClassName(className);
        }
        respDTO.setGenFunctions(functions);
        //设置字段
        setTableColumns(scaffoldGenDbInfoDTO,systemDBInfoDTO);
        return respDTO;
    }

    private void systemDbTransation(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO,SystemDBInfoDTO systemDBInfoDTO){
        scaffoldGenDbInfoDTO.setUserName(systemDBInfoDTO.getDbUsername());
        scaffoldGenDbInfoDTO.setPassword(systemDBInfoDTO.getDbPassword());
        scaffoldGenDbInfoDTO.setDriverClass(systemDBInfoDTO.getDbDriver());
        scaffoldGenDbInfoDTO.setIpPort(systemDBInfoDTO.getDbHost() + "/" + systemDBInfoDTO.getDbPort());
        scaffoldGenDbInfoDTO.setDbName(systemDBInfoDTO.getDbName());
    }

    private void setTableColumns(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO,SystemDBInfoDTO systemDBInfoDTO){
        if(scaffoldGenDbInfoDTO.getScaffoldType() == 2 || scaffoldGenDbInfoDTO.getScaffoldType() ==3){
            //保存字段信息
            TplGenDBInfoDTO tplGenDBInfoDTO = TplGenDBInfoDTO.builder().dbName(systemDBInfoDTO.getDbName()).driverName(systemDBInfoDTO.getDbDriver()).
                    port(Integer.valueOf(systemDBInfoDTO.getDbPort())).host(systemDBInfoDTO.getDbHost()).username(systemDBInfoDTO.getDbUsername()).password(systemDBInfoDTO.getDbPassword()).build();
            String tempType = TplGenParamEnums.TemplateTypeEnum.getByType(scaffoldGenDbInfoDTO.getScaffoldType()).getCode();
            TplGenParamDTO tplGenParamDTO = TplGenParamDTO.builder().templateType(tempType).genInfoId(scaffoldGenDbInfoDTO.getScaffoldGenInfoId()).build();
            String tableName = scaffoldGenDbInfoDTO.getScaffoldGenDbExampleInfoList().stream().map(ScaffoldGenDbExampleInfoDTO::getTableName).collect(Collectors.joining(","));
            tplGenManager.getColumns(tableName,tplGenDBInfoDTO,tplGenParamDTO);
        }
    }

    /**
     * 根据geninfoid 及 dbname
     */
    public List<ScaffoldGenDbInfoDTO> getByGenInfoIdAndDbName(Long scaffoldGenInfoId, String dbName) {
        LambdaQueryWrapper<ScaffoldGenDbInfoDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScaffoldGenDbInfoDO::getScaffoldGenInfoId, scaffoldGenInfoId);
        queryWrapper.eq(ScaffoldGenDbInfoDO::getDbName, dbName);
        List<ScaffoldGenDbInfoDO> scaffoldGenDbInfoDOList = getBaseMapper().selectList(queryWrapper);
        return DCBeanUtil.copyList(scaffoldGenDbInfoDOList, ScaffoldGenDbInfoDTO.class);
    }

    /**
     * 功能：根据scaffoldGenInfoId删除记录
     */
    public Boolean deleteByScaffoldGenInfoId(Long scaffoldGenInfoId) {
        LambdaQueryWrapper<ScaffoldGenDbInfoDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ScaffoldGenDbInfoDO::getScaffoldGenInfoId, scaffoldGenInfoId);
        List<ScaffoldGenDbInfoDO> scaffoldGenDbInfoDOList = this.list(queryWrapper);
        if (DCCollectionUtil.isNotEmpty(scaffoldGenDbInfoDOList)) {
            scaffoldGenDbInfoDOList.stream().forEach(vo -> {
                scaffoldGenDbExampleInfoManager.deleteByScaffoldGenDbInfoId(Lists.newArrayList(vo.getId()));
                super.removeById(vo.getId());
            });
        }
        return true;
    }

    /**
     * 功能：根据id删除记录
     */
    public Boolean deleteById(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO) {
        ScaffoldGenDbInfoDO scaffoldGenDbInfoDO = this.baseMapper.selectById(scaffoldGenDbInfoDTO.getId());
        if (DCObjectUtil.isNotNull(scaffoldGenDbInfoDO)) {
            scaffoldGenDbExampleInfoManager.deleteByScaffoldGenDbInfoId(Lists.newArrayList(scaffoldGenDbInfoDTO.getId()));
            scaffoldGenDbInfoDO.setDeleteFlag(1);
            return this.baseMapper.deleteById(scaffoldGenDbInfoDO) > 0;
        }
        return false;
    }

    /**
     * 功能：根据id获取记录
     */
    public ScaffoldGenDbInfoDTO getById(Long id) {
        ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO = DCBeanUtil.copyNotNull(this.baseMapper.selectById(id), new ScaffoldGenDbInfoDTO());
        scaffoldGenDbInfoDTO.setScaffoldGenDbExampleInfoList(scaffoldGenDbExampleInfoManager.listById(id));
        return scaffoldGenDbInfoDTO;
    }

    /**
     * 功能：根据scaffoldGenInfoId获取记录列表
     */
    public List<ScaffoldGenDbInfoDTO> listByScaffoldGenInfoId(Long scaffoldGenInfoId) {
        LambdaQueryWrapper<ScaffoldGenDbInfoDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ScaffoldGenDbInfoDO::getScaffoldGenInfoId, scaffoldGenInfoId);
        List<ScaffoldGenDbInfoDO> scaffoldGenDbInfoDOList = this.list(queryWrapper);
        if (DCCollectionUtil.isNotEmpty(scaffoldGenDbInfoDOList)) {
            List<ScaffoldGenDbInfoDTO> sgList = Lists.newArrayList();
            scaffoldGenDbInfoDOList.stream().forEach(vo -> {
                ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO = new ScaffoldGenDbInfoDTO();
                DCBeanUtil.copyNotNull(vo, scaffoldGenDbInfoDTO);
                scaffoldGenDbInfoDTO.setScaffoldGenDbExampleInfoList(scaffoldGenDbExampleInfoManager.listById(vo.getId()));
                sgList.add(scaffoldGenDbInfoDTO);
            });
            return sgList;
        }
        return Lists.newArrayList();
    }

    public SystemDBInfoDTO convertScaffoldGenDbToSystemDBInfoDTO(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO) {
        SystemDBInfoDTO systemInfo = new SystemDBInfoDTO();
        systemInfo.setDbDriver(scaffoldGenDbInfoDTO.getDriverClass());
        String[] ipPortArr = DCStrUtil.split(scaffoldGenDbInfoDTO.getIpPort(), "/");
        if (DCArrayUtil.isNotEmpty(ipPortArr) && ipPortArr.length > 1) {
            systemInfo.setDbHost(ipPortArr[0]);
            systemInfo.setDbPort(ipPortArr[1]);
        }
        systemInfo.setDbUsername(scaffoldGenDbInfoDTO.getUserName());
        systemInfo.setDbPassword(scaffoldGenDbInfoDTO.getPassword());
        systemInfo.setDbName(scaffoldGenDbInfoDTO.getDbName());
        return systemInfo;
    }

    /**
     * 根据脚手架工程生成ID删除脚手架工程生成的数据库信息
     */
    public int deleteByScaffoldGenId(Long scaffoldGenId) {
        return this.baseMapper.deleteByScaffoldGenId(scaffoldGenId);
    }

    /**
     * 查询脚手架工程配置的数据库列表
     */
    public List<SystemDBInfoDTO> listByGenInfoId(Long scaffoldGenId) {
        List<ScaffoldGenDbInfoDO> sGenDbInfoList = this.getDbBaseInfoByGenInfoId(scaffoldGenId);
        if (DCCollectionUtil.isNotEmpty(sGenDbInfoList)) {
            List<Long> dbIdList = sGenDbInfoList.stream().map(ScaffoldGenDbInfoDO::getSystemDbId).collect(Collectors.toList());
            return systemDBInfoManager.listByIds(dbIdList);
        }
        return Lists.newArrayList();
    }

    public ScaffoldMultiTableRespDTO getDbInfoByGenInfoId(Long scaffoldGenId) {
        ScaffoldMultiTableRespDTO scaffoldMultiTableRespDTO = new ScaffoldMultiTableRespDTO();
        List<ScaffoldGenDbInfoDO> dbInfList = this.getDbBaseInfoByGenInfoId(scaffoldGenId);
        if (DCCollectionUtil.isEmpty(dbInfList)) {
            return scaffoldMultiTableRespDTO;
        }
        ScaffoldGenDbInfoDO dbInfoDO = dbInfList.get(0);
        Long dbId = dbInfoDO.getId();
        scaffoldMultiTableRespDTO.setScaffoldGenInfoId(scaffoldGenId);
        scaffoldMultiTableRespDTO.setSystemDbId(dbInfoDO.getSystemDbId());
        scaffoldMultiTableRespDTO.setPrimaryDb(dbInfoDO.getPrimaryDb());
        ScaffoldGenDbExampleInfoQueryReqDTO exampleInfoQueryReqDTO = new ScaffoldGenDbExampleInfoQueryReqDTO();
        exampleInfoQueryReqDTO.setScaffoldGenDbInfoId(dbId);
        List<ScaffoldGenDbExampleInfoDTO> exampleInfoList = scaffoldGenDbExampleInfoManager.findList(exampleInfoQueryReqDTO);
        if (DCCollectionUtil.isEmpty(exampleInfoList)) {
            return scaffoldMultiTableRespDTO;
        }
        ScaffoldGenDbExampleInfoDTO exampleInfoDTO = exampleInfoList.get(0);
        String tableName = exampleInfoDTO.getTableName();
        String tableDesc = exampleInfoDTO.getTableDesc();
        String[] nameArrays = tableName.split(",");
        String[] descArrays = DCStrUtil.isBlank(tableDesc) ? null : tableDesc.split(",");
        exampleInfoList = new ArrayList<>();
        for (int i = 0; i < nameArrays.length; i++) {
            ScaffoldGenDbExampleInfoDTO infoDTO = new ScaffoldGenDbExampleInfoDTO();
            DCBeanUtil.copyNotNull(exampleInfoDTO, infoDTO);
            infoDTO.setTableName(nameArrays[i]);
            String desc = "";
            if (descArrays != null && descArrays.length > 0 && i < descArrays.length) {
                desc = descArrays[i];
            }
            infoDTO.setTableDesc(desc);
            List<ChoosedTableColumnsDO> columnsDOList = choosedTableColumnsManager.getColumnByGenIdAndTable(scaffoldGenId, nameArrays[i]);
            if (DCCollectionUtil.isNotEmpty(columnsDOList)) {
                List<ChoosedTableColumnsDTO> columnsList = DCBeanUtil.copyList(columnsDOList, ChoosedTableColumnsDTO.class);
                infoDTO.setColumnsList(columnsList);
            }
            exampleInfoList.add(infoDTO);
        }
        scaffoldMultiTableRespDTO.setScaffoldGenDbExampleInfoList(exampleInfoList);
        FunctionGenSqlDTO functionGenSqlDTO = functionGenSqlManager.getByGenInfoId(scaffoldGenId);
        if (DCObjectUtil.isNotEmpty(functionGenSqlDTO)) {
            scaffoldMultiTableRespDTO.setGenSqlDTO(functionGenSqlDTO);
        }
        ScaffoldGenInfoDTO genInfoDTO = scaffoldGenInfoManager.getById(scaffoldGenId);
        if (DCObjectUtil.isNotEmpty(functionGenSqlDTO)) {
            scaffoldMultiTableRespDTO.setDirNames(genInfoDTO.getDirNames());
            scaffoldMultiTableRespDTO.setGenFunctions(genInfoDTO.getGenFunctions());
        }
        return scaffoldMultiTableRespDTO;
    }

    public List<ScaffoldGenDbInfoDO> getDbBaseInfoByGenInfoId(Long scaffoldGenId) {
        QueryWrapper<ScaffoldGenDbInfoDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("scaffold_gen_info_id", scaffoldGenId);
        queryWrapper.eq("delete_flag", 0);
        return baseMapper.selectList(queryWrapper);
    }

    public List<ScaffoldGenDbInfoDO> getDbListByGenInfoIds(List<Long> scaffoldGenIds) {
        QueryWrapper<ScaffoldGenDbInfoDO> queryWrapper = Wrappers.query();
        queryWrapper.in("scaffold_gen_info_id", scaffoldGenIds);
        return baseMapper.selectList(queryWrapper);
    }

    public int deleteByIds(List<Long> ids) {
        return baseMapper.deleteByIds(ids);
    }

    public List<ChoosedTableColumnsRespDTO> saveBatchTable(ScaffoldMultiTableDbDTO scaffoldMultiTableDbDTO,SystemDBInfoDTO systemDBInfoDTO){
        List<ChoosedTableColumnsRespDTO> respList = new ArrayList<>();
        OriginGenInfoDTO originGenInfoDTO = scaffoldMultiTableDbDTO.getOriginGenInfoDTO();
        //如果没有工程信息，则查询一遍
        if(DCStrUtil.isBlank(originGenInfoDTO.getGroupId())){
            SystemInfoDTO systemInfoDTO = systemInfoManager.getByPrimaryId(originGenInfoDTO.getSystemInfoId());
            DCBeanUtil.copyNotNull(systemInfoDTO,originGenInfoDTO,"id");
            originGenInfoDTO.setVersionNo(systemInfoDTO.getVersion());
            originGenInfoDTO.setProjectPackageName(systemInfoDTO.getPackageName());
        }
        //1.获取原脚手架信息，几张表就生成几份数据，并且删除原脚手架信息
        List<ScaffoldGenInfoDO> genInfoList = new ArrayList<>(scaffoldMultiTableDbDTO.getScaffoldGenDbExampleInfoList().size());
        scaffoldMultiTableDbDTO.getScaffoldGenDbExampleInfoList().forEach(exampleInfoDTO->{
            ScaffoldGenInfoDO genInfoDO = new ScaffoldGenInfoDO();
            DCBeanUtil.copyNotNull(originGenInfoDTO,genInfoDO);
            genInfoDO.setGenFunctions(exampleInfoDTO.getTableDesc());
            genInfoDO.setId(null);
            genInfoList.add(genInfoDO);
        });
        scaffoldGenInfoManager.saveBatch(genInfoList);
        ScaffoldGenInfoDTO genInfoDTO = scaffoldGenInfoManager.getById(originGenInfoDTO.getId());
        //如果描述为空，表示是第一次进来，还未选表
        if(DCObjectUtil.isNotEmpty(genInfoDTO) && DCStrUtil.isBlank(genInfoDTO.getGenFunctions())){
            List<Long> ids = new ArrayList<>();
            ids.add(originGenInfoDTO.getId());
            scaffoldGenInfoManager.deleteByIds(ids);
        }
        TplGenDBInfoDTO tplGenDBInfoDTO = TplGenDBInfoDTO.builder().dbName(systemDBInfoDTO.getDbName()).driverName(systemDBInfoDTO.getDbDriver()).
                port(Integer.valueOf(systemDBInfoDTO.getDbPort())).host(systemDBInfoDTO.getDbHost()).username(systemDBInfoDTO.getDbUsername()).password(systemDBInfoDTO.getDbPassword()).build();
        for(int i = 0; i < scaffoldMultiTableDbDTO.getScaffoldGenDbExampleInfoList().size(); i++){
            ScaffoldGenDbExampleInfoDTO dbExampleInfoDTO = scaffoldMultiTableDbDTO.getScaffoldGenDbExampleInfoList().get(i);
            dbExampleInfoDTO.setPackageName(originGenInfoDTO.getProjectPackageName());
            dbExampleInfoDTO.setAuthor(ContextHoldUtil.getEmpNum());
            String tableName = dbExampleInfoDTO.getTableName();
            //2.保存fcode_scaffold_gen_db_info信息
            ScaffoldGenInfoDO genInfoDO = genInfoList.get(i);
            ScaffoldGenDbInfoDO genDbInfo = new ScaffoldGenDbInfoDO();
            //2.1.设置数据库连接信息
            ScaffoldGenDbInfoDTO tempDTO = new ScaffoldGenDbInfoDTO();
            systemDbTransation(tempDTO,systemDBInfoDTO);
            DCBeanUtil.copyNotNull(tempDTO,genDbInfo);
            genDbInfo.setScaffoldGenInfoId(genInfoDO.getId());
            genDbInfo.setPrimaryDb(scaffoldMultiTableDbDTO.getPrimaryDb());
            genDbInfo.setSystemDbId(systemDBInfoDTO.getId());
            save(genDbInfo);
            //3.保存fcode_scaffold_gen_db_table_info信息

            ChoosedTableColumnsRespDTO respDTO = scaffoldGenDbExampleInfoManager.saveDbExampleInfo(dbExampleInfoDTO,genDbInfo.getId());
            //4.保存字段信息
            if(originGenInfoDTO.getScaffoldType() == 2 || originGenInfoDTO.getScaffoldType() ==3){
                String tempType = TplGenParamEnums.TemplateTypeEnum.getByType(originGenInfoDTO.getScaffoldType()).getCode();
                TplGenParamDTO tplGenParamDTO = TplGenParamDTO.builder().templateType(tempType).genInfoId(genInfoDO.getId()).build();
                tplGenManager.getColumns(tableName,tplGenDBInfoDTO,tplGenParamDTO);
            }
            respDTO.setGenFunctions(genInfoDO.getGenFunctions());
            respDTO.setScaffoldGenInfoId(genInfoDO.getId());
            respDTO.setTableName(tableName);
            respList.add(respDTO);
        }
        return respList;
    }

}
