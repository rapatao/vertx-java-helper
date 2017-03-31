package com.rapatao.vertx.eventbus.future;

import com.rapatao.vertx.eventbus.future.exception.ProxyHelperException;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import lombok.Getter;
import lombok.Setter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Created by rapatao on 22/11/16.
 */
@RunWith(VertxUnitRunner.class)
public class ExceptionSerializationTest {

    private Vertx vertx;

    @Before
    public void setUp(TestContext context) {
        vertx = Vertx.vertx();
    }

    @After
    public void tearDown(TestContext context) {
        vertx.close();
    }

    @Test
    public void failWithThrow(TestContext context) {
        final Async async = context.async();
        ServiceRegistry.toEventBus(vertx.eventBus()).to(new ServiceImpl()).registry();
        final Service service = ProxyCreator.toEventBus(vertx.eventBus()).asSend(Service.class);
        service.failWithThrow().setHandler(handler -> {
                    try {
                        context.assertFalse(handler.succeeded());
                        context.assertEquals(ProxyHelperException.class, handler.cause().getClass());
                        ProxyHelperException exception = (ProxyHelperException) handler.cause();
                        context.assertNotNull(exception.getStackTraceElements());
                    } catch (Exception e) {
                        context.fail();
                    } finally {
                        async.complete();
                    }
                }
        );
    }

    @Test
    public void failFuture(TestContext context) {
        final Async async = context.async();
        ServiceRegistry.toEventBus(vertx.eventBus()).to(new ServiceImpl()).registry();
        final Service service = ProxyCreator.toEventBus(vertx.eventBus()).asSend(Service.class);
        service.failFuture().setHandler(handler -> {
                    try {
                        context.assertFalse(handler.succeeded());
                        context.assertEquals(ProxyHelperException.class, handler.cause().getClass());
                        ProxyHelperException exception = (ProxyHelperException) handler.cause();
                        context.assertNotNull(exception.getStackTraceElements());
                    } catch (Exception e) {
                        context.fail();
                    } finally {
                        async.complete();
                    }
                }
        );
    }


    public interface Service {
        Future<String> failWithThrow();

        Future<String> failFuture();
    }

    public static class TestException extends RuntimeException {
        @Getter
        @Setter
        private String[] tests;

        public TestException(String[] tests) {
            this.tests = tests;
        }

    }

    public static class ServiceImpl implements Service {

        private final TestException testException = new TestException(new String[]{"1", "2"});

        @Override
        public Future<String> failWithThrow() {
            throw testException;
        }

        @Override
        public Future<String> failFuture() {
            return Future.failedFuture(testException);
        }

    }

}
