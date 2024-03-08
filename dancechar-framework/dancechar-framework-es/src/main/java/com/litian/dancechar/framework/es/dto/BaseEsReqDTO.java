package com.litian.dancechar.framework.es.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ES公共入参
 * @author 01391889
 *
 */
@Data
public class BaseEsReqDTO implements Serializable {

	private static final long serialVersionUID = 7669209658578525915L;

	//索引
	private String index;
	
	//类型
	private String type;
}
