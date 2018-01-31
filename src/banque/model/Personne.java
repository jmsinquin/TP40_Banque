package banque.model;

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
	 * @return Le prénom de la personne
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
}
