package e7
import JaCoP.scala._
object exo7 extends jacop with UE {
  //WARNING reference etudiant testé à changer
  /*  Etudiant 1BIN    */ val etudiant_1      = List.fill(36)(0);
	/*  Etudiant 2BIN    */ val etudiant        = List.fill(12)(true) ++ List.fill(24)(false);
	/*  Etudiant 3BIN    */ val etudiant_3      = List.fill(25)(true) ++ List.fill(11)(false);
	/*  Options etudiants*/ val opt_1           = 0; val opt = 1; val opt_3 = 2
	/*  Tous les cours   */ val tout            = _1++_2++_3
	/*  ECTS des cours   */ val ECTS            = tout map ( x => x._3 + x._4 );
  /*  Corequis 			   */ val corequis        = tout map ( x => x._5 );
  /*  Prerequis			   */ val prerequis       = tout map ( x => x._6 );
  /*  Variables			   */ val vars            = tout map ( x => BoolVar(x._2+" "+x._1));
  /*  ECTS*Variables   */ val pondere         = for( i <- all ) yield { ECTS(i)*vars(i) };
	/*  ECTS d'etudiant  */ val total           = (for( (c,i) <- etudiant.zipWithIndex if c ) yield ECTS(i)).sum
	/*  Transformations  */ val infraRequis     = sum(for( i <- all;ps=prerequis(i);p<-ps; if ps!=Nil) yield vars(i)/\vars(p))
	/*  Minimum ECTS	   */ val infraMoins      = 60-sum(pondere);
	/*  Maximum ECTS	   */ val infraTrop       = sum(pondere)-60;
	/*  UE bloquantes q1 */ val q1              = List(vars(9), vars(30))
	/*  UE bloquantes q2 */ val q2              = List(vars(18),vars(35));
	/*	Bloquant 1BIN		 */ val infraB1         = sum(q1);
	/*	Bloquant 2BIN		 */ val infraB2         = sum(q2);
	/*  Bloquant 1BIN    */ val infraB1p        = IntVar("infraction bloc 1 processé", 1, 30);
	/*  Bloquant 2BIN    */ val infraB2p        = IntVar("infraction bloc 2 processé", 1, 30);
	/*  ECTS enfreints   */ val infraECTS       = IntVar("ECTS",0,30);
	/*  Infractions		   */ val infractions     = infraRequis + infraECTS + 1000*infraB1p + 1000*infraB2p 
	/*  Mise en page	   */ def separation      = {List range(0,56)map(x=>print( "=" ));println}
	/*	Cours validable	 */ def valide( i:Int ) = !etudiant(i)  
	/*	Parcours total	 */ def all             = List range(0,tout length)
	/*	Parcours options */ def options         = List range(0,opts length)
	/*  UE options       */ val opts            = List(vars(29),vars(31),vars(33))
  
	def main(args: Array[String]): Unit = {
			if(total<45){
				for ((cours,i) <- _1.zipWithIndex if valide(i) ) println(cours._1);	
				return;
			};
			if(total>120){
			  for((cours,i)<-etudiant.zipWithIndex if valide(i))println(tout(i)._1);
			  return;
			};
			max(List(IntVar("1",1,1), infraB1),infraB1p)
			max(List(IntVar("1",1,1), infraB2),infraB2p)
			for( i <- all){
			  if(!valide(i)) vars(i)#=0;
			  for (requis <- corequis(i)++prerequis(i)  if valide(requis)) vars(i)->(vars(requis)#=1);
			};
			for(i <- options if i!=opt ) opts(i)#=0;
			sum(pondere)>45;
			sum(pondere)<90
			max(List(100*infraMoins, 33*infraTrop), infraECTS);
			val result = minimize(search(vars,smallest, indomain_max),infractions,()=>{
			  println("Cost " + infractions.value + ":\n")
			  for (cours<-vars.filter(c=>c.value()==1)) println(cours.id());
			  separation
			});
			if(!result)println("No Result");
	}
}