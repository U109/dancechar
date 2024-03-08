package com.litian.dancechar.biz.core.scaffold.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.biz.core.scaffold.repository.dataobject.FunctionGenSqlDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 类描述：单功能sql预览
 *
 * @author 01410001
 * @date 2021/08/07 16:51
 */
@Mapper
public interface FunctionGenSqlMapper extends BaseMapper<FunctionGenSqlDO> {

    int deleteByGenInfoId(@Param("genInfoId") Long genInfoId);

}
