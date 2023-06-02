package at.sudo200.Webserver.core

import at.sudo200.Webserver.protocol.http.{Header, Request, Response}
import at.sudo200.Webserver.utils.HandlerFactory

trait RequestHandler extends ((Request, Response) => Unit) {
  override final def apply(req: Request, res: Response): Unit = handle(req, res)

  def handle(req: Request, res: Response): Unit
}

object RequestHandler extends (() => RequestHandler) {
  override def apply(): RequestHandler = HandlerFactory.createStringResponder(
    "<!DOCTYPE html>" +
      "<html lang=\"en\">" +
      "<head>" +
      "<title>It works!</title>" +
      "</head>" +
      "<body>" +
      "<h1>Lol</h1>" +
      "</body>" +
      "</html>",
    Header("Content-Type" -> "text/html")
  )
}
