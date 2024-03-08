package com.litian.dancechar.biz.sysmgr.mongodbmgr.repository.dataobject;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.litian.dancechar.framework.common.util.DCDateUtil;
import com.litian.dancechar.framework.common.util.YYYYMMDDHHMMSSDateAdapter;
import lombok.*;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * 类描述：mongodb配置信息DO对象
 *
 * @author 01410001
 * @date 2021-11-08 16:16:16
 */
@Data
@TableName("fcode_mongodb_info")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FcodeMongodbInfoDO {

        /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
        /**
     * mongodb名字
     */
    private String mongodbName;
        /**
     * mongodb数据库
     */
    private String mongodbDatabase;
    /**
     * mongodb host
     */
    private String mongodbHost;
    /**
     * mongodb用户名
     */
    private String mongodbUsername;

    /**
     * mongodb密码
     */
    private String mongodbPassword;
    /**
     * mongodb服务器地址
     */
    private String mongodbUrl;
        /**
     * 创建时间
     */
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    @JSONField(format = DCDateUtil.YYYY_MM_DD_HH_MM_SS)
    private Date createDate;
        /**
     * 创建人
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;
        /**
     * 删除标识-0: 未删除 1-已删除
     */
    @TableField(value = "delete_flag", fill = FieldFill.INSERT)
    @TableLogic
    private Integer deleteFlag;
        /**
     * 修改时间
     */
    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    @JSONField(format = DCDateUtil.YYYY_MM_DD_HH_MM_SS)
    private Date updateDate;
        /**
     * 更新人
     */
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private String updateUser;
}