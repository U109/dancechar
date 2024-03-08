package com.litian.dancechar.biz.core.codegen.common.enums;

import lombok.Getter;

/**
 * 代码生成过程中被过滤的字段
 */
@Getter
public enum TableFilteredFieldsEnum {
    /**
     * create_time
     */
    CREATE_TIME("create_date"),
    UPDATE_TIME("update_date"),
    CREATE_USER("create_user"),
    UPDATE_USER("update_user"),
    DELETE_FLAG("delete_flag");

    private final String propertyName;

    TableFilteredFieldsEnum(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * 是否本枚举包含该字段
     *
     * @author yubaoshan
     * @date 2020年12月17日00:11:40
     */
    public static boolean contains(String propertyName) {
        for (TableFilteredFieldsEnum xiaonuoFilteredFieldsEnum : TableFilteredFieldsEnum.values()) {
            if (xiaonuoFilteredFieldsEnum.propertyName.equals(propertyName)) {
                return true;
            }
        }
        return false;
    }
}