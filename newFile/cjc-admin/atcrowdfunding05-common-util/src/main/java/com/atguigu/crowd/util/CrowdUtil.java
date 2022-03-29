package com.atguigu.crowd.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.atguigu.crowd.constant.CrowdConstant;

/**
 * 工具类
 */
public class CrowdUtil {

	/**
	 * 获取当前时间为
	 *
	 * @return
	 */
	public static String getNow(String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		String format = dateFormat.format(new Date());
		System.out.println(format);
		return format;
	}

	/**
	 * 获取该时间与现在时间的时间差
	 * 如果是负数，该项目不可用
	 * 用于判断是否众筹失败
	 * @param day 众筹天数
	 * @param createDate 发起日期
	 * @return
	 */

	public static Integer getDateSub(int day,String createDate){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		/*天数差*/
		Date now = new Date();
		try {

			Date toDate1 = simpleDateFormat.parse(createDate);
			long from1 = now.getTime();
			long to1 = toDate1.getTime();
			int days = (int) ((to1 - from1) / (1000 * 60 * 60 * 24))+day;
			System.out.println("两个时间之间的天数差为：" + days);
			return days;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}



	
	/**
	 * 对明文字符串进行MD5加密
	 * @param source 传入的明文字符串
	 * @return 加密结果
	 */
	public static String md5(String source) {
		
		// 1.判断source是否有效
		if(source == null || source.length() == 0) {
		
			// 2.如果不是有效的字符串抛出异常
			throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
		}
		
		try {
			// 3.获取MessageDigest对象
			String algorithm = "md5";
			
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			
			// 4.获取明文字符串对应的字节数组
			byte[] input = source.getBytes();
			
			// 5.执行加密
			byte[] output = messageDigest.digest(input);
			
			// 6.创建BigInteger对象
			int signum = 1;
			BigInteger bigInteger = new BigInteger(signum, output);
			
			// 7.按照16进制将bigInteger的值转换为字符串
			int radix = 16;
			String encoded = bigInteger.toString(radix).toUpperCase();
			
			return encoded;
		
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 判断当前请求是否为Ajax请求
	 * @param request 请求对象
	 * @return
	 * 		true：当前请求是Ajax请求
	 * 		false：当前请求不是Ajax请求
	 */
	public static boolean judgeRequestType(HttpServletRequest request) {
		
		// 1.获取请求消息头
		String acceptHeader = request.getHeader("Accept");
		String xRequestHeader = request.getHeader("X-Requested-With");
		
		// 2.判断
		return (acceptHeader != null && acceptHeader.contains("application/json"))
				
				||
				
				(xRequestHeader != null && xRequestHeader.equals("XMLHttpRequest"));
	}

}
