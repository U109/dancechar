package com.litian.dancechar.examples.excel.util;

import com.litian.dancechar.examples.excel.dao.entity.StudentDO;
import com.litian.dancechar.examples.excel.service.StudentService;
import com.litian.dancechar.framework.excel.core.AbstractExcelImport;

import java.util.List;

/**
 * 学生excel百万数据导入监听
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
public class StudentExcelImport extends AbstractExcelImport<StudentDO> {
    private StudentService studentService;

    public StudentExcelImport(){}

    public StudentExcelImport(StudentService studentService){
        this.studentService = studentService;
    }

    @Override
    protected  void doSaveData(List<StudentDO> data) {
        studentService.saveStuListWithBatch(data);
    }
}
