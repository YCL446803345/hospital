package com.woniu.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * token未空异常
 */
public class ToKenIsNullException extends AuthenticationException {

    public ToKenIsNullException(String msg, Throwable t) {
        super(msg, t);
    }

    public ToKenIsNullException(String msg) {
        super(msg);
    }
}
