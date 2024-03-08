package com.litian.dancechar.common.common.json2java;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Json2BeanZip {

    private String json;

    private JsonParse jsonParse;

    private String name;

    private NameGenerator nameGeneration;

    private BeanGeneratorToZip generationBeanToZip;

    public Json2BeanZip(String json, String name, NameGenerator nameGeneration, JsonParse jsonParse, BeanGeneratorToZip generationBeanToZip) {
        this.json = json;
        this.name = name;
        this.nameGeneration = nameGeneration;
        this.jsonParse = jsonParse;
        this.generationBeanToZip = generationBeanToZip;
    }

    public String execute(Map<String, byte[]> datas) throws IOException {
        if (this.json.startsWith("{")) {
            parseMap(datas);
            return null;
        }
        if (this.json.startsWith("[")) {
            String clz = parseArray(datas);
            if (this.name == null){
                return clz;
            }
            this.generationBeanToZip.writeList(clz,datas);
            return clz;
        }
        return null;
    }

    private void parseMap(Map<String, byte[]> datas) throws IOException {
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
                (new Json2BeanZip(childJson, childName, this.nameGeneration, this.jsonParse, this.generationBeanToZip)).execute(datas);
                continue;
            }
            if (childJson.startsWith("[")) {
                String childName = (new Json2BeanZip(childJson, null, this.nameGeneration, this.jsonParse, this.generationBeanToZip)).execute(datas);
                entry.setValue(childName);
                continue;
            }
            entry.setValue("String");
        }
        this.generationBeanToZip.writeBean(this.name, map,datas);
    }

    private String parseArray(Map<String, byte[]> datas) throws IOException {
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
            (new Json2BeanZip(childJson, childName, this.nameGeneration, this.jsonParse, this.generationBeanToZip)).execute(datas);
            return "List<" + childName + ">";
        }
        if (childJson.startsWith("[")) {
            String childName = (new Json2BeanZip(childJson, null, this.nameGeneration, this.jsonParse, this.generationBeanToZip)).execute(datas);
            return "List<" + childName + ">";
        }
        return "List<String>";
    }
}