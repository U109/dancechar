package com.litian.dancechar.biz.core.scaffold.dto;

import com.litian.dancechar.biz.core.tplgen.dto.TplGenTableColumnDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述：方法响应对象
 *
 * @author 01410001
 * @date 2021/09/12 15:40
 */
@Data
@AllArgsConstructor
@Builder
public class FuncMethodRespDTO implements Serializable {

    @ApiModelProperty(value = "方法类型", name="methodType")
    private String methodType;

    @ApiModelProperty(value = "方法名", name="methodName")
    private String methodName;

    @ApiModelProperty(value = "方法路径", name="methodUrl")
    private String methodUrl;

    @ApiModelProperty(value = "参数列表", name="tableField")
    private List<TplGenTableColumnDTO> tableField;
}
