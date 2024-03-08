package com.litian.dancechar.es.biz.customergroupdetail.dao.document;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 客群详情document
 *
 * @author tojson
 * @date 2021/6/19 11:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerGroupDetailDocument implements Serializable {
    private String id;

    /**
     * 客群编码
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