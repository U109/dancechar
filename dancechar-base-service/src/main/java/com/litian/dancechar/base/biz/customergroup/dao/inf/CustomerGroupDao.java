package com.litian.dancechar.base.biz.customergroup.dao.inf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.base.biz.customergroup.dao.entity.CustomerGroupDO;
import com.litian.dancechar.base.biz.customergroup.dto.CustomerGroupRespDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 客群Dao
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Mapper
public interface CustomerGroupDao extends BaseMapper<CustomerGroupDO> {

    List<CustomerGroupRespDTO> findList(@Param("code") String code, @Param("name") String name);
}