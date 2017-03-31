package com.rapatao.vertx.eventbus.future;

import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by rapatao on 15/09/16.
 */
class EventBusConsumerHandler {

    private final static Logger logger = LoggerFactory.getLogger(EventBusConsumerHandler.class);

    private final Method method;
    private final Class<?>[] attributes;
    private final Object instance;

    EventBusConsumerHandler(final Method method, final Object instance) {
        this.method = method;
        this.attributes = method.getParameterTypes();
        this.instance = instance;
    }

    void handle(Message<String> handler) {
        try {
            final JsonArray arguments = new JsonArray(handler.body());
            final List<Object> parameters = new ArrayList<>();
            for (int i = 0; i < arguments.size(); i++) {
                parameters.add(Json.decodeValue(arguments.getString(i), attributes[i]));
            }
            final Future<Object> future = (Future<Object>) method.invoke(instance, parameters.toArray());
            future.setHandler(futureHandler -> {
                if (futureHandler.succeeded()) {
                    handler.reply(Json.encode(futureHandler.result()));
                } else {
                    handleFail(handler, future);
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            handleFail(handler, Future.failedFuture(e));
        }
    }

    private void handleFail(final Message<String> handler, final Future<Object> future) {
        final JsonArray exception = new JsonArray()
                .add(Optional.ofNullable(future.cause().getMessage()).orElse(""))
                .add(Json.encode(future.cause().getStackTrace()));
        handler.fail(-1, exception.toString());
    }

}
