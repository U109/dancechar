package com.litian.dancechar.biz.core.tplgen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类描述：Redis节点信息
 * @author 01396106
 * @date 2021/07/06 17:33
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RedisNode {

    private Integer id;
    private String host;
    private String port;
    // 临时解决xml velocity嵌套
    private String xmlHostKey;
    // 临时解决xml velocity嵌套
    private String xmlPortKey;

    public String getXmlHostKey(){
        return "${cps.redis.cluster.node"+this.id+"Host}";
    }

    public String getXmlPortKey(){
        return "${cps.redis.cluster.node"+this.id+"Port}";
    }
}
