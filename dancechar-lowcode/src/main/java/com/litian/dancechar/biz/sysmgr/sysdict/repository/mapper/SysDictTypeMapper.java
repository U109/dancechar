package com.litian.dancechar.biz.sysmgr.sysdict.repository.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.biz.sysmgr.sysdict.repository.dataobject.SysDictTypeDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * 类描述：数据字典类型mapper
 *
 * @author 01407390
 * @date 2021-09-28 15:47:15
 */
@DS("cempfcode")
@Mapper
public interface SysDictTypeMapper extends BaseMapper<SysDictTypeDO> {

}
