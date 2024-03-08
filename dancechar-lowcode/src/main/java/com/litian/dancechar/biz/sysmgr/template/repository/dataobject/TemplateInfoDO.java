package com.litian.dancechar.biz.sysmgr.template.repository.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 模板信息管理(TemplateInfo)实体类
 *
 * @author 01406831
 * @since 2021-06-21 13:48:15
 */
@Data
@TableName("fcode_template_info")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class TemplateInfoDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = -30934705783944363L;
    /**
     * 主键
     * 模板编码
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 模板名称
     */
    private String templateName;
    /**
     * 模板描述
     */
    private String templateDesc;
    /**
     * 模板类型 1.应用层；2.微服务层；3.前端
     */
    private Integer templateType;
    /**
     * 包含组件，例如 kafka,redis,sentinel
     */
    private String plugins;
    /**
     * 中间件(多个使用#分隔),比如redis、kafka
     */
    private String middleware;
}