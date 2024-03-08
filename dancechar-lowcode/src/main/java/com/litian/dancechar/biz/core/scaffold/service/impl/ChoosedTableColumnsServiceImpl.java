package com.litian.dancechar.biz.core.scaffold.service.impl;

import com.litian.dancechar.biz.core.scaffold.dto.ChoosedTableColumnsReqDTO;
import com.litian.dancechar.biz.core.scaffold.dto.FunctionGenSqlDTO;
import com.litian.dancechar.biz.core.scaffold.manager.ChoosedTableColumnsManager;
import com.litian.dancechar.biz.core.scaffold.service.ChoosedTableColumnsService;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.DCCollectionUtil;
import com.litian.dancechar.framework.common.util.DCResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 类描述：已选择的库表字段
 *
 * @author 01410001
 * @date 2021/08/07 18:12
 */
@Component
@Slf4j
public class ChoosedTableColumnsServiceImpl implements ChoosedTableColumnsService {

    @Autowired
    private ChoosedTableColumnsManager choosedTableColumnsManager;

    @Override
    public Result<Boolean> saveChoosedTable(ChoosedTableColumnsReqDTO choosedTableColumnsReqDTO){
        if(DCCollectionUtil.isEmpty(choosedTableColumnsReqDTO.getColumnsList())){
            return DCResultUtil.error("参数不能为空");
        }
        return DCResultUtil.success(choosedTableColumnsManager.saveChoosedTable(choosedTableColumnsReqDTO));
    }

    @Override
    public Result<String> previewSql(FunctionGenSqlDTO genSqlDTO){
        return DCResultUtil.success(choosedTableColumnsManager.previewSql(genSqlDTO));
    }


}
