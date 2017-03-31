package com.rapatao.vertx.eventbus.future.exception;

import lombok.Getter;

/**
 * Created by rapatao on 22/11/16.
 */
public class ProxyHelperException extends Exception {

    @Getter
    private StackTraceElement[] stackTraceElements;

    public ProxyHelperException() {
        super();
    }

    public ProxyHelperException(final String message) {
        super(message);
    }

    public ProxyHelperException(final String message, final StackTraceElement[] stackTraceElements) {
        super(message);
        this.stackTraceElements = stackTraceElements;
    }

}
