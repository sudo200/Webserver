package at.sudo200.Webserver


import at.sudo200.Webserver.core.Server
import at.sudo200.Webserver.handlers.{LocalFileHandler, RoutingHandler}

import java.io.File
import java.net.InetSocketAddress
import scala.concurrent.ExecutionContext.Implicits.global

case object Main {
  def main(args: Array[String]): Unit = {

    val server = Server(new InetSocketAddress("127.0.0.1", 8080))

    val router = RoutingHandler()

    router.addRoute("/static*", LocalFileHandler(new File("C:/Users/Fabian").toPath, listDirectories = true))

    server.setHandler(router)
    //server.setHandler(LocalFileHandler(new File("C:/Users/Fabian").toPath, listDirectories = true))

    server.start().onComplete(_ => println("Server started!"))
  }
}
