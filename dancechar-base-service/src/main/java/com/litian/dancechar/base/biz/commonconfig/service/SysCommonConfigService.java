package com.litian.dancechar.base.biz.commonconfig.service;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.base.biz.commonconfig.dao.entity.SysCommonConfigDO;
import com.litian.dancechar.base.biz.commonconfig.dao.inf.SysCommonConfigDao;
import com.litian.dancechar.base.biz.commonconfig.dto.SysCommonConfigAddOrEditDTO;
import com.litian.dancechar.base.biz.commonconfig.dto.SysCommonConfigReqDTO;
import com.litian.dancechar.base.biz.commonconfig.dto.SysCommonConfigRespDTO;
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
 * 系统公共配置服務
 *
 * @author tojson
 * @date 2021/6/19 15:13
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SysCommonConfigService {
    @Resource
    private SysCommonConfigDao sysCommonConfigDao;

    /**
     * 功能: 分页查询系统公共配置列表
     */
    public RespResult<PageWrapperDTO<SysCommonConfigRespDTO>> listPaged(SysCommonConfigReqDTO req) {
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        PageWrapperDTO<SysCommonConfigRespDTO> pageCommon = new PageWrapperDTO<>();
        PageResultUtil.setPageResult(sysCommonConfigDao.findList(req), pageCommon);
        return RespResult.success(pageCommon);
    }

    /**
     * 功能：查询系统公共配置列表
     */
    public RespResult<List<SysCommonConfigRespDTO>> findList(SysCommonConfigReqDTO req) {
        return RespResult.success(sysCommonConfigDao.findList(req));
    }

    /**
     * 保存系统公共配置(新增或修改)
     *
     * @param req 系统公共配置对象
     * @return 保存结果
     */
    public RespResult<Boolean> save(SysCommonConfigAddOrEditDTO req) {
        if (StrUtil.isNotEmpty(req.getId())) {
            SysCommonConfigDO sysCommonConfigDO = sysCommonConfigDao.selectById(req.getId());
            if (sysCommonConfigDO != null) {
                DCBeanUtil.copyNotNull(sysCommonConfigDO, req);
                sysCommonConfigDao.updateById(sysCommonConfigDO);
            }
        } else {
            SysCommonConfigDO sysCommonConfigDO = new SysCommonConfigDO();
            DCBeanUtil.copyNotNull(sysCommonConfigDO, req);
            sysCommonConfigDao.insert(sysCommonConfigDO);
        }
        return RespResult.success(true);
    }
}