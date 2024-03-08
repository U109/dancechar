package com.litian.dancechar.system.biz.user.dao.inf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.system.biz.user.dao.entity.SystemUserDO;
import com.litian.dancechar.system.biz.user.dto.SystemUserReqDTO;
import com.litian.dancechar.system.biz.user.dto.SystemUserRespDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 系统用户Dao
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Mapper
public interface SystemUserDao extends BaseMapper<SystemUserDO> {

    List<SystemUserRespDTO> findList(SystemUserReqDTO req);
}