package com.rapatao.vertx.json;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by rapatao on 31/03/17.
 */
public class JsonParserTest {

    @Test
    public void shouldEncodeWithoutMixin() {
        TestUtil test = new TestUtil("1", "2");
        String expected = "{\"id\":\"1\",\"value\":\"2\"}";
        Assert.assertEquals(expected, JsonParser.encode(test));
    }

    @Test
    public void shouldEncodeWithMixin() {
        TestUtil test = new TestUtil("1", "2");
        String expected = "{\"_id\":\"1\",\"value\":\"2\"}";
        Assert.assertEquals(expected, JsonParser.encode(test, TestUtilIdMixin.class));
    }

    @Test
    public void shouldEncodeWithOverrideMixin() {
        TestUtil test = new TestUtil("1", "2");
        String expected = "{\"id\":\"1\",\"val\":\"2\"}";
        Assert.assertEquals(expected, JsonParser.encode(test, TestUtilIdMixin.class, TestUtilValueMixin.class));
    }

    @Test
    public void shouldEncodeNullValue() {
        Assert.assertNull(JsonParser.encode(null));
    }

    @Test(expected = JsonParserException.class)
    public void shouldFailEncode() {
        TestUtil test = new TestUtil("1", "2");
        JsonParser.encode(test, TestUtilFailMixin.class);
    }

    @Test
    public void shouldDecodeWithoutMixin() {
        String json = "{\"id\":\"1\",\"value\":\"2\"}";
        TestUtil decode = JsonParser.decode(json, TestUtil.class);
        Assert.assertEquals("1", decode.getId());
        Assert.assertEquals("2", decode.getValue());
    }

    @Test
    public void shouldDecodeWithMixin() {
        String json = "{\"_id\":\"1\",\"value\":\"2\"}";
        TestUtil decode = JsonParser.decode(json, TestUtil.class, TestUtilIdMixin.class);
        Assert.assertEquals("1", decode.getId());
        Assert.assertEquals("2", decode.getValue());
    }

    @Test(expected = JsonParserException.class)
    public void shouldFailDecode() {
        String json = "{\"_id\":\"1\",\"value\":}";
        JsonParser.decode(json, TestUtil.class);
    }

}

