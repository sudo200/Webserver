package at.sudo200.Webserver.protocol.http

import java.io.{BufferedReader, InputStreamReader}
import java.net.Socket
import java.nio.charset.Charset

object RequestParser extends ((Socket) => Option[Request]) {
  override def apply(client: Socket): Option[Request] = {
    val reader = new BufferedReader(new InputStreamReader(client.getInputStream, Charset.defaultCharset))
    val stringBuilder = new StringBuilder
    var line: String = null
    do {
      line = reader.readLine
      if (line != null)
        stringBuilder.append(line).append("\r\n")
    } while (line != null && line.nonEmpty)
    if (line != null)
      stringBuilder.append("\r\n")

    if (line == null) None else Some(Request(stringBuilder.toString, client))
  }
}
