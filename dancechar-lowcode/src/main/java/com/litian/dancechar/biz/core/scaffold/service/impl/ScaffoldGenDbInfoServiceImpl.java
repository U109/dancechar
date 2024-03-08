package com.litian.dancechar.biz.core.scaffold.service.impl;

import com.google.common.collect.Lists;
import com.litian.dancechar.biz.core.scaffold.dto.*;
import com.litian.dancechar.biz.core.scaffold.manager.ScaffoldGenDbExampleInfoManager;
import com.litian.dancechar.biz.core.scaffold.manager.ScaffoldGenDbInfoManager;
import com.litian.dancechar.biz.core.scaffold.manager.ScaffoldGenInfoManager;
import com.litian.dancechar.biz.core.scaffold.service.ScaffoldGenDbInfoService;
import com.litian.dancechar.biz.sysmgr.dbmgr.dto.SystemDBInfoDTO;
import com.litian.dancechar.biz.sysmgr.dbmgr.dto.SystemDBInfoQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.dbmgr.dto.SystemTableInfoDTO;
import com.litian.dancechar.biz.sysmgr.dbmgr.manager.SystemDBInfoManager;
import com.litian.dancechar.biz.sysmgr.dbmgr.repository.dataobject.InformationSchemaTable;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.DCCollectionUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCResultUtil;
import com.litian.dancechar.framework.common.util.DCStrUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 脚手架生成-数据库信息(ScaffoldGenDbInfo)服务实现
 *
 * @author 01406831
 * @since 2021-06-21 14:33:21
 */
@Component
@Slf4j
public class ScaffoldGenDbInfoServiceImpl implements ScaffoldGenDbInfoService {
    @Resource
    private ScaffoldGenDbInfoManager scaffoldGenDbInfoManager;
    @Resource
    private ScaffoldGenDbExampleInfoManager scaffoldGenDbExampleInfoManager;
    @Resource
    private SystemDBInfoManager systemDBInfoManager;
    @Resource
    private ScaffoldGenInfoManager scaffoldGenInfoManager;

    private static final String ID_TIPS = "id不能为空";

    @Override
    public Result<PageResp<ScaffoldGenDbInfoDTO>> listPage(ScaffoldGenDbInfoQueryReqDTO req) {
        if (DCObjectUtil.isNull(req.getScaffoldGenInfoId())) {
            return DCResultUtil.error("scaffoldGenInfoId不能为空");
        }
        PageResp<ScaffoldGenDbInfoDTO> scaffoldGenDbInfoDTOPageResp =
                scaffoldGenDbInfoManager.listPage(req);
        scaffoldGenDbInfoDTOPageResp.getList().forEach(scaffoldGenDbInfoDTO -> {
            //1、根据scaffold_gen_db_info_id查询fcode_scaffold_gen_db_example_info信息
            ScaffoldGenDbExampleInfoQueryReqDTO scaffoldGenDbExampleInfoQueryReqDTO = new ScaffoldGenDbExampleInfoQueryReqDTO();
            scaffoldGenDbExampleInfoQueryReqDTO.setScaffoldGenDbInfoId(scaffoldGenDbInfoDTO.getId());
            scaffoldGenDbInfoDTO.setScaffoldGenDbExampleInfoList(scaffoldGenDbExampleInfoManager.findList(scaffoldGenDbExampleInfoQueryReqDTO));
        });
        return DCResultUtil.success(scaffoldGenDbInfoDTOPageResp);
    }

    @Override
    public Result<ChoosedTableColumnsRespDTO> save(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO) {
        SystemDBInfoDTO systemDBInfoDTO = systemDBInfoManager.getById(scaffoldGenDbInfoDTO.getSystemDbId());
        boolean checkConnection = systemDBInfoManager.checkConnection(systemDBInfoDTO);
        if (!checkConnection) {
            return DCResultUtil.error("数据库配置信息错误，请检查!");
        }
        return DCResultUtil.success(scaffoldGenDbInfoManager.save(scaffoldGenDbInfoDTO,systemDBInfoDTO));
    }

    @Override
    public Result<Boolean> deleteById(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO) {
        if (DCObjectUtil.isNull(scaffoldGenDbInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(scaffoldGenDbInfoManager.deleteById(scaffoldGenDbInfoDTO));
    }

    @Override
    public Result<ScaffoldGenDbInfoDTO> getById(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO) {
        if (DCObjectUtil.isNull(scaffoldGenDbInfoDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(scaffoldGenDbInfoManager.getById(scaffoldGenDbInfoDTO.getId()));
    }

    @Override
    public Result<String> checkDBConnection(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO) {
        boolean checkConnection = systemDBInfoManager.checkConnection(
                scaffoldGenDbInfoManager.convertScaffoldGenDbToSystemDBInfoDTO(scaffoldGenDbInfoDTO));
        if (checkConnection) {
            return DCResultUtil.success("db连接测试成功");
        } else {
            return DCResultUtil.error("db连接测试失败");
        }
    }

    @Override
    public Result<List<String>> listDBTable(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO) {
        List<String> tableList = systemDBInfoManager.listDBTable(scaffoldGenDbInfoManager
                .convertScaffoldGenDbToSystemDBInfoDTO(scaffoldGenDbInfoDTO));
        if (DCStrUtil.isNotEmpty(scaffoldGenDbInfoDTO.getTablePrefix())) {
            return DCResultUtil.success(tableList.stream()
                    .filter(vo -> vo.contains(scaffoldGenDbInfoDTO.getTablePrefix())).collect(Collectors.toList()));
        }
        return DCResultUtil.success(tableList);
    }

    @Override
    public Result<List<ScaffoldGenDbInfoDTO>> listByDBName(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO) {
        SystemDBInfoQueryReqDTO systemDbInfoDTO = new SystemDBInfoQueryReqDTO();
        systemDbInfoDTO.setDbName(scaffoldGenDbInfoDTO.getDbName());
        List<SystemDBInfoDTO> sysDbInfoList = systemDBInfoManager.listByDBName(systemDbInfoDTO);
        if (DCCollectionUtil.isNotEmpty(sysDbInfoList)) {
            List<ScaffoldGenDbInfoDTO> result = Lists.newArrayList();
            sysDbInfoList.stream().forEach(vo -> {
                ScaffoldGenDbInfoDTO scaffoldGenDbInfoOne = new ScaffoldGenDbInfoDTO();
                scaffoldGenDbInfoOne.setDriverClass(vo.getDbDriver());
                scaffoldGenDbInfoOne.setIpPort(vo.getDbHost() + "/" + vo.getDbPort());
                scaffoldGenDbInfoOne.setUserName(vo.getDbUsername());
                scaffoldGenDbInfoOne.setPassword(vo.getDbPassword());
                scaffoldGenDbInfoOne.setDbName(vo.getDbName());
                scaffoldGenDbInfoOne.setId(vo.getId());
                result.add(scaffoldGenDbInfoOne);
            });
            return DCResultUtil.success(result);
        }
        return DCResultUtil.success(null);
    }

    @Override
    public Result<List<InformationSchemaTable>> listTablesComment(SystemTableInfoDTO systemTableInfoDTO) {
        if (systemTableInfoDTO.getSystemDbId() == null || DCStrUtil.isBlank(systemTableInfoDTO.getTableName())) {
            return DCResultUtil.error("必传参数不能为空");
        }
        SystemDBInfoDTO systemDBInfoDTO = systemDBInfoManager.getById(systemTableInfoDTO.getSystemDbId());
        return DCResultUtil.success(systemDBInfoManager.listDBTableComment(systemTableInfoDTO.getTableName(), systemDBInfoDTO));
    }

    @Override
    public Result<PageResp<TableInfoRespDTO>> pageListTables(SystemTableInfoDTO systemTableInfoDTO) {
        if (systemTableInfoDTO.getSystemDbId() == null) {
            return DCResultUtil.success(new PageResp());
        }
        SystemDBInfoDTO systemDBInfoDTO = systemDBInfoManager.getById(systemTableInfoDTO.getSystemDbId());
        return DCResultUtil.success(systemDBInfoManager.pageListDBTable(systemTableInfoDTO.getPageNo(),
                systemTableInfoDTO.getPageSize(), systemTableInfoDTO.getTableName(), systemDBInfoDTO));
    }

    @Override
    public Result<List<TableInfoRespDTO>> listTables(SystemTableInfoDTO systemTableInfoDTO) {
        if (systemTableInfoDTO.getSystemDbId() == null) {
            return DCResultUtil.error("systemDbId不能为空");
        }
        SystemDBInfoDTO systemDBInfoDTO = systemDBInfoManager.getById(systemTableInfoDTO.getSystemDbId());
        return DCResultUtil.success(systemDBInfoManager.listTableInfo(systemDBInfoDTO));
    }

    @Override
    public Result<ScaffoldMultiTableRespDTO> getDbInfoByGenInfoId(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO) {
        if (DCObjectUtil.isEmpty(scaffoldGenDbInfoDTO.getScaffoldGenInfoId())) {
            return DCResultUtil.error("scaffoldGenInfoId不能为空");
        }
        return DCResultUtil.success(scaffoldGenDbInfoManager.getDbInfoByGenInfoId(scaffoldGenDbInfoDTO.getScaffoldGenInfoId()));
    }


    @Override
    public Result<List<SystemDBInfoDTO>> listByGenInfoId(ScaffoldGenDbInfoDTO scaffoldGenDbInfoDTO) {
        if (DCObjectUtil.isEmpty(scaffoldGenDbInfoDTO.getScaffoldGenInfoId())) {
            return DCResultUtil.error("scaffoldGenInfoId不能为空");
        }
        return DCResultUtil.success(scaffoldGenDbInfoManager.listByGenInfoId(scaffoldGenDbInfoDTO.getScaffoldGenInfoId()));
    }

    @Override
    public Result<List<ChoosedTableColumnsRespDTO>> saveBatchTable(ScaffoldMultiTableDbDTO scaffoldMultiTableDbDTO){


        SystemDBInfoDTO systemDBInfoDTO = systemDBInfoManager.getById(scaffoldMultiTableDbDTO.getSystemDbId());
        boolean checkConnection = systemDBInfoManager.checkConnection(systemDBInfoDTO);
        if (!checkConnection) {
            return DCResultUtil.error("数据库配置信息错误，请检查!");
        }
        return DCResultUtil.success(scaffoldGenDbInfoManager.saveBatchTable(scaffoldMultiTableDbDTO,systemDBInfoDTO));
    }

    @Override
    public Result<List<ScaffoldMultiTableRespDTO>> getBatchDbInfoByGenInfoId(ScaffoldMultiTableReqDTO multiTableReqDTO) {
        if (DCObjectUtil.isEmpty(multiTableReqDTO.getIds())) {
            return DCResultUtil.error("工程id不能为空");
        }
        List<ScaffoldMultiTableRespDTO> dbInfoList = new ArrayList<>();
        multiTableReqDTO.getIds().forEach(id->{
            dbInfoList.add(scaffoldGenDbInfoManager.getDbInfoByGenInfoId(id));
        });
        return DCResultUtil.success(dbInfoList);
    }



}
