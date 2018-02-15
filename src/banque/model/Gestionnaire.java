package banque.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class Gestionnaire extends Personne {

	private String numMat;
	private TreeSet<Client> clients;
	private final String SEP = "----------------------------------------------";
	
	public Gestionnaire(String prenom) {
		super(prenom);
		this.numMat = genererNumMat();
		this.clients = new TreeSet<Client>();
	}

	/*
	 * Création d'un nouveau client
	 * @param id : numéro à 6 chiffres, sans lettre (générées automatiquement en fonction du prénom)
     * @param prenom 
     * @param age 
     * @param genre (femme ou homme)
	 */
	public void createClient(String prenom, String id, int age, String genre) {
		// Création puis ajout du client dans la liste de type TreeSet "clients"
		Client nouveauClient = new Client(prenom, id, age, genre, this);
		clients.add(nouveauClient);
	};
	
	/*
	 * Crédite un client d'un montant
	 * @param cl Le client à créditer
	 * @param numCompte Le numéro du compte à créditer
	 * @param montant Le montant à créditer
	 */
	public void crediter(Client cl, String numCompte, float montant) {
		cl.crediter(numCompte, montant);
	}
	
	/*
	 * Débite un client d'un montant
	 * @param cl Le client à créditer
	 * @param numCompte Le numéro du compte à débiter
	 * @param montant Le montant à débiter
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
	 * Génération d'un numéro matricule aléatoire composé d'un préfixe GEST
	 * et d'un nombre à 3 chiffres entre 100 et 999:
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
	 * @return Le numéro de matricule du banquier
	 */
	public String getNumMat() {
		return numMat;
	}

	/*
	 * Affiche la liste et le nombre des clients gérés par le gestionnaire
	 */
	public void listerClient() {
		System.out.println("Les clients gérés par le gestionnaire " + this.getPrenom() + " (matricule " + this.numMat + ") (" + denombrerClient() + " client(s))");
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
	 * @return la liste de tous les comptes clients gérés par le gestionnaire. Null si le gestionnaire n'a pas de clients
	 */
	public ArrayList<Compte> getListeComptes() {
		if ( clients.size() != 0 ) {									// Si le gestionnaire a des clients
			ArrayList<Compte> cptListeGest = new ArrayList<Compte>();	// Liste des comptes gérés par le gestionnaire
			ArrayList<Compte> cptListeClie = new ArrayList<Compte>();	// Liste des comptes d'un client
			Iterator<Client> itrClie = clients.iterator();				// Itérateur pour lister les clients du gestionnaire
			while(itrClie.hasNext()) {									// Pour chaque client du gestionnaire
				Client client = itrClie.next();
				if ( client.getnbCompte() != 0 ) {						// Si le client a des comptes
					cptListeClie = client.getListeComptes();			// Récupérer la liste de ses comptes
					Iterator<Compte> itrCpt = cptListeClie.iterator();	// Itérateur pour lister les comptes du client
					while(itrCpt.hasNext()) {							// pour chaque compte du client
						cptListeGest.add(itrCpt.next()); 				// Ajouter le compte à la liste des comptes du gestionnaire
					}
				}
			}
			return cptListeGest;										// Retourne la liste de tous les comptes du gestionnaire
		}
		return null;													// Retourne null si le gestionnaire n'a pas de client
	}
		
	/**
	 * Transfère un montant d'un client à l'autre
	 * @param cliDeb Le client à débiter
	 * @param numCpteDebit Le numéro de compte à débiter
	 * @param cliCre Le client à créditer
	 * @param numCpteCredit Le numéro du compte à créditer
	 * @param montant Le montant à transférer
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

		System.out.format("Patrimone de %s : %f € \n", getPrenom(), total);
		return total;
	}
	
	/**
	 * @return La somme des soldes des comptes d'un client
	 */
	public float getPatrimoine(Client client) {
		System.out.format("Patrimone de %s : %f € \n", client.getPrenom(), client.getPatrimoine());
		return client.getPatrimoine();
	}
	
	public void sauverClientele() {
		// TODO Implémenter la sauvegarde des clients : voir API FileIO...
	}
}
