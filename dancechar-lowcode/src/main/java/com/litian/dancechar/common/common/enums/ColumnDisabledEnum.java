package com.litian.dancechar.common.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ColumnDisabledEnum {

    ID("id","id","主键id"),
    CREATE_USER("create_user","createUser","创建人"),
    CREATE_DATE("create_date","createDate","创建时间"),
    CREATE_BY("create_by","createBy","创建人"),
    CREATE_TIME("create_time","createTime","创建时间"),
    CREATED_BY("created_by","createdBy","创建人"),
    CREATED_TIME("created_time","createdTime","创建时间"),
    CREATED_TM("created_tm","createTm","创建人"),
    CREATOR("creator","creator","创建时间"),
    UPDATE_USER("update_user","updateUser","更新人"),
    UPDATE_DATE("update_date","updateDate","更新时间"),
    UPDATE_BY("update_by","updateBy","更新人"),
    UPDATE_TIME("update_time","updateTime","更新时间"),
    UPDATED_BY("updated_by","updatedBy","更新人"),
    MODIFY_TM("modify_tm","modifyTm","更新时间"),
    MODIFIER("modifier","modifier","更新人"),
    UPDATED_TIME("updated_time","updatedTime","更新时间"),
    DELETE_FLAG("delete_flag","deleteFlag","删除标志");

    /**
     * db字段名
     */
    private String dbColumn;
    /**
     * java字段名
     */
    private String javaColumn;
    /**
     * 字段描述
     */
    private String columnDesc;


    public static String getByJaveColumn(String javaColumn) {
        for (ColumnDisabledEnum columnDisabledEnum : values()) {
            if (javaColumn.equals(columnDisabledEnum.getJavaColumn())) {
                return columnDisabledEnum.getDbColumn();
            }
        }
        return "";
    }
}
