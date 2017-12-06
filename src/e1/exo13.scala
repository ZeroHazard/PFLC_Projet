package e1
import JaCoP.scala._
import JaCoP.core.IntVar

object exo13 extends jacop{
	/*COURS*/
	//Les cours sont classés par UE croissant en fonction des ECTS validés.
	//Un étudiant peut être représenté comme tels (0,0,0,0,0) ou (8,2,3,4,1)
	//Le total D'ECTS identife le cours au sein d'UE

	/*1*/val q1=List(2,1,0,0,0);val q2=List(4,0,1,0,0); val qs1=List(6,1,1,0,0);
	/*2*/val q3=List(5,0,2,0,0);val q4=List(7,2,0,1,0); 
	/*3*/val q5=List(8,0,3,0,1);val q6=List(0,0,0,4,0);
	
	val e = List.fill(18)(0);
	
	/*Périodes des années		 */val annees    = List(List(q1,q2),List(q1,q2,q3,q4),List(q1,q2,q3,q4,q5,q6));              
	/*ECTS totaux            */val cours     = List(List(0,2,4,5,7,8),List(0,1,2),List(0,1,2,3),List(0,1,4),List(0,1));  
	/*ECTS totaux            */val course     = List(0,2,4,5,7,8,0,1,2,0,1,2,3,0,1,4,0,1);  
	/*ECTS des cours  			 */val ECTS      = List(List(0,2,2,1,2,1),List(0,1,1),List(0,1,1,1),List(0,1,3),List(0,1));  
	/*Quadrimestres par cours*/val periodes  = List(List(0,1,2,1,2,1),List(0,1,2),List(0,2,1,1),List(0,2,2),List(0,1));//(0) à 1 ?
	/*Quadrimestres totaux   */val periodes_ = List(List(0,0,1,2,3,4),List(0,0,2),List(1,1,2,4),List(3,3,5),List(4,4));
	/*Quadrimestres totaux   */val periodes_s = List(0,0,1,2,3,4,0,0,2,1,1,2,4,3,3,5,4,4);
	/*Blocage des cours      */val bloquant  = List(List(0,0,0,0,0),List(0,0),List(0,0,0),List(1,1),List(0));           
	/*Codes des UE           */val codes  = List("I","G","A","P","D");
	/*Codes des cours        */val noms   = List("I0","I1","I2","I3","I4","I5","G0","G1","G2","A0","A1","A2","A3","P0","P1","P2","D0","D1");
	/*Exemples testés*/        val es = List(e)
	  //List(List.fill(5)(0),List(4,1,1,0,0),List(7,2,2,1,0),List(4,1,0,0,0),List(5,1,3,1,1),List(8,1,2,1,0),List(5,2,0,0,0));

	//Recoit une liste des cours validés par un étudiant pour rechercher et afficher son programme
	def resolveUEJaCoP(etudiant: List[Int]): Unit = {
			/*Totalise les ECTS*/
			val ects =for(m <- etudiant if(m>0))
  				yield course(course.indexOf(m));
  					
			/*Détermine l'année en totalisant les ECTS*/
  		//TODO determiner annee sans for
  		val annee = for(x<-List.range(0,1))yield
  				if(ects.foldRight(0)((x,y)=>x+y) <5)0;      /*1*/
  				else if(ects.foldRight(0)((x,y)=>x+y) <12)1;/*2*/
  				else 2;                                     /*3*/

		  /*Liste et contraint*/
			val ues = for((ue,i)<-etudiant.zipWithIndex 
				if(course.indexOf(ue) != course.length-1                                       /*Filtre les derniers cours*/
					&& periodes_s(course.indexOf(ue)) < annees(annee(0)).length                  /*Filtre les années*/
					&& (ue == 0 || (ue == annees(annee(0))(periodes_(i)(course.indexOf(ue)))(i)))))/*Filtre les périodes*/
				yield 
				if(course.indexOf(ue) != course.length-2 )                                     /*Filtre les avant-derniers cours*/
					//TODO Ajouter le dernier cours de l'ue au programme
					//TODO Afficher directement les noms des IntVar -> pas substring 
					//TODO verifier quadrimestres. A1 et A2 sont non-conversibles
					if(periodes_s(course.indexOf(ue)+2) >= annees(annee(0)).length)              /*Filtre les périodes*/
						IntVar(noms(course.indexOf(ue)+1),course.indexOf(ue)+2,course.indexOf(ue)+2);
					else {
						IntVar(noms(course.indexOf(ue)+1)+"-"+(noms(course.indexOf(ue)+2)),course.indexOf(ue)+1,course.indexOf(ue)+2);
						//IntVar(noms(i)(0).substring(0,1),course.indexOf(ue)+2,course.indexOf(ue)+2);
					}
				else {
					IntVar('*'+noms(course.indexOf(ue)+1),course.indexOf(ue)+1,course.indexOf(ue)+1);
				}
			
			//print(ues)
			/*Vérifie le total des ECTS*/
			val om =  for((ue,i)<-ues.zipWithIndex)yield ue.value;
			val total_tmp = om.foldRight(0)((x,y)=>x+y);
			//total_tmp#>=6
			/*Recherche en ordre croissant et affiche par ue le programme de l'étudiant*/
			if (satisfy(search(ues,first_fail,indomain_min))) 
				for ((x,i) <- ues.view.zipWithIndex ) 
					//print(noms(codes.indexOf(x.id))(x.value())+" ");
					print(noms(x.value())+" ");
			else print("Erreur");
	}

	//Liste les cas d'étudiants et génère leurs programmes 
	def main(args: Array[String]): Unit = {
			println("n° - Etudiant\t    - Programme");
			for(x<-List.range(0,36))print("=")
			println()
			for(e<-es){
				print(es.indexOf(e)+"  - ");
				for((ue,i) <- e.view.zipWithIndex){
					if (ue ==0) print("0  ")
					else print(noms(course.indexOf(ue))+" ");
				}
				print("- ")
				resolveUEJaCoP(e)
				println()
			}
			for(x<-List.range(0,36))print("=")
	}
}