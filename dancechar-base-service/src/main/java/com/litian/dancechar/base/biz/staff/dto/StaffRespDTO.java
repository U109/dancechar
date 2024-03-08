package com.litian.dancechar.base.biz.staff.dto;

import com.litian.dancechar.framework.common.base.BaseRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 员工返回对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StaffRespDTO extends BaseRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 工号
     */
    private String no;

    /**
     * 姓名
     */
    private String name;
}