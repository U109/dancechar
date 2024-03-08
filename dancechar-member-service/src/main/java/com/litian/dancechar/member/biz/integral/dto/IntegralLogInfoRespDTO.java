package com.litian.dancechar.member.biz.integral.dto;

import com.litian.dancechar.framework.common.base.BaseRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 会员积分流水返回对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IntegralLogInfoRespDTO extends BaseRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

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
     * 操作类型，0:加积分 1:扣减积分
     */
    private Integer operateType;

    /**
     * 操作数量
     */
    private Integer operateNum;
}