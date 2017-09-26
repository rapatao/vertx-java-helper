package com.rapatao.vertx.handler;

import io.vertx.core.Future;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import retrofit2.Call;
import retrofit2.Response;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HandlerUtil {

    public static <T> Future<Response<T>> asFuture(final Call<T> call) {
        final Future<Response<T>> future = Future.future();
        call.enqueue(RetrofitHandler.builder()
                .onResponse((result, succeeded) -> {
                    if (succeeded) {
                        future.complete((Response<T>) result);
                    } else {
                        future.fail(result.message());
                    }
                })
                .onFail(future::fail)
                .onComplete(future::complete)
                .build());
        return future;
    }

}
