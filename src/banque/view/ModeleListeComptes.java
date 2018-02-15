package banque.view;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

//import banque.model.Client;
import banque.model.Compte;

// TUTO : http://baptiste-wicht.developpez.com/tutoriels/java/swing/jtable/
	
public class ModeleListeComptes extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private final Compte[] comptes;
	private final String[] entetes = {"Numéro", "Type", "Solde"};
	
	/**
	 * Constructeur
	 * @param lst la liste de tous les comptes du gestionnaire
	 */
	public ModeleListeComptes(ArrayList<Compte> lst) {
		super();
		comptes = lst.toArray(new Compte[lst.size()]);
	}

	@Override
	public int getColumnCount() {
		return entetes.length;
	}

	@Override
	public int getRowCount() {
		return comptes.length;
	}
	
	@Override
	public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
			case 0 :
				return comptes[rowIndex].getNumCompte();
			case 1 :
				return comptes[rowIndex].getType();
			case 2 :
				return comptes[rowIndex].getSolde();
			default :
				return null; 
		}
		
	}
}
