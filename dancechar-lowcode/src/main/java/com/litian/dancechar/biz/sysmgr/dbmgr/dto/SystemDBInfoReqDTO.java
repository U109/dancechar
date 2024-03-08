package com.litian.dancechar.biz.sysmgr.dbmgr.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SystemDBInfoReqDTO implements Serializable {

    /**
     * 配置的工程id
     */
    private Long systemInfoId;
    /**
     * 1: 脚手架工程生成 2: 单功能生成单表 3：单功能多表
     */
    private Integer scaffoldType;
}
