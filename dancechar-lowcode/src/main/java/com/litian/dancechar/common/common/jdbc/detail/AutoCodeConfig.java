package com.litian.dancechar.common.common.jdbc.detail;

/**
 * 类描述：全局配置
 *
 * @author 01406831
 * @date 2021/09/03 13:17
 */
public class AutoCodeConfig {
    /**
     * 数据库配置
     */
    private DatasourceConfig datasourceConfig;

    /**
     * 全局配置
     */
    private GlobalConfig globalConfig;

    private Bean bean;

    public DatasourceConfig getDatasourceConfig() {
        return datasourceConfig;
    }

    public void setDatasourceConfig(DatasourceConfig datasourceConfig) {
        this.datasourceConfig = datasourceConfig;
    }

    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    public void setGlobalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
    }

    public Bean getBean() {
        return bean;
    }

    public void setBean(Bean bean) {
        this.bean = bean;
    }
}
