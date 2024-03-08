package com.litian.dancechar.biz.sysmgr.dbmgr.manager;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.litian.dancechar.biz.core.codegen.common.constants.CommonConstant;
import com.litian.dancechar.biz.core.codegen.manager.conn.ConnectionPoolUtil;
import com.litian.dancechar.biz.core.scaffold.dto.TableInfoRespDTO;
import com.litian.dancechar.biz.sysmgr.dbmgr.common.enums.SystemDBInfoEnums;
import com.litian.dancechar.biz.sysmgr.dbmgr.dto.*;
import com.litian.dancechar.biz.sysmgr.dbmgr.repository.dataobject.InformationSchemaTable;
import com.litian.dancechar.biz.sysmgr.dbmgr.repository.dataobject.SystemDBInfoDO;
import com.litian.dancechar.biz.sysmgr.dbmgr.repository.mapper.SystemDBInfoMapper;
import com.litian.dancechar.common.common.dto.BaseParam;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import com.litian.dancechar.framework.common.exception.BizException;
import com.litian.dancechar.framework.common.exception.FcodeResponseResultCodeEnum;
import com.litian.dancechar.common.common.util.JasyptUtils;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCCollectionUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCStrUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.util.page.PageRespUtil;
import com.litian.dancechar.framework.common.validator.DCValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 类描述：systemDBInfo manager
 *
 * @author terryhl
 * @date 2021-06-21 11:48:37
 */
@Component
@Slf4j
public class SystemDBInfoManager extends ServiceImpl<SystemDBInfoMapper, SystemDBInfoDO> {
    @Resource
    private DynamicDBContainer dynamicDBContainer;
    @Resource
    private DynamicDBManager dynamicDBManager;
    @Resource
    private RedisHelper sfRedisUtil;
    @Resource
    private SystemDBInfoMapper systemDBInfoMapper;

    /**
     * 刷新表
     */
    public void refreshTableList(SystemDBInfoDTO systemDBInfoDTO) {
        String dsKey = ConnectionPoolUtil.getDSKey(systemDBInfoDTO.getDbUrl());
        sfRedisUtil.remove(dsKey);
    }

    /**
     * 批量根据Id查询列表
     */
    public List<SystemDBInfoDTO> listByIds(List<Long> idList) {
        QueryWrapper<SystemDBInfoDO> queryWrapper = Wrappers.query();
        queryWrapper.in("id", idList);
        queryWrapper.eq("delete_flag", 0);
        return DCBeanUtil.copyList(baseMapper.selectList(queryWrapper), SystemDBInfoDTO.class);
    }

    /**
     * 查询数据库所有表
     */
    public List<String> listDBTable(SystemDBInfoDTO systemDBInfoDTO) {
        DCValidatorUtil.validateModel(systemDBInfoDTO, BaseParam.add.class);
        DynamicDBInfo dynamicDBInfo = DynamicDBInfo.builder().build().initDBInfo(systemDBInfoDTO.getDbUrl(), systemDBInfoDTO.getDbUsername(), systemDBInfoDTO.getDbPassword());
        String dsKey = dynamicDBInfo.getDsKey();
        List<String> tableList = sfRedisUtil.getList(dsKey, String.class);
        // 查询本地缓存
        if (DCCollectionUtil.isEmpty(tableList)) {
            // 查询db所有表
            List<InformationSchemaTable> informationResultList = dynamicDBManager.listInformationTable(dynamicDBInfo);
            if (DCCollectionUtil.isEmpty(informationResultList)) {
                return Lists.newArrayList();
            }
            tableList = informationResultList.stream().map(informationResult -> informationResult.getTableName()).collect(Collectors.toList());
            sfRedisUtil.set(dsKey, tableList, 900L);
        }
        return tableList;
    }


    /**
     * 查询数据库表中的字段和描述
     */
    public List<DbColumnsDTO> listDBTableColumns(DbColumnsQueryDTO dbColumnsQueryDTO) {
        DCValidatorUtil.validateModel(dbColumnsQueryDTO, BaseParam.add.class);
        // 查询本地缓存
        List<DbColumnsDTO> tableList = sfRedisUtil.getList(dbColumnsQueryDTO.getTableSchema() + dbColumnsQueryDTO.getTableName(), DbColumnsDTO.class);
        if (DCCollectionUtil.isEmpty(tableList)) {
            // 查询db所有表
            tableList = systemDBInfoMapper.listDBTableColumns(dbColumnsQueryDTO);
            if (DCCollectionUtil.isEmpty(tableList)) {
                return Lists.newArrayList();
            }
            sfRedisUtil.set(dbColumnsQueryDTO.getTableSchema() + dbColumnsQueryDTO.getTableName(), tableList, 900L);
        }
        return tableList;
    }

    /**
     * 功能描述: 根据数据库名、表前缀查询表列表
     */
    public List<String> listDBTableByTablePrefix(String dbName, String tablePrefix) {
        List<InformationSchemaTable> isList = dynamicDBManager.listLikeInformationTable(dbName, tablePrefix);
        if (DCCollectionUtil.isNotEmpty(isList)) {
            return isList.stream().map(informationResult -> informationResult.getTableName()).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    /**
     * test connection
     *
     * @param systemDBInfoDTO
     */
    public boolean checkConnection(SystemDBInfoDTO systemDBInfoDTO) {
        try {
            DCValidatorUtil.validateModel(systemDBInfoDTO, BaseParam.add.class);
            DynamicDBInfo dynamicDBInfo = DynamicDBInfo.builder().build().initDBInfo(systemDBInfoDTO.getDbUrl(), systemDBInfoDTO.getDbUsername(), systemDBInfoDTO.getDbPassword());
            //            Class.forName("com.mysql.jdbc.Driver");
            if (dynamicDBInfo.getDbUrl().contains(SystemDBInfoEnums.DriverEnum.ORACLE.getCode())) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } else {
                Class.forName("com.mysql.jdbc.Driver");
            }
            Connection conn = DriverManager.getConnection(dynamicDBInfo.getDbUrl(), dynamicDBInfo.getUsername(), dynamicDBInfo.getPassword());
            IoUtil.close(conn);
            dynamicDBContainer.initDynamicDataSource(dynamicDBInfo);
            return true;
        } catch (Exception e) {
            log.error("checkDBConnection 检查数据库链接系统异常! errMsg:{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 功能：根据dbname查询db信息
     */
    public List<SystemDBInfoDTO> listByDBName(SystemDBInfoQueryReqDTO systemDBInfoQueryReqDTO) {
        if (StringUtils.isBlank(systemDBInfoQueryReqDTO.getDbName())) {
            throw new BizException(FcodeResponseResultCodeEnum.ILLEGAL_ARGUMENT_WITH_MESSAGE, "dbName不能为空");
        }
        QueryWrapper<SystemDBInfoDO> queryWrapper = Wrappers.query();
        if (DCStrUtil.isNotEmpty(systemDBInfoQueryReqDTO.getDbName())) {
            queryWrapper.like("db_name", "%" + systemDBInfoQueryReqDTO.getDbName() + "%");
        }
        List<SystemDBInfoDO> list = getBaseMapper().selectList(queryWrapper);
        List<SystemDBInfoDTO> systemDBInfoDTOList = DCBeanUtil.copyList(list, SystemDBInfoDTO.class);
        if (DCCollectionUtil.isEmpty(systemDBInfoDTOList)) {
            return null;
        }
        for (SystemDBInfoDTO systemDBInfoDTO : systemDBInfoDTOList) {
            String username = null;
            String password = null;
            try {
                username = JasyptUtils.decyptPwd(JasyptUtils.JASYPT_KEY, systemDBInfoDTO.getDbUsername());
                password = JasyptUtils.decyptPwd(JasyptUtils.JASYPT_KEY, systemDBInfoDTO.getDbPassword());
                systemDBInfoDTO.setDbUsername(username);
                systemDBInfoDTO.setDbPassword(password);
            } catch (Exception e) {
                log.error("systemDBInfoDTO.dbName={} decrypt fail!", systemDBInfoDTO.getDbName());
                throw new BizException(FcodeResponseResultCodeEnum.DECRYPT_FAIL);
            }
        }
        return systemDBInfoDTOList;
    }

    /**
     * 功能：增加或保存数据库信息
     */
    public SystemDBInfoDTO saveOrUpdate(SystemDBInfoDTO systemDBInfoDTO) {
        // 校验入参
        DCValidatorUtil.validateModel(systemDBInfoDTO, BaseParam.add.class);
        // 校验信息 尝试看是否能创建连接
        boolean checkFlag = checkConnection(systemDBInfoDTO);
        if (!checkFlag) {
            throw new BizException(FcodeResponseResultCodeEnum.DB_CHECK_FAIL);
        }
        // 查询是否已有db信息 有则更新 无则保存
        QueryWrapper<SystemDBInfoDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("db_host", systemDBInfoDTO.getDbHost());
        queryWrapper.eq("db_port", systemDBInfoDTO.getDbPort());
        queryWrapper.eq("db_name", systemDBInfoDTO.getDbName());
        queryWrapper.eq("delete_flag", CommonConstant.DELETE_FLAG_N);
        SystemDBInfoDO systemDBInfoDO = baseMapper.selectOne(queryWrapper);
        // 用户名密码加密
        String username = null;
        String password = null;
        try {
            username = JasyptUtils.encryptPwd(JasyptUtils.JASYPT_KEY, systemDBInfoDTO.getDbUsername());
            password = JasyptUtils.encryptPwd(JasyptUtils.JASYPT_KEY, systemDBInfoDTO.getDbPassword());
        } catch (Exception e) {
            throw new BizException(FcodeResponseResultCodeEnum.ENCRYPT_FAIL);
        }
        if (systemDBInfoDTO.getDbDesc() == null) {
            systemDBInfoDTO.setDbDesc("");
        }
        if (Objects.isNull(systemDBInfoDO)) {
            systemDBInfoDO = SystemDBInfoDO.builder()
                    .dbUrl(systemDBInfoDTO.getDbUrl())
                    .dbDriver(systemDBInfoDTO.getDbDriver())
                    .dbHost(systemDBInfoDTO.getDbHost())
                    .dbPort(systemDBInfoDTO.getDbPort())
                    .dbName(systemDBInfoDTO.getDbName())
                    .dbUsername(username)
                    .dbPassword(password)
                    .dbDesc(systemDBInfoDTO.getDbDesc())
                    .build();
            systemDBInfoDO.setCreateUser(systemDBInfoDTO.getCreateUser());
            save(systemDBInfoDO);
        } else {
            lambdaUpdate().eq(SystemDBInfoDO::getId, systemDBInfoDO.getId())
                    .set(SystemDBInfoDO::getDbUrl, systemDBInfoDTO.getDbUrl())
                    .set(SystemDBInfoDO::getDbHost, systemDBInfoDTO.getDbHost())
                    .set(SystemDBInfoDO::getDbPort, systemDBInfoDTO.getDbPort())
                    .set(SystemDBInfoDO::getDbDriver, systemDBInfoDTO.getDbDriver())
                    .set(SystemDBInfoDO::getDbName, systemDBInfoDTO.getDbName())
                    .set(SystemDBInfoDO::getDbUsername, username)
                    .set(SystemDBInfoDO::getDbPassword, password)
                    .set(SystemDBInfoDO::getDbDesc, systemDBInfoDTO.getDbDesc())
                    .set(SystemDBInfoDO::getUpdateUser, systemDBInfoDTO.getUpdateUser())
                    .update();
        }
        return DCBeanUtil.copyNotNull(systemDBInfoDO, new SystemDBInfoDTO());
    }

    /**
     * 功能：分页查询列表记录 列出所有的数据库信息
     */
    public List<SystemDBInfoDTO> listAllSystemDB() {
        QueryWrapper<SystemDBInfoDO> queryWrapper = Wrappers.query();
        List<SystemDBInfoDO> list = getBaseMapper().selectList(queryWrapper);
        List<SystemDBInfoDTO> systemDBInfoDTOList = DCBeanUtil.copyList(list, SystemDBInfoDTO.class);
        for (SystemDBInfoDTO systemDBInfoDTO : systemDBInfoDTOList) {
            String username = null;
            String password = null;
            try {
                username = JasyptUtils.decyptPwd(JasyptUtils.JASYPT_KEY, systemDBInfoDTO.getDbUsername());
                password = JasyptUtils.decyptPwd(JasyptUtils.JASYPT_KEY, systemDBInfoDTO.getDbPassword());
                systemDBInfoDTO.setDbUsername(username);
                systemDBInfoDTO.setDbPassword(password);
            } catch (Exception e) {
                log.error("systemDBInfoDTO.dbName={} decrypt fail!", systemDBInfoDTO.getDbName());
                throw new BizException(FcodeResponseResultCodeEnum.DECRYPT_FAIL);
            }
        }
        return systemDBInfoDTOList;
    }

    /**
     * 功能：分页查询列表记录 列出所有的数据库信息
     */
    public PageResp<SystemDBInfoDTO> listPage(SystemDBInfoQueryReqDTO systemDBInfoQueryReqDTO) {
        PageHelper.startPage(systemDBInfoQueryReqDTO.getPageNo(), systemDBInfoQueryReqDTO.getPageSize());
        QueryWrapper<SystemDBInfoDO> queryWrapper = Wrappers.query();
        queryWrapper.orderByDesc("create_date");
        if (ObjectUtil.isNotNull(systemDBInfoQueryReqDTO.getDbName())) {
            queryWrapper.like("db_name", systemDBInfoQueryReqDTO.getDbName());
        }
        List<SystemDBInfoDO> list = getBaseMapper().selectList(queryWrapper);
        for (SystemDBInfoDO systemDBInfoDO : list) {
            String username = null;
            String password = null;
            try {
                username = JasyptUtils.decyptPwd(JasyptUtils.JASYPT_KEY, systemDBInfoDO.getDbUsername());
                password = JasyptUtils.decyptPwd(JasyptUtils.JASYPT_KEY, systemDBInfoDO.getDbPassword());
                systemDBInfoDO.setDbUsername(username);
                systemDBInfoDO.setDbPassword(password);
            } catch (Exception e) {
                log.error("systemDBInfoDO.dbName={} decrypt fail!", systemDBInfoDO.getDbName());
                throw new BizException(FcodeResponseResultCodeEnum.DECRYPT_FAIL);
            }
        }
        return PageRespUtil.buildPageResult(list, SystemDBInfoDTO.class);
    }

    /**
     * 功能：新增保存记录
     */
    public SystemDBInfoDTO save(SystemDBInfoDTO systemDBInfoDTO) {
        SystemDBInfoDO systemDBInfoDO = new SystemDBInfoDO();
        DCBeanUtil.copyNotNull(systemDBInfoDTO, systemDBInfoDO);
        save(systemDBInfoDO);
        return DCBeanUtil.copyNotNull(systemDBInfoDO, new SystemDBInfoDTO());
    }

    /**
     * 功能：修改记录
     */
    public Boolean update(SystemDBInfoDTO systemDBInfoDTO) {
        if (DCObjectUtil.isNotNull(systemDBInfoDTO.getId())) {
            SystemDBInfoDO systemDBInfoDO = this.baseMapper.selectById(systemDBInfoDTO.getId());
            if (DCObjectUtil.isNotNull(systemDBInfoDO)) {
                DCBeanUtil.copyNotNull(systemDBInfoDTO, systemDBInfoDO);
                return this.baseMapper.updateById(systemDBInfoDO) > 0;
            }
        }
        return false;
    }

    /**
     * 功能：根据id删除记录
     */
    public Boolean deleteById(SystemDBInfoDTO systemDBInfoDTO) {
        SystemDBInfoDO systemDBInfoDO = this.baseMapper.selectById(systemDBInfoDTO.getId());
        if (DCObjectUtil.isNotNull(systemDBInfoDO)) {
            systemDBInfoDO.setDeleteFlag(1);
            return this.baseMapper.updateById(systemDBInfoDO) > 0;
        }
        return false;
    }

    /**
     * 功能：根据id获取记录
     */
    public SystemDBInfoDTO getById(Long id) {
        SystemDBInfoDTO systemDBInfoDTO = DCBeanUtil.copyNotNull(baseMapper.selectById(id), new SystemDBInfoDTO());
        String username = null;
        String password = null;
        try {
            username = JasyptUtils.decyptPwd(JasyptUtils.JASYPT_KEY, systemDBInfoDTO.getDbUsername());
            password = JasyptUtils.decyptPwd(JasyptUtils.JASYPT_KEY, systemDBInfoDTO.getDbPassword());
            systemDBInfoDTO.setDbUsername(username);
            systemDBInfoDTO.setDbPassword(password);
        } catch (Exception e) {
            log.error("systemDBInfoDTO.dbName={} decrypt fail!", systemDBInfoDTO.getDbName());
            throw new BizException(FcodeResponseResultCodeEnum.DECRYPT_FAIL);
        }
        return systemDBInfoDTO;
    }

    public List<InformationSchemaTable> listDBTableComment(String tableName, SystemDBInfoDTO systemDBInfoDTO) {
        DCValidatorUtil.validateModel(systemDBInfoDTO, BaseParam.add.class);
        DynamicDBInfo dynamicDBInfo = DynamicDBInfo.builder()
                .tableName(tableName)
                .dbName(systemDBInfoDTO.getDbName())
                .build().initDBInfo(systemDBInfoDTO.getDbUrl(),
                        systemDBInfoDTO.getDbUsername(), systemDBInfoDTO.getDbPassword());
        List<InformationSchemaTable> informationResultList = dynamicDBManager.selectInformationByTables(dynamicDBInfo);
        if (CollectionUtils.isEmpty(informationResultList)) {
            return Lists.newArrayList();
        }
        return informationResultList;
    }

    public PageResp<TableInfoRespDTO> pageListDBTable(Integer pageNo, Integer pageSize, String tableName, SystemDBInfoDTO systemDBInfoDTO) {
        DCValidatorUtil.validateModel(systemDBInfoDTO, BaseParam.add.class);
        DynamicDBInfo dynamicDBInfo = DynamicDBInfo.builder().dbName(systemDBInfoDTO.getDbName()).tableName(tableName).build()
                .initDBInfo(systemDBInfoDTO.getDbUrl(), systemDBInfoDTO.getDbUsername(), systemDBInfoDTO.getDbPassword());
        // 查询db所有表
        int tableCount = dynamicDBManager.selectTablesCount(dynamicDBInfo);
        if (tableCount == 0) {
            return new PageResp<>();
        }
        Integer startPage = (pageNo - 1) * pageSize;
        List<InformationSchemaTable> informationResultList = dynamicDBManager.selectTablesByPage(dynamicDBInfo, startPage, pageSize);
        if (CollectionUtils.isEmpty(informationResultList)) {
            return new PageResp<>();
        }
        List<TableInfoRespDTO> respList = new ArrayList<>();
        informationResultList.stream().forEach(information -> {
            TableInfoRespDTO respDTO = new TableInfoRespDTO();
            respDTO.setTableDesc(information.getTableComment());
            respDTO.setTableName(information.getTableName());
            respDTO.setCreateDate(information.getCreateTime());
            respDTO.setUpdateDate(information.getUpdateTime());
            respList.add(respDTO);
        });

        PageResp resp = new PageResp();
        resp.setList(respList);
        resp.setPageNo(pageNo);
        resp.setPageSize(pageSize);
        resp.setTotal(tableCount);
        return resp;
    }

    public List<TableInfoRespDTO> listTableInfo(SystemDBInfoDTO systemDBInfoDTO) {
        DCValidatorUtil.validateModel(systemDBInfoDTO, BaseParam.add.class);
        DynamicDBInfo dynamicDBInfo = DynamicDBInfo.builder().build().initDBInfo(systemDBInfoDTO.getDbUrl(), systemDBInfoDTO.getDbUsername(), systemDBInfoDTO.getDbPassword());
        List<InformationSchemaTable> informationResultList ;
        if (dynamicDBInfo.getDbUrl().contains(SystemDBInfoEnums.DriverEnum.ORACLE.getCode())) {
            informationResultList = dynamicDBManager.selectOracleTables(dynamicDBInfo);
        } else {
            informationResultList = dynamicDBManager.listInformationTable(dynamicDBInfo);
        }
        if (CollectionUtils.isEmpty(informationResultList)) {
            return Lists.newArrayList();
        }
        List<TableInfoRespDTO> respList = new ArrayList<>();
        informationResultList.stream().forEach(information -> {
            TableInfoRespDTO respDTO = new TableInfoRespDTO();
            respDTO.setTableDesc(information.getTableComment());
            respDTO.setTableName(information.getTableName());
            respDTO.setCreateDate(information.getCreateTime());
            respDTO.setUpdateDate(information.getUpdateTime());
            respList.add(respDTO);
        });
        return respList;
    }

}
