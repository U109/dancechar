package com.litian.dancechar.biz.sysmgr.mongodbmgr.service.impl;
import com.litian.dancechar.biz.sysmgr.mongodbmgr.dto.MongodbDemoDTO;
import com.litian.dancechar.biz.sysmgr.mongodbmgr.service.MongodbOprService;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCResultUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MongodbOprServiceImpl implements MongodbOprService {

    @Resource
    private MongodbOprService mongoDbService;

    @Override
    public Result<Boolean> save(MongodbDemoDTO demo) {
        mongoDbService.save(demo);
        return DCResultUtil.success(true);
    }

    @Override
    public Result<MongodbDemoDTO> queryById(MongodbDemoDTO demo) {
        return mongoDbService.queryById(demo);
    }

    @Override
    public Result<Boolean> deleteById(MongodbDemoDTO demo) {
        mongoDbService.deleteById(demo);
        return DCResultUtil.success(true);
    }

    @Override
    public Result<Boolean> updateFirst(MongodbDemoDTO demo) {
        MongodbDemoDTO queryMongoDTO = new MongodbDemoDTO();
        queryMongoDTO.setId(demo.getId());
        MongodbDemoDTO updateMongoDTO = new MongodbDemoDTO();
        DCBeanUtil.copyNotNull(demo, updateMongoDTO);
        mongoDbService.updateFirst(demo);
        return DCResultUtil.success(true);
    }

}
