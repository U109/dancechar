package com.litian.dancechar.base.biz.commonconfig.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 系统公共配置-分页请求对象
 *
 * @author tojson
 * @date 2021/6/19 11:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysCommonConfigReqDTO extends BasePage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 配置类型(不同的业务不一样)
     */
    private String configType;

    /**
     * 配置key
     */
    private String configKey;

    /**
     * 配置value
     */
    private String configValue;

    /**
     * 备注
     */
    private String remark;

    /**
     * 审批状态(0-待审核 1-审核通过 2-审核不通过）
     */
    private String auditStatus;
}