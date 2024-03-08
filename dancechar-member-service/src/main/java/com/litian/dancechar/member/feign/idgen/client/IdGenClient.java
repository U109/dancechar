package com.litian.dancechar.member.feign.idgen.client;

import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.member.feign.idgen.dto.IdGenReqDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 远程id生成服务-feign
 *
 * @author tojson
 * @date 2021/6/19 18:04
 */
@FeignClient("dancechar-id-generator")
public interface IdGenClient {

    @PostMapping("/id/gen/genId")
    RespResult<String> genId(@RequestBody IdGenReqDTO req);
}
