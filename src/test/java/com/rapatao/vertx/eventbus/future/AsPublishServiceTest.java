package com.rapatao.vertx.eventbus.future;

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
 * Created by rapatao on 22/11/16.
 */
@RunWith(VertxUnitRunner.class)
public class AsPublishServiceTest {

    private Vertx vertx;
    private Async noArgAsync;
    private Async oneArgAsync;
    private Async twoArgsAsync;
    private TestContext testContext;

    @Before
    public void setUp(TestContext context) {
        vertx = Vertx.vertx();
    }

    @After
    public void tearDown(TestContext context) {
        vertx.close();
    }

    @Test
    public void shouldCallAllMethods(TestContext context) {
        ServiceRegistry.toEventBus(vertx.eventBus()).to(new ServiceImpl()).registry();
        final Service service = ProxyCreator.toEventBus(vertx.eventBus()).asPublish(Service.class);

        testContext = context;
        noArgAsync = context.async();
        oneArgAsync = context.async();
        twoArgsAsync = context.async();

        service.noArgs();
        service.oneArg("123");
        service.twoArgs("123", 321L);

        while (!noArgAsync.isCompleted() || !oneArgAsync.isCompleted() || !twoArgsAsync.isCompleted()) {
            // wait...
        }

        Assert.assertTrue(noArgAsync.isCompleted());
        Assert.assertTrue(oneArgAsync.isCompleted());
        Assert.assertTrue(twoArgsAsync.isCompleted());
    }


    public interface Service {
        void noArgs();

        void oneArg(String arg);

        void twoArgs(String arg1, Long arg2);
    }

    public class ServiceImpl implements Service {

        @Override
        public void noArgs() {
            noArgAsync.complete();
        }

        @Override
        public void oneArg(String arg) {
            testContext.assertEquals(arg, "123");
            oneArgAsync.complete();
        }

        @Override
        public void twoArgs(String arg1, Long arg2) {
            testContext.assertEquals(arg1, "123");
            testContext.assertEquals(arg2, 321L);
            twoArgsAsync.complete();
        }

    }

}
