package com.litian.dancechar.core.biz.activity.act20220910.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 会员信息DO
 *
 * @author tojson
 * @date 2022/9/5 06:18
 */
@Data
public class CustomerInfoReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 客户手机号
     */
    private String mobile;

    /**
     * 姓名
     */
    private String realName;
}