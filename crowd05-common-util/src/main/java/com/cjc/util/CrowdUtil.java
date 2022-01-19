package com.cjc.util;

import com.cjc.util.constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrowdUtil {
    /*** 判断当前请求是否为 Ajax 请求 * @param request * @return */
    public static boolean judgeRequestType(HttpServletRequest request) {
        // 1.获取请求消息头信息
         String acceptInformation = request.getHeader("Accept");
         String xRequestInformation = request.getHeader("X-Requested-With");
        // 2.检查并返回
        return( acceptInformation != null && acceptInformation.length() > 0 && acceptInformation.contains("application/json") ) || ( xRequestInformation != null && xRequestInformation.length() > 0 && xRequestInformation.equals("XMLHttpRequest") );
    }

    /**
     * md5加密
     * @return
     */
    public static String md5(String source){
        //如果无效
        if(source==null||source.length()==0){
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            // 执行加密
            byte[] digest = md5.digest(source.getBytes());
            BigInteger bigInteger = new BigInteger(1, digest);
            String encoded = bigInteger.toString(16).toUpperCase();
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前时间为
     * @return
     */
    public static String getNow(String pattern){
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String format = dateFormat.format(new Date());
        System.out.println(format);
        return format;
    }

    public static void main(String[] args) {
        String md5 = md5("admin");
        System.out.println(md5);
    }
}
