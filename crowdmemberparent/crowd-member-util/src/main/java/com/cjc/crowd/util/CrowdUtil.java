package com.cjc.crowd.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import com.cjc.crowd.util.constant.CrowdConstant;
import com.sun.xml.internal.ws.spi.db.DatabindingException;
import org.springframework.mail.SimpleMailMessage;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CrowdUtil {


    /*** 判断当前请求是否为 Ajax 请求 * @param request * @return */
    public static boolean judgeRequestType(HttpServletRequest request) {
        // 1.获取请求消息头信息
        String acceptInformation = request.getHeader("Accept");
        String xRequestInformation = request.getHeader("X-Requested-With");
        // 2.检查并返回
        return (acceptInformation != null && acceptInformation.length() > 0 && acceptInformation.contains("application/json")) || (xRequestInformation != null && xRequestInformation.length() > 0 && xRequestInformation.equals("XMLHttpRequest"));
    }


    /**
     * md5加密
     *
     * @return
     */
    public static String md5(String source) {
        //如果无效
        if (source == null || source.length() == 0) {
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
     *
     * @return
     */
    public static String getNow(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String format = dateFormat.format(new Date());
        System.out.println(format);
        return format;
    }



    /*** 专门负责上传文件到 OSS 服务器的工具方法 * @param endpoint OSS 参数 * @param accessKeyId OSS 参数 * @param accessKeySecret OSS 参数 * @param inputStream 要上传的文件的输入流 * @param bucketName OSS 参数 * @param bucketDomain OSS 参数 * @param originalName 要上传的文件的原始文件名 * @return 包含上传结果以及上传的文件在 OSS 上的访问路径 */
    public static ResultEntity<String> uploadFileToOss(String endpoint, String accessKeyId, String accessKeySecret, InputStream inputStream, String bucketName, String bucketDomain, String originalName) {
        // 创建 OSSClient 实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 生成上传文件的目录
        String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());
        // 生成上传文件在 OSS 服务器上保存时的文件名 // 原始文件名：beautfulgirl.jpg // 生成文件名：wer234234efwer235346457dfswet346235.jpg // 使用 UUID 生成文件主体名称
        String fileMainName = UUID.randomUUID().toString().replace("-", ""); // 从原始文件名中获取文件扩展名
        String extensionName = originalName.substring(originalName.lastIndexOf(".")); // 使用目录、文件主体名称、文件扩展名称拼接得到对象名
        String objectName = folderName + "/" + fileMainName + extensionName;
        try {
            // 调用 OSS 客户端对象的方法上传文件并获取响应结果数据
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName, inputStream);
            // 从响应结果中获取具体响应消息
            ResponseMessage responseMessage = putObjectResult.getResponse();
            // 根据响应状态码判断请求是否成功
            if (responseMessage == null) {
                // 拼接访问刚刚上传的文件的路径
                String ossFileAccessPath = bucketDomain + "/" + objectName;
                // 当前方法返回成功
                return ResultEntity.successWithData(ossFileAccessPath);
            } else {// 获取响应状态码
                int statusCode = responseMessage.getStatusCode();
                // 如果请求没有成功，获取错误消息
                String errorMessage = responseMessage.getErrorResponseAsString();
                // 当前方法返回失败
                return ResultEntity.failed(" 当 前 响 应 状 态 码 =" + statusCode + " 错 误 消 息 =" + errorMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 当前方法返回失败
            return ResultEntity.failed(e.getMessage());
        } finally {
            if (ossClient != null) {
                // 关闭 OSSClient。
                ossClient.shutdown();
            }
        }
    }


    /**
     *
     * @param receiver
     * @return 返回验证码
     */
    public static MyMessage getMessage(String receiver){

        // 获取验证码
        // 生成验证码
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 4; i++) {
            int random = (int) (Math.random() * 10);
            builder.append(random);
        }
        String code = builder.toString();

        // 发送验证码
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("大学生创新创业众筹平台注册验证码");
        message.setText("注册验证码是："+code);
        message.setTo(receiver);
        message.setFrom("617028197@qq.com");
        return new MyMessage(message,code);




    }

    public static class MyMessage{
        private SimpleMailMessage message;
        private String code;

        public MyMessage(SimpleMailMessage message, String code) {
            this.message = message;
            this.code = code;
        }

        public SimpleMailMessage getMessage() {
            return message;
        }

        public void setMessage(SimpleMailMessage message) {
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }


    // 获取该时间与现在时间的时间差
    // 如果是负数，该项目不可用
    public static Integer getDateSub(int day,String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        /*天数差*/
        Date now = new Date();
        try {

            Date toDate1 = simpleDateFormat.parse(date);
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

    public static void main(String[] args) {
        String now = getNow("yyyy-MM-dd hh:mm:ss");
        System.out.println(now);
        Integer dateSub = getDateSub(15,"2022-02-10");

    }


}
