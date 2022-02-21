package com.cjc.crowd.util.exception;


import com.cjc.crowd.util.constant.CrowdConstant;

/**
 * 权限控制异常，假如没有登录，不能访问其他的网页
 */
public class AccessForbiddenException extends RuntimeException{
    public AccessForbiddenException() {
        super(CrowdConstant.MESSAGE_NOT_LOGIN_CANT_NOT_VISIT);
    }

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessForbiddenException(Throwable cause) {
        super(cause);
    }
}
