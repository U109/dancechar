package com.litian.dancechar.examples.excel.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.litian.dancechar.examples.excel.dao.entity.StudentDO;
import com.litian.dancechar.examples.excel.dto.StudentReqDTO;
import com.litian.dancechar.examples.excel.service.StudentService;
import com.litian.dancechar.framework.excel.core.AbstractExcelExport;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 学生数据导入excel监听(支持百万数据导入导出)
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Component
public class StudentExcelExport extends AbstractExcelExport<StudentDO> {
    @Resource
    private StudentService studentService;

    /**
     * 百万数据导出excel
     */
    public void exportWithBigData(HttpServletResponse response, String fileName, StudentReqDTO reqDTO){
        List<List<String>> head = new ArrayList<List<String>>();
        head.add(Collections.singletonList("学生编号"));
        head.add(Collections.singletonList("学生姓名"));
        this.exportWithBigData(response, fileName, head, BeanUtil.beanToMap(reqDTO));
    }

    /**
     * 小数据量数据导出excel
     */
    public void exportWithSmallData(HttpServletResponse response, String fileName, StudentReqDTO reqDTO){
        List<List<String>> head = new ArrayList<List<String>>();
        head.add(Collections.singletonList("学生编号"));
        head.add(Collections.singletonList("学生姓名"));
        this.exportSmallData(response, fileName, head, studentService.findList(BeanUtil.beanToMap(reqDTO)));
    }

    @Override
    protected Integer dataTotalCount() {
        return studentService.getTotalCount();
    }

    @Override
    protected Integer eachSheetTotalCount() {
        return 500000;
    }

    @Override
    protected Integer eachTimesWriteSheetTotalCount() {
        return 5000;
    }

    @Override
    protected void buildDataList(List<List<String>> resultList, Map<String, Object> queryCondition,
                                 Integer pageNo, Integer pageSize) {
        List<StudentDO> studentList = studentService.listPage(queryCondition, pageNo, pageSize);
        if(CollUtil.isNotEmpty(studentList)){
            studentList.forEach(item -> {
                resultList.add(Arrays.asList(item.getNo(), item.getName()));
            });
        }
    }
}
