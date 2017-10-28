package com.rapatao.vertx.eventbus.async;

import lombok.Builder;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * Created by rapatao on 01/04/17.
 */
@Builder
@Getter
@Deprecated
class MethodArguments {
    private final Method method;
    private final Class<?>[] arguments;
}
