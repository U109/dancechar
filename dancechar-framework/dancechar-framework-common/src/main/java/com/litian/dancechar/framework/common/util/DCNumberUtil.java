package com.litian.dancechar.framework.common.util;

import cn.hutool.core.util.NumberUtil;

import java.text.NumberFormat;
import java.util.Objects;

/**
 * 类描述: 数字处理工具类
 *
 * @author 01406831
 *
 */
public class DCNumberUtil extends NumberUtil {

    public static final Integer INTEGER_ONE = 1;

    /**flag = true
     * 1、等比数列前n项和,系数和比例都为浮点数 coefficient+coefficient*proportion的1次方+coefficient*proportion的2次方+...
     * flag = false
     * 2、求 总和 coefficient*proportion的(n)次方
     * @param coefficient:中奖的奖品概率
     * @param proportion：兜底礼包概率之和
     * @param n：抽取次数
     * @return   计算后的数值
     */
    public static double getCompareSum(double coefficient, double proportion, int n,boolean flag) {
        if(n>10){
            n = 10;
        }
        if(Double.compare(proportion,0.0) == 0){
            return coefficient;
        }
        return flag?coefficient * getCompareSum(proportion,n-1):coefficient*getFactorial(proportion,n)/proportion;
    }

    private static double getCompareSum(double p, int n) {
        if (Double.compare(p,0.0)== 0) {
            return 0;
        }
        if (n < 0) {
            return -1;
        }
        return n > 0 ? getCompareSum(p, n-1) + getFactorial(p, n) : 1;
    }

    private static double getFactorial(double num, int n) {
        if (Double.compare(num,0.0) == 0) {
            return 0.0;
        }
        if (n < 0) {
            return -1;
        }
        return n > 0 ? num * getFactorial(num, n-1) : 1;
    }

    /**
     * 计算百分比
     * @param real 实际
     * @param sum 总数
     * @param precision 精度，保留小数后多少位
     * @return
     */
    public static String percent(Double real,Double sum,Integer precision){
        Objects.requireNonNull(real,"real can not be null!");
        Objects.requireNonNull(sum,"sum can not be null!");
        Objects.requireNonNull(precision,"precision can not be null!");
        if(sum == 0L){
            return "100";
        }
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(precision);
        return numberFormat.format(real/sum*100);
    }

}
