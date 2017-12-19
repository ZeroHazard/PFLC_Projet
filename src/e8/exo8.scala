package e8;
import JaCoP.scala._;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.{Server, Request};
import javax.servlet.http.{HttpServletRequest, HttpServletResponse};
import org.eclipse.jetty.server.Handler;

object exo8 extends jacop{
  def main(args: Array[String]): Unit = {
  //Cours listés par année en tuples structurés comme suit (Intitulé,Identifiant,Q1,Q2,Corequis,Prérequis)
  val _1 = List(/* index : 0 -> 11 */
  ("Algorithmique","I1010",                                            6, 0,  Nil,               Nil),
  ("Analyse et programmation orientée objet","I1020" ,                 6, 0,  Nil,               Nil),
  ("Fonctionnement des ordinateurs","I1070",                           5, 0,  Nil,               Nil),
  ("L’entreprise et ses relations avec le monde économique","I1080",   5, 0,  Nil,               Nil),
  ("Mathématiques 1 : outils fondamentaux","I1090",                    4, 0,  Nil,               Nil),
  ("Gestion de données : bases","I1030",                               0, 5,  Nil,               Nil),
  ("Introduction aux systèmes d'exploitation","I1060",                 0, 5,  Nil,               Nil),
  ("Mathématiques 2 : structures avancées","I1100",                    0, 6,  Nil,               Nil),
  ("Projet de développement web","I1110",                              0, 3,  List(11,5),        Nil),
  ("Structures de données : bases","I1040",                            0, 6,  Nil,               Nil),
  ("Anglais 1","I1120",                                                2, 2,  Nil,               Nil),
  ("Développement web : bases","I1050",                                1, 4,  Nil,               Nil))
  val _2 = List(/* index : 12- > 24 */
  ("Analyse et modélisation","I2160",                                  4, 0,  Nil,               List(1,5,8)),        
  ("Développement web : avancé","I2150",                               3, 0,  Nil,               List(1,5,11,8)),
  ("Gestion de données : avancé","I2040",                              6, 0,  Nil,               List(1,5,9)),
  ("Langage C","I2010",                                                5, 0,  Nil,               List(2,9)),
  ("Programmation Java : avancé","I2130",                              5, 0,  Nil,               List(1,9)),
  ("Systèmes informatiques : principes et protocoles","I2060",         4, 0,  Nil,               List(6,2,10)),
  ("Conception d'applications d'entreprise","I2090",                   0, 7,  List(15,12,14,13), Nil),
  ("Informatique mobile","I2110",                                      0, 4,  List(16),          Nil),
  ("Organisation des entreprises","I2080",                             0, 5,  Nil,               List(3)),
  ("Structures de données : avancé","I2140",                           0, 4,  Nil,               List(1,9,11,7)),
  ("Systèmes informatiques : principes et protocoles avancés","I2070", 0, 3,  List(17,22),       Nil),
  ("Unix","I2100",                                                     0, 5,  List(15,17),       Nil),
  ("Anglais 2","I2120",                                                3, 2,  Nil,               List(10)));
	val _3 = List(/* index : 25 -> 35 */
  ("Administration et sécurité des réseaux","I3030",                   5, 0,  List(26),          List(22,23)),
  ("Administration et sécurité des systèmes","I3020",                  4, 0,  List(25),          List(22,23)),
  ("Anglais 3","I3050",                                                2, 0,  Nil,               List(24)),
  ("Développement à l'aide d'un moteur de jeux","I3090",               4, 0,  Nil,               List(16)),
  ("Développement web : questions spéciales","I3150",                  4, 0,  Nil,               List(13,18)),
  ("Intelligence artificielle","I3100",                                4, 0,  Nil,               List(16,21,4,7)),
  ("Organisation et gestion des entreprises","I3040",                  5, 0,  Nil,               List(20)),
  ("Progiciel de gestion intégré","I3120",                             4, 0,  List(31),          List(14)),
  ("Programmation : questions spéciales","I3140",                      6, 0,  Nil,               List(16,14,13,21,18)),
  ("Préparation à l'intégration professionnelle","I3160",              0, 4,  List(32,33),       List(12)),
  ("Intégration en milieu professionnel","I3080",                      0,26,  List(31,34),       Nil)
  )
  /*  Tous les cours    */ val UE              = _1++_2++_3;
  /*  Parcours total    */ def index           = List range(0,UE length);
  /*  Noms des ues      */ def nom(i:Int)      = UE(i)._2 +" "+ UE(i)._1
  /*  ects q1 + q2      */ def ects(i:Int)     = UE(i)._3 + UE(i)._4;
  /*  Corequis d'UE     */ def corequis(i:Int) = UE(i)._5;
  /*  Prérequis d'UE 	*/ def prerequis(i:Int)= UE(i)._6;
  /*  Variables		*/ val vars            = index map ( i => BoolVar(nom(i)));
  /*  ECTS*Variables    */ val pondere         = index map ( i => ects(i)*vars(i));
  /*  UE options        */ val options         = List(vars(28),vars(30),vars(32));
  /*  Parcours options  */ def index_options   = List range(0,options length);
  /*  Exemples 		*/ val etudiants       = ((36,0),(12,24),(15,21),(25,11));
  /*  Exemple testé     */ val cours           = List.fill(etudiants._3._1)(true) ++List.fill(etudiants._3._2)(false) zipWithIndex;
  /*  Option testée 	*/ val opt_1           = 0; val opt = 1; val opt_3 = 2;
  /*  ECTS testé	*/ val total           = cours filter(_._1 == true) map(c=>ects(c._2)) sum
  /*  Minimum ECTS	*/ val infraMoins      = 60-sum(pondere);
  /*  Maximum ECTS	*/ val infraTrop       = sum(pondere)-60;
  /*  Bloquant 1BIN	*/ val infraB1         = sum(List(vars(9), vars(30)));
  /*  Bloquant 2BIN	*/ val infraB2         = sum(List(vars(18),vars(35)));
  /*  Bloquant 1BIN     */ val infraB1p        = IntVar("infraction bloc 1 processé", 1, 30);
  /*  Bloquant 2BIN     */ val infraB2p        = IntVar("infraction bloc 2 processé", 1, 30);
  /*  ECTS enfreints    */ val infraECTS       = IntVar("ECTS",0,30);
  /*  Transformations   */ val infraRequis     = sum(for( i <- index;ps=prerequis(i); p<-ps; if ps!=Nil) yield vars(i)/\vars(p));
  /*  Infractions	*/ val infractions     = infraRequis + infraECTS + 1000*infraB1p + 1000*infraB2p ;
  /*  Cours validable	*/ def valide( i:Int ) = !cours(i)._1  ;

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
        vars filter( _.value()==1 ) map( c=>println( c.id() ) );
        List range(0,60)map(x=>print( "=" ));println;
      })
      if(result){
        val server = new Server(8080)
        server.setHandler(new AbstractHandler{        		
            		override def handle(target: String,req: Request,
            		    httpReq: HttpServletRequest,httpRes: HttpServletResponse) = {
                      httpRes.setContentType("text/html");
                      httpRes.setStatus(HttpServletResponse.SC_OK);
                      httpRes.getWriter().write("<b>PAE</b><br>");
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
