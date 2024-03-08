package com.litian.dancechar.common.common.json2java;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Json2Bean {

    private String json;

    private JsonParse jsonParse;

    private String name;

    private NameGenerator nameGeneration;

    private BeanGenerator generationBean;

    public Json2Bean(String json, String name, NameGenerator nameGeneration, JsonParse jsonParse, BeanGenerator generationBean) {
        this.json = json;
        this.name = name;
        this.nameGeneration = nameGeneration;
        this.jsonParse = jsonParse;
        this.generationBean = generationBean;
    }

    public String execute() throws IOException {
        if (this.json.startsWith("{")) {
            parseMap();
            return null;
        }
        if (this.json.startsWith("[")) {
            String clz = parseArray();
            if (this.name == null){
                return clz;
            }
            this.generationBean.writeList(clz);
            return clz;
        }
        return null;
    }

    private void parseMap() throws IOException {
        Map<String, Object> map = this.jsonParse.toMap(this.json);
        Iterator<Map.Entry<String, Object>> itr = map.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, Object> entry = itr.next();
            String k = entry.getKey();
            Object v = entry.getValue();
            if (v == null || v.toString().equals("[]")) {
                itr.remove();
                continue;
            }
            if (v instanceof Integer) {
                entry.setValue("Integer");
                continue;
            }
            if (v instanceof java.math.BigDecimal) {
                entry.setValue("Double");
                continue;
            }
            if (v instanceof String) {
                entry.setValue("String");
                continue;
            }
            String childJson = v.toString();
            if (childJson.startsWith("{")) {
                String childName = this.nameGeneration.formatName(k);
                entry.setValue(childName);
                (new Json2Bean(childJson, childName, this.nameGeneration, this.jsonParse, this.generationBean)).execute();
                continue;
            }
            if (childJson.startsWith("[")) {
                String childName = (new Json2Bean(childJson, null, this.nameGeneration, this.jsonParse, this.generationBean)).execute();
                entry.setValue(childName);
                continue;
            }
            entry.setValue("String");
        }
        this.generationBean.writeBean(this.name, map);
    }

    private String parseArray() throws IOException {
        List<Object> list = this.jsonParse.toArray(this.json);
        if (list == null || list.size() == 0){
            return null;
        }
        Object v = list.get(0);
        if (v instanceof Integer) {
            for (int i = 1; i < list.size(); i++) {
                v = list.get(i);
                if (v instanceof java.math.BigDecimal){
                    return "List<Double>";
                }
            }
            return "List<Integer>";
        }
        if (v instanceof java.math.BigDecimal){
            return "List<Double>";
        }
        if (v instanceof String){
            return "List<String>";
        }
        String childJson = v.toString();
        if (childJson.startsWith("{")) {
            String childName = this.nameGeneration.nextName();
            (new Json2Bean(childJson, childName, this.nameGeneration, this.jsonParse, this.generationBean)).execute();
            return "List<" + childName + ">";
        }
        if (childJson.startsWith("[")) {
            String childName = (new Json2Bean(childJson, null, this.nameGeneration, this.jsonParse, this.generationBean)).execute();
            return "List<" + childName + ">";
        }
        return "List<String>";
    }
}