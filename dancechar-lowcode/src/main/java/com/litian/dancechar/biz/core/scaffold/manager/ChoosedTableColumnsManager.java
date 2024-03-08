package com.litian.dancechar.biz.core.scaffold.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.litian.dancechar.biz.core.scaffold.dto.ChoosedTableColumnsDTO;
import com.litian.dancechar.biz.core.scaffold.dto.ChoosedTableColumnsReqDTO;
import com.litian.dancechar.biz.core.scaffold.dto.FunctionGenSqlDTO;
import com.litian.dancechar.biz.core.scaffold.repository.dataobject.ChoosedTableColumnsDO;
import com.litian.dancechar.biz.core.scaffold.repository.mapper.ChoosedTableColumnsMapper;
import com.litian.dancechar.biz.sysmgr.dbmgr.dto.SystemDBInfoDTO;
import com.litian.dancechar.biz.sysmgr.dbmgr.manager.SystemDBInfoManager;
import com.litian.dancechar.framework.common.exception.FcodeResponseResultCodeEnum;
import com.litian.dancechar.common.common.jdbc.DbConfig;
import com.litian.dancechar.common.common.jdbc.JdbcUtil;
import com.litian.dancechar.framework.common.exception.BusinessException;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCCollectionUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 类描述：已选择字段处理的Manager
 *
 * @author 01396106
 * @date 2021/08/04 17:08
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class ChoosedTableColumnsManager extends ServiceImpl<ChoosedTableColumnsMapper, ChoosedTableColumnsDO> {
    @Resource
    private SystemDBInfoManager systemDBInfoManager;
    @Resource
    private FunctionGenSqlManager functionGenSqlManager;


    public List<ChoosedTableColumnsDO> getColumnByGenId(Long genInfoId) {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.eq("scaffold_gen_info_id", genInfoId);
        queryWrapper.eq("delete_flag", 0);
        return baseMapper.selectList(queryWrapper);
    }

    public List<ChoosedTableColumnsDO> getColumnByGenIdAndTable(Long genInfoId, String tableName) {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.eq("scaffold_gen_info_id", genInfoId);
        queryWrapper.eq("table_name", tableName);
        queryWrapper.eq("delete_flag", 0);
        return baseMapper.selectList(queryWrapper);
    }

    public Boolean saveBatchChoosedTable(List<ChoosedTableColumnsDTO> columnDTOList) {
        List<ChoosedTableColumnsDO> list = getColumnByGenId(columnDTOList.get(0).getScaffoldGenInfoId());
        if (DCCollectionUtil.isNotEmpty(list)) {
            List<Long> ids = list.stream().map(ChoosedTableColumnsDO::getId).collect(Collectors.toList());
            baseMapper.deleteBatchIds(ids);
        }
        List<ChoosedTableColumnsDO> columnDOList = DCBeanUtil.copyList(columnDTOList, ChoosedTableColumnsDO.class);
        return saveBatch(columnDOList);
    }

    public Boolean saveChoosedTable(ChoosedTableColumnsReqDTO choosedTableColumnsReqDTO) {
        String sql = previewSql(choosedTableColumnsReqDTO.getGenSqlDTO());
        choosedTableColumnsReqDTO.getGenSqlDTO().setPreviewSql(sql);
        baseMapper.deleteByGenInfoId(choosedTableColumnsReqDTO.getScaffoldGenInfoId());
        setAliasName(choosedTableColumnsReqDTO.getColumnsList());
        Boolean flag = this.saveBatchChoosedTable(choosedTableColumnsReqDTO.getColumnsList());
        Long sqlId = functionGenSqlManager.saveGenSql(choosedTableColumnsReqDTO.getGenSqlDTO());
        return flag && (sqlId != null);
    }

    public void setAliasName(List<ChoosedTableColumnsDTO> columnsList) {
        Map<String, Long> map = columnsList.stream().collect(Collectors.groupingBy(vo -> vo.getColumnName(), Collectors.counting()));
        List<String> repeatColumn = new ArrayList<>();
        map.keySet().forEach(key -> {
            if (map.get(key) > 1) {
                repeatColumn.add(key);
            }
        });
        List<String> repeatTemp = new ArrayList<>();
        for (ChoosedTableColumnsDTO columnsDO : columnsList) {
            //将重复列名设置为：表名+列名（驼峰方式）
            StringBuilder aliasName = new StringBuilder();
            if (repeatColumn.contains(columnsDO.getColumnName()) && repeatTemp.contains(columnsDO.getColumnName())) {
                String[] tableName = columnsDO.getTableName().split("_");
                for (String name : tableName) {
                    name.toLowerCase();
                    aliasName.append(name.substring(0, 1).toUpperCase()).append(name.substring(1));
                }
                String[] columnName = columnsDO.getColumnName().split("_");
                for (String name : columnName) {
                    aliasName.append(name.substring(0, 1).toUpperCase()).append(name.substring(1));
                }
            } else {
                repeatTemp.add(columnsDO.getColumnName());
                String[] columnName = columnsDO.getColumnName().split("_");
                for (String name : columnName) {
                    aliasName.append(name.substring(0, 1).toUpperCase()).append(name.substring(1));
                }
            }

            columnsDO.setAliasName(aliasName.substring(0, 1).toLowerCase() + aliasName.substring(1));
        }
    }

    public String previewSql(FunctionGenSqlDTO genSqlDTO) {
        SystemDBInfoDTO systemDBInfoDTO = systemDBInfoManager.getById(genSqlDTO.getSystemDbId());
        DbConfig config = DbConfig.builder().driverClass(systemDBInfoDTO.getDbDriver()).port(systemDBInfoDTO.getDbPort()).ip(systemDBInfoDTO.getDbHost())
                .user(systemDBInfoDTO.getDbUsername()).dbName(systemDBInfoDTO.getDbName()).credit(systemDBInfoDTO.getDbPassword()).oracleUrl(systemDBInfoDTO.getDbUrl()).build();
        StringBuilder sb = new StringBuilder();
        //获取所有的列以及列的出现次数
        String[] arrays = genSqlDTO.getResultColumns().split(",");
        List<String> columnList = new ArrayList<>();
        for (String str : arrays) {
            columnList.add(str.split("\\.")[1]);
        }
        StringBuilder columns = new StringBuilder();
        //获取每个字段出现的次数，找出重复出现的列，生成别名
        Map<String, Long> mapCount = columnList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        List<String> repeatColumn = new ArrayList<>();
        for (int i = 0; i < arrays.length; i++) {
            String str = arrays[i];
            StringBuilder temp = new StringBuilder(str);
            StringBuilder aliasName = new StringBuilder();
            //获取重复出现的列，第一个类名驼峰显示，之后的改成表名+列表驼峰显示，没有重复出现的列显示列名驼峰
            if (mapCount.get(str.split("\\.")[1]) > 1 && repeatColumn.contains(str.split("\\.")[1])) {
                //表名驼峰
                String[] tableName = str.split("\\.")[0].split("_");
                for (String name : tableName) {
                    name = name.toLowerCase();
                    aliasName.append(name.substring(0, 1).toUpperCase()).append(name.substring(1));
                }
                //列名驼峰
                String[] columnName = str.split("\\.")[1].split("_");
                for (String name : columnName) {
                    aliasName.append(name.substring(0, 1).toUpperCase()).append(name.substring(1));
                }
            } else {
                repeatColumn.add(str.split("\\.")[1]);
                //列名驼峰
                String[] columnName = str.split("\\.")[1].split("_");
                for (String name : columnName) {
                    aliasName.append(name.substring(0, 1).toUpperCase()).append(name.substring(1));
                }
            }
            temp.append(" as ").append(aliasName.substring(0, 1).toLowerCase()).append(aliasName.substring(1)).append(",");
            columns.append(temp);
        }
        StringBuilder relevanceColumnSb = new StringBuilder();
        List<String> relevanceColumns = Arrays.asList(genSqlDTO.getRelevanceColumns().split("="));
        for(int i = 0 ;i < relevanceColumns.size()-1;i++){
            String relevanceColumn = relevanceColumns.get(i);
            String relevanceColumnNext = relevanceColumns.get(i+1);
            relevanceColumnSb.append(relevanceColumn).append(" = ").append(relevanceColumnNext).append(" and ");
        }
        if(("mysql").equals(systemDBInfoDTO.getDbDriver())){
            sb.append("select ").append(columns.substring(0, columns.length() - 1)).append(" from ").append(genSqlDTO.getTableName()).
                    append(" where ").append(relevanceColumnSb.substring(0,relevanceColumnSb.length()-5)).append(" limit 1");
        }else if("oracle".equals(systemDBInfoDTO.getDbDriver())){
            sb.append("select ").append(columns.substring(0, columns.length() - 1)).append(" from ").append(genSqlDTO.getTableName()).
                    append(" where ").append(relevanceColumnSb.substring(0,relevanceColumnSb.length()-5)).append(" and ROWNUM = 1 ");
        }

        boolean flag = JdbcUtil.validSelectSqlSyntax(config, sb.toString(), Maps.newConcurrentMap());
        if (!flag) {
            throw new BusinessException(FcodeResponseResultCodeEnum.DB_CHECK_FAIL_SQL_ERROR.getMsg());
        }
        return sb.toString();
    }

}
