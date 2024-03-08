package com.litian.dancechar.common.common.json2java;

import java.util.List;
import java.util.Map;

public interface JsonParse {
    Map<String, Object> toMap(String paramString);

    List<Object> toArray(String paramString);
}
