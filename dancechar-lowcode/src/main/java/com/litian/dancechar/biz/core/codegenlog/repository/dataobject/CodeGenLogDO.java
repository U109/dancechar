package com.litian.dancechar.biz.core.codegenlog.repository.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import lombok.*;
import java.util.Date;


/**
 * 类描述：codeGenLog DO对象
 *
 * @author fcoder
 * @date 2021-07-05 10:07:23
 */
@Data
@TableName("fcode_gen_log")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CodeGenLogDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 代码生产量(行)
     */
    private Long codeTotalNum;
    /**
     * 方法数
     */
    private Integer toolMethodNum;
    /**
     * 所有库名
     */
    private String databaseName;
    /**
     * 所有表名
     */
    private String tableName;


    private String systemCode;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改时间
     */
    private Date updateDate;
    /**
     * 删除标识-0: 未删除 1-已删除
     */
    private Integer deleteFlag;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 更新人
     */
    private String updateUser;
}
