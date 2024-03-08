package com.litian.dancechar.core.biz.item.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 本地事务消息请求对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ItemInfoReqDTO extends BasePage implements Serializable {
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

    /**
     * 扣减库存
     */
    private Integer acquire;
}