package com.cy.store.controller.ex;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/26 21:05
 */

/** 上传的文件为空的异常，例如没有选择上传的文件就提交了表单，或选择的文件是零字节的空文件 */
public class FileEmptyException extends FileUploadException {
    public FileEmptyException() {
        super();
    }

    public FileEmptyException(String message) {
        super(message);
    }

    public FileEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileEmptyException(Throwable cause) {
        super(cause);
    }

    public FileEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
