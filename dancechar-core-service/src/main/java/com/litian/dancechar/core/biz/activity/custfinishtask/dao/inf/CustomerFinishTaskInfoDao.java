package com.litian.dancechar.core.biz.activity.custfinishtask.dao.inf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.core.biz.activity.custfinishtask.dao.entity.CustomerFinishTaskInfoDO;
import com.litian.dancechar.core.biz.activity.custfinishtask.dto.CustomerFinishTaskInfoReqDTO;
import com.litian.dancechar.core.biz.activity.custfinishtask.dto.CustomerFinishTaskInfoRespDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 客户完成任务信息Dao
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Mapper
public interface CustomerFinishTaskInfoDao extends BaseMapper<CustomerFinishTaskInfoDO> {

    List<CustomerFinishTaskInfoRespDTO> findList(CustomerFinishTaskInfoReqDTO req);
}