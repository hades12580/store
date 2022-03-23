package com.cy.store.service.ex;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/22 16:56
 */

/** 密码验证失败的异常 */
public class PasswordNotMatchException extends ServiceException {
    public PasswordNotMatchException() {
        super();
    }

    public PasswordNotMatchException(String message) {
        super(message);
    }

    public PasswordNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordNotMatchException(Throwable cause) {
        super(cause);
    }

    protected PasswordNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}