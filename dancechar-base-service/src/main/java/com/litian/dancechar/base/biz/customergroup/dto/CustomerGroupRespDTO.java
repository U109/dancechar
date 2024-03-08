package com.litian.dancechar.base.biz.customergroup.dto;

import com.litian.dancechar.framework.common.base.BaseRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 客群返回对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerGroupRespDTO extends BaseRespDTO implements Serializable {
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
     * 上传文件的类型
     */
    private Integer importType;

    /**
     * 单个拆分文件数量
     */
    private Long splitTotalCount;

    /**
     * 拆分群数量
     */
    private Long splitGroupNum;

    /**
     * 上传文件的路径
     */
    private String uploadPath;

    /**
     * 上传总数
     */
    private Long uploadTotalCount;

    /**
     * 成功总数
     */
    private Long successTotalCount;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 上传状态(上传中,上传成功,上传失败)
     */
    private Integer uploadStatus;

    /**
     * 备注
     */
    private String remark;
}