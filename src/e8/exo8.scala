package e8;
import JaCoP.scala._;
import e7.UE;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.{Server, Request};
import javax.servlet.http.{HttpServletRequest, HttpServletResponse};
import org.eclipse.jetty.server.Handler;

object exo8 extends jacop with UE {
  def main(args: Array[String]): Unit = {
  //WARNING reference etudiant testé à changer
  /*  Tous les cours   */ val ue              = _1++_2++_3;
  /*	Parcours total	 */ def UE              = List range(0,ue length);
  /*  Variables			   */ val vars            = ue map ( x => BoolVar(x._2+" "+x._1));
  /*	ects q1 + q2     */ def ects(i:Int)     = ue(i)._3+ue(i)._4;
  /*  ECTS*Variables   */ val pondere         = for( i <- UE ) yield ects(i)*vars(i);
  /*  UE options       */ val opts            = List(vars(29),vars(31),vars(33));
  /*	Parcours options */ def options         = List range(0,opts length);
	/*  Minimum ECTS	   */ val infraMoins      = 60-sum(pondere);
	/*  Maximum ECTS	   */ val infraTrop       = sum(pondere)-60;
	/*	Bloquant 1BIN		 */ val infraB1         = sum(List(vars(9), vars(30)));
	/*	Bloquant 2BIN		 */ val infraB2         = sum(List(vars(18),vars(35)));
	/*  Bloquant 1BIN    */ val infraB1p        = IntVar("infraction bloc 1 processé", 1, 30);
	/*  Bloquant 2BIN    */ val infraB2p        = IntVar("infraction bloc 2 processé", 1, 30);
	/*  ECTS enfreints   */ val infraECTS       = IntVar("ECTS",0,30);
	/*  Transformations  */ val infraRequis     = sum(for( i <- UE;p<-{ue(i)._6}; if ue(i)._6!=Nil) 
		                                              yield vars(i)/\vars(p));
	/*  Infractions		   */ val infractions     = infraRequis + infraECTS + 1000*infraB1p + 1000*infraB2p ;
  
	/*  Exemples 				 */ val etudiants       = ((36,0),(12,24),(15,21),(25,11));
	/*  Exemple testé    */ val etudiant        = List.fill(etudiants._2._1)(true) ++
	                                                List.fill(etudiants._2._2)(false);
	/*	Cours validable	 */ def valide( i:Int ) = !etudiant(i)  ;
	/*  Options etudiants*/ val opt_1           = 0; val opt = 1; val opt_3 = 2;
	/*  ECTS d'etudiant  */ val total           = (for( (c,i) <- etudiant.zipWithIndex if c )yield ects(i)).sum

    if(total<45)
    	for ((cours,i) <- _1.zipWithIndex if valide(i) ) println(cours._1);	
    else if(total>120)
      for((cours,i)<-etudiant.zipWithIndex if valide(i))println(ue(i)._1);
    else {
      max(List(IntVar("1",1,1), infraB1),infraB1p);
      max(List(IntVar("1",1,1), infraB2),infraB2p);
      for( i <- UE){
        if(!valide(i)) vars(i)#=0;
        for (requis <- ue(i)._6++ue(i)._5 ) if (valide(requis)) vars(i)->(vars(requis)#=1);
      };
      for(i <- options if i!=opt ) opts(i)#=0;
      sum(pondere)>45;
      sum(pondere)<90;
      max(List(10*infraMoins, 7*infraTrop), infraECTS);
      val result = minimize(search(vars,smallest, indomain_max),infractions,()=>{
        println("Cost " + infractions.value + ":\n");
        for (cours<-vars.filter(c=>c.value()==1)) println(cours.id());
        List range(0,56)map(x=>print( "=" ));println;
      })
      if(result){
        val server = new Server(8080)
        server.setHandler(new AbstractHandler{        		
            		override def handle(target: String,req: Request,
            		    httpReq: HttpServletRequest,httpRes: HttpServletResponse) = {
                httpRes.setContentType("text/html");
                httpRes.setStatus(HttpServletResponse.SC_OK);
                httpRes.getWriter().write("<label>PAE</label><br>");
                for (cours<-vars.filter(c=>c.value()==1))
                  httpRes.getWriter().write(""+cours.id()+"<br>");
                req.setHandled(true);
              		}
              });
        server.start
      } else println("No Result");
    }
	}
}