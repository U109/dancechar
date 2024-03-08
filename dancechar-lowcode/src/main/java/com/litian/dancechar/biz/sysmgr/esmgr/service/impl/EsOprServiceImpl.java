package com.litian.dancechar.biz.sysmgr.esmgr.service.impl;

import com.litian.dancechar.biz.sysmgr.esmgr.dto.EsOprDTO;
import com.litian.dancechar.biz.sysmgr.esmgr.manager.EsOperateManager;
import com.litian.dancechar.biz.sysmgr.esmgr.service.EsOprService;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.DCResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 类描述：ES7.6.0操作具体实现
 *
 * @author 01396106
 * @date 2021/10/28 14:22
 */
@Service
@Slf4j
public class EsOprServiceImpl implements EsOprService {
    @Resource
    private EsOperateManager esOperateManager;


    @Override
    public Result<Boolean> createIndex(EsOprDTO req) {
        esOperateManager.createIndex(req.getIndexName(),req.getReqJson());
        return DCResultUtil.success(true);
    }

    @Override
    public Result<Boolean> deleteIndex(EsOprDTO req) {
        esOperateManager.deleteIndex(req.getIndexName());
        return DCResultUtil.success(true);
    }

    @Override
    public Result<Boolean> addInJson(EsOprDTO req) {
        esOperateManager.addInJson(req.getIndexName(),req.getId(),req.getReqJson());
        return DCResultUtil.success(true);
    }

    @Override
    public Result<Boolean> updateInJson(EsOprDTO req) {
        esOperateManager.addInJson(req.getIndexName(),req.getId(),req.getReqJson());
        return DCResultUtil.success(true);
    }

    @Override
    public Result<Boolean> deleteById(EsOprDTO req) {
        esOperateManager.deleteById(req.getIndexName(),req.getId());
        return DCResultUtil.success(true);
    }

    @Override
    public Result<Map<String, Object>> getById(EsOprDTO req) {
        return DCResultUtil.success(esOperateManager.getById(req.getIndexName(),req.getId()));
    }
}
