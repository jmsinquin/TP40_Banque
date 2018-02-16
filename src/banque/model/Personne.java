package banque.model;

/**
 * Classes filles : Gestionnaire, Client
 * @author Jean-Marc SINQUIN
 * @version V1.0
 *
 */
public abstract class Personne {
	
	private String prenom;
	
	/**
	 * Constructeur
	 * @param prenom Le pr�nom de la personne cr��e
	 */
	public Personne(String prenom) {
		 this.prenom = prenom;
    }
	
	/**
	 * M�thode abstraite � impl�menter dans les classes d�riv�es
	 */
	public abstract void listerCompte();

	/**
	 * Retourne le pr�nom de cette personne
	 * @return Le pr�nom de la personne
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Mettre un nom � cette personne
	 * @param prenom Le pr�nom de cette personne
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
}
