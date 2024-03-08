package com.litian.dancechar.biz.sysmgr.sysdict.repository.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.biz.sysmgr.sysdict.repository.dataobject.SysDictDataDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * 类描述：数据字典数据mapper
 *
 * @author 01407390
 * @date 2021-09-28 15:16:56
 */
@DS("cempfcode")
@Mapper
public interface SysDictDataMapper extends BaseMapper<SysDictDataDO> {

}
