package com.rapatao.vertx.eventbus.async;

import com.rapatao.vertx.eventbus.future.exception.InterfaceNotFoundException;
import com.rapatao.vertx.eventbus.future.exception.MultipleInterfacesForServiceException;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * Event Bus Service Registry
 * <p>
 * This class is responsibly to registry all services int the Vert.x EventBus.
 * <p>
 * Created by rapatao on 31/03/17
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceRegistry {

    private final static Logger logger = LoggerFactory.getLogger(ServiceRegistry.class);

    private final EventBus eventBus;
    private Object service;
    private String name;

    /**
     * Create a ServiceRegistry using an {@link EventBus}.
     *
     * @param eventBus the Vert.x EventBus.
     * @return the ServiceRegistry instance.
     */
    public static ServiceRegistry toEventBus(final EventBus eventBus) {
        return new ServiceRegistry(eventBus);
    }

    /**
     * Defines the name to use when registry the consumers.
     *
     * @param name the name to be used in service address. Default is a full interface name.
     * @return the ServiceRegistry instance.
     */
    public ServiceRegistry withName(final String name) {
        this.name = name;
        return this;
    }

    /**
     * Service implementation to be registered in the Vert.x EventBus.
     *
     * @param instance the service implementation instance.
     * @param <T>      the service type.
     */
    public <T> void to(T instance) {
        this.service = instance;
        registry();
    }

    /**
     * Registry the service in the Vert.x EventBus.
     */
    private void registry() {
        final Class<?> anInterface = getInterfaceClass(service);
        final String address = Optional.ofNullable(name).orElse(anInterface.getCanonicalName());
        logger.info("Registering consumer for " + address);
        eventBus.consumer(address, new EventBusConsumerHandler(service)::handle);
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
