package com.fcgl.common.exception;

/**
 * 认证异常(当用户无权访问资源时, 抛出此异常)
 *
 * @author furg@senthink.com
 * @date 2018/03/16
 */
public class AuthenticationException extends RuntimeException {

    public AuthenticationException() {
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }

    public AuthenticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
