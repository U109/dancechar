package com.litian.dancechar.biz.core.tplgen.dto;

import com.litian.dancechar.common.common.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VelContextTableDTO extends BaseDTO {

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

    /**
     * 数据库表名
     */
    private String tableName;

    public String getInstanceName(){
        return className.substring(0,1).toLowerCase()+className.substring(1);
    }
}
