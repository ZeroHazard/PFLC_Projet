package e1

import JaCoP.scala._
import scala.reflect.ClassManifestFactory.classType
import scala.util.control.Breaks._
object exo12 extends jacop{
val I1 = 2;val I2 = 4; val I3 = 5;val I4 = 7;val I5 = 8;
val A1 = 1 ;val A2 = 2;val A3 = 3;
val G1 = 1;  val G2 = 2;
val P1 = 1;val P2 = 4;
val D = 1;


val Info = List(I1,I2,I3,I4,I5) ; val InfoNames = List("I1","I2","I3","I4","I5") ; val InfoCredits = List(2,2,1,2,1); 
val Gestion = List(G1,G2);        val GestionNames = List("G1","G2");              val GestionCredits = List(1,2); 
val Algo = List(A1,A2,A3);        val AlgoNames = List("A1","A2","A3");            val AlgoCredits = List(1,1,1); 
val Projet = List(P1,P2);         val ProjetNames = List("P1","P2");               val ProjetCredits = List(1,3); 
val Droit = D;                    val DroitCredits = 1; 

val q1=List(I1,G1,0,0,0);       val q2=List(I2,0,A1,0,0);
val q3=List(I3,0,A2,0,0);       val q4=List(I4,G2,0,P1,0);
val q5=List(I5,0,A3,0,D);       val q6=List(0,0,0,P2,0);

def resolveCredits(etudiant: List[Int]): Int = {
  var accumulateur = 0;
  	  for((m,i) <- etudiant.view.zipWithIndex){
  	        if(m>0)
      	        if(i==0 )accumulateur+=Info(Info.indexOf(m))
      	        else if(i==1)accumulateur+=Gestion(Gestion.indexOf(m))
      	        else if(i==2)accumulateur+=Algo(Algo.indexOf(m))
      	        else if(i==3)accumulateur+=Projet(Projet.indexOf(m))
      	        else accumulateur+=1
  	    }
  	  println("credits = "+ accumulateur)
    return accumulateur
}

def resolve(credits: Int, etudiant: List[Int]): Unit = {
	if(credits<5){
	  val qs = List(q1,q2) 
	  resolveInner(qs, etudiant)
	  return;
	} else if(credits<12){
	  val qs = List(q1,q2,q3,q4)
    resolveInner(qs, etudiant)
    return
	} else {
	  val qs = List(q1,q2,q3,q4,q5,q6)
	  resolveInner(qs, etudiant)
	  return
	}
}

def resolveInner(qs : List[List[Int]],etudiant: List[Int]): Unit = {
  println("Cours étudiant :")
  	  for((m,i) <- etudiant.view.zipWithIndex)
    	  breakable {
    	    for(q<-qs)   	      
      	    	if( q(i) > m.value()){
      	        if(i==0)println(InfoNames(Info.indexOf(q(i))))
      	        else if(i==1)println(GestionNames(Gestion.indexOf(q(i))))
      	        else if(i==2)println(AlgoNames(Algo.indexOf(q(i))))
      	        else if(i==3)println(ProjetNames(Projet.indexOf(q(i))))
      	        else println("Droit")
      	      }
  	    }
    println()
}

def main(args: Array[String]): Unit = {
  val e1 = List.fill(5)(0)
	val e2 = List(I2,G1,A1,0,0);
	val e3 = List(I4,G2,A2,P1,0)
	val e4 = List(I2,G1,0,0,0)
	val e5 = List(I3,G1,A3,P1,D)
	val e6 = List(I5,G1,A1,P1,0)
	
	resolve(resolveCredits(e1),e1)
	resolve(resolveCredits(e2),e2);
	resolve(resolveCredits(e3),e3);
	resolve(resolveCredits(e4),e4);
	resolve(resolveCredits(e5),e5);
	resolve(resolveCredits(e6),e6);
}
}
