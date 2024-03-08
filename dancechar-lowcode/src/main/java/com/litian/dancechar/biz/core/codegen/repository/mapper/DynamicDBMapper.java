package com.litian.dancechar.biz.core.codegen.repository.mapper;//package com.sf.cemp.fcode.biz.core.codegen.repository.mapper;
//
//import com.sf.cemp.fcode.biz.core.codegen.dto.InforMationColumnsResult;
//import com.sf.cemp.fcode.biz.core.codegen.dto.InformationResult;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//import java.util.List;
//
///**
// * 动态连接池
// * @author terryhl
// * @date 2021年06月11日8:04:37
// */
//@Mapper
//public interface DynamicDBMapper {
//
//
//    List<String> listDB();
//
//    void useDB(@Param("dbName") String dbName);
//
//    /**
//     * 查询指定库中所有表
//     */
//    List<InformationResult> selectInformationTable(@Param("dbName") String dbName);
//
//    /**
//     * 查询指定表中所有字段
//     */
//    List<InforMationColumnsResult> selectInformationColumns(@Param("dbName") String dbName, @Param("tableName") String tableName);
//}
