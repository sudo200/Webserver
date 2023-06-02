package at.sudo200.Webserver.core

import java.net.{InetSocketAddress, SocketAddress}
import java.nio.channels.ServerSocketChannel
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.{ExecutorService, Executors}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Server(val addrs: Seq[SocketAddress]) {
  if (addrs.length <= 0)
    throw new IllegalArgumentException("At least one address is required")

  //private val LOG = Logger(getClass)

  private val requestHandler = new AtomicReference[RequestHandler](RequestHandler())
  private val threadGroup = new ThreadGroup(getClass.toString)

  private val channels = addrs.map(addr => ServerSocketChannel.open.bind(addr))
  channels.foreach(channel => channel.configureBlocking(false))

  private val pool = Executors.newCachedThreadPool(task => {
    //LOG.trace("Creating worker for task \"{}\"", task)
    new Thread(threadGroup, task)
  })
  private val acceptor = Acceptor(channels, threadGroup).addEvent(channel => pool.submit(ClientHandler(channel.socket, requestHandler.get)))

  def start(): Future[Unit] = Future {
    //LOG.debug("Starting acceptor")
    acceptor.start()
    //LOG.debug("Acceptor started")
  }

  def stop(): Future[Unit] = Future {
    //LOG.debug("Stopping acceptor...")
    acceptor.interrupt()
    pool.shutdown()

    //LOG.debug("Awaiting termination of workers...")
    while (!pool.isTerminated) Thread.sleep(1)
    //LOG.debug("Workers terminated")
  }

  def setHandler(handler: RequestHandler): Unit = requestHandler.set(handler)

  def getThreadGroup: ThreadGroup = threadGroup

  def getExecutorService: ExecutorService = pool
}

object Server {
  def apply(addr: SocketAddress*): Server = new Server(addr)

  def apply(port: Int, more: Int*): Server = new Server(
    Seq(new InetSocketAddress(port))
      .concat(more.map(p => new InetSocketAddress(p)))
  )
}
