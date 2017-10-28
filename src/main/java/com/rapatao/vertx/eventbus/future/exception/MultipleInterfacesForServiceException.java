package com.rapatao.vertx.eventbus.future.exception;

/**
 * Created by rapatao on 27/10/16.
 */
@Deprecated
public class MultipleInterfacesForServiceException extends RuntimeException {

    public MultipleInterfacesForServiceException() {
        super("The class must have only one interface");
    }

}
