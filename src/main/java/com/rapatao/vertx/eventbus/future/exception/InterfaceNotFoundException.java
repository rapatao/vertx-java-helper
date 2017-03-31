package com.rapatao.vertx.eventbus.future.exception;

/**
 * Created by rapatao on 27/10/16.
 */
public class InterfaceNotFoundException extends RuntimeException {

    public InterfaceNotFoundException() {
        super("Class must have an interface");
    }

}
