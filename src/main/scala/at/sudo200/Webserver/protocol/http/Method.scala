package at.sudo200.Webserver.protocol.http

object Method extends Enumeration {
  val ACL,
  BIND,
  CHECKOUT,
  CONNECT,
  COPY,
  DELETE,
  GET,
  HEAD,
  LINK,
  LOCK,
  MERGE,
  MKACTIVITY,
  MKCALENDAR,
  MKCOL,
  MOVE,
  NOTIFY,
  OPTIONS,
  PATCH,
  POST,
  PROPFIND,
  PROPPATCH,
  PURGE,
  PUT,
  REBIND,
  REPORT,
  SEARCH,
  SOURCE,
  SUBSCRIBE,
  TRACE,
  UNBIND,
  UNLINK,
  UNLOCK,
  UNSUBSCRIBE = Value
  val M_SEARCH = Value("M-SEARCH")
}