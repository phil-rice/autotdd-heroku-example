

import org.eclipse.jetty.servlet.ServletHolder
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.server.nio.SelectChannelConnector
import org.eclipse.jetty.server.Server
import org.corecdd.website.WebServer
import org.cddcore.heroku.example.TennisPathHandler
import org.cddcore.heroku.example.TennisScorer
import org.corecdd.website.CddPathHandler

object Web {

  def main(args: Array[String]) {
    val scorer = TennisScorer.scorer
    WebServer.withPreHandlers(WebServer.defaultPort, scorer, new TennisPathHandler(scorer)).launch
  }
}