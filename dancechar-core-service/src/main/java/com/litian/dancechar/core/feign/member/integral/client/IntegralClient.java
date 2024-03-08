package com.litian.dancechar.core.feign.member.integral.client;

import com.litian.dancechar.core.feign.member.integral.dto.IntegralLogInfoReqDTO;
import com.litian.dancechar.core.feign.member.integral.dto.IntegralLogInfoRespDTO;
import com.litian.dancechar.core.feign.member.integral.dto.IntegralLogInfoSaveDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 远程积分服务-feign
 *
 * @author tojson
 * @date 2021/6/19 18:04
 */
@FeignClient("dancechar-member-service")
public interface IntegralClient {

    @PostMapping("/member/integral/add")
    RespResult<String> add(@RequestBody IntegralLogInfoSaveDTO req);

    @PostMapping("/member/integral/findByBusinessId")
    RespResult<IntegralLogInfoRespDTO> findByBusinessId(@RequestBody IntegralLogInfoReqDTO req);
}
