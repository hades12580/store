package com.cy.store.service.ex;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/4/5 23:42
 */

/** 购物车数据不存在的异常 */
public class CartNotFoundException extends ServiceException {
    public CartNotFoundException() {
        super();
    }

    public CartNotFoundException(String message) {
        super(message);
    }

    public CartNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CartNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CartNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
