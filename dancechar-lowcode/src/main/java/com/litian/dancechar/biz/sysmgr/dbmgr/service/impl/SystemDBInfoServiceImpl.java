package com.litian.dancechar.biz.sysmgr.dbmgr.service.impl;

import com.litian.dancechar.biz.core.codegen.manager.conn.ConnectionPoolUtil;
import com.litian.dancechar.biz.core.scaffold.manager.ScaffoldGenDbInfoManager;
import com.litian.dancechar.biz.core.scaffold.manager.ScaffoldGenInfoManager;
import com.litian.dancechar.biz.core.scaffold.repository.dataobject.ScaffoldGenInfoDO;
import com.litian.dancechar.biz.sysmgr.dbmgr.common.enums.SystemDBInfoEnums;
import com.litian.dancechar.biz.sysmgr.dbmgr.dto.*;
import com.litian.dancechar.biz.sysmgr.dbmgr.manager.DynamicDBManager;
import com.litian.dancechar.biz.sysmgr.dbmgr.manager.SystemDBInfoManager;
import com.litian.dancechar.biz.sysmgr.dbmgr.repository.dataobject.InformationSchemaTableColumn;
import com.litian.dancechar.biz.sysmgr.dbmgr.service.SystemDBInfoService;
import com.litian.dancechar.common.common.dto.BaseParam;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.context.ContextHoldUtil;
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
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 类描述：systemDBInfo 服务实现
 *
 * @author terryhl
 * @date 2021-06-21 11:48:37
 */
@Component
@Slf4j
public class SystemDBInfoServiceImpl implements SystemDBInfoService {
    private static final String ID_TIPS = "id不能为空";

    @Resource
    private SystemDBInfoManager systemDBInfoManager;
    @Resource
    private DynamicDBManager dynamicDBManager;
    @Resource
    private ScaffoldGenDbInfoManager scaffoldGenDbInfoManager;
    @Resource
    private ScaffoldGenInfoManager scaffoldGenInfoManager;


    /**
     * 查询所有表
     */
    @Override
    public Result<SystemDBInfoDTO> listDBTable(SystemDBInfoDTO systemDBInfoDTO) {
        // 根据库名查询 所有库表缓存
        systemDBInfoManager.listDBTable(systemDBInfoDTO);
        return DCResultUtil.success();
    }

    /**
     * 功能描述: 获取表的字段和描述
     */
    @Override
    public Result<List<InformationSchemaTableColumn>> listDBTableColumns(DbColumnsQueryDTO dbColumnsQueryDTO) {
        // 根据库名查询 所有库表缓存
        DynamicDBInfo dynamicDBInfo = new DynamicDBInfo();
        dynamicDBInfo.setDbName(dbColumnsQueryDTO.getTableSchema());
        dynamicDBInfo.setTableName(dbColumnsQueryDTO.getTableName());
        return DCResultUtil.success(dynamicDBManager.listTableColumn(dynamicDBInfo));
    }

    /**
     * 查询所有表
     */
    @Override
    public Result<Void> refreshTableList(SystemDBInfoDTO systemDBInfoDTO) {
        // 根据库名查询 所有库表缓存
        DCValidatorUtil.validateModel(systemDBInfoDTO, BaseParam.add.class);
        systemDBInfoManager.refreshTableList(systemDBInfoDTO);
        return DCResultUtil.success();
    }

    /**
     * 解析dburl
     */
    @Override
    public Result<DynamicDBInfo> resolveDBUrl(SystemDBInfoDTO systemDBInfoDTO) {
        if (DCObjectUtil.isNull(systemDBInfoDTO.getDbUrl())) {
            return DCResultUtil.error("DBUrl不能为空");
        }
        DynamicDBInfo dynamicDBInfo = ConnectionPoolUtil.resolveDBUrl(systemDBInfoDTO.getDbUrl());
        return DCResultUtil.success(dynamicDBInfo);
    }

    @Override
    public Result<com.litian.dancechar.framework.common.util.page.PageResp<SystemDBInfoDTO>> listPage(SystemDBInfoQueryReqDTO req) {
        PageResp<SystemDBInfoDTO> pageResp = systemDBInfoManager.listPage(req);
        return DCResultUtil.success(pageResp);
    }

    @Override
    public Result<List<SystemDBInfoDTO>> listAllSystemDB(SystemDBInfoReqDTO systemDBInfoReqDTO) {
        //单功能选择工程后获取数据库
        if(systemDBInfoReqDTO != null && DCObjectUtil.isNotEmpty(systemDBInfoReqDTO.getScaffoldType())
                && systemDBInfoReqDTO.getScaffoldType() != 1 && DCObjectUtil.isNotEmpty(systemDBInfoReqDTO.getSystemInfoId())){
            //先获取工程所在的工程信息
            List<ScaffoldGenInfoDO> genInfoDOList = scaffoldGenInfoManager.getGenInfoListBySystemInfoId(systemDBInfoReqDTO.getSystemInfoId());
            if(DCCollectionUtil.isNotEmpty(genInfoDOList)){
                //根据工程信息获取对应的数据库信息
                List<SystemDBInfoDTO> list = scaffoldGenDbInfoManager.listByGenInfoId(genInfoDOList.get(0).getId());
                if(DCCollectionUtil.isNotEmpty(list)){
                    return DCResultUtil.success(list);
                }
            }
        }
        return DCResultUtil.success(systemDBInfoManager.listAllSystemDB());
    }

    @Override
    public Result<SystemDBInfoDTO> save(SystemDBInfoDTO systemDBInfoDTO) {
        return DCResultUtil.success(systemDBInfoManager.save(systemDBInfoDTO));
    }

    @Override
    public Result<SystemDBInfoDTO> saveOrUpdate(SystemDBInfoDTO systemDBInfoDTO) {
        systemDBInfoDTO.setCreateUser(ContextHoldUtil.getEmpNum());
        systemDBInfoDTO.setUpdateUser(ContextHoldUtil.getEmpNum());
        return DCResultUtil.success(systemDBInfoManager.saveOrUpdate(systemDBInfoDTO));
    }

    @Override
    public Result<Boolean> update(SystemDBInfoDTO systemDBInfoDTO) {
        if (DCObjectUtil.isNull(systemDBInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(systemDBInfoManager.update(systemDBInfoDTO));
    }

    @Override
    public Result<Boolean> deleteById(SystemDBInfoDTO systemDBInfoDTO) {
        if (DCObjectUtil.isNull(systemDBInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(systemDBInfoManager.deleteById(systemDBInfoDTO));
    }

    @Override
    public Result<SystemDBInfoDTO> getById(SystemDBInfoDTO systemDBInfoDTO) {
        if (DCObjectUtil.isNull(systemDBInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(systemDBInfoManager.getById(systemDBInfoDTO.getId()));
    }

    /**
     * 批量获取表的字段
     */
    @Override
    public Result<Map<String, List<InformationSchemaTableColumn>>> batchSelectColumns(SystemTableInfoDTO systemTableInfoDTO) {
        // 根据库名查询 所有库表缓存
        if (systemTableInfoDTO.getSystemDbId() == null || DCStrUtil.isBlank(systemTableInfoDTO.getTableName())) {
            return DCResultUtil.error("必传参数不能为空");
        }
        SystemDBInfoDTO systemDBInfoDTO = systemDBInfoManager.getById(systemTableInfoDTO.getSystemDbId());
        DynamicDBInfo dynamicDBInfo = DynamicDBInfo.builder()
                .dbName(systemDBInfoDTO.getDbName())
                .tableName(systemTableInfoDTO.getTableName())
                .build().initDBInfo(systemDBInfoDTO.getDbUrl(), systemDBInfoDTO.getDbUsername(), systemDBInfoDTO.getDbPassword());
        List<InformationSchemaTableColumn> columnList;
        if (dynamicDBInfo.getDbUrl().contains(SystemDBInfoEnums.DriverEnum.ORACLE.getCode())) {
            //获取oracle表字段信息
            columnList = dynamicDBManager.batchSelectOracleColumns(dynamicDBInfo);
            Map<String, List<InformationSchemaTableColumn>> columnMap = columnList.stream().collect(Collectors.groupingBy(InformationSchemaTableColumn::getTableName));
            //获取oracle主键，一个表可能会有多个，比如联合主键
            List<InformationSchemaTableColumn>  primaryKeyList = dynamicDBManager.batchSelectOraclePrimaryKeys(dynamicDBInfo);
            if(DCCollectionUtil.isEmpty(primaryKeyList)){
                return DCResultUtil.success(columnMap);
            }
            Map<String,List<InformationSchemaTableColumn>> priMap = primaryKeyList.stream().collect(Collectors.groupingBy(InformationSchemaTableColumn::getTableName));
            columnMap.keySet().forEach(mapKey->{
                List<InformationSchemaTableColumn> columnTempList = columnMap.get(mapKey);
                List<InformationSchemaTableColumn> priTempList = priMap.get(mapKey);
                //获取每个表的主键列表，判断当前列是否是主键
                List<String> keys = priTempList.stream().map(InformationSchemaTableColumn::getColumnName).collect(Collectors.toList());
                columnTempList.forEach(column->{
                    if(keys.contains(column.getColumnName())){
                        column.setColumnKey("PRI");
                    }
                });
            });
            return DCResultUtil.success(columnMap);
        } else {
            // 数据选项不为空则选择获取db表字段
            columnList = dynamicDBManager.batchSelectColumns(dynamicDBInfo);
            return DCResultUtil.success(columnList.stream().collect(Collectors.groupingBy(InformationSchemaTableColumn::getTableName)));
        }
    }
}
