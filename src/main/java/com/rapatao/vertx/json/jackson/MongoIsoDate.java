package com.rapatao.vertx.json.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rapatao on 31/03/17.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
class MongoIsoDate {

    @JsonProperty("$date")
    private String isoDate;

}
