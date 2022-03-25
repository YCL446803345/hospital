package com.woniu.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * token无效异常
 */
public class ToKenInvalidException extends AuthenticationException {

    public ToKenInvalidException(String msg, Throwable t) {
        super(msg, t);
    }

    public ToKenInvalidException(String msg) {
        super(msg);
    }
}
