package at.sudo200.Webserver.protocol.http

import java.net.URI

case object StatusLine {
  case class Request(method: Method.Value, uri: URI, version: Version.Value) {
    override def toString: String = f"$method $uri $version\r\n"
  }

  case class Response(version: Version.Value, status: Status.Value, statusMessage: String) {
    override def toString: String = f"$version ${status.id} $statusMessage\r\n"
  }

  object Request extends ((String) => Request) {
    override def apply(_status: String): Request = {
      val Array(method, uri, version) = _status.trim.split("\\s")
      Request(Method.withName(method), new URI(uri), Version.withName(version))
    }
  }

  object Response extends ((String) => Response) {
    override def apply(_status: String): Response = {
      val Array(version, status, statusMessage) = _status.trim.split("\\s")
      Response(Version.withName(version), Status.withName(status), statusMessage)
    }

    def apply(version: Version.Value, status: Status.Value): Response = Response(version, status, status.toString)
  }
}
