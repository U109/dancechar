package com.litian.dancechar.core.feign.member.integral.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 会员积分流水完成对象
 *
 * @author tojson
 * @date 2022/9/6 11:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IntegralLogFinishInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 客户Id
     */
    private String customerId;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 业务唯一id(主键或code)
     */
    private String businessId;

    /**
     * 积分流水号
     */
    private String serialNo;

    /**
     * 添加的结果
     */
    private Boolean result;

    /**
     * 添加失败的原因(10001: 重复提交)
     */
    private Integer reason;
}