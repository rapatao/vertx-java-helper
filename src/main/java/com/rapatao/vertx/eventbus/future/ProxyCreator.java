package com.rapatao.vertx.eventbus.future;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Proxy;

/**
 * Proxy Service Creator.
 * <p>
 * This class is responsibly to create the proxy that wrap the Vertx EventBus usage.
 * <p>
 * Created by rapatao on 15/09/16.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProxyCreator {

    private final EventBus eventBus;
    private String prefix = "";

    /**
     * Create a ProxyCreator using an {@link EventBus}.
     *
     * @param eventBus the Vertx EventBus.
     * @return The ProxyCreator instance.
     */
    public static ProxyCreator toEventBus(final EventBus eventBus) {
        return new ProxyCreator(eventBus);
    }

    /**
     * Defines the prefix for the registered consumers.
     *
     * @param prefix the prefix to be used in service address. Default is an empty string.
     * @return the ProxyCreator instance.
     */
    public ProxyCreator withPrefix(final String prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * Create a proxy instance that use {@link EventBus#send(String, Object, Handler)}.
     *
     * @param service The service interface.
     * @param <T>     The service type.
     * @return The proxy service instance.
     */
    public <T> T asSend(@NonNull final Class<T> service) {
        final Class[] classes = new Class[]{service};
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), classes, new EventBusSendInvocationHandler(eventBus, prefix));
    }

    /**
     * Create a proxy instance that use {@link EventBus#publish(String, Object)}
     *
     * @param service The service interface.
     * @param <T>     The service type.
     * @return The proxy service instance.
     */
    public <T> T asPublish(@NonNull final Class<T> service) {
        final Class[] classes = new Class[]{service};
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), classes, new EventBusPublishInvocationHandler(eventBus, prefix));
    }
}
