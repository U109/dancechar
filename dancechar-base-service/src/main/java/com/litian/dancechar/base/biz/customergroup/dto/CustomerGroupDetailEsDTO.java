package com.litian.dancechar.base.biz.customergroup.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 客群详情Es DTO
 *
 * @author tojson
 * @date 2021/6/19 11:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerGroupDetailEsDTO implements Serializable {

    /**
     * 客群主键
     */
    private String customerGroupId;

    /**
     * 客群编码xf
     */
    private String customerGroupCode;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 过期时间
     */
    private Date expireTime;
}
