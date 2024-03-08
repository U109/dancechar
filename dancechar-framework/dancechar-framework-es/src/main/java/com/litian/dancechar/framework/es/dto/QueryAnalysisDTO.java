package com.litian.dancechar.framework.es.dto;

import java.util.Map;

/**
 * 描述：付费会员分析
 *
 * @date: 2019/8/30 15:48
 * @author: venyin[01379223]
 * @since: 1.0
 */
public class QueryAnalysisDTO extends QueryESDTO {

    /**
     * 格式：{"feildName": {"start": "xxxStart", "end": "xxxEnd"}}<br/>
     *
     * 后台会尝试将start和end转换为date类型，如果可以转换则用Date.getTime方法传入<br/>
     *
     * 范围是加上等号的：xxxStart <= fieldName <= xxxEnd
     */
    private Map<String, Map<String, String>> rangeParams;

    public Map<String, Map<String, String>> getRangeParams() {
        return rangeParams;
    }

    public void setRangeParams(Map<String, Map<String, String>> rangeParams) {
        this.rangeParams = rangeParams;
    }

}
