package com.rapatao.vertx.eventbus.future;

import io.vertx.core.Future;

/**
 * Created by rapatao on 13/09/16.
 */
public interface TestService {

    Future<String> stringMethod(String value);

    Future<String> throwMethodWithoutCustomFailMessageHandler(String value);

    Future<String> stringMethodWithoutArgument();

    Future<String> stringMethodWithMultipleArguments(String value1, String value2, Integer value3);

    Future<String> shouldReturnCustomException();

}

