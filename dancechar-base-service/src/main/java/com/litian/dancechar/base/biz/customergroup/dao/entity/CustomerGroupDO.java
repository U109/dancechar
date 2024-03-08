package com.litian.dancechar.base.biz.customergroup.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import com.litian.dancechar.framework.encrypt.annotation.EncryptClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 客群基本表DO
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@TableName("customer_group_info")
@EqualsAndHashCode(callSuper = false)
@EncryptClass
public class CustomerGroupDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 父客群Id
     */
    private String customerGroupParentId;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 上传文件的类型
     */
    private Integer importType;

    /**
     * 上传文件的路径
     */
    private String uploadPath;

    /**
     * 上传总数
     */
    private Long uploadTotalCount;

    /**
     * 成功总数
     */
    private Long successTotalCount;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 上传状态(上传中,上传成功,上传失败)
     */
    private Integer uploadStatus;

    /**
     * 备注
     */
    private String remark;
}