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
		var data = List(new Product("1","11"),new Product("2","22"));
		//Debut du fichier HTML
		var htmlTemplate = "<!DOCTYPE html><html><head><style>table {border-collapse: collapse;width: 100%;}"+
		"th, td {text-align: left;padding: 8px;}tr:nth-child(even){background-color: #f2f2f2}</style></head><body><table>"
		//Fin du fichier HTML
		var htmlTemplate2="</table></body></html>"
		
		var html = htmlTemplate+produceHtml(data).content+htmlTemplate2;
		
		override def handle(target: String,
				req: Request,
				httpReq: HttpServletRequest,
				httpRes: HttpServletResponse) = {
						httpRes.setContentType("text/html")
						httpRes.setStatus(HttpServletResponse.SC_OK)
						httpRes.getWriter().write(html)
						req.setHandled(true)
		}
	}
}
