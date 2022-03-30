package com.atguigu.crowd.monitor.interceptor;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.OperLog;
import com.atguigu.crowd.monitor.annotation.BusinessType;
import com.atguigu.crowd.monitor.service.api.OperLogService;
import com.atguigu.crowd.util.CrowdUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 记录日志操作的拦截器
 * 不知为何不能通过handlermethod获取business注解值
 */
@Component
public class OperLogInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private OperLogService operLogService;



    private OperLog operLog;



    public static final String SUCCESS = "请求成功";
    public static final String FAILED = "请求失败";
    // 是否需要做日志处理
    private boolean isBusinessType = false;

    // 访问前的数据
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            // 判断是否需要做日志处理
            isBusinessType = isBusinessType((HandlerMethod)handler);
            if(isBusinessType){
                operLog = new OperLog();
                setPreInfoToOperLog(request, response, handler);
            }
        }
        return true;
    }

    // 处理访问后的信息
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if(isBusinessType){
            // 设置访问后的状态，成功或者失败
            setVisitStatusInfo(ex);
            // 插入日志
            operLogService.saveOperLog(operLog);
        }

    }

    /**
     * 判断是否需要被拦截
     * @param handlerMethod
     * @return
     */
    private boolean isBusinessType(HandlerMethod handlerMethod){
        BusinessType businessTypeAnnotation = getBusinessTypeAnnotation(handlerMethod);
        BusinessType methodAnnotation = handlerMethod.getMethod().getAnnotation(BusinessType.class);
        // 如果为空那么不需要拦截
        if(methodAnnotation==null){
            return false;
        }
        return true;
    }

    private BusinessType getBusinessTypeAnnotation(HandlerMethod handlerMethod){
        BusinessType businessTypeAnnotation = handlerMethod.getMethod().getAnnotation(BusinessType.class);
        return businessTypeAnnotation;
    }

    // 设置访问后的状态
    private void setVisitStatusInfo(Exception ex){
        if(ex!=null){
            // 错误信息
            this.operLog.setErrorMsg(ex.getMessage());
            // 请求成功还是失败
            this.operLog.setStatus(FAILED);
        }
        // 如果没有异常
        this.operLog.setErrorMsg("");
        this.operLog.setStatus(SUCCESS);
        this.operLog.setCreateTime(CrowdUtil.getNow(CrowdConstant.DATE_PATTERN));
    }


    /**
     *
     * 执行方法的一些数据绑定
     *
     * @param request
     * @param response
     * @param handler
     */
    private void setPreInfoToOperLog(HttpServletRequest request, HttpServletResponse response, Object handler){
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        // 方法全类名
        String method = handlerMethod.getMethod().getDeclaringClass().getName()+"."+handlerMethod.getMethod().getName();
        this.operLog.setMethod(method);
        // 请求方法
        String requestMethod = request.getMethod();
        this.operLog.setRequestMethod(requestMethod);
        // 操作者
        String operName = getCurrentAdmin().getLoginAcct();
        this.operLog.setOperName(operName);
        // url
        String operUrl = request.getRequestURL().toString();
        this.operLog.setOperUrl(operUrl);
        // 操作者ip
        String operIp = request.getRemoteAddr();
        this.operLog.setOperIp(operIp);
        // 获取业务类型，具体为什么业务
        String businessTypeValue = getBusinessTypeValue(handlerMethod);
        this.operLog.setBusinessType(businessTypeValue);
    }



    private String getBusinessTypeValue(HandlerMethod handlerMethod){
        BusinessType businessTypeAnnotation = getBusinessTypeAnnotation(handlerMethod);
        String businessType = businessTypeAnnotation.value();
        return businessType;
    }


    private Admin getCurrentAdmin(){
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        Admin currentAdmin = (Admin)request.getSession().getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
        return currentAdmin;
    }
}
