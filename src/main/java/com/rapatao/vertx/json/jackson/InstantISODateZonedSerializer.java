package com.rapatao.vertx.json.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;

/**
 * Created by rapatao on 31/03/17.
 */
public class InstantISODateZonedSerializer extends JsonSerializer<Instant> {

    @Override
    public void serialize(Instant instant, JsonGenerator jg, SerializerProvider zp) throws IOException {
        jg.writeString(instant.toString());
    }

}
