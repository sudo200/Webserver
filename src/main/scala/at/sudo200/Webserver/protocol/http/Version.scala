package at.sudo200.Webserver.protocol.http

object Version extends Enumeration {
  val v0_9 = Value("")
  val oneline = v0_9
  val v1_0 = Value("HTTP/1.0")
  val v1_1 = Value("HTTP/1.1")
  val v2_0 = Value("HTTP/2.0")
  val v3_0 = Value("HTTP/3.0")
}
