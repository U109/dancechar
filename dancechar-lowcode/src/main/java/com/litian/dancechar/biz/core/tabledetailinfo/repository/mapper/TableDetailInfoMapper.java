package com.litian.dancechar.biz.core.tabledetailinfo.repository.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.biz.core.tabledetailinfo.repository.dataobject.TableDetailInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 类描述：代码生成-表关联字段信息表mapper
 *
 * @author 853523
 * @date 2021-09-22 10:30:53
 */
@DS("cempfcode")
@Mapper
public interface TableDetailInfoMapper extends BaseMapper<TableDetailInfoDO> {

    int deleteByGenInfoId(@Param("genInfoId") Long genInfoId);
}
