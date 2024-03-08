package com.litian.dancechar.examples.excel.controller;

import cn.hutool.core.convert.Convert;
import com.litian.dancechar.examples.excel.dao.entity.StudentDO;
import com.litian.dancechar.examples.excel.dto.StudentReqDTO;
import com.litian.dancechar.examples.excel.util.StudentExcelExport;
import com.litian.dancechar.examples.excel.util.StudentExcelImport;
import com.litian.dancechar.examples.excel.service.StudentService;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.base.RespResultCode;
import com.litian.dancechar.framework.excel.util.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 学生业务处理
 *
 * @author tojson
 * @date 2022/7/9 06:26
 */
@Api(tags = "学生相关api")
@RestController
@Slf4j
@RequestMapping(value = "/student/")
public class StudentController {
    @Resource
    private StudentService studentService;

    @Resource
    private StudentExcelExport studentExcelExport;

    @ApiOperation(value = "从excel读取百万数据导入到db", notes = "从excel读取百万数据导入到db")
    @PostMapping("importBigData")
    public RespResult<Boolean> importData(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request){
        try{
            long start = System.currentTimeMillis();
            Integer sheetNo = Convert.toInt(request.getParameter("sheetNo"), 0);
            ExcelUtil.readExcel(multipartFile.getInputStream(), StudentDO.class,
                                new StudentExcelImport(studentService), sheetNo);
            log.info("本次导入100w数据，总耗时:{}ms", (System.currentTimeMillis() -start));
            return RespResult.success(true);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            return RespResult.error(RespResultCode.IO_EXCEPTION);
        }
    }
    @ApiOperation(value = "导出百万数据到excel", notes = "导出百万数据到excel")
    @GetMapping("exportBigData")
    public void exportData(HttpServletResponse response,
                           @RequestParam(required = false) String no,
                           @RequestParam(required = false) String name){
        StudentReqDTO reqDTO = new StudentReqDTO();
        reqDTO.setNo(no);
        reqDTO.setName(name);
        studentExcelExport.exportWithBigData(response, "学生列表", reqDTO);
    }

    @ApiOperation(value = "导出小数据量数据到excel", notes = "导出小数据量数据到excel")
    @GetMapping("exportSmallData")
    public void exportSmallData(HttpServletResponse response,
                           @RequestParam(required = false) String no,
                           @RequestParam(required = false) String name){
        StudentReqDTO reqDTO = new StudentReqDTO();
        reqDTO.setNo(no);
        reqDTO.setName(name);
        studentExcelExport.exportWithSmallData(response, "学生列表", reqDTO);
    }

    /**
     * 循环设置要添加的数据，最终封装到list集合中
     */
    private static List<StudentDO> getData(){
        List<StudentDO> demoData = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            StudentDO data = new StudentDO();
            data.setNo("100"+i);
            data.setName("Eric" + i);
            demoData.add(data);
        }
        return demoData;
    }

    /*
    public static void main(String[] args) {
        //实现excel写的操作
        //1、设置写入文件夹地址和excel文件名称
        String fileName = "/Users/guohg/project/dancechar/write.xlsx";
        //2、调用EasyExcel里面方法实现写的操作
        //write两个参数：参数1：文件路径名称   参数2：参数实体类class
        EasyExcel.write(fileName, StudentDO.class).sheet("学生列表").doWrite(getData());
    }
     */
}