package com.litian.dancechar.framework.es.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper=true)
public class QueryESReqDTO extends BaseEsReqDTO implements Serializable {

	private static final long serialVersionUID = -5134881930430342343L;

	//term条件
	private Map<String, String> term;
	
	//terms条件
	private Map<String, String> terms;
	
	//range条件
	private Map<String, Map<String, Object>> range;
	
	//短词匹配
	private Map<String, String> multiMatch;
}
