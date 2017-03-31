package com.rapatao.vertx.json.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;

/**
 * Created by rapatao on 31/03/17.
 */
public class MongoISODateDeserializer extends JsonDeserializer<Instant> {

    @Override
    public Instant deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        MongoIsoDate mongoIsoDate = jp.getCodec().readValue(jp, MongoIsoDate.class);
        return Instant.parse(mongoIsoDate.getIsoDate());
    }

}


