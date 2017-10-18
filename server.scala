import org.eclipse.jetty.server.handler.AbstractHandler
import org.eclipse.jetty.server.{Server, Request}
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
object server{
	def main(args: Array[String]): Unit = {
			val server = new Server(8080)
					server.setHandler(new Handler)
					server.start
	}
case class Product(var name: String,var prix: String)

case class Html(content: String)

def produceHtml(products: List[Product]): Html = {
		Html {
			products.map { product =>
			"<tr>"+
			"<td>"+product.name+"</td>"+
			"<td>"+product.prix+"</td>"+
			"</tr>"
			}.mkString("\t")
		}
	}

	class Handler extends AbstractHandler {
		var html = List(new Product("1","11"),new Product("2","22"));
		var s = "<table>"+produceHtml(html).content+"</table>";
		
		override def handle(target: String,
				req: Request,
				httpReq: HttpServletRequest,
				httpRes: HttpServletResponse) = {
						httpRes.setContentType("text/html")
						httpRes.setStatus(HttpServletResponse.SC_OK)
						httpRes.getWriter().write(s)
						req.setHandled(true)
		}
	}
}
