package banque.model;


/**
 * @author Jean-Marc SINQUIN
 * @version V1.0
 *
 */
public class Compte {

    private String numCompte;
    private String type;
    private float solde;
    
	/**
	 * Constructeur
	 * @param typeCpte Dépot ou Placement
	 * @param solde Le solde du compte
	 */
	public Compte(String typeCpte, float solde) {
		this.numCompte = genererNouveauNumCompte();
		this.type = typeCpte;
		this.solde = solde;
	}

	/*
	 * Générer un numéro de compte à 6 chiffres
	 * @ return Un numéro de 6 chiffres
	 */
	private static String genererNouveauNumCompte() {	
		// Note : il n'y a aucune garantie qu'un numéro généré aléatoirement
		// par cette méthode ne soit pas identique à un autre numéro déjà
		// généré aléatoirement, mais pour l'exercice cette méthode sera
		// suffisante, et de toutes façons plus efficace que d'utiliser un
		// TimeStamp : en effet, les comptes étant créés avec un fichier batch,
		// on peut se retrouver avec un même TimeStamp pour 5 ou 6 comptes
		// différents à cause de la rapidité d'exécution du programme.
		String num ="";
		for ( int i=1; i<=6; i++) {
			num = num + (int)(Math.random()*10);
		}
		return num;
	}

	/**
	 * @return Le numéro du compte
	 */
	public String getNumCompte() {
		return numCompte;
	}

	/**
	 * @return Le solde du compte
	 */
	public float getSolde() {
		return solde;
	}

	/**
	 * @return Le type de compte
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param numCompte Affecte le numéro du compte
	 */
	public void setNumCompte(String numCompte) {
		this.numCompte = numCompte;
	}

	/**
	 * @param solde Affecte le solde du compte
	 */
	public void setSolde(float solde) {
		this.solde = solde;
	}
		
	/**
	 * @param type Le type de compte
	 */
	public void setType(String type) {
		this.type = type;
	}
}
