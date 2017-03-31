package com.rapatao.vertx.handler;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Optional;

/**
 * Created by rapatao on 31/03/17.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RetrofitHandler<T> implements Callback<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetrofitHandler.class);

    private static final RequestHandler DEFAULT_HANDLER = result -> {
        // do nothing...
    };

    private static final CompleteHandler DEFAULT_COMPLETE_HANDLER = () -> {
        // do nothing...
    };

    private static final FailHandler DEFAULT_FAILURE_HANDLER = throwable -> {
        // do nothing...
    };

    private final RequestHandler onSuccess;
    private final RequestHandler onFail;
    private final CompleteHandler onComplete;
    private final FailHandler onFailure;

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        try {
            RequestHandler handler;
            if (response.isSuccessful()) {
                handler = Optional.ofNullable(onSuccess).orElse(DEFAULT_HANDLER);
            } else {
                handler = Optional.ofNullable(onFail).orElse(DEFAULT_HANDLER);
            }
            handler.handle(response);
        } catch (Exception e) {
            onFailure(call, e);
        } finally {
            Optional.ofNullable(onComplete).orElse(DEFAULT_COMPLETE_HANDLER).handle();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        LOGGER.error(throwable);
        onFailure.handle(throwable);
    }

    @FunctionalInterface
    public interface RequestHandler<T> {
        void handle(final Response<T> result);
    }

    @FunctionalInterface
    public interface CompleteHandler {
        void handle();
    }

    @FunctionalInterface
    public interface FailHandler {
        void handle(final Throwable cause);
    }

}
