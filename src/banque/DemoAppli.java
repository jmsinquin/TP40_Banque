package banque;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import banque.controler.ControleurFenetrePpale;
import banque.model.Client;
import banque.model.Gestionnaire;
import banque.view.FenetrePpale;


/**
 * @author OtObOx
 *
 */
public class DemoAppli {

	static Gestionnaire banquier;
	
	public static void main(String[] args) {
//		initAppliTest();
		initAppli();
		FenetrePpale laVue = new FenetrePpale();
		new ControleurFenetrePpale(laVue, banquier); 
	}
	
	private static void initAppli() {
		// Cr�ation d'un gestionnaire
		banquier  = new Gestionnaire("Rothchild");
		
		// Cr�ation de clients par le banquier :
        String url="./assets/bankCustomers.txt";
        creerClientParFichier(banquier, url);
        
        // Cr�er entre 1 et 3 comptes par clients
        creerDesComptesPourLesClientsDuBanquier(banquier);
        
	}
/*	
	
	 * Initialise l'application en cr�ant un banquier et des clients
	 
	private static void initAppliTest() {
		// Cr�ation d'un gestionnaire
        banquier  = new Gestionnaire("Rothchild");
        System.out.print(banquier.getNumMat() + " : ");
        System.out.println(banquier.getPrenom());
        
        // Cr�ation de clients par le banquier :
        String url="./assets/bankCustomers.txt";
        creerClientParFichier(banquier, url);
            
        // Cr�er entre 1 et 3 comptes par clients
        creerDesComptesPourLesClientsDuBanquier(banquier);
        
        // Lister les clients g�r�s par le banquier
        banquier.listerClient();
        
        // Lister tous les comptes des clients g�r�s par un gestionnaire
        banquier.listerCompte();
        
        // Test d�biter, cr�diter, transf�rer
        //testOperations(banquier);
        
        // Affiche le chiffre d'affaire du banquier
        System.out.println(banquier.getCA()); 
	}

	*/
	
/*	
	 * Test les op�rations cr�diter, d�biter et transf�rer pour un client et un banquier
	 
	private static void testOperations(Gestionnaire banquier) {
		System.out.println("");
		System.out.println("------------------------------");
		// Cr�ation de de clients
		Client cl1 = new Client("Luc", "100000", 24, "homme", banquier);
		Client cl2 = new Client("Maelys", "999999", 23, "femme", banquier);
		Compte cpte1 = cl1.ajouterCompte("debit", 500f);
		Compte cpte2 = cl2.ajouterCompte("debit", 700f);
		String numCpte1 = cpte1.getNumCompte();
		String numCpte2 = cpte2.getNumCompte();
		System.out.println("Solde du compte client 1 : " + cpte1.getSolde() );
		System.out.println("Solde du compte client 2 : " + cpte2.getSolde() );
		System.out.println("Cr�dite client 1 de 500� : ");
		cl1.crediter(numCpte1, 500f);
		System.out.println("Solde du compte client 1 : " + cpte1.getSolde() );
		
		System.out.println("D�bite client 2 de 200� : ");
		cl2.debiter(numCpte1, 200f);
		System.out.println("Solde du compte client 2 : " + cpte2.getSolde() );
		
		System.out.println("Transfere 500� de client 1 � client 2 : ");
		cl1.transferer(numCpte1, cl2, numCpte2, 200f);
		System.out.println("Solde du compte client 1 : " + cpte1.getSolde() );
		System.out.println("Solde du compte client 2 : " + cpte2.getSolde() );
		
		System.out.println("------------------------------");
		
		System.out.println("Le banquier d�bite le client 1 de 500� : ");
		banquier.debiter(cl1, numCpte1, 500);
		System.out.println("Solde du compte client 1 : " + cpte1.getSolde() );
		
		System.out.println("Le banquier cr�dite le client 2 de 500� : ");
		banquier.crediter(cl2, numCpte2, 500);
		System.out.println("Solde du compte client 2 : " + cpte2.getSolde() );
		
		System.out.println("Le banquier transf�re 500� du client 2 au le client 1 : ");
		banquier.transferer(cl1, numCpte1, cl2, numCpte2, 500);
		System.out.println("Solde du compte client 1 : " + cpte1.getSolde() );
		System.out.println("Solde du compte client 2 : " + cpte2.getSolde() );	
	}

*/
	
	/*
	 * Cr�er un nombre al�atoire (entre 1 et 3) de compte pour chaque client du banquier donn� en param�tre
	 * Cahier des charges :
	 *  - Les comptes seront de type compte de d�p�t ou compte de placement
	 *  - Tous les comptes d�un m�me client peuvent etre de m�me type.
	 *  - Attribuer 0 � 3 comptes par client.
	 *  - Au moins un client sans aucun compte
	 *  - Maxi 3 clients sans aucun compte.
	 *  - Solde de d�part compris entre -1.000,00� et 100.000,00 � � chaque compte.
	 * @param banquier : l'objet Gestionnaire contenant les clients dont les comptes sont � cr�er
	 */
	private static void creerDesComptesPourLesClientsDuBanquier(Gestionnaire banquier) {
		int[] tabNbComptes;		
		tabNbComptes = calculNbCompteParClient(banquier);		// Calcul le nombre de compte pour la collection client
		int i = 0;
		int nbComptes;
		for (Client client: banquier.getClients()) {
			nbComptes = tabNbComptes[i];
			i++;
			for ( int j=1; j<=nbComptes; j++ ) {
				// Calcul du type de compte
				String typeCpt = typeDeCompte();
				// Calcul du solde d�part
				float soldeDepart = calculSoldeDepart();
				// Ajouter le compte au client courant
				client.ajouterCompte(typeCpt, soldeDepart);
			}
		}
	}
	
	/*
	 * @return Un nombre entre -1000.00 et 100000.00
	 */
	private static float calculSoldeDepart() {
		int i = (int) ((-1000 + Math.random()*99001)*100);
		float ff = (float) (i/100.);
		return ff;
	}
	
	/*
	 * @return Le type de compte (d�pot ou placement) al�atoirement
	 */
	private static String typeDeCompte() {
		int typeCpte = (int) (Math.random()*2);	// Nombre 0 ou 1
		return (typeCpte == 0 ? "D�pot" : "Placement");
	}
	
	/*
	 * Calcule le nombre de compte par client en respectant les conditions suivantes :
	 * - Attribuer 0 � 3 comptes par client.
	 * - Au moins un client sans aucun compte
	 * - Maxi 3 clients sans aucun compte.
	 * @return : un tableau de la m�me longueur que la collection de clients contenant le nombre al�atoire
	 * 			 de compte pour chaque client
	 */
	private static int[] calculNbCompteParClient(Gestionnaire banquier) {
		
		// Cr�er un tableau de la taille du nombre de clients dans la collection
		int[] tab = new int[banquier.denombrerClient()];
		// Remplir le tableau d'un nombre al�atoire compris entre 1 et 3
		int i;
		for ( i=0; i<tab.length; i++ ) {
			tab[i] = (int) (Math.random()*3) +1; 
		}
		// D�terminer le nombre de client sans compte (entre 0 et 4)
		int cliSansCompte = (int) (Math.random()*3) + 1;
		i=1;
		while (i <= cliSansCompte) {
			// Tirer un indice du tableau au hasard
			int indice = (int) (Math.random()*tab.length);
			tab[indice]=0;
			i++;
		}

		return tab;
	}
	
	/*
	 * Cr�er des nouveaux clients � partir d'une liste �crite dans un fichier texte
	 * @param banquier : l'objet Gestionnaire contenant les clients � cr�er
	 * @param url : chemin d'acc�s au fichier des donn�es
	 */
	private static void creerClientParFichier(Gestionnaire banquier, String url) {
		File fn = new File(url);
		final String SEPARATEUR = "\\*"; 		// \\* : On �chappe l'�chappement du symbole * dans le regExp.
		
		try {
			FileReader fr = new FileReader(fn);
			BufferedReader br = new BufferedReader(fr);
			// Extraire chaine utile des lignes du fichier plat
			String tmp;
			while ((tmp = br.readLine()) != null) {
				// Split de la chaine dans un tableau en fonction du s�parateur
				String[] tabTmp = tmp.split(SEPARATEUR);
				banquier.createClient(tabTmp[0], tabTmp[1], Integer.parseInt(tabTmp[2]), tabTmp[3]);
			}
			// Liberer les ressources READER apr�s usage
			br.close();
			fr.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println(" ERROR Fichier non TROUV� ");
		} catch (IOException e) {
			System.out.println(" ERROR acces Reader ");
			e.printStackTrace();
		}
	}
	
	
}
