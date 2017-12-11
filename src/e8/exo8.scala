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
  /*  Tous les cours   */ val UE              = _1++_2++_3;
  /*	Parcours total	 */ def index           = List range(0,UE length);
  /*	Noms des ues     */ def nom(i:Int)      = UE(i)._2 +" "+ UE(i)._1
  /*	ects q1 + q2     */ def ects(i:Int)     = UE(i)._3 + UE(i)._4;
  /*							     */ def corequis(i:Int) = UE(i)._5;
  /*							     */ def prerequis(i:Int)= UE(i)._6;
  /*  Variables			   */ val vars            = index map ( i => BoolVar(nom(i)));
  /*  ECTS*Variables   */ val pondere         = index map ( i => ects(i)*vars(i));
	/*  Minimum ECTS	   */ val infraMoins      = 60-sum(pondere);
	/*  Maximum ECTS	   */ val infraTrop       = sum(pondere)-60;
	/*	Bloquant 1BIN		 */ val infraB1         = sum(List(vars(9), vars(30)));
	/*	Bloquant 2BIN		 */ val infraB2         = sum(List(vars(18),vars(35)));
	/*  Bloquant 1BIN    */ val infraB1p        = IntVar("infraction bloc 1 processé", 1, 30);
	/*  Bloquant 2BIN    */ val infraB2p        = IntVar("infraction bloc 2 processé", 1, 30);
	/*  ECTS enfreints   */ val infraECTS       = IntVar("ECTS",0,30);
	/*  Transformations  */ val infraRequis     = sum(for( i <- index;ps=prerequis(i); p<-ps; if ps!=Nil) yield vars(i)/\vars(p));
	/*  Infractions		   */ val infractions     = infraRequis + infraECTS + 1000*infraB1p + 1000*infraB2p ;
  /*  UE options       */ val options         = List(vars(29),vars(31),vars(33));
  /*	Parcours options */ def index_options   = List range(0,options length);
	/*  Exemples 				 */ val etudiants       = ((36,0),(12,24),(15,21),(25,11));
	/*  Exemple testé    */ val cours           = List.fill(etudiants._3._1)(true) ++List.fill(etudiants._3._2)(false) zipWithIndex;
	/*	Cours validable	 */ def valide( i:Int ) = !cours(i)._1  ;
	/*  Options etudiants*/ val opt_1           = 0; val opt = 1; val opt_3 = 2;
	/*  ECTS d'etudiant  */ val total           = cours filter(_._1 == true) map(c=>ects(c._2)) sum

	  if(total<45)
    	for ( (cours,i) <- _1.zipWithIndex if valide(i) ) println(cours._1);	
    else if(total>120)
      cours filter (i=>valide(i._2)) map (i=>println(nom(i._2)));
    else {
      max(List(IntVar("1",1,1), infraB1),infraB1p);
      max(List(IntVar("1",1,1), infraB2),infraB2p);
      for( i <- index){
        if(!valide(i)) vars(i)#=0;
        corequis(i)++prerequis(i) filter( valide(_) ) map (requis=>vars(i)->(vars(requis)#=1));
      };
      index_options filter(i=> i!=opt ) map (i=>options(i)#=0);
      sum(pondere)>45;
      sum(pondere)<90;
      max(List(10*infraMoins, 7*infraTrop), infraECTS);
      val result = minimize(search(vars,smallest, indomain_max),infractions,()=>{
        println("Cost " + infractions.value + ":\n");
        vars filter( c=>c.value()==1 ) map( c=>println( c.id() ) );
        List range(0,60)map(x=>print( "=" ));println;
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
