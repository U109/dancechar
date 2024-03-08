package com.litian.dancechar.framework.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类描述: javax.ws.rs yyyy-MM-dd日期格式化
 *
 * @author 01406831
 * @date 2021/04/21 10:12
 */
@Slf4j
public class YYYYMMDDDateAdapter extends XmlAdapter<String, Date> {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd");

    @Override
    public String marshal(Date str) {
        return DATE_FORMAT.format(str);
    }

    @Override
    public Date unmarshal(String str) {
        if (DCStrUtil.isBlank(str)) {
            return null;
        }
        // 纯13位数字我们就当做毫秒数，因为这个时间已经表示2001-09-09 09:46:40到2286-11-21 01:46:39，足够使用了
        if (str.length() == 13 && DCNumberUtil.isNumber(str)) {
            return new Date(Long.parseLong(str));
        }
        return DCDateUtil.parse(str);
    }
}