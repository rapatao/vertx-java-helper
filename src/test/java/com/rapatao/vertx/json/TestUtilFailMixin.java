package com.rapatao.vertx.json;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by rapatao on 31/03/17.
 */
public class TestUtilFailMixin {

    @JsonSerialize(using = FailSerializer.class)
    private String id;

}
