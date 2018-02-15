package banque.model;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * @author Jean-Marc SINQUIN
 *
 */
public class Client extends Personne implements Comparable<Client> {

	/*
	 * Création du bigramme en fonction du prénom :
	 * Prends les 2 première voyelles contenues dans le prénom et les inverse.
	 * S'il n'y a pas de voyelles dans le prénom, retourne AA
	 * S'il n'y a qu'une voyelle, retourne la voyelle doublée (ex Luc -> UU)
	 */
	private static String bigramme(String prenom) {
		String regExp = "[^AEIOUY]";
		String bg;
		char[] tabPrenom = prenom.toUpperCase().replaceAll(regExp, "").toCharArray();
		switch (tabPrenom.length) {
		case 0:
			bg = "AA";
			break;
		case 1:
			bg = Character.toString(tabPrenom[0]) + Character.toString(tabPrenom[0]);
			break;
		default:
			bg = Character.toString(tabPrenom[1]) + Character.toString(tabPrenom[0]);
			break;
		}
		return bg;
	}
	
	// Variables de classe :
	private String idClient;
	private int age;
	private String genre;
	private Gestionnaire banquier;
	private ArrayList<Compte> comptes;

	/*
	 *  Constructeur
	 *  @param prenom : le prénom du client
	 *  @param id : le numéro d'identification du client (6 chiffres sans le bigramme)
	 *  @param age : l'age du client
	 *  @param genre : le genre du client (homme / femme)
	 *  @param banquier : le gestionnaire du compte
	 */
	 public Client(String prenom, String id, int age, String genre, Gestionnaire banquier) {
		 super(prenom);
		 this.idClient = id + bigramme(prenom);
		 this.age = age;
		 this.genre = genre;
		 this.banquier = banquier;
		 this.comptes = new ArrayList<Compte>();
	 }
	
	/*
	 * Comparaison des objets Client sur l'idClient : tri de la collection par idClient
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
    public int compareTo(Client cli) {
       if (this.idClient.compareTo(cli.getIdClient()) == 0) {
           return 0;
       } else if ( this.idClient.compareTo(cli.getIdClient()) < 0 ) {
           return -1;
       }
       return 1;
    }
	
	/*
	 * Ajouter un compte au client
	 * @param  typeCpte : type du compte (dépot / placement)
	 * @param solde : le solde du compte
	 */
	public Compte ajouterCompte(String typeCpte, float solde) {
		Compte cpte = new Compte(typeCpte, solde);
		comptes.add(cpte);
		return cpte;
	}
		
	/*
	 * @return L'age du client
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @return L'objet banquier
	 */
	public Gestionnaire getBanquier() {
		return banquier;
	}

	/**
	 * @return Le genre du client (homme ou femme)
	 */
	public String getGenre() {
		return genre;
	}

	/*
	 * @return Le numéro unique du client
	 */
	 public String getIdClient() {
		return idClient;
	}

	/*
	 * (non-Javadoc)
	 * @see banque.model.Personne#listerCompte()
	 */
	 public void listerCompte() {
		 // Utiliser l'objet Iterator
		 Iterator<Compte> itr = comptes.iterator();
		 int nbComptes = getnbCompte();
		 
		 if (nbComptes != 0) {
			 System.out.println("  Ce client possède " + nbComptes + " comptes(s) :");
			 final String SEP = "   --------------------";
			 
			 while(itr.hasNext()) {
			     System.out.println(SEP);
				 Compte compte = itr.next();
			     System.out.println("   -> Type  :" + compte.getType());
			     System.out.println("   -> Solde :" + compte.getSolde() + "€");
			 }
		 } else {
			 System.out.println("  Aucun compte pour ce client");
		 }
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @param banquier the banquier to set
	 */
	public void setBanquier(Gestionnaire banquier) {
		this.banquier = banquier;
	};
	
	/**
	 * @param genre the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	/**
	 * @return Le nombre de compte du client
	 */
	public int getnbCompte() {
		return comptes.size();
	}
	
	
	/**
	 * @param numCompte
	 * @param montant
	 */
	public void crediter(String numCompte, float montant) {
		updateSolde(numCompte, montant);
	}
	
	/**
	 * @param numCompte
	 * @param montant
	 */
	public void debiter(String numCompte, float montant) {
		montant = ( montant<0 ? -montant : montant);
		updateSolde(numCompte, -montant);
	}
	
	/**
	 * @param numCpteDebit 
	 * @param crediteur
	 * @param numCpteCredit
	 * @param montant
	 */
	public void transferer(String numCpteDebit, Client crediteur, String numCpteCredit, float montant) {
		updateSolde(numCpteDebit, -montant);
		crediteur.crediter(numCpteCredit, montant);
	}
	
	/**
	 * @param numCompte Numéro de compte
	 * @param montant 
	 */
	private void updateSolde(String numCompte, float montant) {
		try {
			Compte cpte = getCompteFromIdCompte(numCompte);
			float total = cpte.getSolde() + montant;
			setSoldeCompte(cpte, total);
		} catch (Exception e) {
			System.out.println("Une erreur est survenue : " + e.toString());
		}
	}
	
	/**
	 * @param cpte
	 * @param montant
	 */
	public void setSoldeCompte(Compte cpte, float montant) {
		cpte.setSolde(montant);
	}
	
	/*
	 * @return un objet compte à partir de son numéro (null si pas trouvé)
	 */
	public Compte getCompteFromIdCompte(String idCompte) {
		Iterator<Compte> itr = comptes.iterator();
		while(itr.hasNext()) {
			 Compte compte = itr.next();
		     if (idCompte.equals(compte.getNumCompte()));
		     return compte;
		 }
		return null;
	}

	/**
	 * @return La somme des soldes des comptes du client
	 */
	public float getPatrimoine() {
		
		//int nbComptes = getnbCompte();
		 
		//if (nbComptes != 0) {
			float total =0f;
			Iterator<Compte> itr = comptes.iterator();
			while(itr.hasNext()) {
				Compte compte = itr.next();
				total += compte.getSolde();
			}
			
		//}
		return total;
	}
	
	
	public ArrayList<Compte> getListeComptes() {
		return comptes;
	}
	
	
}
