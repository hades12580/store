package com.cy.store.service.ex;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/31 23:04
 */

/** 非法访问的异常*/
public class AccessDeniedException extends ServiceException {
    public AccessDeniedException() {
    }

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessDeniedException(Throwable cause) {
        super(cause);
    }

    public AccessDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
