import org.eclipse.jetty.server.handler.AbstractHandler
import org.eclipse.jetty.server.{Server, Request}
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import org.jacop.scala._

object server extends jacop{
  //Entry Point
	def main(args: Array[String]): Unit = {
			val server = new Server(8080)
			server.setHandler(new Handler)
			server.start
	}
	
  //Return the solution in a List
  case class Product(name: String,prix: Int)
  def solve(): List[Product] = {
    List(new Product("Pomme",1),new Product("Banane",2));
	}
  
  //Format a List as styled HTML
  case class Html(content: String)
  def produceHtml(products: List[Product]): Html = {
    		var sb = StringBuilder.newBuilder
    		sb.append("<style>table {border-collapse: collapse;width: 100%;}th, td {text-align: left;padding: 8px;}tr:nth-child(even){background-color: #f2f2f2}</style><table>")
    		sb.append(products.map { product =>"<th>"+product+"</th>" +"<tr><td>"+product.name+"</td><td>"+product.prix+"</td></tr>"}.mkString(""))
    		sb.append("</table>")
    		Html {sb.result()}
  	}
  
  //Handle HTTP request
	class Handler extends AbstractHandler {
		override def handle(target: String,req: Request,httpReq: HttpServletRequest,httpRes: HttpServletResponse) = {
			httpRes.setContentType("text/html")
			httpRes.setStatus(HttpServletResponse.SC_OK)
			httpRes.getWriter().write(produceHtml(solve()).content)
			req.setHandled(true)
		}
	}
}