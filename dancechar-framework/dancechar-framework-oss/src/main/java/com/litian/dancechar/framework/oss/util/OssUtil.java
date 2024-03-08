package com.litian.dancechar.framework.oss.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import com.litian.dancechar.framework.common.util.DCDateUtil;
import com.litian.dancechar.framework.common.util.DCStrUtil;
import com.litian.dancechar.framework.oss.config.OssConfig;
import com.litian.dancechar.framework.oss.dto.UploadResultDTO;
import com.litian.dancechar.framework.oss.enums.AliyunClassificationEnum;
import com.litian.dancechar.framework.oss.enums.AliyunFolderTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.*;

/**
 * 上传文件到oss(基于Aliyun)
 *
 * @author tojson
 * @date 2021/2/14 21:15
 */
@Slf4j
@Component
public class OssUtil {

    /**
     * WEB直传TOKEN超时时间
     */
    private static final int TOKEN_EXPIRE_SECONDS = 120;

    /**
     * 图片最大允许上传6M
     */
    private static final int MAX_UPLOAD_IMAGE_SIZE = 6144000;
    /**
     * 视频最大允许上传51M
     */
    private static final int MAX_UPLOAD_MP4_SIZE = 53477376;
    private static final String HTTPS = "https://";

    private OSSClient ossClient;

    @Resource
    private OssConfig ossConfig;

    /**
     * 获取临时图片的url开头
     */
    public String getTempUrlHead() {
        return getUrlHead("temp");
    }

    /**
     * 获取指定目录图片的url开头
     *
     * @param folderName 文件名称
     * @return  目录图片的url
     */
    public String getUrlHead(String folderName) {
        return HTTPS + ossConfig.getOssBucket() + "." + ossConfig.getOssDomain() + "/" + AliyunClassificationEnum.IMAGE + "/" + folderName + "/";
    }

    /**
     * 获取http域名
     */
    public String getHttpDomain() {
        return HTTPS + ossConfig.getOssBucket() + "." + ossConfig.getOssDomain();
    }

    @PostConstruct
    public void init() {
        if (StrUtil.isBlank(ossConfig.getOssKey())) {
            log.warn("aliyun oss工具未配置,不启动");
            return;
        }
        try {
            ClientConfiguration conf = new ClientConfiguration();
            conf.setMaxConnections(2048);
            // 设置TCP连接超时为5000毫秒
            conf.setConnectionTimeout(5000);
            //连接空闲超时时间，超时则关闭连接
            conf.setIdleConnectionTime(2000);
            // 设置最大的重试次数为3
            conf.setMaxErrorRetry(3);
            // 设置Socket传输数据超时的时间为2000毫秒
            conf.setSocketTimeout(2000);
            ossClient = new OSSClient(ossConfig.getOssDomain(), ossConfig.getOssKey(), ossConfig.getOssSecret(), conf);
            log.info("aliyun oss工具启动成功！");
        } catch (Exception e) {
            log.error("aliyun oss工具启动失败！errMsg:{}", e.getMessage(), e);
        }

    }

    /**
     * 推送json文件到oss
     *
     * @param aliyunFolderTypeEnum folderType
     * @param json                 内容
     * @param file                 上传的文件
     * @return 上传oss后返回完整的文件路径
     */
    public String pushJsonDataToOSS(AliyunFolderTypeEnum aliyunFolderTypeEnum, Object json, MultipartFile file){
        String uploadOssFileName = buildNewFileName(file.getOriginalFilename());
        pushJsonDataToOSS(aliyunFolderTypeEnum, json, uploadOssFileName);
        return getUrl(AliyunClassificationEnum.JSON, aliyunFolderTypeEnum, uploadOssFileName);
    }

    /**
     * 推送json文件到oss
     *
     * @param aliyunFolderTypeEnum folderType
     * @param file                 上传的文件
     * @return 上传oss后返回完整的文件路径
     */
    public String pushJsonDataToOSS(AliyunFolderTypeEnum aliyunFolderTypeEnum, MultipartFile file){
        try {
            String uploadOssFileName = buildNewFileName(file.getOriginalFilename());
            pushJsonDataToOSS(aliyunFolderTypeEnum, file.getInputStream(), file.getBytes().length, uploadOssFileName);
            return getUrl(AliyunClassificationEnum.JSON, aliyunFolderTypeEnum, uploadOssFileName);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 推送txt文件到oss
     *
     * @param aliyunFolderTypeEnum folderType
     * @param file                 上传的文件
     * @return 上传oss后返回完整的文件路径
     */
    public UploadResultDTO pushTxtDataToOSS(AliyunFolderTypeEnum aliyunFolderTypeEnum, MultipartFile file){
        try {
            String uploadOssFileName = buildNewFileName(file.getOriginalFilename());
            pushTxtDataToOSS(aliyunFolderTypeEnum, file.getInputStream(), file.getBytes().length, uploadOssFileName);
            UploadResultDTO uploadResultDTO = new UploadResultDTO();
            uploadResultDTO.setOssAccessUrl(getUrl(AliyunClassificationEnum.TXT, aliyunFolderTypeEnum, uploadOssFileName));
            String objectName = AliyunClassificationEnum.TXT.getName()+"/"+aliyunFolderTypeEnum.getName()+"/"+uploadOssFileName;
            uploadResultDTO.setObjectName(objectName);
            return uploadResultDTO;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 推送txt文件到oss
     *
     * @param aliyunFolderTypeEnum folderType
     * @param inputStream          文件流
     * @param fileLength           文件流长度
     * @param fileName             文件名称
     * @return 成功失败
     */
    public Boolean pushTxtDataToOSS(AliyunFolderTypeEnum aliyunFolderTypeEnum, InputStream inputStream,long fileLength, String fileName) {
        try {
            this.putObject(AliyunClassificationEnum.TXT, aliyunFolderTypeEnum, fileName, inputStream, fileLength);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 推送json文件到oss
     *
     * @param aliyunFolderTypeEnum folderType
     * @param inputStream          文件流
     * @param fileLength           文件流长度
     * @param fileName             文件名称
     * @return 成功失败
     */
    public Boolean pushJsonDataToOSS(AliyunFolderTypeEnum aliyunFolderTypeEnum, InputStream inputStream,long fileLength, String fileName) {
        try {
            this.putObject(AliyunClassificationEnum.JSON, aliyunFolderTypeEnum, fileName, inputStream, fileLength);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 推送json文件到oss
     *
     * @param aliyunFolderTypeEnum folderType
     * @param json                 内容
     * @param fileName             文件名称
     * @return 成功失败
     */
    public Boolean pushJsonDataToOSS(AliyunFolderTypeEnum aliyunFolderTypeEnum, Object json, String fileName) {
        log.info("pushJsonDataToOSS data:{}", json);
        try {
            if (json == null || StringUtils.isEmpty(json.toString())) {
                return false;
            }
            String jsonStr = JSONUtil.toJsonStr(json);
            byte[] bytes = jsonStr.getBytes(StandardCharsets.UTF_8);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            this.putObject(AliyunClassificationEnum.JSON, aliyunFolderTypeEnum, fileName, inputStream, bytes.length);
            return true;
        } catch (Exception e) {
            log.error("OSSUtil pushOSS data:{} error:{}", json, e.getMessage(), e);
            return false;
        }
    }

    /**
     * 上传文件到 OSS
     *
     * @param classificationEnum 分类
     * @param folderEnum         文件夹
     * @param fileName           文件名称
     * @param fileLength         文件长度
     * @param inputStream        文件流
     * @return 文件名
     */
    public String putObject(AliyunClassificationEnum classificationEnum, AliyunFolderTypeEnum folderEnum,
                            String fileName, InputStream inputStream, long fileLength) {
        try {
            createFolder(classificationEnum, folderEnum);
            //创建上传 Object 的 Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            //必须设置
            objectMetadata.setContentLength(fileLength);
            //上传 Object
            ossClient.putObject(ossConfig.getOssBucket(), StringUtils.join(classificationEnum.getPath(), folderEnum.getPath(), fileName),
                    inputStream, objectMetadata);
        } catch (Exception e) {
            log.error("putObject error！errMsg:{}", e.getMessage(), e);
        } finally {
            close(inputStream);
        }
        return fileName;
    }

    /**
     * 上传文件到 OSS（指定目录 目录必须已经存在）
     * @param path               文件路径
     * @param fileName           文件名称
     * @param inputStream        文件流
     * @param fileLength         文件长度
     * @return 文件名
     */
    public String putObject(String path, String fileName, InputStream inputStream, long fileLength) {
        try {
            //创建上传 Object 的 Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            //必须设置
            objectMetadata.setContentLength(fileLength);
            //上传 Object
            log.info("oss上传key为 {}", StringUtils.join(path, "/", fileName));
            ossClient.putObject(ossConfig.getOssBucket(), StringUtils.join(path, "/", fileName), inputStream, objectMetadata);

        } catch (Exception e) {
            log.error("putObject error={}", e.getMessage(), e);
        } finally {
            close(inputStream);
        }
        return fileName;
    }

    /**
     * 删除单个文件
     *
     * @param classificationEnum 分类
     * @param folderEnum         文件夹
     * @param fileName           文件名称
     */
    public void deleteObject(AliyunClassificationEnum classificationEnum, AliyunFolderTypeEnum folderEnum, String fileName) {
        ossClient.deleteObject(ossConfig.getOssBucket(), StringUtils.join(classificationEnum.getPath(), folderEnum.getPath(), fileName));
    }

    /**
     * 删除单个文件
     *
     * @param fileKey 文件key
     */
    public void deleteObject(String fileKey) {
        ossClient.deleteObject(ossConfig.getOssBucket(), fileKey);
    }

    /**
     * 删除多个文件
     *
     * @param fileKeys 文件key
     */
    public void deleteObjects(List<String> fileKeys) {
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(ossConfig.getOssBucket());
        deleteObjectsRequest.setKeys(fileKeys);
        ossClient.deleteObjects(deleteObjectsRequest);
    }

    /**
     * 列举目录下所有文件
     *
     * @param classificationEnum 分类
     * @param folderEnum         文件夹
     */
    public List<String> showObject(AliyunClassificationEnum classificationEnum, AliyunFolderTypeEnum folderEnum) {
        List<String> keyList = new ArrayList<>();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(ossConfig.getOssBucket());
        listObjectsRequest.setPrefix(StringUtils.join(classificationEnum.getPath(), folderEnum.getPath()));
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            if (objectSummary.getSize() != 0) {
                keyList.add(objectSummary.getKey());
            }
        }
        return keyList;
    }

    /**
     * 拷贝文件到别的目录
     *
     * @param classificationEnum       分类
     * @param fileName                 文件名
     * @param srcAliyunFolderTypeEnum  源目录
     * @param destAliyunFolderTypeEnum 目标目录
     */
    public String copyObject(AliyunClassificationEnum classificationEnum, String fileName, AliyunFolderTypeEnum srcAliyunFolderTypeEnum,
                             AliyunFolderTypeEnum destAliyunFolderTypeEnum) throws IOException {
        createFolder(classificationEnum, destAliyunFolderTypeEnum);
        ossClient.copyObject(ossConfig.getOssBucket(),
                StringUtils.join(classificationEnum.getPath(), srcAliyunFolderTypeEnum.getPath(), fileName),
                ossConfig.getOssBucket(), StringUtils.join(classificationEnum.getPath(),
                        destAliyunFolderTypeEnum.getPath(), fileName));
        return fileName;
    }

    /**
     * 拷贝文件到别的目录（源目录为根目录）
     *
     * @param classificationEnum       分类
     * @param fileName                 文件名
     * @param destAliyunFolderTypeEnum 目标目录
     * @param sourceBucket             源bucket
     * @throws IOException
     */
    public String copyObjectFromDiffBucket(AliyunClassificationEnum classificationEnum, String fileName, AliyunFolderTypeEnum destAliyunFolderTypeEnum, String sourceBucket) throws IOException {
        createFolder(classificationEnum, destAliyunFolderTypeEnum);
        ossClient.copyObject(sourceBucket, fileName, ossConfig.getOssBucket(), StringUtils.join(classificationEnum.getPath(), destAliyunFolderTypeEnum.getPath(), fileName));
        return fileName;
    }

    /**
     * 拷贝文件到别的目录（源目录为子目录）
     *
     * @param classificationEnum         分类
     * @param fileName                   文件名
     * @param destAliyunFolderTypeEnum   目标目录
     * @param sourceBucket               源bucket
     * @param sourceAliyunFolderTypeEnum 源目录
     * @throws IOException
     */
    public String copyObjectFromDiffBucket(AliyunClassificationEnum classificationEnum, String fileName, AliyunFolderTypeEnum destAliyunFolderTypeEnum, String sourceBucket, AliyunFolderTypeEnum sourceAliyunFolderTypeEnum) throws IOException {
        createFolder(classificationEnum, destAliyunFolderTypeEnum);
        ossClient.copyObject(sourceBucket, StringUtils.join(classificationEnum.getPath(),
                sourceAliyunFolderTypeEnum.getPath(), fileName), ossConfig.getOssBucket(),
                StringUtils.join(classificationEnum.getPath(), destAliyunFolderTypeEnum.getPath(), fileName));
        return fileName;
    }


    /**
     * copy bucket
     */
    public String copyObjectFromDiffBucket(AliyunClassificationEnum classificationEnum, String fileName, String destBucket, AliyunFolderTypeEnum destAliyunFolderTypeEnum, String sourceBucket, AliyunFolderTypeEnum sourceAliyunFolderTypeEnum) throws IOException {
        createFolder(classificationEnum, destAliyunFolderTypeEnum);
        ossClient.copyObject(sourceBucket, StringUtils.join(classificationEnum.getPath(), sourceAliyunFolderTypeEnum.getPath(), fileName), destBucket, StringUtils.join(classificationEnum.getPath(), destAliyunFolderTypeEnum.getPath(), fileName));
        return fileName;
    }

    /**
     * 创建文件夹
     *
     * @param classificationEnum 分类
     * @param folderEnum         文件夹
     * @throws IOException
     */
    private void createFolder(AliyunClassificationEnum classificationEnum, AliyunFolderTypeEnum folderEnum) throws IOException {
        if (!ossClient.doesObjectExist(ossConfig.getOssBucket(), classificationEnum.getPath())) {
            /*这里的size为0,注意OSS本身没有文件夹的概念,这里创建的文件夹本质上是一个size为0的Object,dataStream仍然可以有数据*/
            ObjectMetadata objectMeta = new ObjectMetadata();
            byte[] buffer = new byte[0];
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
            objectMeta.setContentLength(0);
            try {
                ossClient.putObject(ossConfig.getOssBucket(), folderEnum.getPath(), byteArrayInputStream, objectMeta);
            } finally {
                close(byteArrayInputStream);
            }
        }
        if (!ossClient.doesObjectExist(ossConfig.getOssBucket(), classificationEnum.getPath() + folderEnum.getPath())) {
            /*这里的size为0,注意OSS本身没有文件夹的概念,这里创建的文件夹本质上是一个size为0的Object,dataStream仍然可以有数据*/
            ObjectMetadata objectMeta = new ObjectMetadata();
            byte[] buffer = new byte[0];
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
            objectMeta.setContentLength(0);
            try {
                ossClient.putObject(ossConfig.getOssBucket(), classificationEnum.getPath() + folderEnum.getPath(), byteArrayInputStream, objectMeta);
            } finally {
                close(byteArrayInputStream);
            }
        }
    }

    /**
     * 获取图片
     */
    public BufferedImage getImage(AliyunClassificationEnum classificationEnum, String fileName, AliyunFolderTypeEnum folderEnum) throws IOException {
        OSSObject object = null;
        try {
            object = ossClient.getObject(ossConfig.getOssBucket(), StringUtils.join(classificationEnum.getPath(), folderEnum.getPath(), fileName));
            return ImageIO.read(object.getObjectContent());
        } catch (Exception e) {
            log.error("getTokens error={}", e.getMessage(), e);
        } finally {
            close(object);
        }
        return null;
    }

    public Boolean doesImageExist(AliyunClassificationEnum classificationEnum, String fileName, AliyunFolderTypeEnum folderEnum) {
        return ossClient.doesObjectExist(ossConfig.getOssBucket(), StringUtils.join(classificationEnum.getPath(), folderEnum.getPath(), fileName));
    }

    /**
     * 获得文件夹枚举
     */
    public AliyunFolderTypeEnum getFolder(String folderName) {
        AliyunFolderTypeEnum folderEnum;
        switch (folderName) {
            case "user":
                folderEnum = AliyunFolderTypeEnum.USER;
                break;
            case "temp":
                folderEnum = AliyunFolderTypeEnum.TEMP;
                break;
            case "applet":
                folderEnum = AliyunFolderTypeEnum.APPLET;
                break;
            default:
                throw new IllegalArgumentException("文件夹不存在！");
        }
        return folderEnum;
    }

    public String getUrl(AliyunClassificationEnum classificationEnum, AliyunFolderTypeEnum folderEnum, String fileName) {
        String enabledCDN = ossConfig.getEnableCDN();
        if ("true".equals(enabledCDN)) {
            return StringUtils.join(HTTPS, ossConfig.getCdnUrl(), "/",
                    classificationEnum.getPath(), folderEnum.getPath(), fileName);
        } else {
            return StringUtils.join(HTTPS, ossConfig.getOssBucket(), ".", ossConfig.getOssDomain(), "/", classificationEnum.getPath(), folderEnum.getPath(), fileName);
        }
    }

    public String getUrl(String cBucket, AliyunClassificationEnum classificationEnum, AliyunFolderTypeEnum folderEnum, String fileName) {
        String enabledCDN = ossConfig.getEnableCDN();
        if ("true".equals(enabledCDN)) {
            return StringUtils.join(HTTPS, ossConfig.getCdnUrl(), "/",
                    classificationEnum.getPath(), folderEnum.getPath(), fileName);
        } else {
            return StringUtils.join(HTTPS, cBucket, ".", ossConfig.getOssDomain(), "/", classificationEnum.getPath(), folderEnum.getPath(), fileName);
        }
    }

    public String getFileKey(AliyunClassificationEnum classificationEnum, AliyunFolderTypeEnum folderEnum, String fileName) {
        return StringUtils.join(classificationEnum.getPath(), folderEnum.getPath(), fileName);
    }

    public String getFileNameByUrl(String url) {
        String[] urls = url.split("/");
        return urls[urls.length - 1];
    }

    public String getFolderNameByUrl(String url) {
        String[] urls = url.split("/");
        return urls[urls.length - 2];
    }

    /**
     * 获取WEB直传阿里云图片的Policy
     */
    public Map<String, String> getUploadImagePolicy(String dir) throws UnsupportedEncodingException {
        long expireEndTime = System.currentTimeMillis() + TOKEN_EXPIRE_SECONDS * 1000;
        Date expiration = new Date(expireEndTime);
        PolicyConditions policyConds = new PolicyConditions();
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, MAX_UPLOAD_IMAGE_SIZE);
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

        String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
        byte[] binaryData = postPolicy.getBytes("utf-8");
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = ossClient.calculatePostSignature(postPolicy);

        Map<String, String> respMap = new LinkedHashMap<>();
        respMap.put("accessid", ossConfig.getOssKey());
        respMap.put("policy", encodedPolicy);
        respMap.put("signature", postSignature);
        respMap.put("dir", dir);
        respMap.put("host", getHttpDomain());
        respMap.put("cdnHost", getCdnHost());
        respMap.put("expire", String.valueOf(expireEndTime / 1000));

        return respMap;
    }

    public String getCdnHost() {
        boolean enabledCdn = Boolean.parseBoolean(ossConfig.getEnableCDN());
        String cdnHost = "";
        if (enabledCdn) {
            cdnHost = HTTPS + ossConfig.getCdnUrl();
        }
        return cdnHost;
    }

    /**
     * 下载文件（返回流）
     */
    public InputStream downloadInputStreamFromOss(String ObjectName) {
        OSSObject object = ossClient.getObject(ossConfig.getOssBucket(), ObjectName);
        return object.getObjectContent();
    }

    public OSSObject getObject(String ObjectName){
        return ossClient.getObject(ossConfig.getOssBucket(), ObjectName);
    }

    /**
     * 下载文件（返回每行数据列表）
     */
    public List<String> downloadListFromOss(String ObjectName) {
        try{
            return IOUtils.readLines(downloadInputStreamFromOss(ObjectName), "UTF-8");
        }catch (Exception e){
            log.error(e.getMessage() ,e);
            return null;
        }
    }


    /***
     * 获得上传tokens
     */
    public String getTokens(AliyunClassificationEnum classificationEnum, AliyunFolderTypeEnum folderEnum, String fileName) {
        OSSObject ossObject = null;
        String tokens = "";
        try {
            String name = StringUtils.join(classificationEnum.getPath(), folderEnum.getPath(), fileName);
            ossObject = ossClient.getObject(ossConfig.getOssBucket(), name);
            ObjectMetadata objectMetadata = ossObject.getObjectMetadata();
            Map<String, String> userMetadata = objectMetadata.getUserMetadata();
            tokens = userMetadata.get("tokens");
        } catch (Exception e) {
            log.error("getTokens error={}", e.getMessage(), e);
        } finally {
            close(ossObject);
        }
        return tokens;
    }

    /**
     * 流关闭
     */
    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                log.error("AliyunOssUtil.close has an error", e);
            }
        }
    }


    /**
     * 上传tokens到 OSS
     *
     * @param classificationEnum 分类
     * @param folderEnum         文件夹
     * @param fileName           文件名称
     * @param inputStream        文件流
     * @return 文件名
     * @throws IOException
     */
    public void putTokens(AliyunClassificationEnum classificationEnum, AliyunFolderTypeEnum folderEnum, String fileName, InputStream inputStream, String tokens) throws IOException {
        try {
            createFolder(classificationEnum, folderEnum);
            //创建上传 Object 的 Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            //必须设置
            objectMetadata.setContentLength(100);
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf('.'))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            Map<String, String> map = new HashMap<>();
            map.put("tokens", tokens);
            objectMetadata.setUserMetadata(map);
            //上传 Object
            ossClient.putObject(ossConfig.getOssBucket(), StringUtils.join(classificationEnum.getPath(), folderEnum.getPath(), fileName), inputStream, objectMetadata);
        } catch (Exception e) {
            log.error("putTokens error={}", e.getMessage(), e);
        } finally {
            close(inputStream);
        }
    }

    /**
     * 重新构建上传到oss的名字
     */
    private String buildNewFileName(String originalFilename){
        // 构建日期路径, 例如：20201031/文件名
        String filePath = DCDateUtil.format(new java.util.Date(), DatePattern.PURE_DATE_FORMAT);
        // 获取文件类型
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 新文件名称
        String newFileName = DCStrUtil.uuid().replace("-", "")+ fileType;
        // 文件上传的路径地址
        return filePath + "/" + newFileName;
    }

    /**
     * 从远程url中读取数据
     */
    public List<String> readDataByUrl(String accessUrl){
        try{
            URL url = new URL(accessUrl);
            return FileUtil.readUtf8Lines(url);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 判断OSS服务文件上传时文件的contentType
     * @param filenameExtension 文件后缀
     * @return String
     */
    public static String getContentType(String filenameExtension) {
        if (filenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (filenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (filenameExtension.equalsIgnoreCase("jpeg") ||
                filenameExtension.equalsIgnoreCase("jpg") ||
                filenameExtension.equalsIgnoreCase("png")) {
            return "image/png";
        }
        if (filenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (filenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (filenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (filenameExtension.equalsIgnoreCase("pptx") ||
                filenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (filenameExtension.equalsIgnoreCase("docx") ||
                filenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (filenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        return "image/png";
    }
}