package com.litian.dancechar.framework.es.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zhh.yin
 * @version v.1.0.0
 * @description This's class description
 * @date 2020/6/13 18:31
 */
@Data
public class BatchUpdateEsReqDTO {

    private List<UpdateEsReqDTO> dataList;
}
