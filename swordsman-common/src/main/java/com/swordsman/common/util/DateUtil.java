package com.swordsman.common.util;

import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author DuChao
 * @Date 2019-10-21 11:29
 * 时间转换工具类
 */
public class DateUtil {

    public static final String COUNTER_ONE = "+";
    public static final String COUNTER_TWO = "-";
    /**
     * String转换Date类型
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date StringToDate(String date) {
        Date result;
        String parse = date.replaceFirst("[0-9]{4}([^0-9]?)", "yyyy$1");
        parse = parse.replaceFirst("^[0-9]{2}([^0-9]?)", "yy$1");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1MM$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}( ?)", "$1dd$2");
        parse = parse.replaceFirst("( )[0-9]{1,2}([^0-9]?)", "$1HH$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1mm$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1ss$2");
        DateFormat format = new SimpleDateFormat(parse);
        try {
            result = format.parse(date);
        } catch (ParseException e) {
            result = null;
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Date类型转String
     * @param date
     * @return
     * @throws ParseException
     */
    public static String DateToString(Date date) {
        if(date == null){
            return null;
        }
        return DateToString(date,null);
    }

    /**
     * 将Date转换为String，并且指定
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static String DateToString(Date date,String pattern){
        if(date == null){
            return null;
        }
        SimpleDateFormat simpleDateFormat;
        if(StringUtils.isEmpty(pattern)){
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }else{
            simpleDateFormat = new SimpleDateFormat(pattern);
        }
        return simpleDateFormat.format(date);
    }

    /**
     * 时间类型转换秒
     * @param date
     * @return
     */
    public static Integer DateToSeconds(Date date){
        if(date == null){
            return null;
        }
        Integer time = Math.toIntExact(date.getTime() / 1000);
        return time;
    }


    /**
     * 传入一个时间获取当天的00:00:00
     * @param date
     * @return
     */
    public static Date getDayFirstTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 传入一个时间获取当天的23:59:59
     * @param date
     * @return
     */
    public static Date getDayLastTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }
    //重载方法
    public static Date getDayLastTime(String date) {
        if(date.trim().isEmpty()){
            return null;
        }
        return getDayLastTime(DateUtil.StringToDate(date));
    }

    /**
     * 根据参数返回时间集合
     * @param date 时间
     * @param day 天数
     * @param clearTime 是否清空时分秒
     * @param counter 向前统计还是向后
     * @return
     */
    public static List<Date> getByDay(Date date,Integer day,boolean clearTime,String counter){
        List<Date> dates =  new CopyOnWriteArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //判断是否清空当天时间
        if(clearTime){
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
        }
        //循环多少天数据
        for(int i = 1; i <= day; ++i) {
            if(i == 1){
                //第一条为当天
                dates.add(calendar.getTime());
            }else{
                if(counter != null){
                    if(COUNTER_ONE.equals(counter)){
                        dates.add(new Date(calendar.getTime().getTime()+1000L*60*60*24*(i-1)));
                    }else if(COUNTER_TWO.equals(counter)){
                        dates.add(new Date(calendar.getTime().getTime()-1000L*60*60*24*(i-1)));
                    }
                }
            }
        }
        return dates;
    }

}
