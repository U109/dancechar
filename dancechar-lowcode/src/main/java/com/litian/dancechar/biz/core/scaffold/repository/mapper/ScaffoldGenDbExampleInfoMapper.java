package com.litian.dancechar.biz.core.scaffold.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.biz.core.scaffold.repository.dataobject.ScaffoldGenDbExampleInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 脚手架生成-数据库-示例信息(ScaffoldGenDbExampleInfo)mapper接口
 *
 * @author 01406831
 * @since 2021-06-21 14:33:55
 */
@Mapper
public interface ScaffoldGenDbExampleInfoMapper extends BaseMapper<ScaffoldGenDbExampleInfoDO> {

    int deleteByScaffoldGenDbInfoId(List<Long> ids);

    int deleteById(@Param("id") Long id);
}