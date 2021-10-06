package com.caibucai.utils;

/**
 * @author Csy
 * @Classname DateUtil
 * @date 2021/9/25 16:48
 * @Description TODO
 */
public class DateUtil {

    public static java.sql.Timestamp d2t(java.util.Date date){
        if(null == date){
            return null;
        }
        return new java.sql.Timestamp(date.getTime());
    }

    public static java.util.Date t2d(java.sql.Timestamp timestamp){
        if(null == timestamp){
            return null;
        }
        return new java.util.Date(timestamp.getTime());
    }
}
