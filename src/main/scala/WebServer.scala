

import org.eclipse.jetty.servlet.ServletHolder
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.server.nio.SelectChannelConnector
import org.eclipse.jetty.server.Server
import com.sun.jersey.spi.container.servlet.ServletContainer
import com.sun.jersey.spi.container.servlet.ServletContainer
import org.corecdd.website.WebServer

object Web {

  def main(args: Array[String]) {
    WebServer("org.cddcore.heroku.example").launch
  }
}