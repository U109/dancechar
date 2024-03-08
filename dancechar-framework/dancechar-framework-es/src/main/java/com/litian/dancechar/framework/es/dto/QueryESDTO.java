package com.litian.dancechar.framework.es.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryESDTO implements Serializable {

	private static final long serialVersionUID = 5558929402393401136L;

	//分页数量
	@Builder.Default
	private int size = 10000;

	//页码
	@Builder.Default
	private int from = 0;

	//正序
	private String asc;

	//倒序
	private String desc;
	
	//索引
	private String index;
	
	//类型
	private String type;

	private String scrollId;
	
	//and条件
	private Map<String, String> mustMatchParams;
	
	//or条件
	private Map<String, Map<String, Object>> shouldMatchParams;
	
	//数据ID
	private String id;
	
	private Map<String, Object> json;

}
