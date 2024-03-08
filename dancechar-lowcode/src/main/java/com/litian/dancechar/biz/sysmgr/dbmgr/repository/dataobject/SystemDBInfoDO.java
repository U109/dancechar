package com.litian.dancechar.biz.sysmgr.dbmgr.repository.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import lombok.*;


/**
 * 类描述：systemDBInfo DO对象
 *
 * @author terryhl
 * @date 2021-06-21 11:48:37
 */
@Data
@TableName("fcode_system_db_info")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SystemDBInfoDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 数据库驱动类型
     */
    private String dbDriver;
    /**
     * 数据库host
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
}