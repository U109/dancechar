package com.litian.dancechar.biz.core.tabledetailinfo.repository.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import com.litian.dancechar.framework.common.util.YYYYMMDDHHMMSSDateAdapter;
import lombok.*;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
    

/**
 * 类描述：代码生成-表关联字段信息表DO对象
 *
 * @author 853523
 * @date 2021-09-22 10:30:53
 */
@Data
@TableName("fcode_scaffold_gen_db_table_detail_info")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TableDetailInfoDO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 脚手架工程生成-基础信息Id
     */
    private Long scaffoldGenInfoId;
    /**
     * 数据字典id
     */
    private Long sysDictId;
    /**
     * 数据库字段名
     */
    private String columnName;
    /**
     * 数据库中类型（物理类型）
     */
    public String dataType;
    /**
     * 首字母大写名称（用于代码生成get set方法）
     */
    public String columnKeyName;
    /**
     * 主外键
     */
    public String columnKey;
    /**
     * java字段
     */
    private String javaColumns;
    /**
     * java类型
     */
    private String javaType;
    /**
     * 字段描述
     */
    private String columnComment;
    /**
     * 显示类型
     */
    private String showType;
    /**
     * 是否在查询列表显示 0:否 1:是
     */
    private Integer queryListShow;
    /**
     * 查询列表显示的顺序号
     */
    private Integer queryListNo;
    /**
     * 是否查询条件 0:否 1:是
     */
    private Integer queryCondition;
    /**
     * 查询条件显示的顺序号
     */
    private Integer queryConditionNo;
    /**
     * 查询字段是否模糊 0:否 1:是
     */
    private Integer queryConditionLike;
    /**
     * 是否在增/改页面显示 0:否 1:是
     */
    private Integer showAddUpdate;
    /**
     * 增/改页面显示的顺序号
     */
    private Integer addUpdateNo;
    /**
     * 增/改页面是否必填 0:否 1:是
     */
    private Integer addUpdateRequire;
    /**
     * 增/改页面必填提示的内容
     */
    private String addUpdateValidateTips;
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