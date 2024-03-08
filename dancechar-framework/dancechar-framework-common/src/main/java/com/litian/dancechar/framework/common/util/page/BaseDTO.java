package com.litian.dancechar.framework.common.util.page;

import com.litian.dancechar.framework.common.util.YYYYMMDDHHMMSSDateAdapter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * 类描述: 基础dto
 *
 * @author 01406831
 * @date 2021/04/19 15:16
 */
@Data
@ApiModel("基础dto")
public class BaseDTO {
    /**
     * 创建时间
     */
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    @ApiModelProperty(value = "创建时间", example = "2021-9-15 10:46:39")
    private Date createDate;

    /**
     * 修改时间
     */
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    @ApiModelProperty(value = "修改时间", example = "2021-9-15 10:46:39")
    private Date updateDate;

    /**
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除", example = "0")
    private Integer deleteFlag;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createUser;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private String updateUser;
}
