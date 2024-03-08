package com.litian.dancechar.biz.sysmgr.esmgr.repository.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.biz.sysmgr.esmgr.repository.dataobject.EsMgrDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * 类描述：ES连接信息管理mapper
 *
 * @author 01396106
 * @date 2021-10-18 13:39:03
 */
@DS("cempfcode")
@Mapper
public interface EsMgrMapper extends BaseMapper<EsMgrDO> {

}
