package com.rapatao.vertx.eventbus.future;

import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by rapatao on 15/09/16.
 */
@RequiredArgsConstructor
@Deprecated
class EventBusPublishInvocationHandler implements InvocationHandler {

    private final EventBus eventBus;
    private final String prefix;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final String address = (prefix.isEmpty() ? method.getDeclaringClass().getName() : prefix) + "#" + method.getName();
        final JsonArray arguments = new JsonArray();
        if (args != null) {
            Arrays.stream(args).forEach(a -> arguments.add(Json.encode(a)));
        }
        eventBus.publish(address, arguments.toString());
        return null;
    }

}
