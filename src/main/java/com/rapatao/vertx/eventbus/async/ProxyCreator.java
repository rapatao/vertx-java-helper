package com.rapatao.vertx.eventbus.async;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Proxy;
import java.util.Optional;

/**
 * Proxy Service Creator.
 * <p>
 * This class is responsibly to create the proxy that wrap the Vertx EventBus usage.
 * <p>
 * Created by rapatao on 15/09/16.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Deprecated
public class ProxyCreator {

    private final EventBus eventBus;
    private String address;

    /**
     * Create a ProxyCreator using an {@link EventBus}.
     *
     * @param eventBus the Vert.x EventBus.
     * @return The ProxyCreator instance.
     */
    public static ProxyCreator toEventBus(final EventBus eventBus) {
        return new ProxyCreator(eventBus);
    }

    /**
     * Defines the address for the registered consumers.
     *
     * @param address the address to be used in service address. Default is a full interface name.
     * @return the ProxyCreator instance.
     */
    public ProxyCreator withAddress(final String address) {
        this.address = address;
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
        final String address = getAddress(service);
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), classes, new EventBusSendInvocationHandler(eventBus, address));
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
        final String address = getAddress(service);
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), classes, new EventBusPublishInvocationHandler(eventBus, address));
    }

    private <T> String getAddress(@NonNull Class<T> service) {
        return Optional.ofNullable(this.address).orElse(service.getCanonicalName());
    }

}
