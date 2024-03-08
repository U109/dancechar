package com.litian.dancechar.base.biz.commonconfig.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 系统公共配置返回對象
 *
 * @author tojson
 * @date 2021/6/19 11:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysCommonConfigRespDTO extends SysCommonDTO implements Serializable {
    private static final long serialVersionUID = 1L;

}