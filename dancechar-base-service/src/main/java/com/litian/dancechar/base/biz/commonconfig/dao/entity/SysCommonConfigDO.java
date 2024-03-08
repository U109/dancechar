package com.litian.dancechar.base.biz.commonconfig.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import com.litian.dancechar.framework.encrypt.annotation.EncryptClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公共配置请求DO
 *
 * @author tojson
 * @date 2021/6/19 11:17
 */
@Data
@TableName("sys_common_config")
@EqualsAndHashCode(callSuper = false)
@EncryptClass
public class SysCommonConfigDO extends BaseDO {

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