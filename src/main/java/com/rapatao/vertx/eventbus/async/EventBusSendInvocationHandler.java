package com.rapatao.vertx.eventbus.async;

import com.rapatao.vertx.json.JsonParser;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by rapatao on 15/09/16.
 */
@RequiredArgsConstructor
class EventBusSendInvocationHandler extends AbstractEventBusInvocationHandler implements InvocationHandler {

    private final EventBus eventBus;
    private final String address;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final JsonArray argumentTypes = new JsonArray();
        final JsonArray argumentValues = new JsonArray();
        fillArguments(method, args, argumentTypes, argumentValues);
        final Optional<Handler> asyncResultHandler = Arrays.stream(args)
                .filter(a -> a instanceof Handler)
                .map(a -> (Handler) a)
                .reduce((a, b) -> a);
        final DeliveryOptions deliveryOptions = getDeliveryOptions(method, argumentTypes);
        eventBus.send(address, argumentValues.encode(), deliveryOptions,
                handler -> {
                    final Future future = Future.future();
                    if (handler.succeeded()) {
                        future.complete(JsonParser.decode(handler.result().body().toString(), String.class));
                    } else {
                        future.fail(handler.cause());
                    }
                    asyncResultHandler.ifPresent(arh -> arh.handle(future));
                });
        return proxy;
    }

}
