package com.litian.dancechar.base.biz.customergroup.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.base.biz.customergroup.dao.entity.CustomerGroupParentInfoDO;
import com.litian.dancechar.base.biz.customergroup.dao.inf.CustomerGroupParentInfoDao;
import com.litian.dancechar.base.biz.customergroup.dto.CustomerGroupParentInfoReqDTO;
import com.litian.dancechar.base.biz.customergroup.dto.CustomerGroupParentInfoRespDTO;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.PageResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 父客群服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CustomerGroupParentInfoService extends ServiceImpl<CustomerGroupParentInfoDao, CustomerGroupParentInfoDO> {
    @Resource
    private CustomerGroupParentInfoDao customerGroupParentInfoDao;

    public RespResult<PageWrapperDTO<CustomerGroupParentInfoRespDTO>> listPaged(CustomerGroupParentInfoReqDTO reqDTO){
        PageHelper.startPage(reqDTO.getPageNo(), reqDTO.getPageSize());
        LambdaQueryWrapper<CustomerGroupParentInfoDO> lambdaQueryWrapper = Wrappers.lambdaQuery();
        if(StrUtil.isNotEmpty(reqDTO.getName())){
            lambdaQueryWrapper.eq(CustomerGroupParentInfoDO::getName, reqDTO.getName());
        }
        List<CustomerGroupParentInfoDO> cgpList = customerGroupParentInfoDao.selectList(lambdaQueryWrapper);
        return RespResult.success(PageResultUtil.setPageResult(cgpList, CustomerGroupParentInfoRespDTO.class));
    }
}