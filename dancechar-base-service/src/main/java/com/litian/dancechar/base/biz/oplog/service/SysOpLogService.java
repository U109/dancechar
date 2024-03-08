package com.litian.dancechar.base.biz.oplog.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.base.biz.oplog.dao.entity.SysOpLogDO;
import com.litian.dancechar.base.biz.oplog.dao.inf.SysOpLogDao;
import com.litian.dancechar.base.biz.oplog.dto.SysOpLogReqDTO;
import com.litian.dancechar.base.biz.oplog.dto.SysOpLogRespDTO;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.PageResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * 操作日志服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SysOpLogService extends ServiceImpl<SysOpLogDao, SysOpLogDO> {
    @Resource
    private SysOpLogDao sysOpLogDao;

    /**
     * 功能: 分页查询操作日志列表
     */
    public RespResult<PageWrapperDTO<SysOpLogRespDTO>> listPaged(SysOpLogReqDTO req) {
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        PageWrapperDTO<SysOpLogRespDTO> pageCommon = new PageWrapperDTO<>();
        PageResultUtil.setPageResult(sysOpLogDao.findList(req), pageCommon);
        return RespResult.success(pageCommon);
    }

    /**
     * 功能：查询操作日志信息
     */
    public SysOpLogDO findById(String id) {
        return sysOpLogDao.selectById(id);
    }

    /**
     * 功能：新增操作日志
     */
    public RespResult<Boolean> saveWithInsert(SysOpLogReqDTO sysOpLogReqDTO) {
        if(StrUtil.isNotEmpty(sysOpLogReqDTO.getUrl()) && sysOpLogReqDTO.getUrl().length() > 500){
            sysOpLogReqDTO.setUrl(sysOpLogReqDTO.getUrl().substring(0,500));
        }
        if(StrUtil.isNotEmpty(sysOpLogReqDTO.getParams()) && sysOpLogReqDTO.getParams().length() > 1024){
            sysOpLogReqDTO.setParams(sysOpLogReqDTO.getParams().substring(0,1024));
        }
        if(StrUtil.isNotEmpty(sysOpLogReqDTO.getOpContent()) && sysOpLogReqDTO.getOpContent().length() > 1024){
            sysOpLogReqDTO.setOpContent(sysOpLogReqDTO.getOpContent().substring(0,1024));
        }
        SysOpLogDO sysOpLogDO = new SysOpLogDO();
        DCBeanUtil.copyNotNull(sysOpLogDO, sysOpLogReqDTO);
        save(sysOpLogDO);
        return RespResult.success(true);
    }
}