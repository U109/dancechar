package com.litian.dancechar.gateway.feign.system.dto;

import com.litian.dancechar.framework.common.base.BaseRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 系统用户返回对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemUserRespDTO extends BaseRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

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

}