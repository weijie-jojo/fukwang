package com.fukwang.util;

import org.springframework.util.StringUtils;

import java.sql.Date;

public class JudgeNull {
    public static String isNull(String str){
        if (!StringUtils.hasText(str)){
            return null;
        }
        return  str;
    }
    public static Integer isNull(Integer integer){
        if (integer == null || integer < 0){
            return null;
        }
        return integer;
    }
    public static Date isNull(Date date){
        if (!StringUtils.hasText((CharSequence) date)){
            return null;
        }
        return date;
    }

    public static java.util.Date isNull(java.util.Date date){
        if (!StringUtils.hasText((CharSequence) date)){
            return null;
        }
        return date;
    }
}
