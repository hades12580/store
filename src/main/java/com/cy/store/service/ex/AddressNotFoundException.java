package com.cy.store.service.ex;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/31 23:05
 */

/** 地址不存在的异常*/
public class AddressNotFoundException extends ServiceException {
    public AddressNotFoundException() {
    }

    public AddressNotFoundException(String message) {
        super(message);
    }

    public AddressNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddressNotFoundException(Throwable cause) {
        super(cause);
    }

    public AddressNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
