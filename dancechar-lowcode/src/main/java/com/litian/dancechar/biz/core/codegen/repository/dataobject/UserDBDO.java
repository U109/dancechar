package com.litian.dancechar.biz.core.codegen.repository.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import lombok.*;

import java.util.Date;


/**
 * 类描述：userDB DO对象
 *
 * @author terryhl
 * @date 2021-06-15 14:43:47
 */
@Data
@TableName("fcode_code_gen_user_db")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDBDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 所属用户id
     */
    private String userId;
    /**
     * 数据库标识code
     */
    private String dbCode;
    /**
     * 数据库驱动
     */
    private String dbDriver;
    /**
     * 数据库ip
     */
    private String dbHost;
    /**
     * 数据库port
     */
    private String dbPort;
    /**
     * 数据库连接url
     */
    private String dbUrl;
    /**
     * 账户
     */
    private String dbUsername;
    /**
     * 密码
     */
    private String dbPassword;
    /**
     * 数据库名
     */
    private String dbName;
    /**
     * 数据库描述用于展示
     */
    private String dbDesc;
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