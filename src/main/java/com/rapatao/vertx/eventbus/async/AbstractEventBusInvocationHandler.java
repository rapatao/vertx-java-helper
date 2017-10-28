package com.rapatao.vertx.eventbus.async;

import com.rapatao.vertx.json.JsonParser;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonArray;

import java.lang.reflect.Method;

/**
 * Created by rapatao on 01/04/17.
 */
@Deprecated
class AbstractEventBusInvocationHandler {

    public void fillArguments(Method method, Object[] args, JsonArray argumentTypes, JsonArray argumentValues) {
        if (args != null) {
            for (int i = 0; i < method.getParameterTypes().length; i++) {
                final Class<?>[] aClass = method.getParameterTypes();
                final Class<?> argument = aClass[i];
                argumentTypes.add(JsonParser.encode(argument.getCanonicalName()));
                if (!(argument == Handler.class)) {
                    argumentValues.add(JsonParser.encode(args[i]));
                }

            }
        }
    }

    public DeliveryOptions getDeliveryOptions(Method method, JsonArray argumentTypes) {
        return new DeliveryOptions()
                .addHeader("method", method.getName())
                .addHeader("arguments", argumentTypes.encode());
    }

}
