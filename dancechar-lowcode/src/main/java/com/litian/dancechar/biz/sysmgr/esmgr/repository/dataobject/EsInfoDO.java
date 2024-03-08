package com.litian.dancechar.biz.sysmgr.esmgr.repository.dataobject;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.litian.dancechar.framework.common.util.YYYYMMDDHHMMSSDateAdapter;
import lombok.*;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

import static com.litian.dancechar.framework.common.util.DCDateUtil.YYYY_MM_DD_HH_MM_SS;


/**
 * 类描述：es连接信息管理DO对象
 *
 * @author 01406831
 * @date 2021-11-04 15:28:33
 */
@Data
@TableName("monitor_es_info")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EsInfoDO {

        /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
        /**
     * es版本(5.x或7.x)
     */
    private String esVersion;
        /**
     * es名称
     */
    private String name;
        /**
     * 系统名称
     */
    private String clusterName;
        /**
     * es地址(类似ip:port)
     */
    private String esAddr;
        /**
     * es用户名(7.x以后需要)
     */
    private String esUserName;
        /**
     * es用户密码(7.x以后需要)
     */
    private String esUserPwd;
        /**
     * 备注
     */
    private String remark;
        /**
     * 创建时间
     */
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    @JSONField(format = YYYY_MM_DD_HH_MM_SS)
    private Date createDate;
        /**
     * 修改时间
     */
    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    @JSONField(format = YYYY_MM_DD_HH_MM_SS)
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