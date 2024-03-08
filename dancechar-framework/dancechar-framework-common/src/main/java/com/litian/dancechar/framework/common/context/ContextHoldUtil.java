package com.litian.dancechar.framework.common.context;

import org.slf4j.MDC;

/**
 * 类描述：获取当前操作用户工具类
 *
 * @author 01402521
 * @date 2021/4/30 13:58
 */
public class ContextHoldUtil {

    public static final String EMP_NUM = "empNum";
    public static final String EMP_NAME = "empName";
    public static final String EMP_AREA_CODE = "empAreaCode";
    public static final String EMP_DISTRICT_FLAG = "empDistrictFlag";
    public static final String EMP_NET_CODE = "empNetCode";

    /**
     * 员工工号
     */
    public static String getEmpNum() {
        return MDC.get(EMP_NUM);
    }

    /**
     * 员工姓名
     */
    public static String getEmpName() {
        return MDC.get(EMP_NAME);
    }

    /**
     * 员工地区
     */
    public static String getEmpAreaCode() {
        return MDC.get(EMP_AREA_CODE);
    }

    /**
     * 员工区域  1-代表区域1 0-代表区域0
     */
    public static String getEmpDistrictFlag() {
        return MDC.get(EMP_DISTRICT_FLAG);
    }

    /**
     * 员工网点
     */
    public static String getEmpNetCode() {
        return MDC.get(EMP_NET_CODE);
    }
}
