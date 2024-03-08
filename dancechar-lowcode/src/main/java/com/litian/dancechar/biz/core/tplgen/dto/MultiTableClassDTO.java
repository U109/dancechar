package com.litian.dancechar.biz.core.tplgen.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MultiTableClassDTO implements Serializable {

    /**
     * 类名
     */
    private String className;

    /**
     * 实例名
     */
    private String instanceName;
    /**
     * 功能目录 tplgen.xxx
     */
    private String functionDir;

    public String getInstanceName() {
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }
}
