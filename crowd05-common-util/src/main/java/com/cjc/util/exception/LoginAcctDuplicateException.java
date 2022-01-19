package com.cjc.util.exception;

import com.cjc.util.constant.CrowdConstant;

public class LoginAcctDuplicateException extends RuntimeException{

    public LoginAcctDuplicateException() {
        super(CrowdConstant.MESSAGE_LOGIN_ACCOUNT_DUPLICATE);
    }

    public LoginAcctDuplicateException(String message) {
        super(message);
    }

    public LoginAcctDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctDuplicateException(Throwable cause) {
        super(cause);
    }
}
