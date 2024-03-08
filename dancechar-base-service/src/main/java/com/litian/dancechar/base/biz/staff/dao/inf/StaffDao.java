package com.litian.dancechar.base.biz.staff.dao.inf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.base.biz.staff.dao.entity.StaffDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 员工Dao
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Mapper
public interface StaffDao extends BaseMapper<StaffDO> {

    List<StaffDO> findList(@Param("maxId") String maxId, @Param("pageSize") int pageSize);

}