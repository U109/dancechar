package com.litian.dancechar.framework.common.util;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 类描述: 顺丰字符串处理工具类
 *
 * @author 01406831
 */
public class DCStrUtil extends StrUtil {


    public static final Comparator<Object> CHINA_COMPARE = Collator.getInstance(java.util.Locale.CHINA);

    private static final String DESENSITIZATION = "****";
    private static final String DESENSITIZATION2 = "**";


    private static final Character ARTE = '@';

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source 被过滤的String
     * @return 过滤后的String
     */
    public static String filterEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return source;
        }
        StringBuilder buf = null;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            }
        }
        if (buf == null) {
            return source;
        } else {
            if (buf.length() == len) {
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return codePoint == 0x0 || codePoint == 0x9 || codePoint == 0xA || codePoint == 0xD || codePoint >= 0x20 && codePoint <= 0xD7FF || codePoint >= 0xE000 && codePoint <= 0xFFFD;
    }

    /**
     * 字符串加*号处理   字符串长度3到7之间 截取前三位后面脱敏   字符串长度8 截取后四位前面脱敏
     * 字符串长度小于等于3的全部脱敏   兜底 截取前三位 中间四个脱敏 加上后面部分
     *
     * @param string 脱敏前字符串
     * @return 脱敏后字符串
     */
    public static String mobileSafetyDeal(String string) {
        if (DCStrUtil.isBlank(string)) {
            return null;
        }
        if (string.length() > 3 && string.length() <= 7) {
            return string.substring(0, 3) + DESENSITIZATION;
        } else if (string.length() == 8) {
            return DESENSITIZATION + string.substring(4, 8);
        } else if (string.length() <= 3) {
            return DESENSITIZATION;
        } else {
            return string.substring(0, 3) + DESENSITIZATION + string.substring(7);
        }
    }

    /**
     * string加*号处理
     * 前4位+两个星号
     * 1853**
     *
     * @param string 脱敏前字符串
     * @return 脱敏后字符串
     */
    public static String mobileSafetyDeal2(String string) {
        if (DCStrUtil.isBlank(string)) {
            return null;
        }
        if (string.length() <= 4) {
            return DESENSITIZATION2;
        }
        return string.substring(0, 4) + DESENSITIZATION2;
    }

    /**
     * 邮箱加*号处理
     *
     * @param email 邮箱
     * @return 脱敏邮箱
     */
    public static String emailSafetyDeal(String email) {
        if (DCStrUtil.isBlank(email)) {
            return null;
        }
        int intOfValue = email.lastIndexOf(ARTE);
        if (intOfValue != -1) {
            return email.substring(0, intOfValue) + DESENSITIZATION;
        }
        return DESENSITIZATION;
    }

    /**
     * 手机号码脱敏-前三后四
     *
     * @param mobile 手机号
     * @return 脱敏后的数据
     */
    public static String mobileConceal(String mobile) {
        if (DCStrUtil.isBlank(mobile)) {
            return null;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 截取两个字符串之间的类型，示例入参为：abcde,a,d 返回bc;入参为：abcd,a,e,返回bcd
     *
     * @param str      目标String
     * @param strStart 开始String
     * @param strEnd   结束String
     * @return 开始和结束之间的String
     */
    public static String getDimensionValue(String str, String strStart, String strEnd) {
        int strStartIndex = str.indexOf(strStart);
        if (strStartIndex < 0) {
            return null;
        }
        String result = str.substring(strStartIndex + strStart.length());
        int strEndIndex = result.indexOf(strEnd);
        if (strEndIndex < 0) {
            return result;
        }
        result = result.substring(0, strEndIndex);
        return result;
    }

    /**
     * 将集合按中文规则排序
     *
     * @param sortList 需要排序的List
     * @return 返回排序的String
     */
    public static String sortManager(List<String> sortList) {
        StringBuilder sb = new StringBuilder();
        sortList.sort(CHINA_COMPARE);
        for (int i = 0; i < sortList.size(); i++) {
            if (i + 1 == sortList.size()) {
                sb.append(sortList.get(i));
            } else {
                sb.append(sortList.get(i)).append(",");
            }

        }
        return sb.toString();
    }

    public static boolean notEqual(CharSequence str1, CharSequence str2) {
        return !equals(str1, str2);
    }

    public static boolean notEqual(CharSequence str1, CharSequence str2, boolean ignoreCase) {
        return !equals(str1, str2, ignoreCase);
    }

    /**
     * 首字母小写
     */
    public static String firstLowerCase(String str1) {
        return str1.substring(0, 1).toLowerCase() + str1.substring(1);
    }

    /**
     * 首字母大写
     */
    public static String firstUpperCase(String str1) {
        return str1.substring(0, 1).toUpperCase() + str1.substring(1);
    }

    /**
     * 拼接字符串
     */
    public static void append(StringBuilder stringBuilder, String text, String... param) {
        stringBuilder.append(String.format(text + "\n", param));
    }


    /**
     * 拼接字符串
     */
    public static void append(StringBuilder stringBuilder, String text, int tab, String... param) {
        for (int i = 0; i < tab; i++) {
            stringBuilder.append("\t");
        }
        append(stringBuilder, text, param);
    }

    /**
     * 将下划线转化为大写
     */
    public static String underLineToUpperCase(String name, boolean firstCase) {
        if (isEmpty(name)) {
            return "";
        }
        String[] s = name.split("_");
        StringBuilder stringBuilder = new StringBuilder();
        for (String s1 : s) {
            stringBuilder.append(s1.substring(0, 1).toUpperCase() + s1.substring(1));
        }
        if (!firstCase) {
            return firstLowerCase(stringBuilder.toString());
        }
        return stringBuilder.toString();
    }


    /**
     * 以大写字母为分界点切割字符串
     */
    public static List<String> splitByUpperCase(String str, Boolean isLowerCase) {
        List<String> rs = new ArrayList<>();
        int index = 0;
        int len = str.length();
        for (int i = 1; i < len; i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                if (isLowerCase) {
                    rs.add(firstLowerCase(str.substring(index, i)));
                } else {
                    rs.add(str.substring(index, i));
                }
                index = i;
            }
        }
        if (isLowerCase) {
            rs.add(firstLowerCase(str.substring(index, len)));
        } else {
            rs.add(str.substring(index, len));
        }
        return rs;
    }
}
