package com.litian.dancechar.base.biz.customergroup.dto;

import com.litian.dancechar.framework.common.base.BaseRespDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * 父客群DTO
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
public class CustomerGroupParentInfoRespDTO extends BaseRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 上传总数
     */
    private Long uploadTotalCount;

    /**
     * 单个拆分文件数量
     */
    private Long splitTotalCount;

    /**
     * 拆分群数量
     */
    private Long splitGroupNum;
}