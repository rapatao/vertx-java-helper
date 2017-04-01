package com.rapatao.vertx.eventbus.async;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by rapatao on 01/04/17.
 */
public interface TestService {

    void t1(String t1, Handler<AsyncResult<String>> handler);

    void t1t2(String t1, String t2, Handler<AsyncResult<String>> handler);

}
