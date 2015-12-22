package chk.commons


object Params extends Params with LogParams
trait Params {
  final lazy val LOOPBACK_ADDRESS = List("127.0.0.1", "::1", "0:0:0:0:0:0:0:1", "localhost", "example.com")
}

trait LogParams {
  final lazy val LM_ACCESS_LOG = "ACCESS_LOG"

  final lazy val MDC_REQUEST_ID  = "req_id"
  final lazy val MDC_REQUEST_URI = "req_uri"
  final lazy val MDC_REQ_TIME    = "req_time"
  final lazy val MDC_IP_ADDRESS  = "ip_address"
}

object RequestParams extends RequestParams
trait RequestParams {
  final lazy val REQ_ID = "rid"
  final lazy val REQ_FIELDS = "fields"
  final lazy val REQ_EXCLUDE_FIELDS = "exfields"
  final lazy val REQ_LANG = "lang"
  final lazy val REQ_TIME_ZONE = "tz"
  final lazy val REQ_REGION = "region"
  final lazy val REQ_CITY = "city"
  final lazy val REQ_COUNTRY = "country"
}

object HttpHeaders extends HttpHeaders
trait HttpHeaders {
  final lazy val REQ_ID = "X-Request-ID"
  final lazy val REQ_TIME = "Request-Time"

  final lazy val FORWARDED_FOR = "X-Forwarded-For"
  final lazy val FORWARDED_PROTO = "X-Forwarded-Proto"

  final lazy val AUTHORIZATION = "Authorization"
  final lazy val USER_AGENT = "User-Agent"
}