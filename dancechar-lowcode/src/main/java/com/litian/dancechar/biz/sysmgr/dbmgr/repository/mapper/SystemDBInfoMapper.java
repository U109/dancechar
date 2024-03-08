package com.litian.dancechar.biz.sysmgr.dbmgr.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.biz.sysmgr.dbmgr.dto.DbColumnsDTO;
import com.litian.dancechar.biz.sysmgr.dbmgr.dto.DbColumnsQueryDTO;
import com.litian.dancechar.biz.sysmgr.dbmgr.repository.dataobject.SystemDBInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 类描述：示例mapper
 *
 * @author terryhl
 * @date 2021-06-21 11:48:37
 */
@Mapper
public interface SystemDBInfoMapper extends BaseMapper<SystemDBInfoDO> {

    List<DbColumnsDTO> listDBTableColumns(DbColumnsQueryDTO dbColumnsQueryDTO);

}
