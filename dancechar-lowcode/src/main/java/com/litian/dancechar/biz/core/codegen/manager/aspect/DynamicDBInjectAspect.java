package com.litian.dancechar.biz.core.codegen.manager.aspect;//package com.sf.cemp.fcode.biz.core.codegen.manager.aspect;
//
//import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
//import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
//import com.sf.cemp.fcode.biz.core.codegen.dto.DynamicDBInfo;
//import com.sf.cemp.fcode.biz.sysmgr.dbinfo.manager.DynamicDBManager;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class DynamicDBInjectAspect {
//
//    @Autowired
//    private DynamicRoutingDataSource dynamicRoutingDataSource;
//
//    @Autowired
//    private DynamicDBManager dynamicDBManager;
//
//    @Pointcut("execution(public * com.sf.cemp.fcode.biz.core.codegen.manager.DynamicDBManager.*(..))")
//    public void injectDB() {
//    }
//
//    @Around("injectDB()")
//    public Object inject(ProceedingJoinPoint pjp) {
//        // 从切点获取方法的参数
//        Object[] args = pjp.getArgs();
//        DynamicDBInfo dynamicDBInfo = (DynamicDBInfo)args[0];
//        // 初始化DataSource
//        dynamicDBManager.initDynamicDataSource(dynamicDBInfo);
//        // 上下文存储DataSource对应的key
//        DynamicDataSourceContextHolder.push(dynamicDBInfo.getDsKey());
//        Object obj = null;
//        try {
//            obj = pjp.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        // 使用完清理相应的key
//        DynamicDataSourceContextHolder.clear();
//        return obj;
//    }
//
//    @AfterThrowing("injectDB()")
//    public void failHandle(JoinPoint joinPoint) {
//        // 从切点获取方法的参数清理threadLocalKey
//        DynamicDataSourceContextHolder.clear();
//    }
//
//}
