package com.litian.dancechar.system.biz.user.dto;

import com.litian.dancechar.framework.common.base.BaseRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 系统用户鉴权返回对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemUserAuthRespDTO extends BaseRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * access token
     */
    private String accessToken;

    /**
     * 刷新token
     */
    private String refreshToken;

    /**
     * 用户信息
     */
    private SystemUserRespDTO systemUserRespDTO;

}