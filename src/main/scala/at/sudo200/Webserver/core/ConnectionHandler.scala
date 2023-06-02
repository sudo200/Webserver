package at.sudo200.Webserver.core

import java.nio.channels.SocketChannel

trait ConnectionHandler extends (SocketChannel => Unit)
