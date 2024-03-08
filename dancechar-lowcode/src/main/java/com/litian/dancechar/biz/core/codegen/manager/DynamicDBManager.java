package com.litian.dancechar.biz.core.codegen.manager;//package com.sf.cemp.fcode.biz.core.codegen.manager;
//
//import com.sf.cemp.fcode.biz.core.codegen.dto.DynamicDBInfo;
//import com.sf.cemp.fcode.biz.core.codegen.dto.InforMationColumnsResult;
//import com.sf.cemp.fcode.biz.core.codegen.dto.InformationResult;
//import com.sf.cemp.fcode.biz.core.codegen.repository.mapper.DynamicDBMapper;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * 代码生成基础配置service接口实现类
// */
//@Service
//public class DynamicDBManager {
//
//
//    @Resource
//    private DynamicDBMapper dynamicDBMapper;
//
//
//    /**
//     * 查询数据库所有表
//     * @param dynamicDBInfo
//     * @return
//     */
//    public List<String> listDBTable(DynamicDBInfo dynamicDBInfo) {
//            // 查询db所有表
//        List<InformationResult> informationResultList = listInformationTable(dynamicDBInfo);
//        List<String> tableList = informationResultList.stream().map(informationResult -> informationResult.getTableName()).collect(Collectors.toList());
//        return tableList;
//    }
//
//    public List<InformationResult> listInformationTable(DynamicDBInfo dynamicDBInfo) {
//        return dynamicDBMapper.selectInformationTable(dynamicDBInfo.getDbName());
//    }
//
//    public List<String> listDB(DynamicDBInfo dynamicDBInfo) {
//        return dynamicDBMapper.listDB();
//    }
//
//    public void useDB(DynamicDBInfo dynamicDBInfo) {
//        dynamicDBMapper.useDB(dynamicDBInfo.getDbName());
//    }
//
//    /**
//     * 获取表中所有字段集合
//     */
//    public List<InforMationColumnsResult> getInforMationColumnsResultList(DynamicDBInfo dynamicDBInfo) {
//        return dynamicDBMapper.selectInformationColumns(dynamicDBInfo.getDbName(), dynamicDBInfo.getTableName());
//    }
//
//}