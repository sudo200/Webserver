package at.sudo200.Webserver.protocol.http

import java.io.{InputStream, OutputStream, Writer}

case class Response(private val stream: OutputStream, version: Version.Value) extends Writer {
  def writeHead(status: Status.Value, header: Header): Unit = {
    stream.write(StatusLine.Response(version, status).toString.getBytes)
    stream.write(header.toString.getBytes)
    stream.write("\r\n".getBytes)
  }

  override def write(buf: Array[Char], off: Int, len: Int): Unit = stream.write(buf.map(c => c.toByte), off, len)
  def write(input: InputStream): Int = {
    val buffer = new Array[Byte](1 << 16)
    var bytesRead = 0
    while(input.available() > 0) {
      bytesRead += input.read(buffer)
      stream.write(buffer)
    }
    bytesRead
  }

  override def flush(): Unit = stream.flush()

  override def close(): Unit = throw new IllegalStateException
}
