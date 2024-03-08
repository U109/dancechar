package com.litian.dancechar.biz.core.componentpage.repository.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import lombok.*;
import java.util.Date;


/**
 * 类描述：genFileRenameExtra DO对象
 *
 * @author fcoder
 * @date 2021-06-30 16:42:34
 */
@Data
@TableName("fcode_gen_file_rename_extra")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenFileRenameExtraDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 关联生成主表gen_info的id
     */
    private Long genInfoId;
    /**
     * 关联表gen_db_info主键id
     */
    private Long genDbId;
    /**
     * 关联表gen_db_example_info主键id
     */
    private Long genDbExampleId;
    /**
     * 固定好所有的文件名默认先用classname
     */
    private String renameExtra;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 更新时间
     */
    private Date updateDate;
    /**
     * 是否删除
     */
    private Integer deleteFlag;
}