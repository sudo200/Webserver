package at.sudo200.Webserver.core

import at.sudo200.Webserver.protocol.http.{Header, RequestParser, Response, Status}

import java.io.IOException
import java.net.{Socket, URI}

object ClientHandler extends ((Socket, RequestHandler) => Runnable) {
  override def apply(client: Socket, handler: RequestHandler): Runnable = () => client.synchronized {
    val clientOut = client.getOutputStream

    while (client.isConnected && !client.isClosed)
      try {
        RequestParser(client) match {
          case Some(req) =>
            handler.synchronized {
              handler(req, Response(clientOut, req.statusLine.version))
            }
            req.body.foreach(in => in.skip(in.available))
          case None =>
        }
      } catch {
        case io: IOException => client.close()
        case e: Exception =>
          e.printStackTrace()
          client.close()
      } finally {
        clientOut.flush()
      }

    client.close()
  }
}
