package com.rapatao.vertx.eventbus.async;

import com.rapatao.vertx.handler.AsyncHandler;
import com.rapatao.vertx.json.JsonParser;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by rapatao on 15/09/16.
 */
@Deprecated
class EventBusConsumerHandler {

    private final static Logger logger = LoggerFactory.getLogger(EventBusConsumerHandler.class);

    private static final Map<String, MethodArguments> METHODS = new ConcurrentHashMap<>();

    private final Object instance;

    EventBusConsumerHandler(final Object instance) {
        this.instance = instance;
    }

    public MethodArguments getMethod(String methodName, JsonArray arguments) {
        final String signature = methodName + "-" + arguments.encode();

        MethodArguments methodArguments = METHODS.get(signature);
        if (methodArguments == null) {
            Class<?> classes[] = arguments.stream().map(a -> {
                try {
                    return Class.forName(JsonParser.decode(a.toString(), String.class));
                } catch (ClassNotFoundException e) {
                    logger.error(e.getMessage(), e);
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList()).toArray(new Class<?>[arguments.size()]);
            try {
                methodArguments = MethodArguments.builder()
                        .method(instance.getClass().getMethod(methodName, classes))
                        .arguments(classes)
                        .build();
                METHODS.putIfAbsent(signature, methodArguments);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return methodArguments;
    }

    protected void handle(Message<String> handler) {

        final MultiMap headers = handler.headers();
        final String encodedArguments = headers.get("arguments");
        final String method = headers.get("method");

        final JsonArray arguments = new JsonArray();
        if (encodedArguments != null) {
            arguments.addAll(new JsonArray(encodedArguments));
        }
        final MethodArguments methodArguments = getMethod(method, arguments);

        final JsonArray parametersToDecode = new JsonArray(handler.body());

        final List<Object> parameters = new ArrayList<>();
        for (int i = 0; i < parametersToDecode.size(); i++) {
            parameters.add(JsonParser.decode(parametersToDecode.getString(i), methodArguments.getArguments()[i]));
        }

        if (methodArguments.getArguments()[methodArguments.getArguments().length - 1] == Handler.class) {
            final AsyncHandler<Object> asyncHandler = AsyncHandler.builder()
                    .onSuccess(result -> {
                        try {
                            handler.reply(JsonParser.encode(result));
                        } catch (Exception e) {
                            handleFail(handler, e);
                        }
                    })
                    .onFail(fail -> handleFail(handler, fail))
                    .build();
            parameters.add(asyncHandler);
        }

        try {
            methodArguments.getMethod().invoke(instance, parameters.toArray());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void handleFail(final Message<String> handler, final Throwable throwable) {
        final JsonArray exception = new JsonArray()
                .add(Optional.ofNullable(throwable.getMessage()).orElse(""))
                .add(JsonParser.encode(throwable.getStackTrace()));
        handler.fail(-1, exception.toString());
    }

}

