package com.litian.dancechar.common.common.json2java;

import java.io.IOException;
import java.util.Map;

public interface BeanGeneratorToZip {

    void writeBean(String paramString, Map<String, Object> paramMap,Map<String, byte[]> datas) throws IOException;

    void writeList(String paramString,Map<String, byte[]> datas) throws IOException;
}
