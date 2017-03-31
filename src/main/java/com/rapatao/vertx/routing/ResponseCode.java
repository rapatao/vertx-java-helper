package com.rapatao.vertx.routing;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by rapatao on 31/03/17.
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {

    Continue(100),
    SwitchingProtocols(101),
    Ok(200),
    Created(201),
    Accepted(202),
    NonAuthoritative(203),
    NoContent(204),
    ResetContent(205),
    MultipleChoices(300),
    MovedPermanently(301),
    Found(302),
    SeeOther(303),
    UseProxy(305),
    TemporaryRedirect(307),
    BadRequest(400),
    PaymentRequired(402),
    Forbidden(403),
    NotFound(404),
    MethodNotAllowed(405),
    NotAcceptable(406),
    RequestTimeout(408),
    Conflict(409),
    Gone(410),
    LengthRequired(411),
    PayloadTooLarge(413),
    URITooLong(414),
    UnsupportedMediaType(415),
    ExpectationFailed(417),
    UpgradeRequired(426),
    InternalServerError(500),
    NotImplemented(501),
    BadGateway(502),
    ServiceUnavailable(503),
    GatewayTimeout(504),
    HTTPVersionNotSupported(505);

    private int value;

}
