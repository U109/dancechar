package com.litian.dancechar.common.common.util;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.litian.dancechar.framework.common.util.DCJSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.annotation.AnnotationUtils;

import javax.ws.rs.Path;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 文档帮助类
 *
 * @author leo
 * @date 2021/7/9 21:04
 */
public class DocumentHelper {

    public static void genDoc(Class clz) {
        Api api = AnnotationUtil.getAnnotation(clz, Api.class);
        if (api == null) {
            return;
        }
        System.out.println(api.value());
        Method[] methods = ReflectUtil.getMethodsDirectly(clz, false);
        for (Method method : methods) {
            if (method == null) {
                continue;
            }
            String reqUrl = "http://www.baidu,com/dancechar/" + api.value();
            Map<String, Object> paramMap = Maps.newConcurrentMap();
            Path methodPath = AnnotationUtils.findAnnotation(method, Path.class);
            if (methodPath != null && StrUtil.isNotEmpty(methodPath.value())) {
                reqUrl += methodPath.value();
                paramMap.put("reqUrl", reqUrl);
            }
            ApiOperation apiOperation = AnnotationUtils.findAnnotation(method, ApiOperation.class);
            if (apiOperation != null) {
                paramMap.put("reqUrlDesc", apiOperation.value());
            }
            if (methodPath != null) {
                parseMethodRequestParam(paramMap, method);
                parseMethodReturnParam(paramMap, method);
            }
            System.out.println(JSONUtil.toJsonStr(paramMap));
        }
    }

    public static void parseMethodReturnParam(Map<String, Object> paramMap, Method method) {
        Type returnType = method.getGenericReturnType();
        if (returnType instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) returnType).getRawType();
            if (!StrUtil.equals(rawType.getTypeName(), "com.sf.framework.domain.Result")) {
                return;
            }
            System.out.println(rawType.getTypeName());
            Type[] types = ((ParameterizedType) returnType).getActualTypeArguments();
            for (Type type : types) {
                System.out.println(JSONUtil.toJsonStr(type.getTypeName()));
            }
        }
    }

    public static void parseMethodRequestParam(Map<String, Object> paramMap, Method method) {
        Class[] paramClass = method.getParameterTypes();
        if (paramClass != null && paramClass.length > 0) {
            ApiModel apiModel = AnnotationUtil.getAnnotation(paramClass[0], ApiModel.class);
            if (apiModel != null) {
                System.out.println(apiModel.description());
                paramMap.put("reqMethodParamName", apiModel.description());
            }
            Field[] fields = ReflectUtil.getFieldsDirectly(paramClass[0], false);
            if (fields != null && fields.length > 0) {
                List<Map<String, String>> filedList = Lists.newArrayList();
                for (Field field : fields) {
                    ApiModelProperty apiModelProperty = AnnotationUtils.findAnnotation(field, ApiModelProperty.class);
                    if (apiModelProperty != null) {
                        Map<String, String> filedMap = Maps.newConcurrentMap();
                        filedMap.put("fieldName", field.getName());
                        filedMap.put("filedType", field.getType().getSimpleName());
                        filedMap.put("filedTxt", apiModelProperty.value());
                        //System.out.println(field.getName() + ":" + apiModelProperty.value() + ":" + apiModelProperty.name());\
                        filedList.add(filedMap);
                    }
                }
                paramMap.put("reqParamList", filedList);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        //Set<Class<?>> set = ClassUtil.scanPackage("com.sf.cemp.fcode.biz.personinfo");
        //set.stream().forEach(clazz -> genDoc(clazz));
        Class cls = Class.forName("com.sf.cemp.fcode.biz.sysmgr.template.service.TemplateInfoService");
        Method[] methods = ReflectUtil.getMethods(cls);
        for (Method method : methods) {
            Class[] paramClass = method.getParameterTypes();
            if (paramClass != null && paramClass.length > 0) {
                Field[] fields = ReflectUtil.getFieldsDirectly(paramClass[0], false);
                if (fields != null && fields.length > 0) {
                    List<Map<String, String>> filedList = Lists.newArrayList();
                    for (Field field : fields) {
                        ApiModelProperty apiModelProperty = AnnotationUtils.findAnnotation(field, ApiModelProperty.class);
                        if (apiModelProperty != null) {
                            Map<String, String> filedMap = Maps.newConcurrentMap();
                            filedMap.put("fieldName", field.getName());
                            filedMap.put("filedType", field.getType().getSimpleName());
                            filedMap.put("filedTxt", apiModelProperty.value());
                            //System.out.println(field.getName() + ":" + apiModelProperty.value() + ":" + apiModelProperty.name());\
                            filedList.add(filedMap);
                        }
                    }
                    System.out.println(DCJSONUtil.toJsonStr(filedList));
                }
            }
        }
    }
}