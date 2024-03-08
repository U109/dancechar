package com.litian.dancechar.biz.sysmgr.esmgr.repository.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.biz.sysmgr.esmgr.repository.dataobject.EsInfoDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * 类描述：es连接信息管理mapper
 *
 * @author 01406831
 * @date 2021-11-04 15:28:33
 */
@DS("cempfcode")
@Mapper
public interface EsInfoMapper extends BaseMapper<EsInfoDO> {

}
