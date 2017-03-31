package com.rapatao.vertx.handler;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AsyncHandler<T> implements Handler<AsyncResult<T>> {

    private static final Logger logger = LoggerFactory.getLogger(AsyncHandler.class);

    private static final OnSuccessHandler DEFAULT_SUCCESS_HANDLER = result -> {
        // do nothing...
    };

    private static final OnFailHandler DEFAULT_FAIL_HANDLER = cause -> {
        // do nothing...
    };

    private static final OnCompleteHandler DEFAULT_COMPLETE_HANDLER = () -> {
        // do nothing
    };

    private final OnSuccessHandler<T> onSuccess;
    private final OnFailHandler onFail;
    private final OnCompleteHandler onComplete;

    @Override
    public void handle(final AsyncResult<T> event) {
        try {
            if (event.succeeded()) {
                Optional.ofNullable(onSuccess).orElse(DEFAULT_SUCCESS_HANDLER).handle(event.result());
            } else {
                final Throwable cause = event.cause();
                logger.error(cause.getMessage(), event.cause());
                Optional.ofNullable(onFail).orElse(DEFAULT_FAIL_HANDLER).handle(cause);
            }
        } finally {
            Optional.ofNullable(onComplete).orElse(DEFAULT_COMPLETE_HANDLER).handle();
        }
    }

    @FunctionalInterface
    public interface OnSuccessHandler<T> {
        void handle(final T result);
    }

    @FunctionalInterface
    public interface OnFailHandler {
        void handle(final Throwable cause);
    }

    @FunctionalInterface
    public interface OnCompleteHandler {
        void handle();
    }

}

