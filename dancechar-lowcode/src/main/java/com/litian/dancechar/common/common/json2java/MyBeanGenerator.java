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

public class MyBeanGenerator implements BeanGenerator {
    String packName;

    public MyBeanGenerator(String packName) {
        this.packName = packName;
    }

    @Override
    public void writeBean(String className, Map<String, Object> map) throws IOException {
        File file = new File("D:/hello/" + this.packName.replace(".", "/"));
        if (!file.exists() || (file.exists() && file.isFile())) {
            file.mkdirs();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(file, String.valueOf(className) + ".java")));
        bw.write("package ");
        bw.write(this.packName);
        bw.write(";\n");
        bw.write("import java.util.List;\n\n");
        bw.write("/**\n");
        bw.write(" * " + className + "实体对象\n");
        bw.write(" *\n");
        bw.write(" * @author " + ContextHoldUtil.getEmpNum() + "\n");
        bw.write(" * @date " + DCDateUtil.getCurrentDateString() + "\n");
        bw.write(" */\n");
        bw.write("public class ");
        bw.write(className);
        bw.write("{\n");
        map = sortMapByKey(map);
        Set<Map.Entry<String, Object>> set = map.entrySet();
        for (Map.Entry<String, Object> entry : set) {
            bw.write("    ");
            bw.write(entry.getValue().toString());
            bw.write(" ");
            bw.write(entry.getKey());
            bw.write(";\n");
        }
        bw.write("\n");
        set = map.entrySet();
        for (Map.Entry<String, Object> entry : set) {
            bw.write("    public ");
            bw.write(entry.getValue().toString());
            bw.write(" get");
            bw.write(capitalUpperCase(entry.getKey()));
            bw.write("(){\n");
            bw.write("        ");
            bw.write("return ");
            bw.write(entry.getKey());
            bw.write(";\n    }\n\n");
            bw.write("    public void ");
            bw.write("set");
            bw.write(capitalUpperCase(entry.getKey()));
            bw.write("(");
            bw.write(entry.getValue().toString());
            bw.write(" ");
            bw.write(entry.getKey());
            bw.write("){\n");
            bw.write("        ");
            bw.write("this.");
            bw.write(entry.getKey());
            bw.write("=");
            bw.write(entry.getKey());
            bw.write(";\n    }\n");
            bw.write("\n");
        }
        bw.write("}");
        bw.close();
    }

    private String capitalUpperCase(String s) {
        char[] chs = s.toCharArray();
        if (chs[0] >= 'a' && chs[0] <= 'z') {
            chs[0] = (char) (chs[0] - 32);
        }
        return new String(chs);
    }

    @Override
    public void writeList(String list) throws IOException {
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
