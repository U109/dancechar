package com.litian.dancechar.framework.excel.util;

import com.alibaba.excel.EasyExcel;
import com.litian.dancechar.framework.excel.core.AbstractExcelImport;
import lombok.extern.slf4j.Slf4j;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;


@Slf4j
public class ExcelUtil {

    /**
     * 从Excel中指定sheet读取文件
     */
    public static <T> void readExcel(final InputStream inputStream, Class<?> clazz,
                                     AbstractExcelImport<T> listener, Integer sheetNo) {
        if(sheetNo != null){
            EasyExcel.read(inputStream, clazz, listener).sheet(sheetNo).doRead();
        }
    }

    /**
     * 从Excel中读取文件(第一个sheet)
     */
    public static <T> void readExcel(final InputStream inputStream, Class<?> clazz, AbstractExcelImport<T> listener) {
        EasyExcel.read(inputStream, clazz, listener).sheet().doRead();
    }

    /**
     * 写入到指定excel文件
     */
    public static <T> void write(String fileName, String sheetName, Class<T> head, Collection<T> datas){
        EasyExcel.write(fileName).head(head).sheet(sheetName).doWrite(datas);
    }

    /**
     * 写入到指定excel的sheet文件
     */
    public static <T> void write(OutputStream outputStream, String sheetName, Class<T> head, Collection<T> data){
        EasyExcel.write(outputStream).head(head).sheet(sheetName).doWrite(data);
    }

    /**
     * 写入到指定excel的sheet文件
     */
    public static <T> void write(OutputStream outputStream, Integer sheetNo, String sheetName, Class<T> head, Collection<T> data){
        EasyExcel.write(outputStream).head(head).sheet(sheetNo).sheetName(sheetName).doWrite(data);
    }

    /**
     * 写入到指定excel文件
     */
    public static <T> void write(String fileName, String sheetName, List<List<String>> head, Collection<T> datas){
        EasyExcel.write(fileName).head(head).sheet(sheetName).doWrite(datas);
    }

    /**
     * 写入到指定excel的sheet文件
     */
    public static <T> void write(OutputStream outputStream, String sheetName, List<List<String>> head, Collection<T> data){
        EasyExcel.write(outputStream).head(head).sheet(sheetName).doWrite(data);
    }

    /**
     * 写入到指定excel的sheet文件
     */
    public static <T> void write(OutputStream outputStream, Integer sheetNo, String sheetName, List<List<String>> head,
                                 Collection<T> data){
        EasyExcel.write(outputStream).head(head).sheet(sheetNo).sheetName(sheetName).doWrite(data);
    }
}
