package e7
trait UE {
  //Cours listés en tuples structurés comme suit (Intitulé,Identifiant,Q1,Q2,Corequis,Prérequis)
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
  ("Intégration en milieu professionnel","I3080",                      0,26,  List(31,34),       Nil))
}