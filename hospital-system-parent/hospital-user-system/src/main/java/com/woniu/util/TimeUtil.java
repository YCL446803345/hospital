package com.woniu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 **/
public class TimeUtil {

    /** 将时间类型转换成对应的字符串类型
     * @param date 传进来的时间类型参数
     * @return
     */
    public static String getNowTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /** 方法重载,将字符串类型转换成对应的时间类型
     * @param date 传进来的字符串类型
     * @return
     */
    public static Date getNowTime(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = null;
        try {
            parse = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }


    /**
     * 自动生成当前系统时间Data
     * @return
     */
    public static Date getNowDataTime() {
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = simpleDateFormat.format(new Date());
            date = simpleDateFormat.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }



}
