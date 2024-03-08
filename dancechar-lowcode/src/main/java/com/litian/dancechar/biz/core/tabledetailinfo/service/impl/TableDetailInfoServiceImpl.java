package com.litian.dancechar.biz.core.tabledetailinfo.service.impl;

import com.litian.dancechar.biz.core.tabledetailinfo.dto.TableDetailInfoDTO;
import com.litian.dancechar.biz.core.tabledetailinfo.dto.TableDetailInfoQueryReqDTO;
import com.litian.dancechar.biz.core.tabledetailinfo.manager.TableDetailInfoManager;
import com.litian.dancechar.biz.core.tabledetailinfo.service.TableDetailInfoService;
import com.litian.dancechar.biz.core.tplgen.dto.TplGenTableColumnDTO;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCResultUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述：代码生成-表关联字段信息表服务实现
 *
 * @author 853523
 * @date 2021-09-22 10:30:53
 */
@Component
@Slf4j
public class TableDetailInfoServiceImpl implements TableDetailInfoService {
    @Resource
    private TableDetailInfoManager tableDetailInfoManager;

    private static final String ID_TIPS = "id不能为空";

    @Override
    public Result<PageResp<TableDetailInfoDTO>> listPage(TableDetailInfoQueryReqDTO req) {
        PageResp<TableDetailInfoDTO> pageResp = tableDetailInfoManager.listPage(req);
        return DCResultUtil.success(pageResp);
    }

    @Override
    public Result<Boolean> save(List<TableDetailInfoDTO> tableDetailInfos) {
        return DCResultUtil.success(tableDetailInfoManager.insertBatch(tableDetailInfos));
    }

    @Override
    public Result<Boolean> update(List<TableDetailInfoDTO> tableDetailInfos) {
        return DCResultUtil.success(tableDetailInfoManager.updateBatch(tableDetailInfos));
    }

    @Override
    public Result<Boolean> deleteById(TableDetailInfoDTO tableDetailInfoDTO) {
        if (DCObjectUtil.isNull(tableDetailInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(tableDetailInfoManager.deleteById(tableDetailInfoDTO));
    }

    @Override
    public Result<TableDetailInfoDTO> getWrapperById(TableDetailInfoDTO tableDetailInfoDTO) {
        if (DCObjectUtil.isNull(tableDetailInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(tableDetailInfoManager.getWrapperById(tableDetailInfoDTO.getId()));
    }

    @Override
    public Result<List<TplGenTableColumnDTO>> getConfigListByGenInfoId(TableDetailInfoDTO tableDetailInfoDTO){
        if(DCObjectUtil.isEmpty(tableDetailInfoDTO.getScaffoldGenInfoId())){
            return DCResultUtil.error("工程id不能为空");
        }
        return DCResultUtil.success(tableDetailInfoManager.getConfigListByGenInfoId(tableDetailInfoDTO.getScaffoldGenInfoId()));
    }
}
