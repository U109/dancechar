package com.litian.dancechar.framework.log.bizlog.eventbus;

import cn.hutool.core.util.IdUtil;
import com.litian.dancechar.framework.common.eventbus.BaseEvent;
import com.litian.dancechar.framework.log.bizlog.dto.SysOpLogReqDTO;
import lombok.Builder;
import lombok.Getter;

/**
 * 系统操作日志异步事件
 *
 * @author tojson
 * @date 2021/6/14 21:15
 */
@Getter
public class SysOpLogEvent extends BaseEvent {
    /**
     * 操作日志对象
     */
    private SysOpLogReqDTO sysOpLogReqDTO;

    @Builder
    public SysOpLogEvent(SysOpLogReqDTO sysOpLogReqDTO, String eventName) {
        super(IdUtil.objectId(), eventName);
        this.sysOpLogReqDTO = sysOpLogReqDTO;
    }
}
