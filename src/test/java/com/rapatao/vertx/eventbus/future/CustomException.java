package com.rapatao.vertx.eventbus.future;

/**
 * Created by rapatao on 27/10/16.
 */
public class CustomException extends Exception {

    public static final String VALUE = "custom fail message";

    CustomException() {
        super(VALUE);
    }

}
