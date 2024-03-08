package com.litian.dancechar.biz.core.scaffold.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.biz.core.scaffold.repository.dataobject.ChoosedTableColumnsDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 类描述：已选择字段Mapper
 *
 * @author 01396106
 * @date 2021/08/04 17:05
 */
@Mapper
public interface ChoosedTableColumnsMapper extends BaseMapper<ChoosedTableColumnsDO> {

    int deleteByGenInfoId(@Param("genInfoId") Long genInfoId);

}
