package com.litian.dancechar.examples.excel.dao.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 学生DO
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@TableName("t_student")
@EqualsAndHashCode(callSuper = false)
@ExcelIgnoreUnannotated
public class StudentDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 工号
     */
    @ExcelProperty("学生编号")
    private String no;

    /**
     * 姓名
     */
    @ExcelProperty("学生姓名")
    private String name;
}