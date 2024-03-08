package com.litian.dancechar.biz.core.scaffold.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.biz.core.scaffold.repository.dataobject.ScaffoldGenInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 脚手架生成-基础信息(ScaffoldGenInfo)mapper接口
 *
 * @author 01406831
 * @since 2021-06-21 14:32:35
 */
@Mapper
public interface ScaffoldGenInfoMapper extends BaseMapper<ScaffoldGenInfoDO> {

    List<String> findUseSystemList();

    int deleteByIds(@Param("ids") List<Long> ids);

    int updateGenInfoById(ScaffoldGenInfoDO scaffoldGenInfoDO);
}