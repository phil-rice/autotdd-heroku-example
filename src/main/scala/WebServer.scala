

import org.eclipse.jetty.servlet.ServletHolder
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.server.nio.SelectChannelConnector
import org.eclipse.jetty.server.Server
import com.sun.jersey.spi.container.servlet.ServletContainer
import com.sun.jersey.spi.container.servlet.ServletContainer

object WebServer {

  def launch(s: String*) {
    val server = new Server(80)
    val connector = new SelectChannelConnector()
    server.addConnector(connector)

    val holder: ServletHolder = new ServletHolder(classOf[ServletContainer])
    holder.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig")
    holder.setInitParameter("com.sun.jersey.config.property.packages", s.mkString(";"))
    val context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS)
    context.addServlet(holder, "/*")
    server.start
    server.join
  }

  def main(args: Array[String]) {
    launch("org.autotdd.example.tennisScore")
  }
}