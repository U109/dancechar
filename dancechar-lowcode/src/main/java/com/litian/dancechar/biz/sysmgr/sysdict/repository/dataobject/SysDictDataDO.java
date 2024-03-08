package com.litian.dancechar.biz.sysmgr.sysdict.repository.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import com.litian.dancechar.framework.common.util.YYYYMMDDHHMMSSDateAdapter;
import lombok.*;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;


/**
 * 类描述：数据字典数据DO对象
 *
 * @author 01407390
 * @date 2021-09-28 15:16:56
 */
@Data
@TableName("fcode_sys_dict_data")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysDictDataDO {

    /**
     * 主键，自增长
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 数据字典类型id
     */
    private Long typeId;
    /**
     * 字典code
     */
    private String code;
    /**
     * 字典值
     */
    private String value;
    /**
     * 状态 0:禁用 1:启用
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer showNo;
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
    private Date updateDate;
    /**
     * 更新人
     */
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private String updateUser;
}