package com.litian.dancechar.framework.excel.core;

import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.litian.dancechar.framework.excel.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * excel数据导出监听抽象类(采用模版模式, 支持百万数据处理方式)
 *
 * @author tojson
 * @date 2022/7/9 11:26
 */
@Slf4j
public abstract class AbstractExcelExport<T>  {

    /**
     * 导出小数据量(百万以下)
     */
    protected void exportSmallData(HttpServletResponse response, String fileName, List<List<String>> head, Collection<T> data){
        OutputStream outputStream = null;
        try {
            long startTime = System.currentTimeMillis();
            outputStream = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码
            String sheetName = fileName;
            fileName = URLEncoder.encode(fileName+ IdUtil.objectId(), "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName
                    + ExcelTypeEnum.XLSX.getValue());
            ExcelUtil.write(outputStream, sheetName, head, data);
            log.info("导出所用时间:{}", (System.currentTimeMillis() - startTime) / 1000 + "秒");
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    log.error(e.getMessage(),e);
                }
            }
        }
    }

    /**
     * 导出大数据量(百万级别)
     */
    protected void exportWithBigData(HttpServletResponse response, String fileName, List<List<String>> head,
                                  Map<String, Object> queryCondition) {
        // 总的记录数
        Integer totalCount = dataTotalCount();
        // 每一个Sheet存放的数据
        Integer sheetDataRows = eachSheetTotalCount();
        // 每次写入的数据量20w
        Integer writeDataRows = eachTimesWriteSheetTotalCount();
        if(totalCount < sheetDataRows){
            sheetDataRows = totalCount;
        }
        if(sheetDataRows < writeDataRows){
            writeDataRows = sheetDataRows;
        }
        doExport(response, fileName, head, queryCondition, totalCount, sheetDataRows, writeDataRows);
    }

    /**
     * 计算导出数据的总数
     */
    protected abstract Integer dataTotalCount();

    /**
     * 每一个sheet存放的数据总数
     */
    protected abstract Integer eachSheetTotalCount();

    /**
     * 每次写入sheet的总数
     */
    protected abstract Integer eachTimesWriteSheetTotalCount();

    protected abstract void buildDataList(List<List<String>> resultList, Map<String, Object> queryCondition,
                                          Integer pageNo, Integer pageSize);

    private void doExport(HttpServletResponse response, String fileName, List<List<String>> head,
                          Map<String, Object> queryCondition, Integer totalCount, Integer sheetDataRows,
                          Integer writeDataRows){
        OutputStream outputStream = null;
        try {
            long startTime = System.currentTimeMillis();
            outputStream = response.getOutputStream();
            WriteWorkbook writeWorkbook = new WriteWorkbook();
            writeWorkbook.setOutputStream(outputStream);
            writeWorkbook.setExcelType(ExcelTypeEnum.XLSX);
            ExcelWriter writer = new ExcelWriter(writeWorkbook);
            WriteTable table = new WriteTable();
            table.setHead(head);
            // 计算需要的Sheet数量
            int sheetNum = totalCount % sheetDataRows == 0 ? (totalCount / sheetDataRows) : (totalCount / sheetDataRows + 1);
            // 计算一般情况下每一个Sheet需要写入的次数(一般情况不包含最后一个sheet,因为最后一个sheet不确定会写入多少条数据)
            int oneSheetWriteCount = totalCount > sheetDataRows ? sheetDataRows / writeDataRows :
                    totalCount % writeDataRows > 0 ? totalCount / writeDataRows + 1 : totalCount / writeDataRows;
            // 计算最后一个sheet需要写入的次数
            int lastSheetWriteCount = totalCount % sheetDataRows == 0 ? oneSheetWriteCount :
                    (totalCount % sheetDataRows % writeDataRows == 0 ? (totalCount / sheetDataRows / writeDataRows) :
                            (totalCount / sheetDataRows / writeDataRows + 1));
            // 分批查询分次写入
            List<List<String>> dataList = new ArrayList<>();
            for (int i = 0; i < sheetNum; i++) {
                // 创建Sheet
                WriteSheet sheet = new WriteSheet();
                sheet.setSheetNo(i);
                sheet.setSheetName(sheetNum == 1 ? fileName : fileName + i);
                // 循环写入次数: j的自增条件是当不是最后一个Sheet的时候写入次数为正常的每个Sheet写入的次数,如果是最后一个就需要使用计算的次数lastSheetWriteCount
                for (int j = 0; j < (i != sheetNum - 1 || i==0 ? oneSheetWriteCount : lastSheetWriteCount); j++) {
                    // 集合复用,便于GC清理
                    dataList.clear();
                    buildDataList(dataList, queryCondition, j + 1 + oneSheetWriteCount * i, writeDataRows);
                    // 写数据
                    writer.write(dataList, sheet, table);
                }
            }
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String((fileName+ IdUtil.objectId()).getBytes("gb2312"),
                    "ISO-8859-1") + ".xlsx");
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            writer.finish();
            outputStream.flush();
            log.info("导出所用时间:{}", (System.currentTimeMillis() - startTime) / 1000 + "秒");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    log.error(e.getMessage(),e);
                }
            }
        }
    }
}
