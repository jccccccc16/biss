package com.atguigu.crowd.mvc.config;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.OperLog;
import com.atguigu.crowd.monitor.aspect.OperLogAspect;
import com.atguigu.crowd.monitor.service.api.OperLogService;
import com.atguigu.crowd.util.CrowdUtil;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@Component
public class CrowdAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Autowired
    private OperLogService operLogService;
    private Logger logger = LoggerFactory.getLogger(CrowdAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        OperLog operLog = new OperLog();
        operLog = new OperLog();
        // 方法名
        // 请求方法
        String requestMethod = request.getMethod();
        operLog.setRequestMethod(requestMethod);
        // 操作者
        String operName = OperLogAspect.getCurrentAdmin().getLoginAcct();
        operLog.setOperName(operName);
        // url
        String operUrl = request.getServletPath();
        operLog.setOperUrl(operUrl);
        System.out.println(operUrl + "--------------------");
        // 操作者ip
        String operIp = request.getRemoteAddr();
        operLog.setOperIp(operIp);
        // 获取业务类型，具体为什么业务
        String login = "登录操作";
        String businessTypeValue = login;
        operLog.setBusinessType(businessTypeValue);
        operLog.setErrorMsg("");
        operLog.setStatus(OperLogAspect.SUCCESS);
        // 设置访问时间
        operLog.setCreateTime(CrowdUtil.getNow(CrowdConstant.DATE_PATTERN));
        operLogService.saveOperLog(operLog);
        logger.info("操作者：" + operLog.getOperName() + ",业务类型：" + operLog.getBusinessType() + ",访问：" + operLog.getStatus() +",ip："+operLog.getOperIp()+",访问时间：" + operLog.getCreateTime());
        httpServletResponse.sendRedirect("http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath());
    }
}
