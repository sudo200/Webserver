package at.sudo200.Webserver.core

import java.nio.channels.{ServerSocketChannel, SocketChannel}
import java.util.function.Consumer
import scala.collection.mutable

case class Acceptor(private val channels: Seq[ServerSocketChannel], private val group: ThreadGroup = Thread.currentThread.getThreadGroup) extends Thread {
  super.setDaemon(false)

  private val events = mutable.Set[ConnectionHandler]()

  def addEvent(e: ConnectionHandler*): this.type = {
    events.synchronized {
      events.addAll(e)
    }
    this
  }


  override def run(): Unit = {
    while (true) {
      val _events = events.synchronized {
        events.clone
      }

      channels.synchronized {
        channels.foreach(channel => Option(channel.accept()) match {
          case Some(sock) => _events.foreach(_.apply(sock))
          case _ =>
        })
      }

      Thread.sleep(1)
    }
  }
}
