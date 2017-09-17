package com.rapatao.vertx.routing;

/**
 * Created by rapatao on 31/03/17.
 */
public interface ResponseCode {

    int Continue = 100;
    int SwitchingProtocols = 101;

    int Ok = 200;
    int Created = 201;
    int Accepted = 202;
    int NonAuthoritativeInformation = 203;
    int NoContent = 204;
    int ResetContent = 205;

    int MultipleChoices = 300;
    int MovedPermanently = 301;
    int Found = 302;
    int SeeOther = 303;
    int UseProxy = 305;
    int SwitchProxy = 306;
    int TemporaryRedirect = 307;

    int BadRequest = 400;
    int Unauthorized = 401;
    int PaymentRequired = 402;
    int Forbidden = 403;
    int NotFound = 404;
    int MethodNotAllowed = 405;
    int NotAcceptable = 406;
    int RequestTimeout = 408;
    int Conflict = 409;
    int Gone = 410;
    int LengthRequired = 411;
    int PayloadTooLarge = 413;
    int URITooLong = 414;
    int UnsupportedMediaType = 415;
    int ExpectationFailed = 417;
    int UpgradeRequired = 426;

    int InternalServerError = 500;
    int NotImplemented = 501;
    int BadGateway = 502;
    int ServiceUnavailable = 503;
    int GatewayTimeout = 504;
    int HTTPVersionNotSupported = 505;

}
