package com.cjc.util.exception;

import com.cjc.util.constant.CrowdConstant;

public class LoginFailedException extends Exception {
    public LoginFailedException(){
        super();
    }

    @Override
    public String getMessage() {
        return CrowdConstant.MESSAGE_LOGIN_FAILED;
    }
}
