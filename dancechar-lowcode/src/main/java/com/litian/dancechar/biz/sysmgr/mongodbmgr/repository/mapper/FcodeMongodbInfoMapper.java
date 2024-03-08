package com.litian.dancechar.biz.sysmgr.mongodbmgr.repository.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.biz.sysmgr.mongodbmgr.repository.dataobject.FcodeMongodbInfoDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * 类描述：mongodb配置信息mapper
 *
 * @author 01410001
 * @date 2021-11-08 16:16:16
 */
@DS("cempfcode")
@Mapper
public interface FcodeMongodbInfoMapper extends BaseMapper<FcodeMongodbInfoDO> {

}
