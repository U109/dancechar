package com.litian.dancechar.core.biz.item.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 物品信息DO
 *
 * @author tojson
 * @date 2022/9/5 06:18
 */
@Data
@TableName("item_info")
@EqualsAndHashCode(callSuper = false)
public class ItemInfoDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

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