package com.litian.dancechar.member.biz.integral.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.PageResultUtil;
import com.litian.dancechar.member.biz.integral.dao.entity.IntegralLogInfoDO;
import com.litian.dancechar.member.biz.integral.dao.inf.IntegralLogInfoDao;
import com.litian.dancechar.member.biz.integral.dto.IntegralLogInfoReqDTO;
import com.litian.dancechar.member.biz.integral.dto.IntegralLogInfoRespDTO;
import com.litian.dancechar.member.biz.integral.dto.IntegralLogInfoSaveDTO;
import com.litian.dancechar.member.common.constants.RedisKeyConstants;
import com.litian.dancechar.member.feign.idgen.client.IdGenClient;
import com.litian.dancechar.member.feign.idgen.dto.IdGenReqDTO;
import com.litian.dancechar.member.feign.idgen.enums.IdGenTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 会员积分流水服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class IntegralLogInfoService extends ServiceImpl<IntegralLogInfoDao, IntegralLogInfoDO> {
    @Resource
    private IntegralLogInfoDao integralDao;
    @Resource
    private RedisHelper redisHelper;
    @Resource
    private IdGenClient idGenClient;

    /**
     * 功能: 分页查询积分列表
     */
    public RespResult<PageWrapperDTO<IntegralLogInfoRespDTO>> listPaged(IntegralLogInfoReqDTO req) {
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        PageWrapperDTO<IntegralLogInfoRespDTO> pageCommon = new PageWrapperDTO<>();
        PageResultUtil.setPageResult(integralDao.findList(req), pageCommon);
        return RespResult.success(pageCommon);
    }

    /**
     * 功能：根据Id-查询积分信息
     */
    public IntegralLogInfoDO findById(String id) {
        String key = String.format(RedisKeyConstants.Student.ID_KEY, id);
        String studentFromRedis = redisHelper.get(key);
        if(StrUtil.isNotEmpty(studentFromRedis)){
            return JSONUtil.toBean(studentFromRedis, IntegralLogInfoDO.class);
        }
        IntegralLogInfoDO studentDO = integralDao.selectById(id);
        if(ObjectUtil.isNotNull(studentDO)){
            redisHelper.set(key, JSONUtil.toJsonStr(studentDO));
        }
        return studentDO;
    }

    /**
     * 功能：根据条件-查询积分信息
     */
    public IntegralLogInfoDO findByCondition(IntegralLogInfoReqDTO reqDTO) {
        LambdaQueryWrapper<IntegralLogInfoDO> lambda = new LambdaQueryWrapper<IntegralLogInfoDO>();
        lambda.eq(IntegralLogInfoDO::getBusinessType,reqDTO.getBusinessType());
        lambda.eq(IntegralLogInfoDO::getBusinessId,reqDTO.getBusinessId());
        List<IntegralLogInfoDO> integralInfoDOList = integralDao.selectList(lambda);
        return CollUtil.isNotEmpty(integralInfoDOList) ? integralInfoDOList.get(0) : null;
    }

    /**
     * 功能：批量查询积分信息
     */
    public List<IntegralLogInfoDO> findByIds(List<String> ids) {
        return integralDao.selectBatchIds(ids);
    }


    /**
     * 功能：查询积分列表
     */
    public RespResult<List<IntegralLogInfoRespDTO>> findList(IntegralLogInfoReqDTO req) {
        return RespResult.success(integralDao.findList(req));
    }

    /**
     * 功能：新增积分
     */
    public RespResult<String> saveWithInsert(IntegralLogInfoSaveDTO integralInfoSaveDTO) {
        // 幂等处理
        LambdaQueryWrapper<IntegralLogInfoDO> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(IntegralLogInfoDO::getCustomerId, integralInfoSaveDTO.getCustomerId());
        lambdaQueryWrapper.eq(IntegralLogInfoDO::getBusinessType, integralInfoSaveDTO.getBusinessType());
        lambdaQueryWrapper.eq(IntegralLogInfoDO::getBusinessId, integralInfoSaveDTO.getBusinessId());
        if(CollUtil.isNotEmpty(integralDao.selectList(lambdaQueryWrapper))){
            return RespResult.error("10001","新增或扣减积分重复");
        }
        IntegralLogInfoDO integralInfoDO = new IntegralLogInfoDO();
        DCBeanUtil.copyNotNull(integralInfoDO, integralInfoSaveDTO);
        RespResult<String> integralR = idGenClient.genId(IdGenReqDTO.builder()
                .module(IdGenTypeEnum.INTEGRAL.getCode()).build());
        if(integralR.isOk()){
            integralInfoDO.setSerialNo(integralR.getData());
        }
        save(integralInfoDO);
        return RespResult.success(integralInfoDO.getSerialNo());
    }
}