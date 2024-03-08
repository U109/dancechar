package com.litian.dancechar.base.biz.customergroup.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import lombok.Data;

import java.io.Serializable;

/**
 * 父客群DTO
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
public class CustomerGroupParentInfoReqDTO extends BasePage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;
}