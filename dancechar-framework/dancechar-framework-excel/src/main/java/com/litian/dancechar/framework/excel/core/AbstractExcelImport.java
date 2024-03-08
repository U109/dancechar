package com.litian.dancechar.framework.excel.core;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.litian.dancechar.framework.excel.constants.ExcelConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * excel数据导入监听抽象类(采用模版模式, 百万数据处理方式)
 *
 * @author tojson
 * @date 2022/7/9 11:26
 */
@Slf4j
public abstract class AbstractExcelImport<T> extends AnalysisEventListener<T> {
    /**
     * 自定义用于暂时存储data。
     */
    private List<T> dataList = new ArrayList<>();

    @Override
    public void invoke(T object, AnalysisContext context) {
        //数据存储
        dataList.add(object);
        //百万数据处理方式，达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (dataList.size() >= ExcelConstants.SAVE_DB_MAX_SIZE) {
            saveData();
            log.info("数据量达到{}条,保存内存数据到db", ExcelConstants.SAVE_DB_MAX_SIZE);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("解析excel数据完成！！！");
        saveData();
        log.info("保存内存数据到db");
    }

    /**
     * 保存数据到 DB
     */
    private void saveData() {
        if (dataList.size() > 0) {
            doSaveData(dataList);
            dataList.clear();
        }
    }
    protected abstract void doSaveData(List<T> data);
}
