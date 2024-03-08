package com.litian.dancechar.biz.sysmgr.dbmgr.repository.mapper;

import com.litian.dancechar.biz.sysmgr.dbmgr.repository.dataobject.InformationSchemaTable;
import com.litian.dancechar.biz.sysmgr.dbmgr.repository.dataobject.InformationSchemaTableColumn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 动态连接池
 *
 * @author terryhl
 * @date 2021年06月11日8:04:37
 */
@Mapper
public interface DynamicDBMapper {


    List<String> listDB();

    void useDB(@Param("dbName") String dbName);

    /**
     * 查询指定库中所有表
     */
    List<InformationSchemaTable> selectInformationTables(@Param("dbName") String dbName);

    /**
     * 查询指定库中所有表
     */
    List<InformationSchemaTable> selectLikeInformationTables(@Param("dbName") String dbName, @Param("tablePrefix") String tablePrefix);

    /**
     * 查询指定表中所有字段
     */
    List<InformationSchemaTableColumn> selectInformationColumns(@Param("dbName") String dbName, @Param("tableName") String tableName);

    /**
     * 查找mysql
     * @param dbName
     * @param tableName
     * @return
     */
    List<String> selectMysqlPrimaryKeys(@Param("dbName") String dbName, @Param("tableName") String tableName);

//    /**
//     * 查询指定表中所有字段
//     */
//    List<InformationSchemaTableColumn> selectInformationColumns(@Param("dbName") String dbName, @Param("tableNameList") List<String> tableNameList);

    /**
     * 查询多张表的字段
     */
    List<InformationSchemaTableColumn> batchSelectColumns(@Param("dbName") String dbName,@Param("tableNames") List<String> tableNames);
    /**
     * 查询指定库指定表信息
     */
    List<InformationSchemaTable> selectInformationByTables(@Param("dbName") String dbName,@Param("tableNames") List<String> tableNames);

    /**
     * 分页查询指定库中所有表
     */
    List<InformationSchemaTable> selectTablesByPage(@Param("dbName") String dbName,@Param("tableNames") List<String> tableNames,@Param("startPage")Integer startPage,@Param("pageSize")Integer pageSize);

    int selectTablesCount(@Param("dbName") String dbName,@Param("tableNames") List<String> tableNames);

    /**
     * 获取oracle库的所有表
     * @return
     */
    List<InformationSchemaTable> selectOracleTables();

    /**
     * 获取oracle表的字段信息
     * @return
     */
    List<InformationSchemaTableColumn> selectOracleColumns(@Param("tableName") String tableName);

    /**
     * 获取oracle表的主键
     * @return
     */
    List<String> selectOraclePrimaryKeys(@Param("tableName") String tableName);

    /**
     * 获取oracle表的字段信息
     * @return
     */
    List<InformationSchemaTableColumn> batchSelectOracleColumns(@Param("tableNames") List<String> tableNames);

    /**
     * 获取oracle表的主键
     * @return
     */
    List<InformationSchemaTableColumn> batchSelectOraclePrimaryKeys(@Param("tableNames") List<String> tableNames);

}
