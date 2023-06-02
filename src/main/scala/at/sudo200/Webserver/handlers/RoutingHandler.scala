package at.sudo200.Webserver.handlers

import at.sudo200.Webserver.core.RequestHandler
import at.sudo200.Webserver.protocol.http.{Header, Request, Response, Status, StatusLine}
import at.sudo200.Webserver.utils.HandlerFactory

import java.net.URI
import scala.collection.mutable

case class RoutingHandler(defaultRoutes: (String, RequestHandler)*) extends RequestHandler {
  private val routes: mutable.Map[String, RequestHandler] = mutable.Map(defaultRoutes: _*)
  private var defaultRoute = HandlerFactory.createStatusResponder(Status.NOT_FOUND)

  def addRoute(path: String, handler: RequestHandler): Unit = routes.put(path, handler)

  def setDefaultRoute(default: RequestHandler): Unit = defaultRoute = default

  override def handle(req: Request, res: Response): Unit = {
    val path = req.statusLine.uri.getPath
    val matchingRoutes = routes.keys.filter(key => path.matches(key.replaceAll("\\*", ".*")))
    if (matchingRoutes.nonEmpty) {
      val route = matchingRoutes.maxBy(_.length)
      val newReq = Request(
        StatusLine.Request(
          req.statusLine.method,
          new URI(req.statusLine.uri.toString.stripPrefix(route.replaceAll("\\*", ""))),
          req.statusLine.version
        ),
        req.header,
        req.body,
        req.client
      )
      routes(route)(newReq, res)
    } else
      defaultRoute(req, res)
  }
}
