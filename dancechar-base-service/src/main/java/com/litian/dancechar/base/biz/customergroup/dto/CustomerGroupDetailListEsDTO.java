package com.litian.dancechar.base.biz.customergroup.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 客群详情Es DTO
 *
 * @author tojson
 * @date 2021/6/19 11:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerGroupDetailListEsDTO implements Serializable {

    private List<CustomerGroupDetailEsDTO> detailDTOS;
}
