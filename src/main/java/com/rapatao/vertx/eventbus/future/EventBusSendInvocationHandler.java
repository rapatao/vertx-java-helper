package com.rapatao.vertx.eventbus.future;

import com.rapatao.vertx.eventbus.future.exception.ProxyHelperException;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Created by rapatao on 15/09/16.
 */
@RequiredArgsConstructor
class EventBusSendInvocationHandler implements InvocationHandler {

    private final EventBus eventBus;
    private final String prefix;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final Type returnType = ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
        final Future<Object> future = Future.future();
        final String address = (prefix.isEmpty() ? method.getDeclaringClass().getName() : prefix) + "#" + method.getName();

        final JsonArray arguments = new JsonArray();
        if (args != null) {
            Arrays.stream(args).forEach(a -> arguments.add(Json.encode(a)));
        }

        eventBus.<String>send(address, arguments.toString(), handler -> {
            if (handler.succeeded()) {
                future.complete(Json.decodeValue(handler.result().body(), ((Class) returnType)));
            } else {
                final JsonArray exception = new JsonArray(handler.cause().getMessage());
                future.fail(new ProxyHelperException(exception.getString(0), Json.decodeValue(exception.getString(1), StackTraceElement[].class)));
            }
        });
        return future;
    }

}
