package com.litian.dancechar.biz.core.codegen.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.biz.core.codegen.repository.dataobject.UserDBDO;

import org.apache.ibatis.annotations.Mapper;

/**
 * 类描述：示例mapper
 *
 * @author terryhl
 * @date 2021-06-15 14:43:47
 */
@Mapper
public interface UserDBMapper extends BaseMapper<UserDBDO> {

}
