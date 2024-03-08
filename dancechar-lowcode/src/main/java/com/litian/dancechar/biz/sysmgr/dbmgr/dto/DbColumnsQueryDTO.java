package com.litian.dancechar.biz.sysmgr.dbmgr.dto;

import com.litian.dancechar.common.common.dto.BaseParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 类描述：库表字段查询入参
 *
 * @author 01396106
 * @date 2021/08/04 15:21
 */
@Data
public class DbColumnsQueryDTO {
    /**
     * 表名
     */
    @NotBlank(message = "表名不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private String tableName;
    /**
     * 库名
     */
    @NotBlank(message = "库名不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private String tableSchema;
}
