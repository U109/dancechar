package com.litian.dancechar.canal.biz.oplog;

import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志DO
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@TableName("sys_op_log")
@EqualsAndHashCode(callSuper = false)
public class SysOpLogDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单按钮
     */
    private String menuBtn;

    /**
     * 请求url
     */
    private String url;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 请求的类型(Get、POST)
     */
    private String reqType;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 执行是否成功
     */
    private Boolean success;

    /**
     * 执行结果
     */
    private Boolean result;

    /**
     * 操作时间
     */
    private Date opTime;

    /**
     * 操作账号
     */
    private String opAccount;

    /**
     * 操作内容
     */
    private String opContent;

    /**
     * 操作IP
     */
    private String opIp;
}