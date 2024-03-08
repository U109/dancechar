package com.litian.dancechar.biz.sysmgr.esmgr.repository.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import com.litian.dancechar.framework.common.util.YYYYMMDDHHMMSSDateAdapter;
import lombok.*;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;


/**
 * 类描述：ES连接信息管理DO对象
 *
 * @author 01396106
 * @date 2021-10-18 13:39:03
 */
@Data
@TableName("fcode_es_mgr")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EsMgrDO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 集群名
     */
    private String clusterName;
    /**
     * es地址
     */
    private String esAddr;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    private Date createDate;
    /**
     * 修改时间
     */
    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    private Date updateDate;
    /**
     * 删除标识-0: 未删除 1-已删除
     */
    @TableField(value = "delete_flag", fill = FieldFill.INSERT)
    @TableLogic
    private Integer deleteFlag;
    /**
     * 创建人
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;
    /**
     * 更新人
     */
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private String updateUser;
}