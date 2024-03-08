package com.litian.dancechar.core.feign.member.integral.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 会员积分流水保存对象
 *
 * @author tojson
 * @date 2022/9/6 11:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IntegralLogInfoSaveDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 客户Id
     */
    @NotEmpty(message = "customerId不能为空")
    private String customerId;

    /**
     * 业务类型
     */
    @NotEmpty(message = "业务类型不能为空")
    private String businessType;

    /**
     * 业务唯一id(主键或code)
     */
    @NotEmpty(message = "业务唯一id不能为空")
    private String businessId;

    /**
     * 积分流水号
     */
    private String serialNo;

    /**
     * 操作类型，0:加积分 1:扣减积分
     */
    @NotNull(message = "operateType不能为空")
    private Integer operateType;

    /**
     * 操作数量
     */
    @NotNull(message = "operateNum不能为空")
    private Integer operateNum;
}