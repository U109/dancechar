package com.litian.dancechar.base.biz.oplog.dao.inf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.base.biz.oplog.dao.entity.SysOpLogDO;
import com.litian.dancechar.base.biz.oplog.dto.SysOpLogReqDTO;
import com.litian.dancechar.base.biz.oplog.dto.SysOpLogRespDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 操作日志Dao
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Mapper
public interface SysOpLogDao extends BaseMapper<SysOpLogDO> {

    List<SysOpLogRespDTO> findList(SysOpLogReqDTO req);
}