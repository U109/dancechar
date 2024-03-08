package com.litian.dancechar.framework.oss.constants;

/**
 * oss文件具体业务子目录常量类(目前这里放公共的)
 */
public class OssFolderSubTypeConstants {
    /**
     * 小程序子目录
     */
    public static final String APPLET = "applet";

    /**
     * 公共子目录
     */
    public static final String COMMON = "common";

    public static String getPath(String name) {
        return name + "/";
    }
}