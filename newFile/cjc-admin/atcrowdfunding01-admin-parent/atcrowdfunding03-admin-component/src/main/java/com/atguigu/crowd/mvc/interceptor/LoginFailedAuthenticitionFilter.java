package com.atguigu.crowd.mvc.interceptor;

import com.atguigu.crowd.constant.CrowdConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFailedAuthenticitionFilter implements AuthenticationFailureHandler {
    private Logger log = LoggerFactory.getLogger(LoginFailedAuthenticitionFilter.class);
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.warn(e.getMessage());
        log.warn(CrowdConstant.MESSAGE_LOGIN_FAILED);
        httpServletRequest.setAttribute("exception",new RuntimeException(CrowdConstant.MESSAGE_LOGIN_FAILED));
        String loginAcct = httpServletRequest.getParameter("loginAcct");
//        httpServletRequest.
    }
}
