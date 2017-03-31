package com.rapatao.vertx.eventbus.future;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by rapatao on 13/09/16.
 */
@RunWith(VertxUnitRunner.class)
public class ServiceRegistryTest {

    private Vertx vertx;

    @Before
    public void setUp(TestContext context) {
        vertx = Vertx.vertx();
    }

    @After
    public void tearDown(TestContext context) {
        vertx.close();
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailToRegistryServiceWithMultiplesInterfaces(TestContext context) {
        ServiceRegistry.toEventBus(vertx.eventBus()).withPrefix("").to(new InvalidTestServiceImpl()).registry();
    }

}
