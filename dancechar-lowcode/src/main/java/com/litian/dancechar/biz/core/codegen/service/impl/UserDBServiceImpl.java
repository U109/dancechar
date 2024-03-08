package com.litian.dancechar.biz.core.codegen.service.impl;

import com.litian.dancechar.biz.core.codegen.dto.UserDBDTO;
import com.litian.dancechar.biz.core.codegen.dto.UserDBQueryReqDTO;
import com.litian.dancechar.biz.core.codegen.manager.UserDBManager;
import com.litian.dancechar.biz.core.codegen.manager.conn.ConnectionPoolUtil;
import com.litian.dancechar.biz.core.codegen.service.UserDBService;
import com.litian.dancechar.biz.sysmgr.dbmgr.dto.DynamicDBInfo;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCResultUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 类描述：userDB 服务实现
 *
 * @author terryhl
 * @date 2021-06-15 14:43:47
 */
@Component
@Slf4j
public class UserDBServiceImpl implements UserDBService {
    @Resource
    private UserDBManager userDBManager;

    private static final String ID_TIPS = "id不能为空";

    @Override
    public Result<PageResp<UserDBDTO>> listMyUserDB(UserDBQueryReqDTO req) {
        // session 获取userId TODO
        req.setUserId("test123");
        PageResp<UserDBDTO> pageResp = userDBManager.listPage(req);
        return DCResultUtil.success(pageResp);
    }

    @Override
    public Result<PageResp<UserDBDTO>> listPage(UserDBQueryReqDTO req) {
        PageResp<UserDBDTO> pageResp = userDBManager.listPage(req);
        return DCResultUtil.success(pageResp);
    }

    @Override
    public Result<Boolean> save(UserDBDTO userDBDTO) {
        return DCResultUtil.success(userDBManager.save(userDBDTO));
    }

    @Override
    public Result<Boolean> update(UserDBDTO userDBDTO) {
        return DCResultUtil.success(userDBManager.update(userDBDTO));
    }

    @Override
    public Result<Boolean> deleteById(UserDBDTO userDBDTO) {
        if (DCObjectUtil.isNull(userDBDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(userDBManager.deleteById(userDBDTO));
    }

    @Override
    public Result<Boolean> deleteByDBCode(UserDBDTO userDBDTO) {
        return DCResultUtil.success(userDBManager.deleteByDBCode(userDBDTO));
    }

    @Override
    public Result<UserDBDTO> getByDBCode(UserDBDTO userDBDTO) {
        return DCResultUtil.success(userDBManager.getByDBCode(userDBDTO));
    }

    @Override
    public Result<UserDBDTO> getById(UserDBDTO userDBDTO) {
        if (DCObjectUtil.isNull(userDBDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(userDBManager.getById(userDBDTO.getId()));
    }

    /**
     * 解析dburl
     * @param userDBDTO
     * @return
     */
    @Override
    public Result<DynamicDBInfo> resolveDBUrl(UserDBDTO userDBDTO) {
        DynamicDBInfo dynamicDBInfo = ConnectionPoolUtil.resolveDBUrl(userDBDTO.getDbUrl());
        return DCResultUtil.success(dynamicDBInfo);
    }


    /**
     * 测试创建数据源 依赖druid createdatabase校验
     * @param userDBDTO
     * @return
     */
    @Override
    public Result<Void> checkConnection(UserDBDTO userDBDTO) {
        ConnectionPoolUtil.createDataSource(userDBDTO.getDbUrl(), userDBDTO.getDbUsername(), userDBDTO.getDbPassword());
        return DCResultUtil.success();
    }
}
