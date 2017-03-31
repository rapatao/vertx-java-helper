package com.rapatao.vertx.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rapatao on 31/03/17.
 */
class TestUtilIdMixin {
    @JsonProperty("_id")
    private String id;
}
