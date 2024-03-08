package com.litian.dancechar.framework.log.bizlog.feign;

import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.log.bizlog.dto.SysOpLogReqDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 基础服务feign
 *
 * @author tojson
 * @date 2021/6/19 18:04
 */
@FeignClient("dancechar-base-service")
public interface BaseServiceClient {

    /**
     * 保存操作日志
     */
    @PostMapping("/sys/oplog/saveWithInsert")
    RespResult<Boolean> saveWithInsert(@RequestBody SysOpLogReqDTO req);
}
