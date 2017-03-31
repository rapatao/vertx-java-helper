package com.rapatao.vertx.eventbus.future;

import io.vertx.core.Future;

/**
 * Created by rapatao on 13/09/16.
 */
public class ValidTestServiceImpl implements TestService {

    @Override
    public Future<String> stringMethod(String value) {
        Future<String> future = Future.future();

        future.complete("future complete: " + value);

        return future;
    }

    @Override
    public Future<String> throwMethodWithoutCustomFailMessageHandler(String value) {
        Future<String> future = Future.future();
        future.fail(new RuntimeException(value));
        return future;
    }

    @Override
    public Future<String> stringMethodWithoutArgument() {
        return Future.succeededFuture("ok");
    }

    @Override
    public Future<String> stringMethodWithMultipleArguments(String value1, String value2, Integer value3) {
        return Future.succeededFuture(value1 + "-" + value2 + "-" + value3);
    }

    @Override
    public Future<String> shouldReturnCustomException() {
        return Future.failedFuture(new CustomException());
    }

}
