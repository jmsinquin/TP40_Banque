package banque.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;


/**
 * @author Jean-Marc SINQUIN
 * @version V1.0
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
	 * Crédite ce client d'une somme
	 * @param numCompte Numéro du compte à créditer
	 * @param montant Montant à créditer
	 */
	public void crediter(String numCompte, float montant) {
		updateSolde(numCompte, montant);
	}
	
	/**
	 * Débite ce client d'un somme
	 * @param numCompte Numéro du compte à débiter
	 * @param montant Montant à débiter
	 */
	public void debiter(String numCompte, float montant) {
		montant = ( montant<0 ? -montant : montant);
		updateSolde(numCompte, -montant);
	}
	
	/**
	 * Tranfère une somme d'un compte de ce client vers un compte d'un autre client
	 * @param numCpteDebit Le compte de ce client à débiter
	 * @param crediteur Le client à créditer
	 * @param numCpteCredit Le compte du client à créditer
	 * @param montant Montant à transférer
	 */
	public void transferer(String numCpteDebit, Client crediteur, String numCpteCredit, float montant) {
		updateSolde(numCpteDebit, -montant);
		crediteur.crediter(numCpteCredit, montant);
	}
	
	/**
	 * Mise à jour du solde d'un compte de ce client
	 * @param numCompte Numéro de compte à mettre à jour
	 * @param montant  Montant à créditer/débiter
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
	 * Crédite/débite un compte de ce client
	 * @param cpte Le compte à créditer/débiter
	 * @param montant Le montant à créditer/débiter
	 */
	public void setSoldeCompte(Compte cpte, float montant) {
		cpte.setSolde(montant);
	}
	
	/*
	 * Retourne un compte à partir de son numéro
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
	 * Donne le patrimoine de ce client
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
	
	
	/**
	 * Donne le patrimoine de ce client au format monaitaire francais
	 * @return Le patrimoine du client formaté
	 */
	public String getPatrimoineToString() {
		float total = getPatrimoine();
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);	// Format du nombre suivant standard FR
		DecimalFormat df = (DecimalFormat)nf;
		df.applyPattern("###,###.00 €");								// Formatage de l'affichage du solde
		return df.format(total);	
	}
	
	public ArrayList<Compte> getListeComptes() {
		return comptes;
	}
	
	
}
