package com.atguigu.crowd.exception;

public class CanNotRemoveCurrentUser extends RuntimeException {

    public CanNotRemoveCurrentUser() {
        super();
    }

    public CanNotRemoveCurrentUser(String message) {
        super(message);
    }

    public CanNotRemoveCurrentUser(String message, Throwable cause) {
        super(message, cause);
    }

    public CanNotRemoveCurrentUser(Throwable cause) {
        super(cause);
    }
}
