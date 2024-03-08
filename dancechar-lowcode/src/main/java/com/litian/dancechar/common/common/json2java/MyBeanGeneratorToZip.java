package com.litian.dancechar.common.common.json2java;


import com.litian.dancechar.framework.common.context.ContextHoldUtil;
import com.litian.dancechar.framework.common.util.DCDateUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MyBeanGeneratorToZip implements BeanGeneratorToZip {
    String packName;

    public MyBeanGeneratorToZip(String packName) {
        this.packName = packName;
    }

    @Override
    public void writeBean(String className, Map<String, Object> map, Map<String, byte[]> datas) throws IOException {

        StringBuilder bw = new StringBuilder();
        bw.append("package ");
        bw.append(this.packName);
        bw.append(";\n");
        bw.append("import java.util.List;\n\n");
        bw.append("/**\n");
        bw.append(" * " + className + "实体对象\n");
        bw.append(" *\n");
        bw.append(" * @author " + ContextHoldUtil.getEmpNum() + "\n");
        bw.append(" * @date " + DCDateUtil.getCurrentDateString() + "\n");
        bw.append(" */\n");
        bw.append("public class ");
        bw.append(className);
        bw.append("{\n");
        map = sortMapByKey(map);
        Set<Map.Entry<String, Object>> set = map.entrySet();
        for (Map.Entry<String, Object> entry : set) {
            bw.append("    ");
            bw.append(entry.getValue().toString());
            bw.append(" ");
            bw.append(entry.getKey());
            bw.append(";\n");
        }
        bw.append("\n");
        set = map.entrySet();
        for (Map.Entry<String, Object> entry : set) {
            bw.append("    public ");
            bw.append(entry.getValue().toString());
            bw.append(" get");
            bw.append(capitalUpperCase(entry.getKey()));
            bw.append("(){\n");
            bw.append("        ");
            bw.append("return ");
            bw.append(entry.getKey());
            bw.append(";\n    }\n\n");
            bw.append("    public void ");
            bw.append("set");
            bw.append(capitalUpperCase(entry.getKey()));
            bw.append("(");
            bw.append(entry.getValue().toString());
            bw.append(" ");
            bw.append(entry.getKey());
            bw.append("){\n");
            bw.append("        ");
            bw.append("this.");
            bw.append(entry.getKey());
            bw.append("=");
            bw.append(entry.getKey());
            bw.append(";\n    }\n");
            bw.append("\n");
        }
        bw.append("}");
        datas.put(className + ".java", bw.toString().getBytes());

    }

    private String capitalUpperCase(String s) {
        char[] chs = s.toCharArray();
        if (chs[0] >= 'a' && chs[0] <= 'z') {
            chs[0] = (char) (chs[0] - 32);
        }
        return new String(chs);
    }

    @Override
    public void writeList(String list, Map<String, byte[]> datas) throws IOException {
        File file = new File("D:/hello/" + this.packName.replace(".", "/"));
        if (!file.exists() || (file.exists() && file.isFile())) {
            file.mkdirs();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(file,
                String.valueOf(list.replaceAll("<|>", "_")) + ".txt")));
        bw.write(list);
        bw.write(";");
        bw.close();
    }

    public Map<String, Object> sortMapByKey(Map<String, Object> oriMap) {
        Map<String, Object> sortedMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String key1, String key2) {
                return key1.compareTo(key2);
            }
        });
        sortedMap.putAll(oriMap);
        return sortedMap;
    }
}
