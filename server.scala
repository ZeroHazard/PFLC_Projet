import org.eclipse.jetty.server.handler.AbstractHandler
import org.eclipse.jetty.server.{Server, Request}
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
object server{
	def main(args: Array[String]): Unit = {
			val server = new Server(8080)
					server.setHandler(new Handler)
					server.start
	}
	class Handler extends AbstractHandler {
		//var html = <h1>Something, innit</h1>
		var html = "Something, innit"

				override def handle(target: String,
						req: Request,
						httpReq: HttpServletRequest,
						httpRes: HttpServletResponse) = {
								httpRes.setContentType("text/html")
								httpRes.setStatus(HttpServletResponse.SC_OK)
								httpRes.getWriter().println(html.toString)
								req.setHandled(true)
		}
	}
}