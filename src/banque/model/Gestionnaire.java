package banque.model;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * @author Jean-Marc
 * @version V1.0
 *
 */
public class Gestionnaire extends Personne {

	private String numMat;
	private TreeSet<Client> clients;
	private final String SEP = "----------------------------------------------";
	private static String URL = "./assets/clients.xml";
	
	public Gestionnaire(String prenom) {
		super(prenom);
		this.numMat = genererNumMat();
		this.clients = new TreeSet<Client>(new TrierPersonne());	// La liste des clients par ordre alphab�tique est tri�e automatiquement
	}

	/*
	 * Cr�ation d'un nouveau client
	 * @param id : num�ro � 6 chiffres, sans lettre (g�n�r�es automatiquement en fonction du pr�nom)
     * @param prenom 
     * @param age 
     * @param genre (femme ou homme)
	 */
	public void createClient(String prenom, String id, int age, String genre) {
		// Cr�ation puis ajout du client dans la liste de type TreeSet "clients"
		Client nouveauClient = new Client(prenom, id, age, genre, this);
		clients.add(nouveauClient);
	};
	
	/*
	 * Cr�dite un client d'un montant
	 * @param cl Le client � cr�diter
	 * @param numCompte Le num�ro du compte � cr�diter
	 * @param montant Le montant � cr�diter
	 */
	public void crediter(Client cl, String numCompte, float montant) {
		cl.crediter(numCompte, montant);
	}
	
	/*
	 * D�bite un client d'un montant
	 * @param cl Le client � cr�diter
	 * @param numCompte Le num�ro du compte � d�biter
	 * @param montant Le montant � d�biter
	 */
	public void debiter(Client cl, String numCompte, float montant) {
		cl.debiter(numCompte, montant);
	}
	
	/*
	 * @return Le nombre d'objet Client dans la collection
	 */
	public int denombrerClient() {
		return clients.size();
	}
	
	/*
	 * G�n�ration d'un num�ro matricule al�atoire compos� d'un pr�fixe GEST
	 * et d'un nombre � 3 chiffres entre 100 et 999:
	 */
	private String genererNumMat() {
		int num = 100 + (int)(Math.random() * ((999 - 100) + 1));
		return "GEST" + num;
	}
	
	/*
	 * @return L'objet collection de clients du banquier
	 */
	public TreeSet<Client> getClients() {
		return clients;
	}
	
	/**
	 * @return Le num�ro de matricule du banquier
	 */
	public String getNumMat() {
		return numMat;
	}

	/*
	 * Affiche la liste et le nombre des clients g�r�s par le gestionnaire
	 */
	public void listerClient() {
		System.out.println("Les clients g�r�s par le gestionnaire " + this.getPrenom() + " (matricule " + this.numMat + ") (" + denombrerClient() + " client(s))");
		System.out.println(SEP);
		for (Client client: clients) {
			System.out.println("  CLIENT : " + client.getPrenom() + " (id = " + client.getIdClient() + ")");
			System.out.println("  Age : " + client.getAge());
			System.out.println("  Genre : " + client.getGenre());
			System.out.println("  (Banquier  " + client.getBanquier().getPrenom() + ")");
			System.out.println(SEP);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see banque.model.Personne#listerCompte()
	 */
	public void listerCompte() {
		// Utiliser l'objet Iterator
		Iterator<Client> itrClie = clients.iterator();
		System.out.println("");
		System.out.println("Liste des comptes suivis par le banquier " + this.getPrenom() + " :");
		while(itrClie.hasNext()) {
		     System.out.println(SEP);
			 Client client = itrClie.next();
			 System.out.println("  CLIENT : " + client.getPrenom() + " (id = " + client.getIdClient() + ")");
			 client.listerCompte();
			 //System.out.println(SEP);
		 }
	}
	
	/**
	 * @return la liste de tous les comptes clients g�r�s par le gestionnaire. Null si le gestionnaire n'a pas de clients
	 */
	public ArrayList<Compte> getListeComptes() {
		if ( clients.size() != 0 ) {									// Si le gestionnaire a des clients
			ArrayList<Compte> cptListeGest = new ArrayList<Compte>();	// Liste des comptes g�r�s par le gestionnaire
			ArrayList<Compte> cptListeClie = new ArrayList<Compte>();	// Liste des comptes d'un client
			Iterator<Client> itrClie = clients.iterator();				// It�rateur pour lister les clients du gestionnaire
			while(itrClie.hasNext()) {									// Pour chaque client du gestionnaire
				Client client = itrClie.next();
				if ( client.getnbCompte() != 0 ) {						// Si le client a des comptes
					cptListeClie = client.getListeComptes();			// R�cup�rer la liste de ses comptes
					Iterator<Compte> itrCpt = cptListeClie.iterator();	// It�rateur pour lister les comptes du client
					while(itrCpt.hasNext()) {							// pour chaque compte du client
						cptListeGest.add(itrCpt.next()); 				// Ajouter le compte � la liste des comptes du gestionnaire
					}
				}
			}
			return cptListeGest;										// Retourne la liste de tous les comptes du gestionnaire
		}
		return null;													// Retourne null si le gestionnaire n'a pas de client
	}
		
	/**
	 * Transf�re un montant d'un client � l'autre
	 * @param cliDeb Le client � d�biter
	 * @param numCpteDebit Le num�ro de compte � d�biter
	 * @param cliCre Le client � cr�diter
	 * @param numCpteCredit Le num�ro du compte � cr�diter
	 * @param montant Le montant � transf�rer
	 */
	public void transferer(Client cliDeb, String numCpteDebit, Client cliCre, String numCpteCredit, float montant) {
		cliDeb.transferer(numCpteDebit, cliCre, numCpteCredit, montant);
	}

	/**
	 * @return L'ensemble des patrimoines des clients
	 */
	public float getCA() {
		
		Iterator<Client> itr = clients.iterator();
		float total = 0f;
		
		while(itr.hasNext()) {
			Client client = itr.next();
			total += getPatrimoine(client);
		}

		System.out.format("Patrimone de %s : %f � \n", getPrenom(), total);
		return total;
	}
	
	/**
	 * Retourne le patrimone d'un client
	 * @param client Le client dont on veut retourner le patrimoine
	 * @return La somme des soldes des comptes d'un client
	 */
	public float getPatrimoine(Client client) {
		System.out.format("Patrimone de %s : %f � \n", client.getPrenom(), client.getPatrimoine());
		return client.getPatrimoine();
	}
	
	/**
	 * Ecrit dans un fichier les donn�es pass�es en param�tre
	 * @param datas Les donn�es � �crire
	 */
	public void sauverClientele(String datas) {
       File fichier =new File(URL); 
       
       
       try {
    	   // Creation du fichier
    	   fichier.createNewFile();
    	   // creation d'un writer
    	   FileWriter writer = new FileWriter(fichier);
    	   try {
    		   writer.write(datas);
    		   System.out.println("Fichier " + URL + " sauvegard�.");
    	   } 
    	   finally {
    	   writer.close();  // Fermeture du fichier
    	   }
		} 
       	catch (Exception e) {
		System.out.println("Erreur dans la cr�ation du le fichier");
		System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Retourne une chaine de caract�res contenant les donn�es � sauvegarder dans un fichier
	 * @return Les donn�es � sauvegarder dans un fichier
	 */
	public String getDonneesClients() {
		Iterator<Client> itr = clients.iterator();
		//Date date = new Date();
		Calendar c = Calendar.getInstance();
		
		String donnees = "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>\n";
		donnees += "<?xml-stylesheet href=\"look.xsl\" type=\"text/xsl\"?>\n";
		donnees += "<!-- Sauvegarde de la clientele du banquier " + this.getPrenom() + " -->\n";
		donnees += "<!-- Enregistr� le " + c.getTime() + " -->\n";
		donnees += "<gestionnaire nom=\"" + this.getPrenom() + "\">\n";
		while(itr.hasNext()) {																		// It�ration sur les clients
			   Client client = itr.next();
			   String s = "";
			   s = "    <client>\n";
			   s += "        <nom>" + client.getPrenom() + "</nom>\n";
			   s += "        <id>" + client.getIdClient() + "</id>\n";
			   s += "        <age>" + client.getAge() + "</age>\n";
			   ArrayList<Compte> comptes = client.getListeComptes();
			   if (comptes.size() != 0) {															// It�ration sur les comptes s'ils existent
				   Iterator<Compte> itrCompte = comptes.iterator();
				   s += "        <comptes>\n";
				   while(itrCompte.hasNext()) {
					   Compte compte = itrCompte.next();
					   s += "             <compte no=\"" + compte.getNumCompte() + "\">\n";
					   s += "                  <type>" + compte.getType() + "</type>\n";
					   s += "                  <solde>" + compte.getSolde() + "</solde>\n";
					   s += "             </compte>\n";
				   }
				   s += "        </comptes>\n";
			   }
			   s += "    </client>\n";
			   donnees += s;
		   }
		donnees += "</gestionnaire>\n";
		
		return donnees;
	}
}
