package com.cjc.util;

import javax.servlet.http.HttpServletRequest;

public class CrowdUtil {
    /*** 判断当前请求是否为 Ajax 请求 * @param request * @return */
    public static boolean judgeRequestType(HttpServletRequest request) {
        // 1.获取请求消息头信息
         String acceptInformation = request.getHeader("Accept");
         String xRequestInformation = request.getHeader("X-Requested-With");
        // 2.检查并返回
        return( acceptInformation != null && acceptInformation.length() > 0 && acceptInformation.contains("application/json") ) || ( xRequestInformation != null && xRequestInformation.length() > 0 && xRequestInformation.equals("XMLHttpRequest") );
    }
}
