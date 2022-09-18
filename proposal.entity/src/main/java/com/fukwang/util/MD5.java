package com.fukwang.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    //单次加密
    public String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }
    //循环多次加密
    public String EncoderByMd5Many(String str,int count) throws UnsupportedEncodingException, NoSuchAlgorithmException {
         if(count==1){
             return  EncoderByMd5(str);
         }else {
             String strMd5  =   EncoderByMd5(str);

             return EncoderByMd5Many(strMd5,count-1);
         }
    }

}
