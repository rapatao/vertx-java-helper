package com.rapatao.vertx.eventbus.async;

import com.rapatao.vertx.handler.AsyncHandler;
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
 * Created by rapatao on 01/04/17.
 */
@RunWith(VertxUnitRunner.class)
public class AsyncTest {

    private Vertx vertx;
    private Async noCallback;
    private TestContext testContext;

    @Before
    public void setUp(TestContext context) {
        vertx = Vertx.vertx();
        ServiceRegistry.toEventBus(vertx.eventBus()).to(new TestServiceImpl());
        ServiceRegistry.toEventBus(vertx.eventBus()).to(new TestServiceNoResult());
    }

    @After
    public void tearDown(TestContext context) {
        vertx.close();
    }

    @Test
    public void shouldConsumeAndReturnExpectedValue(TestContext context) throws InterruptedException {
        final Async async = context.async();
        final TestService testService = ProxyCreator.toEventBus(vertx.eventBus()).asSend(TestService.class);
        testService.t1("123", AsyncHandler.builder()
                .onSuccess(t123 -> {
                    Assert.assertEquals("123-ok", t123);
                    testService.t1("321", AsyncHandler.builder()
                            .onSuccess(t321 -> Assert.assertEquals("321-ok", t321))
                            .onComplete(async::complete)
                            .build());
                })
                .build());
    }

    @Test
    public void shouldConsumeMultipleArguments(TestContext context) {
        final Async async = context.async();
        final TestService testService = ProxyCreator.toEventBus(vertx.eventBus()).asSend(TestService.class);
        testService.t1t2("123", "456", AsyncHandler.builder()
                .onSuccess(t -> Assert.assertEquals("123-456-ok", t))
                .onComplete(async::complete)
                .build());
    }

    @Test
    public void shouldConsumeWithoutCallback(TestContext context) {
        noCallback = context.async();
        testContext = context;
        final TestService2 testService = ProxyCreator.toEventBus(vertx.eventBus()).asSend(TestService2.class);
        String expected = "123";
        testService.t3(expected);
        Assert.assertEquals("123", expected);
    }

    @Test
    public void shouldConsumeWithoutCallbackAndAsPublish(TestContext context) {
        noCallback = context.async();
        testContext = context;
        final TestService2 testService = ProxyCreator.toEventBus(vertx.eventBus()).asPublish(TestService2.class);
        String expected = "123";
        testService.t3(expected);
        Assert.assertEquals("123", expected);
    }

    private class TestServiceNoResult implements TestService2 {
        @Override
        public void t3(String t1) {
            testContext.assertEquals("123", t1);
            noCallback.complete();
        }
    }

}

