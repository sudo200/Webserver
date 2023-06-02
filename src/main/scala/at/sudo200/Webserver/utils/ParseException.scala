package at.sudo200.Webserver.utils

class ParseException(private val message: String, private val cause: Throwable) extends RuntimeException(message, cause) {
  def this(message: String) = this(message, null)

  def this(cause: Throwable) = this(null, cause)

  def this() = this(null, null)
}
