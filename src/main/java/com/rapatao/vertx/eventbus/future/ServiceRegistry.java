package com.rapatao.vertx.eventbus.future;

import com.rapatao.vertx.eventbus.future.exception.InterfaceNotFoundException;
import com.rapatao.vertx.eventbus.future.exception.MultipleInterfacesForServiceException;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Event Bus Service Registry
 * <p>
 * This class is responsibly to registry all services int the Vertx EventBus.
 * <p>
 * Created by rapatao on 13/09/16
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Deprecated
public class ServiceRegistry {

    private final static Logger logger = LoggerFactory.getLogger(ServiceRegistry.class);

    private final EventBus eventBus;
    private final List<Object> services = new ArrayList<>();
    private String prefix = "";

    /**
     * Create a ServiceRegistry using an {@link EventBus}.
     *
     * @param eventBus the Vertx EventBus.
     * @return the ServiceRegistry instance.
     */
    public static ServiceRegistry toEventBus(final EventBus eventBus) {
        return new ServiceRegistry(eventBus);
    }

    /**
     * Defines the prefix to use when registry the consumers.
     *
     * @param prefix the prefix to be used in service address. Default is an empty string.
     * @return the ServiceRegistry instance.
     */
    public ServiceRegistry withPrefix(final String prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * Add an Service implementation to be registered in the Vertx EventBus.
     *
     * @param instance the service implementation instance.
     * @param <T>      the service type.
     * @return the ServiceRegistry instance.
     */
    public <T> ServiceRegistry to(T instance) {
        services.add(instance);
        return this;
    }

    /**
     * Registry all services in the Vertx EventBus.
     */
    public void registry() {
        services.forEach(this::registry);
    }

    private void registry(final Object instance) {
        final Class<?> anInterface = getInterfaceClass(instance);
        for (Method method : anInterface.getMethods()) {
            final String address = (prefix.isEmpty() ? anInterface.getName() : prefix) + "#" + method.getName();
            logger.info("Registering consumer for " + address);
            EventBusConsumerHandler eventBusConsumerHandler = new EventBusConsumerHandler(method, instance);
            eventBus.consumer(address, eventBusConsumerHandler::handle);
        }
    }

    private <T> Class<?> getInterfaceClass(T instance) {
        final Class<?>[] interfaces = instance.getClass().getInterfaces();
        if (interfaces == null) {
            throw new InterfaceNotFoundException();
        }
        if (interfaces.length > 1) {
            throw new MultipleInterfacesForServiceException();
        }
        return interfaces[0];
    }

}
