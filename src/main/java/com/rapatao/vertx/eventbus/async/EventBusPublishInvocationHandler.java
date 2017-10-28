package com.rapatao.vertx.eventbus.async;

import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by rapatao on 15/09/16.
 */
@RequiredArgsConstructor
@Deprecated
class EventBusPublishInvocationHandler extends AbstractEventBusInvocationHandler implements InvocationHandler {

    private final EventBus eventBus;
    private final String address;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final JsonArray argumentTypes = new JsonArray();
        final JsonArray argumentValues = new JsonArray();
        fillArguments(method, args, argumentTypes, argumentValues);
        final DeliveryOptions deliveryOptions = getDeliveryOptions(method, argumentTypes);
        eventBus.publish(address, argumentValues.encode(), deliveryOptions);
        return proxy;
    }

}
