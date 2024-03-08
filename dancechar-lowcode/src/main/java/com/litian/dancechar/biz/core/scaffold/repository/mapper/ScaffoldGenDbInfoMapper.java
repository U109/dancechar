package com.litian.dancechar.biz.core.scaffold.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.biz.core.scaffold.repository.dataobject.ScaffoldGenDbInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 脚手架生成-数据库信息(ScaffoldGenDbInfo)mapper接口
 *
 * @author 01406831
 * @since 2021-06-21 14:33:21
 */
@Mapper
public interface ScaffoldGenDbInfoMapper extends BaseMapper<ScaffoldGenDbInfoDO> {

    int deleteByScaffoldGenId(@Param("scaffoldGenId") Long scaffoldGenId);

    int deleteByIds(@Param("ids") List<Long> ids);

}