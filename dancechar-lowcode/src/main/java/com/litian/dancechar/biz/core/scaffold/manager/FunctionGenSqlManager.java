package com.litian.dancechar.biz.core.scaffold.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.litian.dancechar.biz.core.scaffold.dto.FunctionGenSqlDTO;
import com.litian.dancechar.biz.core.scaffold.repository.dataobject.FunctionGenSqlDO;
import com.litian.dancechar.biz.core.scaffold.repository.mapper.FunctionGenSqlMapper;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 类描述：单功能sql预览
 *
 * @author 01410001
 * @date 2021/08/07 16:53
 */
@Component
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class FunctionGenSqlManager extends ServiceImpl<FunctionGenSqlMapper, FunctionGenSqlDO> {

    public Long saveGenSql(FunctionGenSqlDTO functionGenSqlDTO){
        baseMapper.deleteByGenInfoId(functionGenSqlDTO.getScaffoldGenInfoId());
        FunctionGenSqlDO sqlDO = new FunctionGenSqlDO();
        DCBeanUtil.copyNotNull(functionGenSqlDTO,sqlDO);
        baseMapper.insert(sqlDO);
        return sqlDO.getId();
    }

    public FunctionGenSqlDTO getByGenInfoId(Long genInfoId){
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.eq("scaffold_gen_info_id",genInfoId);
        queryWrapper.eq("delete_flag",0);
        FunctionGenSqlDO sqlDO = baseMapper.selectOne(queryWrapper);
        FunctionGenSqlDTO functionGenSqlDTO = new FunctionGenSqlDTO();
        DCBeanUtil.copyNotNull(sqlDO,functionGenSqlDTO);
        return functionGenSqlDTO;
    }
}
