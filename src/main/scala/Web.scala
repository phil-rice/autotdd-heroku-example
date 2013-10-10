import org.jboss.netty.handler.codec.http.{ HttpRequest, HttpResponse }
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.{ Http, Response }
import com.twitter.finagle.Service
import com.twitter.util.Future
import java.net.InetSocketAddress
import util.Properties

object Web {
  def main(args: Array[String]) {
    val port = Properties.envOrElse("PORT", "8080").toInt
    println("Starting on port:" + port)
    ServerBuilder()
      .codec(Http())
      .name("hello-server")
      .bindTo(new InetSocketAddress(port))
      .build(new Hello)
    println("Started.")
  }
  val engine = org.autotdd.engine.Engine[Int, String]().withDefaultCode((i: Int) => "Hello World(" + i + ")").build
}

class Hello extends Service[HttpRequest, HttpResponse] {
  var i = 0
  def apply(req: HttpRequest): Future[HttpResponse] = {
    val response = Response()
    response.setStatusCode(200)
    i += 1
    response.setContentString(Web.engine(i))
    Future(response)
  }
}