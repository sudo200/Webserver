package at.sudo200.Webserver.protocol.http

import at.sudo200.Webserver.utils.ParseException

import java.io.InputStream
import java.net.Socket

case class Request(statusLine: StatusLine.Request, header: Header, body: Option[InputStream], client: Socket)

object Request extends ((String, Socket) => Request) {
  @throws[ParseException] override def apply(req: String, client: Socket): Request = {
    try {
      val parts = req.split("\\r\\n\\r\\n", 2)

      val firstCRLF = parts(0).indexOf("\r\n")
      val statusLine = parts(0).substring(0, firstCRLF)
      val header = parts(0).substring(firstCRLF)

      Request(
        StatusLine.Request(statusLine),
        Header(header),
        Option(client).map(c => c.getInputStream),
        client
      )
    } catch {
      case e: Exception => throw new ParseException(e)
    }
  }
}
