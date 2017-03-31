package com.rapatao.vertx.json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by rapatao on 31/03/17.
 */
public class FailSerializer extends JsonSerializer<TestUtil> {

    @Override
    public void serialize(TestUtil value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        throw new JsonGenerationException("test", gen);
    }

}
