package com.litian.dancechar.common.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 类描述：service方法枚举
 *
 * @author 01410001
 * @date 2021/09/10 11:27
 */
@Getter
@AllArgsConstructor
public enum MethodEnum {
    /**
     * 分页查询列表
     */
    PAGR_LIST("select", "pageList", "分页查询列表"),
    SAVE("save", "save", "新增保存"),
    UPDATE("update", "update", "修改记录"),
    DELETE_BY_ID("delete", "deleteById", "根据id删除记录"),
    DETAIL_BY_ID("detail", "getById", "根据id获取记录");

    /**
     * 方法类型，对应前端方法类型
     */
    private String methodType;

    /**
     * 方法名，对应模板url
     */
    private String methodName;

    /**
     * 方法描述
     */
    private String desc;

    public static String getByCode(String methodType) {
        for (MethodEnum methodEnum : values()) {
            if (methodType.equals(methodEnum.getMethodType())) {
                return methodEnum.getDesc();
            }
        }
        return "";
    }

}
