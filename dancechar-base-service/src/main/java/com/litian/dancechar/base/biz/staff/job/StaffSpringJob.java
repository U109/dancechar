package com.litian.dancechar.base.biz.staff.job;

import com.litian.dancechar.base.biz.staff.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class StaffSpringJob {
    @Resource
    private StaffService staffService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void execute() {
        log.info("开始执行xxljob-将员工表数据刷新到redis");
        staffService.refreshDBToRedis();
        log.info("完成xxljob执行-将员工表数据刷新到redis");
    }
}
