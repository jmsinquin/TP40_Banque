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
	 * @param typeCpte D�pot ou Placement
	 * @param solde Le solde du compte
	 */
	public Compte(String typeCpte, float solde) {
		this.numCompte = genererNouveauNumCompte();
		this.type = typeCpte;
		this.solde = solde;
	}

	/*
	 * G�n�rer un num�ro de compte � 6 chiffres
	 * @ return Un num�ro de 6 chiffres
	 */
	private static String genererNouveauNumCompte() {	
		// Note : il n'y a aucune garantie qu'un num�ro g�n�r� al�atoirement
		// par cette m�thode ne soit pas identique � un autre num�ro d�j�
		// g�n�r� al�atoirement, mais pour l'exercice cette m�thode sera
		// suffisante, et de toutes fa�ons plus efficace que d'utiliser un
		// TimeStamp : en effet, les comptes �tant cr��s avec un fichier batch,
		// on peut se retrouver avec un m�me TimeStamp pour 5 ou 6 comptes
		// diff�rents � cause de la rapidit� d'ex�cution du programme.
		String num ="";
		for ( int i=1; i<=6; i++) {
			num = num + (int)(Math.random()*10);
		}
		return num;
	}

	/**
	 * @return Le num�ro du compte
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
	 * @param numCompte Affecte le num�ro du compte
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
