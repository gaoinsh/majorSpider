package com.spider.common.utils;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiang.gao on 2017/5/18.
 * project majorSpider
 */
public class DateUtils {
    private static Logger logger = Logger.getLogger(DateUtils.class);
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date getDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        try {
            Date e = sdf.parse(date);
            return e;
        } catch (ParseException var4) {
            logger.error(var4);
            return null;
        }
    }
}
