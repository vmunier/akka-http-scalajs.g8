package $package$

import akka.http.scaladsl.server.Directives
import $package$.shared.SharedMessages
import $package$.twirl.Implicits._

class WebService() extends Directives {

  val route = {
    pathSingleSlash {
      get {
        complete {
          $package$.html.index.render(SharedMessages.itWorks)
        }
      }
    } ~
      pathPrefix("assets" / Remaining) { file =>
        // optionally compresses the response with Gzip or Deflate
        // if the client accepts compressed responses
        encodeResponse {
          getFromResource("public/" + file)
        }
      }
  }
}
