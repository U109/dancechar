package com.litian.dancechar.biz.sysmgr.dbmgr.manager;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.litian.dancechar.biz.core.codegen.manager.conn.ConnectionPoolUtil;
import com.litian.dancechar.biz.sysmgr.dbmgr.dto.DynamicDBInfo;
import com.litian.dancechar.framework.common.exception.BusinessException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Objects;

@Component
public class DynamicDBContainer {

    @Resource
    private DynamicRoutingDataSource dynamicRoutingDataSource;

    /**
     * 初始化动态数据源
     * @param dynamicDBInfo
     */
    public void initDynamicDataSource(DynamicDBInfo dynamicDBInfo)throws Exception{
        DataSource dataSource = null;
        try {
            dataSource = dynamicRoutingDataSource.getDataSource(dynamicDBInfo.getDsKey());
        } catch (RuntimeException e) {
            // 严格校验情况不匹配抛错 重新加载
        }
        if(Objects.isNull(dataSource)){
            dataSource = ConnectionPoolUtil.createDataSource(dynamicDBInfo.getDbUrl(), dynamicDBInfo.getUsername(), dynamicDBInfo.getPassword());
            if(Objects.nonNull(dataSource)){
                dynamicRoutingDataSource.addDataSource(dynamicDBInfo.getDsKey(), dataSource);
            }
        }
        if(Objects.isNull(dataSource)){
            throw new BusinessException("无法创建DataSource");
        }
    }
}
