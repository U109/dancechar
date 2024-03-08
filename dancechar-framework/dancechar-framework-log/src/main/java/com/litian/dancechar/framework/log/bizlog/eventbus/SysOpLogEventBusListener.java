package com.litian.dancechar.framework.log.bizlog.eventbus;

import cn.hutool.core.util.ObjectUtil;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.litian.dancechar.framework.log.bizlog.dto.SysOpLogReqDTO;
import com.litian.dancechar.framework.log.bizlog.feign.BaseServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.EventListener;


/**
 * 系统操作日志异步操作监听
 *
 * @author tojson
 * @date 2021/6/14 21:15
 */
@Slf4j
@Component
public class SysOpLogEventBusListener implements EventListener {
    @Resource
    private BaseServiceClient baseServiceClient;

    @Subscribe
    @AllowConcurrentEvents
    public void listener(SysOpLogEvent sysOpLogEvent){
        SysOpLogReqDTO sysOpLogReqDTO = sysOpLogEvent.getSysOpLogReqDTO();
        if(ObjectUtil.isNotNull(sysOpLogReqDTO)){
            baseServiceClient.saveWithInsert(sysOpLogReqDTO);
        }
    }
}
