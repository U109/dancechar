package com.litian.dancechar.biz.core.scaffold.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.litian.dancechar.biz.core.scaffold.dto.*;
import com.litian.dancechar.biz.core.scaffold.repository.dataobject.ScaffoldGenDbExampleInfoDO;
import com.litian.dancechar.biz.core.scaffold.repository.mapper.ScaffoldGenDbExampleInfoMapper;
import com.litian.dancechar.framework.common.context.ContextHoldUtil;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCCollectionUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.util.page.PageRespUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 脚手架生成-数据库-示例信息(ScaffoldGenDbExampleInfo)manager处理
 *
 * @author 01406831
 * @since 2021-06-21 14:33:55
 */
@Component
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ScaffoldGenDbExampleInfoManager extends ServiceImpl<ScaffoldGenDbExampleInfoMapper, ScaffoldGenDbExampleInfoDO> {
    @Resource
    private ChoosedTableColumnsManager choosedTableColumnsManager;

    /**
     * 功能：分页查询列表记录
     */
    public PageResp<ScaffoldGenDbExampleInfoDTO> listPage(ScaffoldGenDbExampleInfoQueryReqDTO scaffoldGenDbExampleInfoQueryReqDTO) {
        PageHelper.startPage(scaffoldGenDbExampleInfoQueryReqDTO.getPageNo(), scaffoldGenDbExampleInfoQueryReqDTO.getPageSize());
        QueryWrapper<ScaffoldGenDbExampleInfoDO> queryWrapper = Wrappers.query();
        List<ScaffoldGenDbExampleInfoDO> list = getBaseMapper().selectList(queryWrapper);
        return PageRespUtil.buildPageResult(list, ScaffoldGenDbExampleInfoDTO.class);
    }

    public List<ScaffoldGenDbExampleInfoDTO> findList(ScaffoldGenDbExampleInfoQueryReqDTO scaffoldGenDbExampleInfoQueryReqDTO) {
        PageHelper.startPage(scaffoldGenDbExampleInfoQueryReqDTO.getPageNo(), scaffoldGenDbExampleInfoQueryReqDTO.getPageSize());
        QueryWrapper<ScaffoldGenDbExampleInfoDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("scaffold_gen_db_info_id", scaffoldGenDbExampleInfoQueryReqDTO.getScaffoldGenDbInfoId());
        queryWrapper.eq("delete_flag", 0);
        List<ScaffoldGenDbExampleInfoDO> list = getBaseMapper().selectList(queryWrapper);
        return DCBeanUtil.copyList(list, ScaffoldGenDbExampleInfoDTO.class);
    }

    /**
     * 功能：单表新增/修改保存记录
     */
    public ChoosedTableColumnsRespDTO saveScaffoldGenDbExampleInfoList(List<ScaffoldGenDbExampleInfoDTO> sgiList, Long scaffoldGenDbInfoId) {
        if (DCCollectionUtil.isNotEmpty(sgiList)) {
            ScaffoldGenDbExampleInfoDTO infoDTO = sgiList.get(0);
            return saveDbExampleInfo(infoDTO,scaffoldGenDbInfoId);
        }
        return new ChoosedTableColumnsRespDTO();
    }

    public ChoosedTableColumnsRespDTO saveDbExampleInfo(ScaffoldGenDbExampleInfoDTO dbExampleInfoDTO, Long scaffoldGenDbInfoId){
        ChoosedTableColumnsRespDTO respDTO = new ChoosedTableColumnsRespDTO();
        String firstTableName = dbExampleInfoDTO.getTableName().toLowerCase();
        String[] arrays = firstTableName.split("_");
        StringBuilder className = new StringBuilder();
        if(arrays != null && arrays.length > 0){
            for (String str : arrays) {
                String temp = str.substring(0, 1).toUpperCase() + str.substring(1);
                className.append(temp);
            }
        }
        String catalog = firstTableName.replaceAll("_", "");
        respDTO.setCatalog(StringUtils.lowerCase(catalog));
        respDTO.setClassName(className.toString());
        dbExampleInfoDTO.setScaffoldGenDbInfoId(scaffoldGenDbInfoId);
        dbExampleInfoDTO.setCatalog(StringUtils.lowerCase(catalog));
        dbExampleInfoDTO.setClassName(className.toString());
        ScaffoldGenDbExampleInfoDO dbDO = DCBeanUtil.copyNotNull(dbExampleInfoDTO, new ScaffoldGenDbExampleInfoDO());
        this.save(dbDO);
        respDTO.setId(dbDO.getId());
        respDTO.setScaffoldGenDbInfoId(scaffoldGenDbInfoId);
        return respDTO;
    }

    /**
     * 功能描述:单功能多表处理
     */
    public ChoosedTableColumnsRespDTO saveMultiTable(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO, Long dbInfoId) {
        ChoosedTableColumnsRespDTO respDTO = saveMultiTableExampleInfoList(scaffoldGenDbInfoDTO.getScaffoldGenDbExampleInfoList(), dbInfoId);
        respDTO.setId(dbInfoId);
        List<ChoosedTableColumnsDTO> columnsList = new ArrayList<>();
        for (ScaffoldGenDbExampleInfoDTO exampleInfoDTO : scaffoldGenDbInfoDTO.getScaffoldGenDbExampleInfoList()) {
            if (DCCollectionUtil.isNotEmpty(exampleInfoDTO.getColumnsList())) {
                for (ChoosedTableColumnsDTO columnsDTO : exampleInfoDTO.getColumnsList()) {
                    columnsDTO.setTableName(exampleInfoDTO.getTableName());
                    columnsDTO.setScaffoldGenInfoId(scaffoldGenDbInfoDTO.getScaffoldGenInfoId());
                    columnsList.add(columnsDTO);
                }
            }
        }
        if (DCCollectionUtil.isNotEmpty(columnsList) && DCObjectUtil.isNotEmpty(scaffoldGenDbInfoDTO.getGenSqlDTO())) {
            scaffoldGenDbInfoDTO.getGenSqlDTO().setScaffoldGenInfoId(scaffoldGenDbInfoDTO.getScaffoldGenInfoId());
            scaffoldGenDbInfoDTO.getGenSqlDTO().setSystemDbId(scaffoldGenDbInfoDTO.getSystemDbId());
            ChoosedTableColumnsReqDTO choosedTableColumnsReqDTO = new ChoosedTableColumnsReqDTO();
            choosedTableColumnsReqDTO.setColumnsList(columnsList);
            choosedTableColumnsReqDTO.setGenSqlDTO(scaffoldGenDbInfoDTO.getGenSqlDTO());
            choosedTableColumnsReqDTO.setScaffoldGenInfoId(scaffoldGenDbInfoDTO.getScaffoldGenInfoId());
            choosedTableColumnsManager.saveChoosedTable(choosedTableColumnsReqDTO);
        }
        return respDTO;
    }

    public ChoosedTableColumnsRespDTO saveMultiTableExampleInfoList(List<ScaffoldGenDbExampleInfoDTO> sgiList, Long scaffoldGenDbInfoId) {
        ChoosedTableColumnsRespDTO respDTO = new ChoosedTableColumnsRespDTO();
        // 先删除记录，后保存
        if (DCCollectionUtil.isNotEmpty(sgiList)) {
            String firstTableName = sgiList.get(0).getTableName().toLowerCase();
            String tableName = sgiList.stream().map(ScaffoldGenDbExampleInfoDTO::getTableName).collect(Collectors.joining(","));
            String tableDesc = sgiList.stream().map(ScaffoldGenDbExampleInfoDTO::getTableDesc).collect(Collectors.joining(","));
            ScaffoldGenDbExampleInfoDTO scaffoldGenDbExampleInfoDTO = sgiList.get(0);
            ScaffoldGenDbExampleInfoDO scaffoldGenDbExampleInfoDO = new ScaffoldGenDbExampleInfoDO();
            DCBeanUtil.copyNotNull(scaffoldGenDbExampleInfoDTO, scaffoldGenDbExampleInfoDO);
            scaffoldGenDbExampleInfoDO.setTableName(tableName);
            scaffoldGenDbExampleInfoDO.setTableDesc(tableDesc);
            scaffoldGenDbExampleInfoDO.setScaffoldGenDbInfoId(scaffoldGenDbInfoId);
            scaffoldGenDbExampleInfoDO.setAuthor(ContextHoldUtil.getEmpNum());
            String[] arrays = firstTableName.split("_");
            StringBuilder catalog = new StringBuilder();
            StringBuilder className = new StringBuilder();
            if(arrays != null && arrays.length > 0){
                for (String str : arrays) {
                    catalog.append(str);
                    String temp = str.substring(0, 1).toUpperCase() + str.substring(1);
                    className.append(temp);
                }
            }
            scaffoldGenDbExampleInfoDO.setCatalog(catalog.append("wrapper").toString());
            scaffoldGenDbExampleInfoDO.setClassName(className.append("Wrapper").toString());
            save(scaffoldGenDbExampleInfoDO);
            respDTO.setCatalog(catalog.toString());
            respDTO.setClassName(className.toString());
        }
        return respDTO;
    }

    /**
     * 功能：根据id删除记录
     */
    public Boolean deleteById(ScaffoldGenDbExampleInfoDTO scaffoldGenDbExampleInfoDTO) {
        return this.baseMapper.deleteById(scaffoldGenDbExampleInfoDTO.getId()) > 0;
    }

    /**
     * 功能：根据scaffoldGenDbInfoId获取记录列表
     */
    public Boolean deleteByScaffoldGenDbInfoId(List<Long> scaffoldGenDbInfoIds) {
        if (DCCollectionUtil.isEmpty(scaffoldGenDbInfoIds)) {
            return false;
        }
        return this.baseMapper.deleteByScaffoldGenDbInfoId(scaffoldGenDbInfoIds) > 0;
    }

    /**
     * 功能：根据scaffoldGenDbInfoId获取记录列表
     */
    public List<ScaffoldGenDbExampleInfoDTO> listById(Long scaffoldGenDbInfoId) {
        if (DCObjectUtil.isNull(scaffoldGenDbInfoId)) {
            return Lists.newArrayList();
        }
        LambdaQueryWrapper<ScaffoldGenDbExampleInfoDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ScaffoldGenDbExampleInfoDO::getScaffoldGenDbInfoId, scaffoldGenDbInfoId);
        return DCBeanUtil.copyList(this.list(queryWrapper), ScaffoldGenDbExampleInfoDTO.class);
    }

    /**
     * 功能：根据id获取记录
     */
    public ScaffoldGenDbExampleInfoDTO getById(Long id) {
        return DCBeanUtil.copyNotNull(this.baseMapper.selectById(id), new ScaffoldGenDbExampleInfoDTO());
    }

    public void updateMultiTableClassName(String className, String catalog, Long dbId) {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.eq("scaffold_gen_db_info_id", dbId);
        queryWrapper.eq("delete_flag", 0);
        ScaffoldGenDbExampleInfoDO exampleInfoDO = baseMapper.selectOne(queryWrapper);
        exampleInfoDO.setClassName(className);
        exampleInfoDO.setCatalog(catalog);
        baseMapper.updateById(exampleInfoDO);
    }

    public List<ScaffoldGenDbExampleInfoDO> getTableListByDbIds(List<Long> dbIds) {
        LambdaQueryWrapper<ScaffoldGenDbExampleInfoDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ScaffoldGenDbExampleInfoDO::getScaffoldGenDbInfoId, dbIds);
        return this.list(queryWrapper);
    }
}