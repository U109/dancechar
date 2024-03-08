package com.litian.dancechar.biz.sysmgr.redismgr.repository.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.biz.sysmgr.redismgr.repository.dataobject.FcodeRedisInfoDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * 类描述：Redis信息管理mapper
 *
 * @author 01396106
 * @date 2021-10-12 11:05:08
 */
@DS("cempfcode")
@Mapper
public interface FcodeRedisInfoMapper extends BaseMapper<FcodeRedisInfoDO> {

}
