package e1
import JaCoP.scala._
import JaCoP.core.IntVar

object exo12 extends jacop{
	//Quadrimestres et cours, classés par années
	val q1=List(2,1,0,0,0);val q2=List(4,0,1,0,0);
	val q3=List(5,0,2,0,0);val q4=List(7,2,0,1,0);
	val q5=List(8,0,3,0,1);val q6=List(0,0,0,4,0);
	//Les UE, leurs noms, leurs crédits, les ECTS, les quadrimestres et les bloquages
	val noms     = List(List("I1","I2","I3","I4","I5"),List("G1","G2"),List("A1","A2","A3"),List("P1","P2"),List("D "));
	val annees   = List(List(q1,q2),List(q1,q2,q3,q4),List(q1,q2,q3,q4,q5));
	val cours    = List(List(2,4,5,7,8),List(1,2),List(1,2,3),List(1,4),List(1));
	val ECTS     = List(List(2,2,1,2,1),List(1,1),List(1,1,1),List(1,3),List(1));
	val periodes = List(List(1,2,1,2,1),List(1,2),List(2,1,1),List(2,2),List(1));
	val bloquant = List(List(0,0,0,0,0),List(0,0),List(0,0,0),List(1,1),List(0));
	val es = List(List.fill(5)(0),List(4,1,1,0,0),List(7,2,2,1,0),List(4,1,0,0,0),List(5,1,3,1,1),List(8,1,2,1,0),List(5,2,0,0,0));
	//Génère le programme de l'étudiant en vérifiant les contraintes
	def resolveUE(etudiant: List[Int]): Unit = {
			var ects = 0;
			var ects_ = 0;
			var index = 0;
			var bloquage = false;
			var conversion = false;
			var conversions = 0;
			//Compte le total d'ects et sélectionne les quadrimestres
			for((m,i) <- etudiant.view.zipWithIndex if(m>0))
			  ects_ +=cours(i)(cours(i).indexOf(m));
			if(ects_ <5)index= 0;
				else if(ects_ <12)index =  1;
				else index= 2;
			//Affiche le programme de l'étudiant et somme les ects
			for((ue,i) <- etudiant.view.zipWithIndex){
				for((q,j)<-annees(index).view.zipWithIndex if(q(i)>ue)){
					var qi = cours(i).indexOf(q(i));
					var ei = cours(i).indexOf(etudiant(i));
					if(ects+ECTS(i)(qi)<=6 
					    && ((ue == 0 && (periodes(i)(0) <= 1 || periodes(i)(qi) > 1)) 
					    || (ue>0 && (periodes(i)(qi)>periodes(i)(ei)||periodes(i)(qi) <= 1)))){
						ects += ECTS(i)(qi);
						print(noms(i)(qi)+" ");
						if(periodes(i)(qi) == 2)bloquage = true;
						if(conversion)conversions+=1;
					}
				}
				if(i > 2 && ects<5 && index < 2 ) {
				  index+=1;
				  conversion=true;
				}
			}
			if((ects+ects_  == 18 - ECTS(3)(1)) && !bloquage){
				print(noms(3)(1)+" ");
				ects += ECTS(3)(1);
			}
			print("(" +ects_ +"-"+ects+"-"+conversions+")");
	} 
	//Liste les cas d'étudiants et génère le programme d'étudiant
	def main(args: Array[String]): Unit = {
			println("n° - Etudiant\t    - Programme");
			for(x<-List.range(0,36))print("=")
			println()
			for(e<-es){
				print(es.indexOf(e)+"  - ");
				for((ue,i) <- e.view.zipWithIndex){
					if (ue ==0) print("0  ")
					else print(noms(i)(cours(i).indexOf(ue))+" ");
				}
				print("- ")
				resolveUE(e);
				println()
			}
			for(x<-List.range(0,36))print("=")
	}
}