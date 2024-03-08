package com.litian.dancechar.biz.sysmgr.redismgr.repository.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import com.litian.dancechar.framework.common.util.YYYYMMDDHHMMSSDateAdapter;
import lombok.*;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;


/**
 * 类描述：Redis信息管理DO对象
 *
 * @author 01396106
 * @date 2021-10-12 11:05:08
 */
@Data
@TableName("fcode_redis_info")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FcodeRedisInfoDO {

    /**
     * 主键，自增长
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 部署方式:sentinel或cluster
     */
    private String installType;
    /**
     * 名称
     */
    private String name;
    /**
     * 集群名
     */
    private String clusterName;
    /**
     * 连接信息
     */
    private String connectInfo;
    /**
     * 超时时间，默认0-表示永不过期
     */
    private Integer timeOut;
    /**
     * 密码
     */
    private String password;
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