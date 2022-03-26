package com.cy.store.controller.ex;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/26 21:10
 */

/** 上传的文件状态异常 */
public class FileStateException extends FileUploadException {
    public FileStateException() {
        super();
    }

    public FileStateException(String message) {
        super(message);
    }

    public FileStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileStateException(Throwable cause) {
        super(cause);
    }

    public FileStateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
