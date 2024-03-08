package com.litian.dancechar.biz.core.scaffold.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 类描述：方法请求对象
 *
 * @author 01410001
 * @date 2021/09/10 16:30
 */
@Data
public class FuncMethodReqDTO implements Serializable {

    @NotNull(message = "工程id不能为空")
    @ApiModelProperty(value = "工程id", name="genInfoId")
    private Long genInfoId;

}
