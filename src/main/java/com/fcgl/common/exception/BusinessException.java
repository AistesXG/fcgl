package com.fcgl.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常
 *
 * @author furg@senthink.com
 * @date 2017/11/16
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

    /**
     * 错误代码
     */
    private int code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 构造函数
     *
     * @param code  code
     * @param msg   msg
     * @param cause cause
     */
    public BusinessException(int code, String msg, Throwable cause) {
        super(code + ": " + cause.getMessage());
        this.code = code;
        this.msg = msg;
    }

    /**
     * 构造函数
     *
     * @param code code
     * @param msg  msg
     */
    public BusinessException(int code, String msg) {
        super(code + ": " + msg);
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return this.getClass() + "{" + getMessage() + "}";
    }
}
