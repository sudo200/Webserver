package at.sudo200.Webserver.handlers

import at.sudo200.Webserver.core.RequestHandler
import at.sudo200.Webserver.protocol.http._
import at.sudo200.Webserver.utils.HandlerFactory

import java.io.FileInputStream
import java.net.URI
import java.nio.file.{Files, Path}

case class LocalFileHandler(private val base: Path, private val listDirectories: Boolean = false) extends RequestHandler {
  private val notFoundHandler = HandlerFactory.createStatusResponder(Status.NOT_FOUND)
  private val forbiddenHandler = HandlerFactory.createStatusResponder(Status.FORBIDDEN)
  private val methodNotAllowedHandler = HandlerFactory.createStatusResponder(Status.METHOD_NOT_ALLOWED)

  override def handle(req: Request, res: Response): Unit = {
    if(req.statusLine.method != Method.GET) {
      methodNotAllowedHandler(req, res)
      return
    }

    val file = base.resolve(req.statusLine.uri.getPath.stripPrefix("/")).toFile
    if (file.exists()) {
      if (file.isDirectory) {
        if (listDirectories) {
          if(!req.statusLine.uri.getPath.endsWith("/")) {
            val uri = req.statusLine.uri
            res.writeHead(Status.TEMPORARY_REDIRECT, Header(
              "Location" -> new URI(
                uri.getScheme,
                uri.getAuthority,
                uri.getPath + '/',
                uri.getQuery,
                uri.getFragment
              ).toString
            ))
            res.write(0)
            return
          }

          val builder = new StringBuilder
          builder.append("<!DOCTYPE html>" +
            "<head>" +
            "<meta charset=\"ascii\">" +
            f"<title>Listing for \"${req.statusLine.uri.getPath}\"</title>" +
            "</head>" +
            "<body>" +
            f"<h1>Listing for \"${req.statusLine.uri.getPath}\"</h1>" +
            "<hr>" +
            "<table>")

          builder.append(f"<tr><td><a href=\"..\">..</a></td></tr>")

          Files.newDirectoryStream(file.toPath).iterator.forEachRemaining(
            file => builder.append(
              f"<tr><td><a href=\"${file.getFileName.toString}\">" +
                f"${file.getFileName}</a></td><td>${if (file.toFile.canRead) "r" else "-"}${if (file.toFile.canWrite) "w" else "-"}${if (file.toFile.canExecute) "x" else "-"} " +
                f"${Files.getOwner(file).getName}</td><td>${Files.getLastModifiedTime(file)}</td><td>${file.toFile.length}</td></tr>"
            )
          )

          builder.append("</table>" +
            "</body>" +
            "</html>")

          res.writeHead(Status.OK, Header(
            "Content-Type" -> "text/html",
            "Content-Length" -> builder.length.toString
          ))
          res.write(builder.toString)
        } else
          forbiddenHandler(req, res)
      } else {
        res.writeHead(Status.OK, Header(
          "Content-Type" -> Option(Files.probeContentType(file.toPath)).getOrElse("application/octet-stream"),
          "Content-Length" -> file.length.toString
        ))
        res.write(new FileInputStream(file))
      }
    } else
      notFoundHandler(req, res)
  }
}
