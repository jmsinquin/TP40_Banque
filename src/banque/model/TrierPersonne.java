package banque.model;

import java.util.Comparator;

/**
 * Trie les personnes en ordre alphabétique.<br>
 * @author Jean-Marc SINQUIN
 *
 */
public class TrierPersonne implements Comparator<Personne> {

	@Override
	public int compare(Personne o1, Personne o2) {
		return o1.getPrenom().compareTo(o2.getPrenom());
	}
	
}
