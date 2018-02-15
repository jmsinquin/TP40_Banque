package banque.model;

import java.util.Comparator;


/**
 * Trie des soldes des comptes.<br>
 * Utilisée par la méthode <code>setListeComptes()</code> de la classe FenetrePpale
 * @author Jean-Marc SINQUIN
 * @version 1.0
 */
public class TrierComptes implements Comparator<Compte>  {

	@Override
	public int compare(Compte arg0, Compte arg1) {
		return (arg0.getSolde() > arg1.getSolde() ? 1 : -1);
	}
	
}
