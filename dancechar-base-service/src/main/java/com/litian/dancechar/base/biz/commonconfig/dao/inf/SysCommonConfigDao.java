package com.litian.dancechar.base.biz.commonconfig.dao.inf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.base.biz.commonconfig.dao.entity.SysCommonConfigDO;
import com.litian.dancechar.base.biz.commonconfig.dto.SysCommonConfigReqDTO;
import com.litian.dancechar.base.biz.commonconfig.dto.SysCommonConfigRespDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 公共配置请求Dao
 *
 * @author tojson
 * @date 2021/6/19 11:17
 */
@Mapper
public interface SysCommonConfigDao extends BaseMapper<SysCommonConfigDO> {

    List<SysCommonConfigRespDTO> findList(SysCommonConfigReqDTO req);
}