package com.litian.dancechar.core.biz.item.dao.inf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.core.biz.item.dao.entity.ItemInfoDO;
import com.litian.dancechar.core.biz.item.dto.ItemInfoReqDTO;
import com.litian.dancechar.core.biz.item.dto.ItemInfoRespDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 物品信息Dao
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Mapper
public interface ItemInfoDao extends BaseMapper<ItemInfoDO> {

    List<ItemInfoRespDTO> findList(ItemInfoReqDTO req);

    /**
     * 扣减库存
     */
    Integer updateStock(ItemInfoReqDTO req);
}