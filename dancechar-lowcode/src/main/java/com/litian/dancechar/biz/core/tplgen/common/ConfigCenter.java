package com.litian.dancechar.biz.core.tplgen.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = Integer.MIN_VALUE)
public class ConfigCenter {

    /**
     * 模板跟路径
     */
    @Value("${tpl.root}")
    public String tplRootPath;

//    @Value("${tpl.root}")
//    public void setTplRootPath(String tplRootPathTmp){
//        tplRootPath = tplRootPathTmp;
//    }
}
