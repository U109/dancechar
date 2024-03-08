package com.litian.dancechar.common.common.json2java;

import java.io.IOException;
import java.util.Map;

public interface BeanGenerator {

    void writeBean(String paramString, Map<String, Object> paramMap) throws IOException;

    void writeList(String paramString) throws IOException;
}
