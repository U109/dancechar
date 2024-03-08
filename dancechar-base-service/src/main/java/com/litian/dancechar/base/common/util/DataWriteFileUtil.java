package com.litian.dancechar.base.common.util;

import cn.hutool.core.io.FileUtil;
import com.google.common.collect.Lists;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DataWriteFileUtil {

    // 预设手机号码首位
    public static String[] FRISTNUMLIST = {"130", "131", "132", "145", "155", "156", "185", "186", "176", "175", "133", "149", "153", "180", "181", "189", "177", "134", "139", "147",
            "150", "152"};

    // 生成手机号码
    public static ArrayList<String> createPhoneNumber(String[] firstNumList, int len, boolean unrepeat) {
        ArrayList re = new ArrayList();
        String first;
        String last;
        for (int i = 0; i < len; i++) {
            // 随机获取预设号码首位的数据数组下标
            first = firstNumList[(int) Math.floor(Math.random() * firstNumList.length)];
            // 随机生成8位随机数
            last = String.valueOf((int) Math.floor(Math.random() * 100000000));
            //生成随机号不够8位，调用d补齐
            if (last.length() < 8) {
                last = d(last);
            }
            re.add(first + last);
        }

        // 是否去重
        if (unrepeat) {
            // 调用去重函数unrepeated
            re = unrepeated(re, firstNumList);
        }

        return re;
    }


    private static ArrayList unrepeated(List reArray, String[] firstNumList) {
        HashSet reSet = new HashSet(reArray);

        System.out.println("Now is " + reSet.size());
        if (reSet.size() < reArray.size()) {
            int diff = reArray.size() - reSet.size();
            reArray.clear();
            reArray.addAll(reSet);
            reArray.addAll(createPhoneNumber(firstNumList, diff, false));
            // System.out.println("this is " + diff + "___" + reArray.size());

            // 回归调用自身，以达到去重效果
            reArray = unrepeated(reArray, firstNumList);
        }

        return (ArrayList) reArray;
    }

    // 补位函数
    private static String d(String a) {
        String temp = null;
        for (int i = 8 - a.length(); i > 0; i--) {
            temp = String.valueOf((int) Math.floor(Math.random() * 10));
            a = a + temp;
        }
        return a;
    }

    public static void main(String[] args) {
        String[] s = {"158", "132", "166","156", " 185", "134", "139"};
        List<String> newList = Lists.newArrayList();
        List<String> phoneList = createPhoneNumber(s, 1000, true);
        for(String str : phoneList){
            newList.add(str.trim());
        }
        FileUtil.writeLines(newList, new File("/Users/guohg/project/cg.txt"), "utf-8");
        System.out.println("===finish===");
    }
}
