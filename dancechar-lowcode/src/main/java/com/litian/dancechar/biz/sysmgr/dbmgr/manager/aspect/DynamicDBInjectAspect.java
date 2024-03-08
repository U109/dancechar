package com.litian.dancechar.biz.sysmgr.dbmgr.manager.aspect;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.litian.dancechar.biz.sysmgr.dbmgr.dto.DynamicDBInfo;
import com.litian.dancechar.biz.sysmgr.dbmgr.manager.DynamicDBContainer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Aspect
@Component
public class DynamicDBInjectAspect {

    @Resource
    private DynamicRoutingDataSource dynamicRoutingDataSource;

    @Resource
    private DynamicDBContainer dynamicDBContainer;

    @Pointcut("execution(public * com.litian.dancechar.biz.sysmgr.dbmgr.manager.DynamicDBManager.*(..))")
    public void injectDB() {
    }

    @Around("injectDB()")
    public Object inject(ProceedingJoinPoint pjp)throws Exception {
        // 从切点获取方法的参数
        Object[] args = pjp.getArgs();
        DynamicDBInfo dynamicDBInfo = (DynamicDBInfo)args[0];
        // 初始化DataSource
        dynamicDBContainer.initDynamicDataSource(dynamicDBInfo);
        // 上下文存储DataSource对应的key
        DynamicDataSourceContextHolder.push(dynamicDBInfo.getDsKey());
        Object obj = null;
        try {
            obj = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        // 使用完清理相应的key
        DynamicDataSourceContextHolder.clear();
        return obj;
    }

    @AfterThrowing("injectDB()")
    public void failHandle(JoinPoint joinPoint) {
        // 从切点获取方法的参数清理threadLocalKey
        DynamicDataSourceContextHolder.clear();
    }

}
