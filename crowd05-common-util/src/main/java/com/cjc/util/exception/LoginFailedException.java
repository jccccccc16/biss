package com.cjc.util.exception;

import com.cjc.util.constant.CrowdConstant;

public class LoginFailedException extends RuntimeException {

    public LoginFailedException(){
        super(CrowdConstant.MESSAGE_LOGIN_FAILED);
    }

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailedException(Throwable cause) {
        super(cause);
    }



}
