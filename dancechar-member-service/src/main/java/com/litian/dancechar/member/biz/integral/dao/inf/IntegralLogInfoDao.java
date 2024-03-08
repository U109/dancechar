package com.litian.dancechar.member.biz.integral.dao.inf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.member.biz.integral.dao.entity.IntegralLogInfoDO;
import com.litian.dancechar.member.biz.integral.dto.IntegralLogInfoReqDTO;
import com.litian.dancechar.member.biz.integral.dto.IntegralLogInfoRespDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 会员积分Dao
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Mapper
public interface IntegralLogInfoDao extends BaseMapper<IntegralLogInfoDO> {

    List<IntegralLogInfoRespDTO> findList(IntegralLogInfoReqDTO req);
}