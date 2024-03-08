package com.litian.dancechar.common.common.json2java;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

public class MyJsonParser implements JsonParse {
    @Override
    public Map<String, Object> toMap(String json) {
        return (Map<String, Object>) JSON.parseObject(json, Map.class);
    }

    @Override
    public List<Object> toArray(String json) {
        return JSON.parseArray(json, Object.class);
    }
}
