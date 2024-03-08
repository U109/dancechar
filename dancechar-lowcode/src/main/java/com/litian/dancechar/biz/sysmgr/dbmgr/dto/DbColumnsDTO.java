package com.litian.dancechar.biz.sysmgr.dbmgr.dto;

import lombok.Data;

/**
 * 类描述：库表字段和描述信息
 *
 * @author 01396106
 * @date 2021/08/04 15:21
 */
@Data
public class DbColumnsDTO {
    private String columnName;
    private String columnComment;
}
