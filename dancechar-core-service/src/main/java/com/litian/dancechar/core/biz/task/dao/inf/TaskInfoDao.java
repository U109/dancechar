package com.litian.dancechar.core.biz.task.dao.inf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.core.biz.task.dao.entity.TaskInfoDO;
import com.litian.dancechar.core.biz.task.dto.TaskInfoReqDTO;
import com.litian.dancechar.core.biz.task.dto.TaskInfoRespDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 任务信息Dao
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Mapper
public interface TaskInfoDao extends BaseMapper<TaskInfoDO> {

    List<TaskInfoRespDTO> findList(TaskInfoReqDTO req);
}