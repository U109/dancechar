package com.litian.dancechar.biz.core.scaffold.repository.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 脚手架生成-数据库信息(ScaffoldGenDbInfo)实体类
 *
 * @author 01406831
 * @since 2021-06-21 14:33:21
 */
@Data
@TableName("fcode_scaffold_gen_db_info")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScaffoldGenDbInfoDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = -95253727555694762L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 脚手架工程生成-基础信息Id
     */
    private Long scaffoldGenInfoId;

    private Long systemDbId;
    /**
     * 数据库驱动类型,例如mysql
     */
    private String driverClass;
    /**
     * ip/端口
     */
    private String ipPort;
    /**
     * 数据库名
     */
    private String dbName;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 是否主库(0-否 1-是)
     */
    private Integer primaryDb;
}