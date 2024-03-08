package com.litian.dancechar.biz.sysmgr.kafkamgr.repository.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.biz.sysmgr.kafkamgr.repository.dataobject.FcodeKafkaInfoDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * 类描述：kafka配置信息mapper
 *
 * @author 01410001
 * @date 2021-10-12 14:07:34
 */
@DS("cempfcode")
@Mapper
public interface FcodeKafkaInfoMapper extends BaseMapper<FcodeKafkaInfoDO> {

}
