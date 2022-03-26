package com.cy.store.controller.ex;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/26 21:06
 */

/** 上传文件时读写异常 */
public class FileUploadIOException extends FileUploadException {
    public FileUploadIOException() {
        super();
    }

    public FileUploadIOException(String message) {
        super(message);
    }

    public FileUploadIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUploadIOException(Throwable cause) {
        super(cause);
    }

    public FileUploadIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
