package com.litian.dancechar.framework.common.mybatis;

import cn.hutool.core.convert.Convert;
import cn.hutool.db.sql.SqlFormatter;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Intercepts({
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class})})
@Component
@Slf4j
public class MybatisInterceptor implements Interceptor {
    private static final String DEFAULT_SQL_LOG_SHOW_SWITCH = "false";

    @Resource
    private Environment environment;

    public MybatisInterceptor() {
    }

    public Object intercept(Invocation invocation) throws Throwable {
        Boolean enableSqlFormat = Convert.toBool(environment.getProperty("enableSqlFormat"), true);
        if(!enableSqlFormat){
            return invocation.proceed();
        }
        try {
            StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
            MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
            BoundSql boundSql = statementHandler.getBoundSql();
            Configuration configuration = (Configuration) metaObject.getValue("delegate.configuration");
            long start = System.currentTimeMillis();
            Object returnValue = invocation.proceed();
            long end = System.currentTimeMillis();
            long time = end - start;
            String sql = getSql(configuration, boundSql);
            log.info("执行sql-{}", SqlFormatter.format(sql)+ "\n"+"<==执行总耗时【 " + time + " ms】\n");
            if (returnValue instanceof List<?>) {
                List<?> list = (ArrayList<?>) returnValue;
                log.info("返回sql执行结果-返回总行数:{},结果集:{}", list.size(), JSONUtil.toJsonStr(returnValue));
            } else if (returnValue instanceof Integer) {
                log.info("返回sql执行结果-成功执行行数:{}", Convert.toInt(returnValue));
            } else {
                log.info("返回sql执行结果-结果集:{}", JSONUtil.toJsonStr(returnValue));
            }
            return returnValue;
        } catch (Throwable e) {
            log.error("执行sql报错!errMsg:{}", e.getMessage(), e);
            return invocation.proceed();
        }
    }

    /**
     * 获取SQL
     */
    private String getSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterObject == null || parameterMappings.size() == 0) {
            return sql;
        }
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
            sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
        } else {
            MetaObject metaObject = configuration.newMetaObject(parameterObject);
            for (ParameterMapping parameterMapping : parameterMappings) {
                String propertyName = parameterMapping.getProperty();
                if (metaObject.hasGetter(propertyName)) {
                    Object obj = metaObject.getValue(propertyName);
                    sql = sql.replaceFirst("\\?", getParameterValue(obj));
                } else if (boundSql.hasAdditionalParameter(propertyName)) {
                    Object obj = boundSql.getAdditionalParameter(propertyName);
                    sql = sql.replaceFirst("\\?", getParameterValue(obj));
                }
            }
        }
        return sql;
    }

    private String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(obj) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }
}