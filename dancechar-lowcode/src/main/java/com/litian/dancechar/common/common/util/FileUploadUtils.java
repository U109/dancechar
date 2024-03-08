package com.litian.dancechar.common.common.util;

import com.litian.dancechar.framework.common.util.DCDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public  class FileUploadUtils {
    @Value("${win.system.fileUploadPath}")
    private String winFileUploadPath;

    @Value("${linux.system.fileUploadPath}")
    private String linuxFileUploadPath;

    private static String WIN_FILE_UPLOAD_PATH;
    private static String LINUX_FILE_UPLOAD_PATH;

    private static final String OS_NAME = "os.name";

    private FileUploadUtils() {
    }

    @PostConstruct
    public void init() {
        FileUploadUtils.WIN_FILE_UPLOAD_PATH = winFileUploadPath;
        FileUploadUtils.LINUX_FILE_UPLOAD_PATH = linuxFileUploadPath;
    }
    /***
     * 保存文件
     *
     * @author 01416702
     * @date 2021/10/13 16:13
     * @param savePath  保存路径
     * @param file      文件
     * @return boolean
     */
    public static boolean saveFileUpload(String savePath, File file) {
        try {
            if (StringUtils.isEmpty(savePath)) {
                log.info("savePath is null");
                return false;
            }
            log.info("save file path : " + savePath);
            java.nio.file.Files.copy(file.toPath(), new File(savePath).toPath());
            return true;
        } catch (IOException e) {
            log.error("saveFileUpload error", e);
        }
        return false;
    }
    public static String getPath() {
        String os = System.getProperty(OS_NAME);
        if (os.toLowerCase().startsWith("win")) {
            return WIN_FILE_UPLOAD_PATH;
        } else {
            return LINUX_FILE_UPLOAD_PATH;
        }
    }

    /**
     * 获取文件路径
     * @param fileParent
     * @param fileName
     * @return
     */
    public static String getDefaultSavePath(String fileParent, String fileName) {
        return getNewPath(getPath(), fileParent, fileName);
    }
    /***
     * 生成文件日期目录路径
     *
     * @author 01416702
     * @date 2021/10/13 19:29
     * @param rootPath        nas目录
     * @param parentPath      分类目录
     * @param fileName        文件名
     * @return java.lang.String
     */
    private static String getNewPath(String rootPath, String parentPath, String fileName) {
        StringBuilder path = new StringBuilder(rootPath);
        path.append(File.separator);
        path.append(parentPath);
        path.append(File.separator);
        path.append("fcode");
        path.append(File.separator);
        File destFile = new File(path.toString());
        if (!destFile.exists()) {
            destFile.mkdirs();
        }
        String timeStr = DCDateUtil.getYearsMonthDayHms();
        path.append(timeStr).append((int) ((Math.random() * 9 + 1) * 100));
        path.append(fileName.substring(fileName.lastIndexOf(".")));
        return path.toString();
    }
    /**
     * MultipartFile 转 File
     *@author 01396003
     * @param file
     */
    public static File multipartFileToFile(MultipartFile file) {

        File toFile = null;
        try{
            if (file == null || org.apache.commons.lang3.StringUtils.isBlank(file.getOriginalFilename()) || file.getSize() <= 0) {
                return null;
            } else {
                InputStream ins;
                ins = file.getInputStream();
                toFile = new File(file.getOriginalFilename());
                inputStreamToFile(ins, toFile);
                ins.close();
            }
        }catch (Exception e){
            log.error("multipartFileToFile err", e);
        }
        return toFile;
    }

    public static File MultipartFormDataInputToFile(MultipartFormDataInput formDataInput) {
        File toFile = null;
        try{
            Map<String, List<InputPart>> uploadForm = formDataInput.getFormDataMap();
            InputPart inputPart = uploadForm.get("fileData").get(0);
//            inputPart.setMediaType(MediaType.TEXT_PLAIN_TYPE);
            //获取文件的名称
            String fileNmae = uploadForm.get("fileName").get(0).getBodyAsString();
            //以下的inputStream就是文件的流数据
            InputStream ins = inputPart.getBody(InputStream.class, null);
            toFile = new File(fileNmae);
            inputStreamToFile(ins, toFile);
            ins.close();
        }catch (Exception e){
            log.error("multipartFileToFile err", e);
        }
        return toFile;
    }
    /**
     *  获取文件流
     * @author 01416702
     * @param ins
     * @param file
     */
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
