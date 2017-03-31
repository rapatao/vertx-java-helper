package com.rapatao.vertx.routing;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * Created by rapatao on 31/03/17.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseBuilder {

    private final RoutingContext routingContext;
    private MultiMap header = null;
    private ResponseCode responseCode = null;
    private Object content = null;

    public static ResponseBuilder of(final RoutingContext routingContext) {
        return new ResponseBuilder(routingContext);
    }

    private MultiMap defaultHeader() {
        return MultiMap.caseInsensitiveMultiMap().add("content-type", "application/json; charset=utf-8");
    }

    public ResponseBuilder header(final MultiMap header) {
        this.header = header;
        return this;
    }

    public ResponseBuilder header(final String key, final String value) {
        if (this.header == null) {
            this.header = MultiMap.caseInsensitiveMultiMap();
        }
        this.header.add(key, value);
        return this;
    }

    public ResponseBuilder responseCode(final ResponseCode responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public ResponseBuilder content(final Object content) {
        this.content = content;
        return this;
    }

    public void build() {
        final HttpServerResponse response = routingContext.response();
        response.headers().addAll(Optional.ofNullable(this.header).orElse(defaultHeader()));
        response.setStatusCode(Optional.ofNullable(this.responseCode).orElse(ResponseCode.Ok).getValue());

        final Optional<Object> content = Optional.ofNullable(this.content);
        if (content.isPresent()) {
            response.end(Json.encode(content.get()));
        } else {
            response.end();
        }
    }

}
