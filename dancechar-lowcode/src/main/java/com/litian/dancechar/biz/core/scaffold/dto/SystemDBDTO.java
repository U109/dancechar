package com.litian.dancechar.biz.core.scaffold.dto;

import lombok.Data;

/**
 * 类描述：单工程生成入参DB信息
 *
 * @author 01396106
 * @date 2021/08/05 15:21
 */
@Data
public class SystemDBDTO {
    /**
     * 数据库记录id
     */
    private Long systemDbId;
    /**
     * 是否主库 1-是，0-否
     */
    private Integer primaryDb;

    private String dbDriver;

    private String dbName;
}
