package com.litian.dancechar.core.biz.item.dto;

import com.litian.dancechar.framework.common.base.BaseRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 活动领取返回对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ItemInfoRespDTO extends BaseRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 物品类型
     */
    private String itemType;

    /**
     * 物品code
     */
    private String itemCode;

    /**
     * 物品名称
     */
    private String itemName;

    /**
     * 物品库存
     */
    private Integer itemStock;
}