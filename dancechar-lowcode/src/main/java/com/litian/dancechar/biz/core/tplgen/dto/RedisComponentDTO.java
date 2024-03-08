package com.litian.dancechar.biz.core.tplgen.dto;

import com.litian.dancechar.biz.core.tplgen.common.enums.TplGenParamEnums;
import com.litian.dancechar.common.common.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RedisComponentDTO extends BaseDTO {

    // 部署类别 sentinel cluster
    @Builder.Default
    private String deployType = TplGenParamEnums.RedisDeployTypeEnum.CLUSTER.getCode();
    // 主master sentinel选举后有效
    /**
     * #哨兵监控主redis节点名称，必选
     * spring.redis.sentinel.master=mymaster
     * #哨兵节点
     * spring.redis.sentinel.nodes=127.0.0.1:26379,127.0.0.1:26380
     */
    private List<String> sentinelMasterList;
    // 集群名
    /**
     * spring.redis.cluster.timeout=
     * spring.redis.cluster.nodes=
     */
    private String clusterName;
    // 校验密码
    private String password;
    // 超时
    private Long timeout;

    // 节点集合 sentinel 模式下为 sentinel 节点
    private List<RedisNode> redisNodeList;

}
