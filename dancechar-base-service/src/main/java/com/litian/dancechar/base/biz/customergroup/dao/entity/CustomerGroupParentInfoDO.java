package com.litian.dancechar.base.biz.customergroup.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import com.litian.dancechar.framework.encrypt.annotation.EncryptClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 父客群基本表DO
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@TableName("customer_group_parent_info")
@EqualsAndHashCode(callSuper = false)
@EncryptClass
public class CustomerGroupParentInfoDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 上传总数
     */
    private Long uploadTotalCount;

    /**
     * 单个拆分文件数量
     */
    private Long splitTotalCount;

    /**
     * 拆分群数量
     */
    private Long splitGroupNum;
}