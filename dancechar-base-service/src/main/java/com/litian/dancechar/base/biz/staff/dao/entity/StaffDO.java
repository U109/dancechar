package com.litian.dancechar.base.biz.staff.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import com.litian.dancechar.framework.encrypt.annotation.EncryptClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 员工表DO
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@TableName("t_staff")
@EqualsAndHashCode(callSuper = false)
@EncryptClass
public class StaffDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 员工工号
     */
    private String no;

    /**
     * 员工姓名
     */
    private String name;
}