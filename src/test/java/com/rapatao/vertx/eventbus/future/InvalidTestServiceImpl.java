package com.rapatao.vertx.eventbus.future;

import io.vertx.core.Future;

import java.io.Serializable;

/**
 * Created by rapatao on 13/09/16.
 */
public class InvalidTestServiceImpl implements TestService, Serializable {

    @Override
    public Future<String> stringMethod(String value) {
        return null;
    }

    @Override
    public Future<String> throwMethodWithoutCustomFailMessageHandler(String value) {
        return null;
    }

    @Override
    public Future<String> stringMethodWithoutArgument() {
        return null;
    }

    @Override
    public Future<String> stringMethodWithMultipleArguments(String value1, String value2, Integer value3) {
        return null;
    }

    @Override
    public Future<String> shouldReturnCustomException() {
        return null;
    }
}
