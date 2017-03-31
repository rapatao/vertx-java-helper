package com.rapatao.vertx.handler;

import io.vertx.core.AsyncResult;
import lombok.Getter;
import lombok.Setter;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by rapatao on 31/03/17.
 */
public class AsyncHandlerTest {

    private final AsyncResult<String> FAILED_RESULT = new AsyncResult<String>() {
        @Override
        public String result() {
            return "ok";
        }

        @Override
        public Throwable cause() {
            return new Exception("fail");
        }

        @Override
        public boolean succeeded() {
            return false;
        }

        @Override
        public boolean failed() {
            return true;
        }
    };

    private final AsyncResult<String> SUCCESS_RESULT = new AsyncResult<String>() {
        @Override
        public String result() {
            return "ok";
        }

        @Override
        public Throwable cause() {
            return new Exception("fail");
        }

        @Override
        public boolean succeeded() {
            return true;
        }

        @Override
        public boolean failed() {
            return false;
        }
    };

    @Setter
    @Getter
    private final class TestUtil {
        private String result;
    }

    @Test
    public void shouldRunSuccess() {
        final TestUtil testUtil = new TestUtil();
        final AsyncHandler<String> asyncHandler = AsyncHandler.<String>builder()
                .onSuccess(testUtil::setResult)
                .build();
        asyncHandler.handle(SUCCESS_RESULT);
        Assert.assertEquals("ok", testUtil.getResult());
    }

    @Test
    public void shouldRunFail() {
        final TestUtil testUtil = new TestUtil();
        final AsyncHandler<String> asyncHandler = AsyncHandler.<String>builder()
                .onFail(f -> testUtil.setResult(f.getMessage()))
                .build();
        asyncHandler.handle(FAILED_RESULT);
        Assert.assertEquals("fail", testUtil.getResult());
    }

    @Test
    public void shouldRunComplete() {
        final TestUtil testUtil = new TestUtil();
        final AsyncHandler<String> asyncHandler = AsyncHandler.<String>builder()
                .onComplete(() -> testUtil.setResult("completed"))
                .build();
        asyncHandler.handle(SUCCESS_RESULT);
        Assert.assertEquals("completed", testUtil.getResult());
    }

}