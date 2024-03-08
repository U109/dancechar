package com.litian.dancechar.framework.oss.constants;

import cn.hutool.core.util.StrUtil;

/**
 * oss文件目录分类常量类
 */
public class OssFolderTypeConstants {
    /**
     * 视频
     */
    public static final String VIDEO = "video";

    /**
     * 图片
     */
    public static final String IMAGE = "image";

    /**
     * excel文件
     */
    public static final String EXCEL = "excel";

    /**
     * json文件
     */
    public static final String JSON = "json";

    /**
     * zip文件
     */
    public static final String ZIP = "zip";

    /**
     * audio文件
     */
    public static final String AUDIO = "audio";

    /**
     * 公共文件
     */
    public static final String COMMON = "common";

    public static String getPath(String env, String name) {
        return StrUtil.isNotEmpty(env) ? env + "/" + name + "/" : name + "/";
    }
}
