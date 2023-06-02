package at.sudo200.Webserver.utils

import at.sudo200.Webserver.core.RequestHandler
import at.sudo200.Webserver.protocol.http.{Header, Status}

object HandlerFactory {
  def createStringResponder(msg: String, header: Header = Header(), statusCode: Status.Value = Status.OK): RequestHandler = (req, res) => {
    header.putIfAbsent("Content-Length", msg.length.toString)
    header.putIfAbsent("Content-Type", "text/plain")
    res.writeHead(statusCode, header)
    res.write(msg)
  }

  def createStatusResponder(status: Status.Value): RequestHandler = createStringResponder(
    "<!DOCTYPE html>" +
      "<html lang=\"en\">" +
      "<head>" +
      f"<title>${status.id} ${status.toString}</title>" +
      "<meta charset=\"utf-8\">" +
      "</head>" +
      "<body>" +
      f"<h1>${status.id} ${status.toString}</h1>" +
      "</body>" +
      "</html>",
    Header(
      "Content-Type" -> "text/html"
    ),
    status
  )
}
