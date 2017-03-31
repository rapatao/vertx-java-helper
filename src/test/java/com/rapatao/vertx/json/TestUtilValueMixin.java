package com.rapatao.vertx.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rapatao on 31/03/17.
 */
public class TestUtilValueMixin {

    @JsonProperty("val")
    private String value;
}
