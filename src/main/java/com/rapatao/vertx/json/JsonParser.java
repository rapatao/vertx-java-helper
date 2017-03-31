package com.rapatao.vertx.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by rapatao on 31/03/17.
 */
public class JsonParser {

    private static <T> ObjectMapper getObjectMapper(Class<T> value, Class<?>... mixins) {
        final ObjectMapper objectMapper = new ObjectMapper();
        for (Class<?> mixin : mixins) {
            objectMapper.addMixIn(value, mixin);
        }
        return objectMapper;
    }

    public static <T> String encode(T value, Class<?>... mixins) {
        if (value == null) {
            return null;
        }
        final ObjectMapper objectMapper = getObjectMapper(value.getClass(), mixins);
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new JsonParserException(e);
        }
    }

    public static <T> T decode(String json, Class<?> target, Class<?>... mixins) {
        final ObjectMapper objectMapper = getObjectMapper(target, mixins);
        try {
            return objectMapper.readValue(json, (Class<T>) target);
        } catch (IOException e) {
            throw new JsonParserException(e);
        }
    }

}
