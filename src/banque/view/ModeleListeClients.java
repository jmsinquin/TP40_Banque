package banque.view;

import java.util.TreeSet;

import javax.swing.table.AbstractTableModel;

import banque.model.Client;



/**
 * Arrange la source d'une JTable listant les clients
 * @author Jean-Marc SINQUIN
 * @version V1.0
 *
 */
public class ModeleListeClients extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private final Client[] clients;
	private final String[] entetes = {"Identifiant", "Nom"};
	
	/**
	 * Constructeur
	 * @param lst la liste de tous les clients du gestionnaire
	 */
	public ModeleListeClients(TreeSet<Client> lst) {
		super();
		clients = lst.toArray(new Client[lst.size()]);
	}
	
	public Client getClient(int index) {
		return clients[index];
	}

	@Override
	public int getColumnCount() {
		System.out.println("toto");
		return entetes.length;
	}

	@Override
	public int getRowCount() {
		return clients.length;
	}
	
	@Override
	public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
			case 0 :
				return clients[rowIndex].getIdClient();
			case 1 :
				return clients[rowIndex].getPrenom();
			default :
				return null; 
		}
		
	}
}
