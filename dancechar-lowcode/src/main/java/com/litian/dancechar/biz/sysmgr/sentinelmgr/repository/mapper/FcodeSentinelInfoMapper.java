package com.litian.dancechar.biz.sysmgr.sentinelmgr.repository.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.biz.sysmgr.sentinelmgr.repository.dataobject.FcodeSentinelInfoDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * 类描述：sentinel配置信息mapper
 *
 * @author 01410001
 * @date 2021-10-12 14:08:05
 */
@DS("cempfcode")
@Mapper
public interface FcodeSentinelInfoMapper extends BaseMapper<FcodeSentinelInfoDO> {

}
