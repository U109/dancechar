package com.litian.dancechar.core.biz.task.service;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.core.biz.task.dao.entity.TaskInfoDO;
import com.litian.dancechar.core.biz.task.dao.inf.TaskInfoDao;
import com.litian.dancechar.core.biz.task.dto.TaskInfoReqDTO;
import com.litian.dancechar.core.biz.task.dto.TaskInfoRespDTO;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.PageResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 任务信息服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class TaskInfoService extends ServiceImpl<TaskInfoDao, TaskInfoDO> {
    @Resource
    private TaskInfoDao taskInfoDao;

    /**
     * 功能: 任务信息列表
     */
    public RespResult<PageWrapperDTO<TaskInfoRespDTO>> listPaged(TaskInfoReqDTO req) {
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        PageWrapperDTO<TaskInfoRespDTO> pageCommon = new PageWrapperDTO<>();
        PageResultUtil.setPageResult(taskInfoDao.findList(req), pageCommon);
        return RespResult.success(pageCommon);
    }

    /**
     * 功能：根据Id-查询任务信息
     */
    public TaskInfoDO findById(String id) {
        return taskInfoDao.selectById(id);
    }

    /**
     * 功能：根据code-查询任务信息
     */
    public TaskInfoDO findByCode(String code) {
        LambdaQueryWrapper<TaskInfoDO> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(TaskInfoDO::getTaskCode, code);
        return taskInfoDao.selectOne(lambdaQueryWrapper);
    }

    /**
     * 功能：查询任务信息列表
     */
    public List<TaskInfoRespDTO> findList(TaskInfoReqDTO req) {
        return taskInfoDao.findList(req);
    }

    /**
     * 功能：新增任务信息
     */
    public void insert(TaskInfoReqDTO req) {
        TaskInfoDO taskInfoDO = new TaskInfoDO();
        DCBeanUtil.copyNotNull(taskInfoDO, req);
        taskInfoDao.insert(taskInfoDO);
    }

    /**
     * 功能：更新任务信息
     */
    public void update(TaskInfoReqDTO req) {
        TaskInfoDO taskInfoDO = this.findById(req.getId());
        if(ObjectUtil.isNotNull(taskInfoDO)){
            DCBeanUtil.copyNotNull(taskInfoDO, req);
            taskInfoDao.updateById(taskInfoDO);
        }
    }
}