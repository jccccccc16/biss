package com.atguigu.crowd.monitor.aspect;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.OperLog;
import com.atguigu.crowd.exception.AccessForbiddenException;
import com.atguigu.crowd.monitor.annotation.BusinessType;
import com.atguigu.crowd.monitor.service.api.OperLogService;
import com.atguigu.crowd.mvc.config.SecurityAdmin;
import com.atguigu.crowd.util.CrowdUtil;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.nimbus.NimbusStyle;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@EnableAspectJAutoProxy
@Component
@Order(0)
public class OperLogAspect {

    @Autowired
    private OperLogService operLogService;

    private OperLog operLog;

    private boolean isBusinessType = false;
    private boolean isLogin = true;
    private Logger logger = LoggerFactory.getLogger(OperLogAspect.class);



    public static final String SUCCESS = "请求成功";
    public static final String FAILED = "请求失败";

    @Pointcut("execution(* *..*Handler.*(..))")
    public void cutPointExpression() {
    }


    @Pointcut("@annotation(com.atguigu.crowd.monitor.annotation.BusinessType)")
    public void AnnotationCutPoint() {

    }



    @Around("AnnotationCutPoint() ")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        Object returnValue = null;
        Throwable currentThrowable=null;
        try {
            // 前置通知
            beforeMethod(joinPoint);
            // 执行方法体
            returnValue = joinPoint.proceed();
            // 后置通知
            afterMethod(joinPoint);
            Throwable throwable;
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage());
            afterThrowingMethod(joinPoint, throwable);
            currentThrowable = throwable;
        }finally {
            // 插入日志
            operLogService.saveOperLog(operLog);
            logger.info("操作者：" + operLog.getOperName() + ",业务类型：" + operLog.getBusinessType() + ",访问：" + operLog.getStatus() +",ip："+operLog.getOperIp()+",访问时间：" + operLog.getCreateTime());
            // 如果出现异常被捕获的情况，在这里抛出异常，交给全局异常处理器捕获处理
            if(currentThrowable!=null){
                throw currentThrowable;
            }
            // 插入日志
            return returnValue;
        }
    }

    /**
     * 拦截控制器
     */


    public void beforeMethod(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        // 如果是登录操作，那么就在后置消息中配置setPreInfoToOperLog
        if (isLoginAction()) {
            return;
        }

        logger.info("执行beforeMethod");

        setPreInfoToOperLog(joinPoint);

    }

    private boolean isLoginAction() {
        Admin currentAdmin = getCurrentAdmin();
        if (currentAdmin == null) {
            this.isLogin = true;
        } else {
            this.isLogin = false;
        }
        return isLogin;
    }

    /**
     * 后置通知
     *
     * @param joinPoint
     */
    public void afterMethod(ProceedingJoinPoint joinPoint) {
        afterMethod0(joinPoint,null);
    }

    private void afterMethod0(ProceedingJoinPoint joinPoint, Throwable throwable){
        // 是否是登录操作
        if (isLogin) {
            setPreInfoToOperLog(joinPoint);
        }
        // 设置访问后的状态，访问成功
        setVisitStatusInfo(throwable);
    }

    /**
     * 出现错误后
     *
     * @param joinPoint
     * @param
     */

    public void afterThrowingMethod(ProceedingJoinPoint joinPoint, Throwable throwable) {
        afterMethod0(joinPoint,throwable);
    }

    // 设置访问后的状态
    private void setVisitStatusInfo(Throwable throwable) {
        if (throwable != null) {
            // 错误信息
            this.operLog.setErrorMsg(throwable.getMessage());
            // 请求成功还是失败
            this.operLog.setStatus(FAILED);
        } else {
            // 如果没有异常
            this.operLog.setErrorMsg("");
            this.operLog.setStatus(SUCCESS);
        }
        // 设置访问时间
        this.operLog.setCreateTime(CrowdUtil.getNow(CrowdConstant.DATE_PATTERN));
    }


    private BusinessType getBusinessType(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        BusinessType annotation = signature.getMethod().getAnnotation(BusinessType.class);
        return annotation;
    }


    private String getBusinessTypeValue(ProceedingJoinPoint joinPoint) {
        BusinessType businessType = getBusinessType(joinPoint);
        return businessType.value();
    }

    /**
     * 执行方法的一些数据绑定
     */
    private void setPreInfoToOperLog(ProceedingJoinPoint joinPoint) {
        this.operLog = new OperLog();
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        // 方法名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String methodName = method.getName();
        this.operLog.setMethod(methodName);
        // 请求方法
        String requestMethod = request.getMethod();
        this.operLog.setRequestMethod(requestMethod);
        // 操作者
        String operName = getCurrentAdmin().getLoginAcct();
        this.operLog.setOperName(operName);
        // url
        String operUrl = request.getServletPath();
        this.operLog.setOperUrl(operUrl);
        System.out.println(operUrl + "--------------------");
        // 操作者ip
        String operIp = request.getRemoteAddr();
        this.operLog.setOperIp(operIp);
        // 获取业务类型，具体为什么业务
        String businessTypeValue = getBusinessTypeValue(joinPoint);
        this.operLog.setBusinessType(businessTypeValue);
    }

    public static Admin getCurrentAdmin() {
        SecurityAdmin securityAdmin = (SecurityAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Admin originalAdmin = securityAdmin.getOriginalAdmin();
        return originalAdmin;
    }


//    private Admin getCurrentAdmin() {
//        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
//        Admin currentAdmin = (Admin) request.getSession().getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
//        return currentAdmin;
//    }

    //    private boolean isBusiness(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
//
//
//        Object[] args = joinPoint.getArgs();
//        Class<?>[] argTypes = new Class[joinPoint.getArgs().length];
//        for (int i = 0; i < args.length; i++) {
//            argTypes[i] = args[i].getClass();
//        }
//        Method method = null;
//        try {
//            method = joinPoint.getTarget().getClass()
//                    .getDeclaredMethod(joinPoint.getSignature().getName(), argTypes);
//
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }
//
//        BusinessType methodAnnotation = method.getAnnotation(BusinessType.class);
//        if (methodAnnotation != null) {
//            this.operLog = new OperLog();
//            return true;
//        }
//        return false;
//    }

}
