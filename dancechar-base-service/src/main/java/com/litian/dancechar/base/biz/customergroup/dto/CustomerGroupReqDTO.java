package com.litian.dancechar.base.biz.customergroup.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 客群请求对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerGroupReqDTO extends BasePage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 父客群Id
     */
    private String customerGroupParentId;

    /**
     * 编码
     */
    private String code;



    /**
     * 名称
     */
    private String name;
}