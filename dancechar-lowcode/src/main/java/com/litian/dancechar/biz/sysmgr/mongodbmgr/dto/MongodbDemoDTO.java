package com.litian.dancechar.biz.sysmgr.mongodbmgr.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MongodbDemoDTO implements Serializable {

    private String sysCode;

    private String  sysName;

    private String  id;
}
