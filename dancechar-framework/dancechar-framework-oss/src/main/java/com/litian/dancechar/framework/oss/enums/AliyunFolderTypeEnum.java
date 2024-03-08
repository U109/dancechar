package com.litian.dancechar.framework.oss.enums;

/**
 * AliyunFolderTypeEnum 阿里云oss服务定义的文件夹类型
 */
public enum AliyunFolderTypeEnum {
    /**
     * 用户相关
     */
    USER("user"),
    /**
     * 客户
     */
    CUSTOMER("customer"),
    /**
     * 客群
     */
    CUSTOMER_GROUP("customerGroup"),
    /**
     * 小程序
     */
    APPLET("applet"),
    /**
     * 营销
     */
    MARKETING("marketing"),
    /**
     *协议
     */
    AGREEMENT("agreement"),
    /**
     *城市
     */
    CITY("city"),
    /**
     * 临时文件
     */
    TEMP("temp");

    private String name;

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    AliyunFolderTypeEnum(String name) {
        this.name = name;
    }

    public String getPath() {
        return this.toString() + "/";
    }

    public static AliyunFolderTypeEnum getFolderType(String folderName) {
        for (AliyunFolderTypeEnum typeEnum : AliyunFolderTypeEnum.values()) {
            if (typeEnum.getName().equals(folderName)) {
                return typeEnum;
            }
        }
        return AliyunFolderTypeEnum.TEMP;
    }

    @Override
    public String toString() {
        return name;
    }
}