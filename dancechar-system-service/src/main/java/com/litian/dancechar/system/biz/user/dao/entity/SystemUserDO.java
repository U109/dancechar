package com.litian.dancechar.system.biz.user.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import com.litian.dancechar.framework.encrypt.annotation.EncryptClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 系统用户DO
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@TableName("sys_user_info")
@EqualsAndHashCode(callSuper = false)
@EncryptClass
public class SystemUserDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 账号
     */
    private String accountNo;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 是否黑名单(0-黑名单 1-非黑名单)
     */
    private Integer blackList;
}
