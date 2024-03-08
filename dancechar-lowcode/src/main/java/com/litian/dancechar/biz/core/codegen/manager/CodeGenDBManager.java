package com.litian.dancechar.biz.core.codegen.manager;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.litian.dancechar.biz.core.codegen.dto.CodeGenerateDBParam;
import com.litian.dancechar.biz.core.codegen.manager.conn.ConnectionPoolUtil;
import com.litian.dancechar.framework.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Objects;

@Component
public class CodeGenDBManager {

    @Autowired
    private DynamicRoutingDataSource dynamicRoutingDataSource;

    private String initDataSource(CodeGenerateDBParam codeGenerateDBParam){
        String dsKey = ConnectionPoolUtil.getDSKey(codeGenerateDBParam.getDbUrl());
        DataSource dataSource = null;
        try {
            dataSource = dynamicRoutingDataSource.getDataSource(dsKey);
        } catch (RuntimeException e) {
            // 严格校验情况不匹配抛错 重新加载
        }
        if(Objects.isNull(dataSource)){
            dataSource = ConnectionPoolUtil.createDataSource(codeGenerateDBParam.getDbUrl(), codeGenerateDBParam.getUsername(), codeGenerateDBParam.getPassword());
            if(Objects.nonNull(dataSource)){
                dynamicRoutingDataSource.addDataSource(dsKey, dataSource);
            }
        }
        if(Objects.isNull(dataSource)){
            throw new BusinessException("无法创建DataSource");
        }
        return dsKey;
    }

    /**
     * 注入ds
     * @param codeGenerateDBParam
     */
    public String injectDataSource(CodeGenerateDBParam codeGenerateDBParam){
        String dsKey = initDataSource(codeGenerateDBParam);
        DynamicDataSourceContextHolder.push(dsKey);
        return dsKey;
    }
}
