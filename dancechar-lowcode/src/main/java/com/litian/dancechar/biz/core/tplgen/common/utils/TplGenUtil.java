package com.litian.dancechar.biz.core.tplgen.common.utils;

import cn.hutool.core.util.ObjectUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TplGenUtil {

    /**
     * 下划线命名转为驼峰命名
     */
    public static String underlineToHump(String para){
        if (ObjectUtil.isEmpty(para)){
            System.out.printf("para is null "+ para);
            return "";
        }
        para = para.toLowerCase();
        StringBuilder result=new StringBuilder();
        String a[]=para.split("_");
        for(String s:a){
            if(result.length()==0){
                result.append(s.toLowerCase());
            }else{
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 驼峰命名转为下划线命名
     */
    public static String humpToUnderline(String para){
        StringBuilder sb=new StringBuilder(para);
        int temp=0;//偏移量，第i个下划线的位置是 当前的位置+ 偏移量（i-1）,第一个下划线偏移量是0
        for(int i=0;i<para.length();i++){
            if(Character.isUpperCase(para.charAt(i))){
                sb.insert(i+temp, "_");
                temp+=1;
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 分割后驼峰
     * @param str
     * @return
     */
    public static String upperCaseWithSeparatorChars(String str, String separatorChars) {
        String[] strArr = StringUtils.split(str, separatorChars);
        return Arrays.stream(strArr).map(tmpStr -> StringUtils.replaceFirst(tmpStr, tmpStr.substring(0, 1), tmpStr.substring(0, 1).toUpperCase())).collect(Collectors.joining());
    }

    public static void main(String[] args) {
        String startupName = "esg-cemp-core-fcode-demo";
        startupName = startupName.substring("esg-cemp-core-".length());
        System.out.println(upperCaseWithSeparatorChars(startupName, "-"));
    }

    /**
     * 首字母大写
     * @param realName
     * @return
     */
    public static String firstUpperCase(String realName) {
        return StringUtils.replaceChars(realName, realName.substring(0, 1),realName.substring(0, 1).toUpperCase());
    }

    /**
     * 首字母小写
     * @param realName
     * @return
     */
    public static String firstLowerCase(String realName) {
        return StringUtils.replaceChars(realName, realName.substring(0, 1),realName.substring(0, 1).toLowerCase());
    }

    /**
     * 数据类型转化JAVA
     */
    public static String sqlToJava(String sqlType) {
        if (sqlType == null || sqlType.trim().length() == 0) {
            return sqlType;
        }
        sqlType = sqlType.toLowerCase();
        if (sqlType.startsWith("int")) {
            //如果以int开头，则直接返回int，兼容pgsql中int2 int8等
            return "Integer";
        }
        switch (sqlType) {
            case "nvarchar":
                return "String";
            case "nvarchar2":
                return "String";
            case "char":
                return "String";
            case "varchar":
                return "String";
            case "enum":
                return "String";
            case "set":
                return "String";
            case "text":
                return "String";
            case "nchar":
                return "String";
            case "mediumtext":
                return "String";
            case "varchar2":
                return "String";
            case "longtext":
                return "String";
            case "blob":
                return "byte[]";
            case "integer":
                return "Long";
            case "int":
                return "Integer";
            case "tinyint":
                return "Integer";
            case "smallint":
                return "Integer";
            case "mediumint":
                return "Integer";
            case "bit":
                return "Boolean";
            case "bigint":
                return "Long";
            case "number":
                return "Long";
            case "float":
                return "Float";
            case "double":
                return "Double";
            case "decimal":
                return "BigDecimal";
            case "boolean":
                return "Boolean";
            case "id":
                return "Long";
            case "date":
                return "Date";
            case "datetime":
                return "Date";
            case "year":
                return "Date";
            case "time":
                return "Time";
            case "timestamp":
                return "Date";
            case "numeric":
                return "BigDecimal";
            case "real":
                return "BigDecimal";
            case "money":
                return "Double";
            case "smallmoney":
                return "Double";
            case "image":
                return "byte[]";
            default:
                System.out.println(">>> 转化失败：未发现的类型 " + sqlType);
                break;
        }
        return sqlType;
    }


    /**
     * java转显示类型
     *
     * @author yubaoshan
     * @date 2021-2-8 02:30
     */
    public static String javaToEff(String javaType) {
        if (javaType == null || javaType.trim().length() == 0) {
            return javaType;
        }
        switch (javaType) {
            case "String":
                return "input";
            case "Integer":
                return "input";
            case "Long":
                return "input";
            case "BigDecimal":
                return "input";
            case "Date":
                return "datepicker";
            default:
                System.out.println(">>> 转化失败：未发现的类型" + javaType);
                break;
        }
        return javaType;
    }
}
