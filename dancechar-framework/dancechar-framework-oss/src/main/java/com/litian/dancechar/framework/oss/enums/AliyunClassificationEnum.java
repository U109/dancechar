package com.litian.dancechar.framework.oss.enums;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 * 阿里云Oss分类枚举
 */
public enum AliyunClassificationEnum {
    /**
     * 图片
     */
    IMAGE("images"),
    /**
     * 视频
     */
    VIDEO("videos"),
    /**
     * 音频
     */
    AUDIO("audios"),
    /**
     * excel表格
     */
    EXCEL("excel"),

    /**
     * zip文件
     */
    ZIP("zip"),
    /**
     * 城市政策
     */
    POLICY("policy"),
    /**
     * JSON
     */
    JSON("json"),
    /**
     * txt
     */
    TXT("txt"),
    /**
     * 课程
     */
    COURSE("course"),
    /**
     * 临时
     */
    /**
     * 临时
     */
    TEMP("temp");

    private String name;

    @Value("${oss.env:}")
    String ossEnv;

    AliyunClassificationEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return StrUtil.isNotEmpty(ossEnv) ? ossEnv + "/" + name : name;
    }

    public String getClassificationName() {
        return name;
    }

    public String getPath() {
        return StringUtils.join(this.toString(), "/");
    }

    @Override
    public String toString() {
        return StrUtil.isNotEmpty(ossEnv) ? ossEnv + "/" + name : name;
    }

    public static AliyunClassificationEnum getClassification(String folderName) {
        for (AliyunClassificationEnum typeEnum : AliyunClassificationEnum.values()) {
            if (typeEnum.getClassificationName().equals(folderName)) {
                return typeEnum;
            }
        }
        return AliyunClassificationEnum.TEMP;
    }
}
