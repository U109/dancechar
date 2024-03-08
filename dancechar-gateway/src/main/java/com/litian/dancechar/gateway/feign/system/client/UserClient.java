package com.litian.dancechar.gateway.feign.system.client;

import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.gateway.feign.system.dto.SystemUserReqDTO;
import com.litian.dancechar.gateway.feign.system.dto.SystemUserRespDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


/**
 * 用户服务-feign
 *
 * @author tojson
 * @date 2021/6/19 18:04
 */
@FeignClient("dancechar-system-service")
public interface UserClient {

    @PostMapping("/user/findBlackList")
    RespResult<List<SystemUserRespDTO>> findBlackList();

    @PostMapping("/user/isBlackList")
    RespResult<Boolean> isBlackList(SystemUserReqDTO req);
    
}
