package banque.model;

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
	 * @return Le pr�nom de la personne
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
