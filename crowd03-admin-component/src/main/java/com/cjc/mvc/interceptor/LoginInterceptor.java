package com.cjc.mvc.interceptor;

import com.cjc.crowd.entity.Admin;
import com.cjc.util.constant.CrowdConstant;
import com.cjc.util.exception.AccessForbiddenException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截器，拦截没有登录的用户
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
        // 如果为空那么没有登录
        if(admin==null){
            // 抛出异常
            throw new AccessForbiddenException();
        }
        return true;
    }
}
