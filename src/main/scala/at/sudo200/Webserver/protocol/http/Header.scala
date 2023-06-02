package at.sudo200.Webserver.protocol.http

case class Header(private val default_entries: (String, String)*) extends java.util.Hashtable[String, String] {
  default_entries.foreach(entry => super.put(entry._1, entry._2))

  def apply(key: String): Option[String] = Option(super.get(key))

  override def toString: String = {
    val stringBuilder = new StringBuilder()
    super.forEach((key, value) => stringBuilder.append(f"$key: $value\r\n"))
    stringBuilder.result
  }
}

object Header extends ((String) => Header) {
  override def apply(header: String): Header = {
    val h = Header()
    header.trim.split("\\r*\\n+").foreach(line => {
      val Array(key, value) = line.split(":", 2).map(s => s.trim)
      h.put(key, value)
    })
    h
  }
}
