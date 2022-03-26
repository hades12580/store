package com.cy.store.controller.ex;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/26 21:06
 */

/** 上传的文件的大小超出了限制值 */
public class FileSizeException extends FileUploadException {
    public FileSizeException() {
        super();
    }

    public FileSizeException(String message) {
        super(message);
    }

    public FileSizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileSizeException(Throwable cause) {
        super(cause);
    }

    public FileSizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
