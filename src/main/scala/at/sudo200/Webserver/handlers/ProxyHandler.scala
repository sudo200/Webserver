package at.sudo200.Webserver.handlers

import at.sudo200.Webserver.core.RequestHandler
import at.sudo200.Webserver.protocol.http.{Header, Method, Request, Response, Status}

import java.net.{HttpURLConnection, MalformedURLException, Proxy, URL}

case class ProxyHandler @throws[MalformedURLException]() (private val host: URL, private val proxy: Proxy = Proxy.NO_PROXY) extends RequestHandler {
  if(host.getProtocol.equals("file"))
    throw new MalformedURLException("Local files are not supported")

  override def handle(req: Request, res: Response): Unit = {
    val connection = host.toURI.resolve(req.statusLine.uri).toURL.openConnection(proxy).asInstanceOf[HttpURLConnection]
    val method = req.statusLine.method
    connection.setRequestMethod(method.toString)

    req.header.forEach((key, value) => connection.addRequestProperty(key, value))

    connection.setDoInput(true)
    if ((method == Method.PUT || method == Method.POST || method == Method.PATCH) && req.body.isDefined) {
      connection.setDoOutput(true)
      val input = req.body.get
      val output = connection.getOutputStream
      val buffer = new Array[Byte](1 << 16)
      while (input.available() > 0) {
        input.read(buffer)
        output.write(buffer)
      }
      output.flush()
      output.close()
    }

    val responseCode = connection.getResponseCode

    val header = Header()
    connection.getHeaderFields.forEach((key, values) => if(key != null) values.forEach(value => header.put(key, value)))
    res.writeHead(Status(responseCode), header)

    val input = connection.getInputStream
    val buffer = new Array[Byte](1 << 16)
    while(input.available() > 0) {
      input.read(buffer)
      res.write(buffer.map(_.asInstanceOf[Char]))
    }
  }
}
