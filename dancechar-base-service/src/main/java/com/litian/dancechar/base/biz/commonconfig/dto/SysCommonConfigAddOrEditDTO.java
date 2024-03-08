package com.litian.dancechar.base.biz.commonconfig.dto;

import com.litian.dancechar.framework.common.validator.groups.Update;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 系统公共配置-新增或修改DTO
 *
 * @author tojson
 * @date 2021/6/19 11:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysCommonConfigAddOrEditDTO extends SysCommonDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotBlank(message = "id不能为空", groups = Update.class)
    private String id;
}
