package com.litian.dancechar.biz.core.codegen.common.util;

import com.litian.dancechar.biz.core.codegen.common.constants.CommonConstant;
import com.litian.dancechar.biz.core.codegen.context.ConstantContext;
import com.litian.dancechar.biz.sysmgr.dbmgr.common.enums.SystemDBInfoEnums;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.app.Velocity;

import javax.servlet.http.HttpServletResponse;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 代码生成工具类
 */
@Slf4j
public class Util {

    /**
     * 初始化vm
     *
     * @author yubaoshan
     * @Date 2020年12月16日23:29:53
     */
    public static void initVelocity() {
        Properties properties = new Properties();
        try {
            properties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            properties.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
            properties.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
            Velocity.init(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成压缩包文件
     *
     * @author yubaoshan
     * @Date 2020年12月16日23:29:53
     */
    public static void DownloadGen(HttpServletResponse response, byte[] bytes, String zipName) {
        try {
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + zipName + ".zip");
            response.addHeader("Content-Length", "" + bytes.length);
            response.setContentType("application/octet-stream; charset=UTF-8");
            IOUtils.write(bytes, response.getOutputStream());
        } catch (Exception e) {
            log.error("生成压缩包文件系统异常！errMsg:{}", e.getMessage(), e);
        }
    }

    /**
     * 查询某字符串第i次出现的游标
     *
     * @param string 字符串
     * @param i      第i次出现
     * @param str    子字符串
     * @author yubaoshan
     * @date 2020年12月16日23:29:53
     */
    private static int getIndex(String string, int i, String str) {
        Matcher slashMatcher = Pattern.compile(str).matcher(string);
        int mIdx = 0;
        while (slashMatcher.find()) {
            mIdx++;
            //当"/"符号第三次出现的位置
            if (mIdx == i) {
                break;
            }
        }
        return slashMatcher.start();
    }

    /**
     * 获取数据库用户
     *
     * @author yubaoshan
     * @date 2021-03-11 18:37:00
     */
    public static String getDataBasename() {
        String dataUrl = ConstantContext.me().getStr(CommonConstant.DATABASE_URL_NAME);
        String driverName = ConstantContext.me().getStr(CommonConstant.DATABASE_DRIVER_NAME);
        if (driverName.contains(SystemDBInfoEnums.DriverEnum.MYSQL.getCode())) {
            return dataUrl.substring(getIndex(dataUrl, 3, "/") + 1, dataUrl.indexOf("?"));
        } else if (driverName.contains(SystemDBInfoEnums.DriverEnum.ORACLE.getCode())) {
            return ConstantContext.me().getStr(CommonConstant.DATABASE_USER_NAME);
        } else {
            return "";
        }
    }

}
