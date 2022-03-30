package com.atguigu.crowd.mvc.config;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.OperLog;
import com.atguigu.crowd.monitor.aspect.OperLogAspect;
import com.atguigu.crowd.monitor.service.api.OperLogService;
import com.atguigu.crowd.util.CrowdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败，进行日志记录
 */
@Component
public class CrowdAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    private OperLogService operLogService;
    private Logger logger = LoggerFactory.getLogger(CrowdAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        OperLog operLog = new OperLog();
        operLog = new OperLog();
        // 方法名
        // 请求方法
        String requestMethod = request.getMethod();
        operLog.setRequestMethod(requestMethod);
        // 操作者
        String operName = request.getParameter("loginAcct");
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
        operLog.setErrorMsg("登录失败");
        operLog.setStatus(OperLogAspect.FAILED);
        // 设置访问时间
        operLog.setCreateTime(CrowdUtil.getNow(CrowdConstant.DATE_PATTERN));
        operLogService.saveOperLog(operLog);
        logger.info("操作者：" + operLog.getOperName() + ",业务类型：" + operLog.getBusinessType() + ",访问：" + operLog.getStatus() +",ip："+operLog.getOperIp()+",访问时间：" + operLog.getCreateTime());

        String loginAcct = request.getParameter("loginAcct");
        request.getSession().setAttribute("message","账号或密码错误");
        request.getSession().setAttribute("loginAcct",loginAcct);

        response.sendRedirect("http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/admin/to/login/page.html");
    }
}
