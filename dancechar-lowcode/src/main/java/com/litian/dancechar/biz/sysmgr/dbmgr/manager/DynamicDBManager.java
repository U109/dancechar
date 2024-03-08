package com.litian.dancechar.biz.sysmgr.dbmgr.manager;

import com.litian.dancechar.biz.sysmgr.dbmgr.dto.DynamicDBInfo;
import com.litian.dancechar.biz.sysmgr.dbmgr.repository.dataobject.InformationSchemaTable;
import com.litian.dancechar.biz.sysmgr.dbmgr.repository.dataobject.InformationSchemaTableColumn;
import com.litian.dancechar.biz.sysmgr.dbmgr.repository.mapper.DynamicDBMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DynamicDBManager {

    @Resource
    private DynamicDBMapper dynamicDBMapper;


    /**
     * 查询数据库所有表
     *
     * @param dynamicDBInfo
     * @return
     */
    public List<String> listDBTable(DynamicDBInfo dynamicDBInfo) {
        // 查询db所有表
        List<InformationSchemaTable> informationResultList = listInformationTable(dynamicDBInfo);
        List<String> tableList = informationResultList.stream().map(informationResult -> informationResult.getTableName()).collect(Collectors.toList());
        return tableList;
    }

    public List<InformationSchemaTable> listInformationTable(DynamicDBInfo dynamicDBInfo) {
        return dynamicDBMapper.selectInformationTables(dynamicDBInfo.getDbName());
    }

    public List<InformationSchemaTable> listLikeInformationTable(String dbName, String tablePrefix) {
        return dynamicDBMapper.selectLikeInformationTables(dbName, tablePrefix);
    }

    public List<String> listDB(DynamicDBInfo dynamicDBInfo) {
        return dynamicDBMapper.listDB();
    }

    public void useDB(DynamicDBInfo dynamicDBInfo) {
        dynamicDBMapper.useDB(dynamicDBInfo.getDbName());
    }

    /**
     * 获取表中所有字段集合
     */
    public List<InformationSchemaTableColumn> getInformationSchemaTableColumnList(DynamicDBInfo dynamicDBInfo) {
        return dynamicDBMapper.selectInformationColumns(dynamicDBInfo.getDbName(), dynamicDBInfo.getTableName());
    }


    /**
     * 获取表中所有字段集合 后续优化 todo 可同库多表查询
     */
    public List<InformationSchemaTableColumn> listTableColumn(DynamicDBInfo dynamicDBInfo) {
        return dynamicDBMapper.selectInformationColumns(dynamicDBInfo.getDbName(), dynamicDBInfo.getTableName());
    }

    public List<InformationSchemaTableColumn> batchSelectColumns(DynamicDBInfo dynamicDBInfo) {
        List<String> tableNames = Arrays.asList(dynamicDBInfo.getTableName().split(","));
        return dynamicDBMapper.batchSelectColumns(dynamicDBInfo.getDbName(), tableNames);
    }

    public List<InformationSchemaTable> selectInformationByTables(DynamicDBInfo dynamicDBInfo){
        List<String> tableNames = Arrays.asList(dynamicDBInfo.getTableName().split(","));
        return dynamicDBMapper.selectInformationByTables(dynamicDBInfo.getDbName(),tableNames);
    }

    public List<InformationSchemaTable> selectTablesByPage(DynamicDBInfo dynamicDBInfo, Integer startPage, Integer pageSize){
        List<String> tableNames = new ArrayList<>();
        if(!StringUtils.isEmpty(dynamicDBInfo.getTableName())){
            tableNames = Arrays.asList(dynamicDBInfo.getTableName().split(","));
        }
        return dynamicDBMapper.selectTablesByPage(dynamicDBInfo.getDbName(),tableNames,startPage,pageSize);
    }

    public int selectTablesCount(DynamicDBInfo dynamicDBInfo){
        List<String> tableNames = new ArrayList<>();
        if(!StringUtils.isEmpty(dynamicDBInfo.getTableName())){
            tableNames = Arrays.asList(dynamicDBInfo.getTableName().split(","));
        }
        return dynamicDBMapper.selectTablesCount(dynamicDBInfo.getDbName(),tableNames);
    }

    public List<InformationSchemaTable> selectOracleTables(DynamicDBInfo dynamicDBInfo){
        return dynamicDBMapper.selectOracleTables();
    }

    public List<InformationSchemaTableColumn> selectOracleColumns(DynamicDBInfo dynamicDBInfo){
        return dynamicDBMapper.selectOracleColumns(dynamicDBInfo.getTableName());
    }

    public List<String> selectOraclePrimaryKeys(DynamicDBInfo dynamicDBInfo){
        return dynamicDBMapper.selectOraclePrimaryKeys(dynamicDBInfo.getTableName());
    }

    public List<InformationSchemaTableColumn> batchSelectOracleColumns(DynamicDBInfo dynamicDBInfo){
        List<String> tableNames = Arrays.asList(dynamicDBInfo.getTableName().split(","));
        return dynamicDBMapper.batchSelectOracleColumns(tableNames);
    }

    public List<InformationSchemaTableColumn> batchSelectOraclePrimaryKeys(DynamicDBInfo dynamicDBInfo){
        List<String> tableNames = Arrays.asList(dynamicDBInfo.getTableName().split(","));
        return dynamicDBMapper.batchSelectOraclePrimaryKeys(tableNames);
    }

    public List<String> selectMysqlPrimaryKeys(DynamicDBInfo dynamicDBInfo){
        return dynamicDBMapper.selectMysqlPrimaryKeys(dynamicDBInfo.getDbName(),dynamicDBInfo.getTableName());
    }
}
