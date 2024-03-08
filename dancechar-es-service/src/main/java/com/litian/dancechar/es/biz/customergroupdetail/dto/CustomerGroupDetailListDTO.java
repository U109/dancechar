package com.litian.dancechar.es.biz.customergroupdetail.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 客群详情DTO
 *
 * @author tojson
 * @date 2021/6/19 11:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerGroupDetailListDTO implements Serializable {

    private List<CustomerGroupDetailDTO> detailDTOS;
}
