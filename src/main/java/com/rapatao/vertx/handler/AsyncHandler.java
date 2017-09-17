package com.rapatao.vertx.handler;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AsyncHandler<T> implements Handler<AsyncResult<T>> {

    private final OnSuccessHandler<T> onSuccess;
    private final OnFailHandler onFail;
    private final OnCompleteHandler onComplete;

    public static AsyncHandlerBuilder builder() {
        return new AsyncHandlerBuilder();
    }

    @Override
    public void handle(final AsyncResult<T> event) {
        try {
            if (event.succeeded()) {
                onSuccess.handle(event.result());
            } else {
                final Throwable cause = event.cause();
                onFail.handle(cause);
            }
        } finally {
            onComplete.handle();
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

    public static class AsyncHandlerBuilder {

        private static final OnSuccessHandler DEFAULT_SUCCESS_HANDLER = result -> {
            // do nothing...
        };

        private static final OnFailHandler DEFAULT_FAIL_HANDLER = cause -> {
            // do nothing...
        };

        private static final OnCompleteHandler DEFAULT_COMPLETE_HANDLER = () -> {
            // do nothing
        };

        private OnSuccessHandler onSuccessHandler;
        private OnFailHandler onFailHandler;
        private OnCompleteHandler onCompleteHandler;

        public <T> AsyncHandlerBuilder onSuccess(final OnSuccessHandler<T> onSuccessHandler) {
            this.onSuccessHandler = onSuccessHandler;
            return this;
        }

        public AsyncHandlerBuilder onFail(final OnFailHandler onFailHandler) {
            this.onFailHandler = onFailHandler;
            return this;
        }

        public AsyncHandlerBuilder onComplete(final OnCompleteHandler onCompleteHandler) {
            this.onCompleteHandler = onCompleteHandler;
            return this;
        }

        public <T> AsyncHandler<T> build() {
            return new AsyncHandler<>(
                    Optional.ofNullable(onSuccessHandler).orElse(DEFAULT_SUCCESS_HANDLER),
                    Optional.ofNullable(onFailHandler).orElse(DEFAULT_FAIL_HANDLER),
                    Optional.ofNullable(onCompleteHandler).orElse(DEFAULT_COMPLETE_HANDLER));
        }

    }

}

