package com.litian.dancechar.base.biz.staff.job;

import com.litian.dancechar.base.biz.staff.service.StaffService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class StaffXxlJob {
    @Resource
    private StaffService staffService;

    @XxlJob(value = "employeeXxlJobHandler")
    public void execute() throws Exception {
        log.info("开始执行xxljob-将员工表数据刷新到redis");
        staffService.refreshDBToRedis();
        log.info("完成xxljob执行-将员工表数据刷新到redis");
    }
}
