package com.cy.store.controller.ex;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/26 21:06
 */

/** 上传的文件类型超出了限制 */
public class FileTypeException extends FileUploadException {
    public FileTypeException() {
        super();
    }

    public FileTypeException(String message) {
        super(message);
    }

    public FileTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileTypeException(Throwable cause) {
        super(cause);
    }

    public FileTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
