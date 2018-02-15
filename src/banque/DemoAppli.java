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
		// Création d'un gestionnaire
		banquier  = new Gestionnaire("Rothchild");
		
		// Création de clients par le banquier :
        String url="./assets/bankCustomers.txt";
        creerClientParFichier(banquier, url);
        
        // Créer entre 1 et 3 comptes par clients
        creerDesComptesPourLesClientsDuBanquier(banquier);
        
	}
/*	
	
	 * Initialise l'application en créant un banquier et des clients
	 
	private static void initAppliTest() {
		// Création d'un gestionnaire
        banquier  = new Gestionnaire("Rothchild");
        System.out.print(banquier.getNumMat() + " : ");
        System.out.println(banquier.getPrenom());
        
        // Création de clients par le banquier :
        String url="./assets/bankCustomers.txt";
        creerClientParFichier(banquier, url);
            
        // Créer entre 1 et 3 comptes par clients
        creerDesComptesPourLesClientsDuBanquier(banquier);
        
        // Lister les clients gérés par le banquier
        banquier.listerClient();
        
        // Lister tous les comptes des clients gérés par un gestionnaire
        banquier.listerCompte();
        
        // Test débiter, créditer, transférer
        //testOperations(banquier);
        
        // Affiche le chiffre d'affaire du banquier
        System.out.println(banquier.getCA()); 
	}

	*/
	
/*	
	 * Test les opérations créditer, débiter et transférer pour un client et un banquier
	 
	private static void testOperations(Gestionnaire banquier) {
		System.out.println("");
		System.out.println("------------------------------");
		// Création de de clients
		Client cl1 = new Client("Luc", "100000", 24, "homme", banquier);
		Client cl2 = new Client("Maelys", "999999", 23, "femme", banquier);
		Compte cpte1 = cl1.ajouterCompte("debit", 500f);
		Compte cpte2 = cl2.ajouterCompte("debit", 700f);
		String numCpte1 = cpte1.getNumCompte();
		String numCpte2 = cpte2.getNumCompte();
		System.out.println("Solde du compte client 1 : " + cpte1.getSolde() );
		System.out.println("Solde du compte client 2 : " + cpte2.getSolde() );
		System.out.println("Crédite client 1 de 500€ : ");
		cl1.crediter(numCpte1, 500f);
		System.out.println("Solde du compte client 1 : " + cpte1.getSolde() );
		
		System.out.println("Débite client 2 de 200€ : ");
		cl2.debiter(numCpte1, 200f);
		System.out.println("Solde du compte client 2 : " + cpte2.getSolde() );
		
		System.out.println("Transfere 500€ de client 1 à client 2 : ");
		cl1.transferer(numCpte1, cl2, numCpte2, 200f);
		System.out.println("Solde du compte client 1 : " + cpte1.getSolde() );
		System.out.println("Solde du compte client 2 : " + cpte2.getSolde() );
		
		System.out.println("------------------------------");
		
		System.out.println("Le banquier débite le client 1 de 500€ : ");
		banquier.debiter(cl1, numCpte1, 500);
		System.out.println("Solde du compte client 1 : " + cpte1.getSolde() );
		
		System.out.println("Le banquier crédite le client 2 de 500€ : ");
		banquier.crediter(cl2, numCpte2, 500);
		System.out.println("Solde du compte client 2 : " + cpte2.getSolde() );
		
		System.out.println("Le banquier transfère 500€ du client 2 au le client 1 : ");
		banquier.transferer(cl1, numCpte1, cl2, numCpte2, 500);
		System.out.println("Solde du compte client 1 : " + cpte1.getSolde() );
		System.out.println("Solde du compte client 2 : " + cpte2.getSolde() );	
	}

*/
	
	/*
	 * Créer un nombre aléatoire (entre 1 et 3) de compte pour chaque client du banquier donné en paramètre
	 * Cahier des charges :
	 *  - Les comptes seront de type compte de dépôt ou compte de placement
	 *  - Tous les comptes d’un même client peuvent etre de même type.
	 *  - Attribuer 0 à 3 comptes par client.
	 *  - Au moins un client sans aucun compte
	 *  - Maxi 3 clients sans aucun compte.
	 *  - Solde de départ compris entre -1.000,00€ et 100.000,00 € à chaque compte.
	 * @param banquier : l'objet Gestionnaire contenant les clients dont les comptes sont à créer
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
				// Calcul du solde départ
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
	 * @return Le type de compte (dépot ou placement) aléatoirement
	 */
	private static String typeDeCompte() {
		int typeCpte = (int) (Math.random()*2);	// Nombre 0 ou 1
		return (typeCpte == 0 ? "Dépot" : "Placement");
	}
	
	/*
	 * Calcule le nombre de compte par client en respectant les conditions suivantes :
	 * - Attribuer 0 à 3 comptes par client.
	 * - Au moins un client sans aucun compte
	 * - Maxi 3 clients sans aucun compte.
	 * @return : un tableau de la même longueur que la collection de clients contenant le nombre aléatoire
	 * 			 de compte pour chaque client
	 */
	private static int[] calculNbCompteParClient(Gestionnaire banquier) {
		
		// Créer un tableau de la taille du nombre de clients dans la collection
		int[] tab = new int[banquier.denombrerClient()];
		// Remplir le tableau d'un nombre aléatoire compris entre 1 et 3
		int i;
		for ( i=0; i<tab.length; i++ ) {
			tab[i] = (int) (Math.random()*3) +1; 
		}
		// Déterminer le nombre de client sans compte (entre 0 et 4)
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
	 * Créer des nouveaux clients à partir d'une liste écrite dans un fichier texte
	 * @param banquier : l'objet Gestionnaire contenant les clients à créer
	 * @param url : chemin d'accès au fichier des données
	 */
	private static void creerClientParFichier(Gestionnaire banquier, String url) {
		File fn = new File(url);
		final String SEPARATEUR = "\\*"; 		// \\* : On échappe l'échappement du symbole * dans le regExp.
		
		try {
			FileReader fr = new FileReader(fn);
			BufferedReader br = new BufferedReader(fr);
			// Extraire chaine utile des lignes du fichier plat
			String tmp;
			while ((tmp = br.readLine()) != null) {
				// Split de la chaine dans un tableau en fonction du séparateur
				String[] tabTmp = tmp.split(SEPARATEUR);
				banquier.createClient(tabTmp[0], tabTmp[1], Integer.parseInt(tabTmp[2]), tabTmp[3]);
			}
			// Liberer les ressources READER après usage
			br.close();
			fr.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println(" ERROR Fichier non TROUVÉ ");
		} catch (IOException e) {
			System.out.println(" ERROR acces Reader ");
			e.printStackTrace();
		}
	}
	
	
}
