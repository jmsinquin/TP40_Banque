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
	 * Cr�ation du bigramme en fonction du pr�nom :
	 * Prends les 2 premi�re voyelles contenues dans le pr�nom et les inverse.
	 * S'il n'y a pas de voyelles dans le pr�nom, retourne AA
	 * S'il n'y a qu'une voyelle, retourne la voyelle doubl�e (ex Luc -> UU)
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
	 *  @param prenom : le pr�nom du client
	 *  @param id : le num�ro d'identification du client (6 chiffres sans le bigramme)
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
	 * @param  typeCpte : type du compte (d�pot / placement)
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
	 * @return Le num�ro unique du client
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
			 System.out.println("  Ce client poss�de " + nbComptes + " comptes(s) :");
			 final String SEP = "   --------------------";
			 
			 while(itr.hasNext()) {
			     System.out.println(SEP);
				 Compte compte = itr.next();
			     System.out.println("   -> Type  :" + compte.getType());
			     System.out.println("   -> Solde :" + compte.getSolde() + "�");
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
	 * Cr�dite ce client d'une somme
	 * @param numCompte Num�ro du compte � cr�diter
	 * @param montant Montant � cr�diter
	 */
	public void crediter(String numCompte, float montant) {
		updateSolde(numCompte, montant);
	}
	
	/**
	 * D�bite ce client d'un somme
	 * @param numCompte Num�ro du compte � d�biter
	 * @param montant Montant � d�biter
	 */
	public void debiter(String numCompte, float montant) {
		montant = ( montant<0 ? -montant : montant);
		updateSolde(numCompte, -montant);
	}
	
	/**
	 * Tranf�re une somme d'un compte de ce client vers un compte d'un autre client
	 * @param numCpteDebit Le compte de ce client � d�biter
	 * @param crediteur Le client � cr�diter
	 * @param numCpteCredit Le compte du client � cr�diter
	 * @param montant Montant � transf�rer
	 */
	public void transferer(String numCpteDebit, Client crediteur, String numCpteCredit, float montant) {
		updateSolde(numCpteDebit, -montant);
		crediteur.crediter(numCpteCredit, montant);
	}
	
	/**
	 * Mise � jour du solde d'un compte de ce client
	 * @param numCompte Num�ro de compte � mettre � jour
	 * @param montant  Montant � cr�diter/d�biter
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
	 * Cr�dite/d�bite un compte de ce client
	 * @param cpte Le compte � cr�diter/d�biter
	 * @param montant Le montant � cr�diter/d�biter
	 */
	public void setSoldeCompte(Compte cpte, float montant) {
		cpte.setSolde(montant);
	}
	
	/*
	 * Retourne un compte � partir de son num�ro
	 * @return un objet compte � partir de son num�ro (null si pas trouv�)
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
	 * @return Le patrimoine du client format�
	 */
	public String getPatrimoineToString() {
		float total = getPatrimoine();
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);	// Format du nombre suivant standard FR
		DecimalFormat df = (DecimalFormat)nf;
		df.applyPattern("###,###.00 �");								// Formatage de l'affichage du solde
		return df.format(total);	
	}
	
	public ArrayList<Compte> getListeComptes() {
		return comptes;
	}
	
	
}
