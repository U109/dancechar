package com.litian.dancechar.examples.excel.dao.inf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.examples.excel.dao.entity.StudentDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 学生Dao
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Mapper
public interface StudentDao extends BaseMapper<StudentDO> {

    List<StudentDO> findByPage(@Param("no")String no, @Param("name")String name,
                               @Param("pageBegin") Integer pageBegin, @Param("pageSize") Integer pageSize);
}