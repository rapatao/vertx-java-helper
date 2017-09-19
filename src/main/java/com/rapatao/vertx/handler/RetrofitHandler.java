package com.rapatao.vertx.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Optional;

/**
 * Created by rapatao on 31/03/17.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrofitHandler<T> implements Callback<T> {

    private final ResponseHandler onResponse;
    private final FailHandler onFail;
    private final CompleteHandler onComplete;

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        try {
            onResponse.handle(response, response.isSuccessful());
        } catch (Exception e) {
            onFailure(call, e);
        } finally {
            onComplete.handle();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        onFail.handle(throwable);
    }

    public static RetrofitHandlerBuilder builder() {
        return new RetrofitHandlerBuilder();
    }

    @FunctionalInterface
    public interface ResponseHandler<T> {
        void handle(final Response<T> result, final Boolean succeeded);
    }

    @FunctionalInterface
    public interface CompleteHandler {
        void handle();
    }

    @FunctionalInterface
    public interface FailHandler {
        void handle(final Throwable cause);
    }

    public static class RetrofitHandlerBuilder {

        private static final ResponseHandler DEFAULT_HANDLER = (result, succeeded) -> {
            // do nothing...
        };

        private static final CompleteHandler DEFAULT_COMPLETE_HANDLER = () -> {
            // do nothing...
        };

        private static final FailHandler DEFAULT_FAILURE_HANDLER = throwable -> {
            // do nothing...
        };

        private ResponseHandler responseHandler;
        private CompleteHandler onComplete;
        private FailHandler onFailure;

        public RetrofitHandlerBuilder onResponse(final ResponseHandler responseHandler) {
            this.responseHandler = responseHandler;
            return this;
        }

        public RetrofitHandlerBuilder onFail(final FailHandler failHandler) {
            this.onFailure = failHandler;
            return this;
        }

        public RetrofitHandlerBuilder onComplete(final CompleteHandler completeHandler) {
            this.onComplete = completeHandler;
            return this;
        }

        public RetrofitHandler build() {
            return new RetrofitHandler(
                    Optional.ofNullable(responseHandler).orElse(DEFAULT_HANDLER),
                    Optional.ofNullable(onFailure).orElse(DEFAULT_FAILURE_HANDLER),
                    Optional.ofNullable(onComplete).orElse(DEFAULT_COMPLETE_HANDLER)
            );
        }

    }

}
