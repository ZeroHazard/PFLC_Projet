package e3

import JaCoP.scala._

object exo3 extends jacop {
	val I1 = 0;val G1 = 1;            /*1.1*/
	val I2 = 2;val A1 = 3 ;           /*1.2*/
	val I3 = 4;val P1 = 5;val A2 = 6; /*2.1*/
	val I4 = 7;val G2 = 8;            /*2.2*/
	val I5 = 9;val D = 10;val A3 = 11;/*3.1*/
	val P2 = 12;                      /*3.2*/

	val i1 = BoolVar("Info1");val g1 = BoolVar("Gestion1");
	val i2 = BoolVar("Info2");val a1 = BoolVar("Algo1");
	val i3 = BoolVar("Info3");val p1 = BoolVar("Projet1"); val a2 = BoolVar("Algo2");
	val i4 = BoolVar("Info4");val g2 = BoolVar("Gestion2");
	val i5 = BoolVar("Info5");val d = BoolVar("Droit");    val a3 = BoolVar("Algo3");
	val p2 = BoolVar("Projet2");

	val tous_les_cours = List(I1,G1,I2,A1,I3,P1,A2,I4,G2,I5,D,A3,P2);
	val corequis_de = List(
			List(), 
			List(), 
			List(I1),
			List(),
			List(),
			List(),
			List(),
			List(I3),
			List(),
			List(),
			List(),
			List(),
			List(I5,D,A3));
	val prerequis= List(
			List(), 
			List(),
			List(), 
			List(),
			List(I1,I2),
			List(),
			List(A1),
			List(I1,I2),
			List(G1),
			List(I3,I4),
			List(),
			List(A1,A2),
			List(I1,G1,I2,A1,I3,P1,A2,I4,G2));
	val prerequisVersCorequis= List(
			List(), 
			List(),
			List(), 
			List(),
			List(i1/\i3,i2/\i3),
			List(),
			List(a1/\a2),
			List(i1/\i4,i2/\i4),
			List(g1/\g2),
			List(i3/\i5,i4/\i5),
			List(),
			List(a1/\a3,a2/\a3),
			List(i1/\p2,g1/\p2,i2/\p2,a1/\p2,i3/\p2,p1/\p2,a2/\p2,i4/\p2,g2/\p2));
	val bloc1 = List(I1,G1,I2,A1);
	val bloc2= List(I3,P1,A2,I4,G2);
	val noms = List("Info1","Gestion1","Info2","Algo1","Info3","Projet1","Algo2","Info4","Gestion2","Info5","Droit","Algo3","Projet2");
	val vars = List(i1, g1, i2, a1, i3, p1, a2, i4, g2, i5, d, a3, p2);
	val pondere = List(2*i1, g1, 2*i2, a1, i3, p1, a2, 2*i4, g2, i5, d, a3, 3*p2);
	val credits_c = List(2,1,2,1,1,1,1,2,1,1,1,1,3);
	val block1 = List(g1, p1);
	val block2 = List(g2, p2);

	val infractionP = sum(prerequisVersCorequis.flatten);		
	val infractionMoins = 6-sum(pondere);
	val infractionTrop = sum(pondere)-6;
	val infractionECTS = IntVar("ECTS",0,30);
	val infractions = infractionP + infractionECTS;
	val etudiant1 = List(true,true,true,true,false,false,false,false,false,false,false,false,false);
	val etudiant2 = List(true,true,true,true,true,false,false,true,false,true,false,false,false);
	val etudiant3 = List(false,false,false,false,false,false,false,false,false,false,false,false,false);

	def resolve(credits: Int, etudiant: List[Boolean]): Unit = {
			if(credits<5){
				for (cours <- bloc1)
					if(!etudiant(cours))
						print(noms(cours) + " ");	
				println();
				return;
			}

			for( cours <- tous_les_cours ){
				if(etudiant(cours)){
					vars(cours)#=0;
				};
				for (requis <- corequis_de(cours)){
					if (!etudiant(requis)){
						vars(cours)->(vars(requis)#=1);
					}
				};
				for(requis <- prerequis(cours))
					if (!etudiant(requis)){
						vars(cours)->(vars(requis)#=1);
					}
			};
			sum(pondere)>5;
			max(List(10*infractionMoins, 7*infractionTrop), infractionECTS);
			val result = minimize(search(vars,smallest, indomain_max),infractions,printSol);
			if(!result)print("No Result");
	}
	def main(args: Array[String]): Unit = {
			val ects = for((cours,i) <- etudiant3.view.zipWithIndex if(cours))yield credits_c(i);
			resolve(ects.foldRight(0)((x,y)=>x+y), etudiant3);
	}

	def printSol(): Unit ={
			println("With Cost: " + infractions.value+ "\n==============")
			for (cours <- vars if (cours.value()==1))
				print(cours.id()+" ");
			println("\n")
	}
}