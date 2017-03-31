package com.rapatao.vertx.eventbus.future;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by rapatao on 13/09/16.
 */
@RunWith(VertxUnitRunner.class)
public class ServiceRegistryConsumerTest {

    private Vertx vertx;

    @Before
    public void setUp(TestContext context) {
        vertx = Vertx.vertx();
        ServiceRegistry.toEventBus(vertx.eventBus()).to(new ValidTestServiceImpl()).registry();
    }

    @After
    public void tearDown(TestContext context) {
        vertx.close();
    }

    @Test
    public void shouldConsumeAndReturnExpectedValue(TestContext context) throws InterruptedException {
        final Async async = context.async();

        final TestService testService = ProxyCreator.toEventBus(vertx.eventBus()).asSend(TestService.class);

        Future<String> stringFuture = testService.stringMethod("test 1");
        stringFuture.setHandler(handler -> {
            Assert.assertEquals("future complete: test 1", handler.result());
            async.complete();
        });
    }

    @Test
    public void shouldReturnFailWithoutCustomHandler(TestContext context) {
        final Async async = context.async();

        final TestService testService = ProxyCreator.toEventBus(vertx.eventBus()).asSend(TestService.class);

        Future<String> stringFuture = testService.throwMethodWithoutCustomFailMessageHandler("test");
        stringFuture.setHandler(handler -> {
            Assert.assertTrue(handler.failed());
            Assert.assertNotNull(handler.cause());
            Assert.assertEquals("test", handler.cause().getMessage());
            async.complete();
        });
    }

    @Test
    public void shouldCallMethodWithNoArgument(TestContext context) {
        final Async async = context.async();
        final TestService testService = ProxyCreator.toEventBus(vertx.eventBus()).asSend(TestService.class);
        final Future<String> future = testService.stringMethodWithoutArgument();
        future.setHandler(handler -> {
            Assert.assertEquals("ok", handler.result());
            async.complete();
        });
    }

    @Test
    public void shouldCallMethodWithMultipleArguments(TestContext context) {
        final Async async = context.async();
        final TestService testService = ProxyCreator.toEventBus(vertx.eventBus()).asSend(TestService.class);
        final Future<String> future = testService.stringMethodWithMultipleArguments("v1", "v2", 123);
        future.setHandler(handler -> {
            Assert.assertEquals("v1-v2-123", handler.result());
            async.complete();
        });
    }

    @Test
    public void shouldReturnCustomException(TestContext context) {
        final Async async = context.async();
        final TestService testService = ProxyCreator.toEventBus(vertx.eventBus()).asSend(TestService.class);
        final Future<String> future = testService.shouldReturnCustomException();
        future.setHandler(handler -> {
            Assert.assertTrue(handler.failed());
            Assert.assertEquals(CustomException.VALUE, handler.cause().getMessage());
            async.complete();
        });
    }

}