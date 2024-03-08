package com.litian.dancechar.framework.es.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper=true)
public class UpdateEsReqDTO extends BaseEsReqDTO implements Serializable {

	private static final long serialVersionUID = -2256997304833573829L;

	//ES生成的唯一ID
	private String id;
	
	//需要修改的数据JSON
	private Map<String, Object> json;
}
