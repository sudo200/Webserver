package at.sudo200.Webserver.protocol.http

object Status extends Enumeration {
  val CONTINUE = Value(100, "Continue")
  val SWITCHING_PROTOCOLS = Value(101, "Switching Protocols")
  val PROCESSING = Value(102, "Processing")
  val EARLY_HINTS = Value(103, "Early Hints")

  val OK = Value(200, "OK")
  val CREATED = Value(201, "Created")
  val ACCEPTED = Value(202, "Accepted")
  val NON_AUTHORITATIVE_INFORMATION = Value(203, "Non-Authoritative Information")
  val NO_CONTENT = Value(204, "No Content")
  val PARTIAL_CONTENT = Value(206, "Partial Content")
  val MULTI_STATUS = Value(207, "Multi-Status")

  val MULTIPLE_CHOICES = Value(300, "Multiple Choices")
  val MOVED_PERMANENTLY = Value(301, "Moved Permanently")
  val FOUND = Value(302, "Found")
  val SEE_OTHER = Value(303, "See Other")
  val NOT_MODIFIED = Value(304, "Not Modified")
  val USE_PROXY = Value(305, "Use Proxy")
  val TEMPORARY_REDIRECT = Value(307, "Temporary Redirect")
  val PERMANENT_REDIRECT = Value(308, "Permanent Redirect")

  val BAD_REQUEST = Value(400, "Bad Request")
  val UNAUTHORIZED = Value(401, "Unauthorized")
  val PAYMENT_REQUIRED = Value(402, "Payment Required")
  val FORBIDDEN = Value(403, "Forbidden")
  val NOT_FOUND = Value(404, "Not Found")
  val METHOD_NOT_ALLOWED = Value(405, "Method Not Allowed")
  val NOT_ACCEPTABLE = Value(406, "Not Acceptable")
  val PROXY_AUTH_REQUIRED = Value(407, "Proxy Auth Required")
  val REQUEST_TIMEOUT = Value(408, "Request Timeout")
  val CONFLICT = Value(409, "Conflict")
  val GONE = Value(410, "Gone")
  val LENGTH_REQUIRED = Value(411, "Length Required")
  val PRECONDITION_FAILED = Value(412, "Precondition Failed")
  val PAYLOAD_TOO_LARGE = Value(413, "Payload Too Large")
  val REQUEST_URI_TOO_LONG = Value(414, "Request URI too long")
  val UNSUPPORTED_MEDIA_TYPE = Value(415, "Unsupported Media Type")
  val REQUEST_RANGE_NOT_SATISFIABLE = Value(416, "Request Range Not Satisfiable")
  val EXPECTATION_FAILED = Value(417, "Expectation failed")
  val I_M_A_TEAPOT = Value(418, "I'm A Teapot")
  val ENHANCE_YOUR_CALM = Value(420, "Enhance Your Calm")
  val MISDIRECTED_REQUEST = Value(421, "Misdirected Request")
  val UNPROCESSABLE_ENTITY = Value(422, "Unprocessable Entity")
  val LOCKED = Value(423, "Locked")
  val FAILED_DEPENDENCY = Value(424, "Failed Dependency")
  val TOO_EARLY = Value(425, "Too Early")
  val UPGRADE_REQUIRED = Value(426, "Upgrade Required")
  val TOO_MANY_REQUESTS = Value(429, "Too Many Requests")
  val REQUEST_HEADER_FIELDS_TOO_LARGE = Value(431, "Request Header Fields Too Large")
  val NO_RESPONSE = Value(444, "No Response")
  val UNAVAILABLE_FOR_LEGAL_REASONS = Value(451, "Unavailable For Legal Reasons")
  val TOKEN_INVALID = Value(498, "Token Invalid")
  val CLIENT_CLOSED_REQUEST = Value(499, "Client Closed Request")

  val INTERNAL_SERVER_ERROR = Value(500, "Internal Server Error")
  val NOT_IMPLEMENTED = Value(501, "Not Implemented")
  val BAD_GATEWAY = Value(502, "Bad Gateway")
  val SERVICE_UNAVAILABLE = Value(503, "Service Unavailable")
  val GATEWAY_TIMEOUT = Value(504, "Gateway Timeout")
  val VARIANT_ALSO_NEGOTIATES = Value(506, "Variant Also Negotiates")
  val INSUFFICIENT_STORAGE = Value(507, "Insufficient Storage")
  val LOOP_DETECTED = Value(508, "Loop Detected")
  val BANDWIDTH_LIMIT_EXCEEDED = Value(509, "Bandwidth Limit Exceeded")
  val NOT_EXTENDED = Value(510, "Not Extended")
  val NETWORK_AUTH_REQUIRED = Value(511, "Network Auth Required")
  val WEB_SERVER_IS_DOWN = Value(521, "Web Server Is Down")
  val CONNECTION_TIMED_OUT = Value(522, "Connection Timed Out")
  val ORIGIN_UNREACHABLE = Value(523, "Origin Unreachable")
  val NETWORK_CONNECTION_TIMEOUT_ERROR = Value(599, "Network Connection Timeout Error")
}