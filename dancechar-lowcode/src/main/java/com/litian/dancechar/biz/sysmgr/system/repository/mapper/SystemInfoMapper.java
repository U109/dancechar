package com.litian.dancechar.biz.sysmgr.system.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.biz.sysmgr.system.dto.SystemInfoDTO;
import com.litian.dancechar.biz.sysmgr.system.repository.dataobject.SystemInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述：SystemInfomapper
 *
 * @author fcoder
 * @date 2021-06-26 18:49:31
 */
@Mapper
public interface SystemInfoMapper extends BaseMapper<SystemInfoDO> {

    List<SystemInfoDTO> getUnusedSystemList(@Param("scaffoldGenInfoId") Long scaffoldGenInfoId);

}
