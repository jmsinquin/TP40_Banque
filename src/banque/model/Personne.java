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
	 * @param prenom Le prénom de la personne créée
	 */
	public Personne(String prenom) {
		 this.prenom = prenom;
    }
	
	/**
	 * Méthode abstraite à implémenter dans les classes dérivées
	 */
	public abstract void listerCompte();

	/**
	 * Retourne le prénom de cette personne
	 * @return Le prénom de la personne
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Mettre un nom à cette personne
	 * @param prenom Le prénom de cette personne
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
}
