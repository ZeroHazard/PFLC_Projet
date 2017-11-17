package e1

import JaCoP.scala._
import scala.reflect.ClassManifestFactory.classType

object exo1 extends jacop{
val I1 = 0;val G1 = 1;val I2 = 2; val A1 = 3 ;
val I3 = 4;val P1 = 5;val A2 = 6; val I4 = 7; val G2 = 8;
val I5 = 9;val D = 10;val A3 = 11;val P2 = 12;

val i1 = BoolVar("Info1");val g1 = BoolVar("Gestion1");val i2 = BoolVar("Info2");val a1 = BoolVar("Algo1");
val i3 = BoolVar("Info3");val p1 = BoolVar("Projet1");val a2 = BoolVar("Algo2");val i4 = BoolVar("Info4");val g2 = BoolVar("Gestion2");
val i5 = BoolVar("Info5");val d = BoolVar("Droit");val a3 = BoolVar("Algo3");val p2 = BoolVar("Projet2");

val tous_les_cours = List(I1,G1,I2,A1,I3,P1,A2,I4,G2,I5,D,A3,P2)
val bloc1 = List(I1,G1,I2,A1);val bloc2= List(I3,P1,A2,I4,G2);val bloc3= List(I5,D,A3,P2);
val noms = List("Info1","Gestion1","Info2","Algo1","Info3","Projet1","Algo2","Info4","Gestion2","Info5","Droit","Algo3","Algo3","Projet2")
val corequis = List(List(),List(),List(I1),List(),List(I2),List(),List(A1),List(I4),List(G1),List(I4),List(),List(A2),tous_les_cours);

def main(args: Array[String]): Unit = {
	val etudiant1 = List.fill(13)(0)
	resolve(0,etudiant1)
/*	val etudiant2 = bloc1 
	val etudiant3 = List(bloc1,bloc2).flatten
	val etudiant4 = List(I1,G1,I2)
	val etudiant5 = List(I1,G1,I2,A1,I3,P1,A2,I4,I5,D,A3)
	val etudiant6 = List(I1,G1,I2,A1,I3,P1,I5)*/
}
def printSol(pae : List[Int]): Unit = {
	for (x <- pae) print(x)
	println()
}
def resolve(credits: Int, etudiant: List[Int]): Unit = {
	val vars = List(i1, g1, i2, a1, i3, p1, a2, i4, g2, i5, d, a3, p2);
	val block1 = List(g1, p1);
	val block2 = List(g2, p2);

	/*if(credits == 0) 
		printSol(bloc1)
	else */if(credits<5){
	  for (cours <- tous_les_cours)
	    if(cours <= bloc1.last)
	      if(etudiant(cours)==0)
		      System.out.print(noms(cours) + " ")
	  System.out.println();
	  return;
	} else if(credits<12){
	  sum(vars)#=6;
	}
	sum(block1)#<2;
	sum(block2)#<2;
	for( cours <- etudiant ) vars(cours)#=0;
}
}
