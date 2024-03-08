package com.litian.dancechar.common.common.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

/**
 * 基础DTO 包含通用方法
 * @author 01410377
 */
public class BaseDTO implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private static SerializerFeature[] serializerFeatureArr = new SerializerFeature[]{SerializerFeature.WriteEnumUsingToString, SerializerFeature.WriteDateUseDateFormat};

    @Override
    public String toString(){
        return JSON.toJSONString(this, serializerFeatureArr);
    }

    @Override
    public Object clone(){
        return SerializationUtils.clone(this);
    }

    /**
     * json字符串转对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz){
        return JSON.parseObject(json, clazz);
    }

}
