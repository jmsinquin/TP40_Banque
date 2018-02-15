package banque.controler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.TreeSet;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import banque.model.Client;
import banque.model.Gestionnaire;
import banque.view.FenetrePpale;
import banque.view.ModeleListeClients;


/**
 * @author Jean-Marc
 * La partie CONTROLEUR dans un pattern MVC
 */
public class ControleurFenetrePpale {
	private FenetrePpale fen;
	private Gestionnaire gest;
	private JTable jListClients; 
	
	/**
	 * Constructeur
	 */
	public ControleurFenetrePpale(FenetrePpale laFenetre, Gestionnaire leGestionnaire) {
		fen = laFenetre;
		gest = leGestionnaire;
		jListClients = fen.getWidgetListeClient();
		// Voir exemple listener pour JTable: http://java-buddy.blogspot.fr/2013/12/java-swing-jtable-and.html
		ListSelectionModel lsm = jListClients.getSelectionModel();
		
		// Ajout des écouteurs
		fen.addValiderListener(new btnValiderListener());			// Ajout ActionListener bouton Valider (clic)
		fen.addTxtChoixKeyListener(new txtChoixKeyListener());		// Ajout KeyListener txtChoix (clavier Entrée)
		lsm.addListSelectionListener(new jListClientsListener());	// Ajout d'un ListSelectionListener (Liste client)
	}
	
	// Inner class Table des clients
	class jListClientsListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {

				int index = jListClients.getSelectedRow();

				TableModel tm = jListClients.getModel();
				Client c = ((ModeleListeClients) tm).getClient(index);

				//System.out.println(index + " " + c);
				
				fen.setListeComptesClient(c);
			}
		}
		
	}
	
	// Inner class Bouton valider
	class btnValiderListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			choixMenu();
		}
	}
	
	// Inner class Touche entrée pressé sur le txtbox Choix
	class txtChoixKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			if (keyCode == 10) {
				choixMenu();
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {}

		@Override
		public void keyTyped(KeyEvent arg0) {}		
	}
	
	/*
	 * Sélection des méthodes à exécuter après validation du choix utilisateur
	 */
	private void choixMenu() {
		// On récupère la valeur du choix
		int choix = fen.getChoix();
		if ( choix != -1) {
			System.out.println("Choix : " + choix);
			switch (choix) {
				case 0 : quitter();
					break;
				case 1 : listerComptes();
					break;
				case 2 : listerClients();
					break;
				case 3 : tresorerieClient();
					break;
				case 4 : portefeuilleGestionnaire();
					break;
				case 5 : saveClients();
					break;
			}
		}
	}
	
	private void quitter() {
		System.out.println("Sortie de l'appli");
		System.exit(0);								// 0 = Status code, fin normale du programme.
	}
	
	private void listerComptes() {
		System.out.println("listerComptes");
		fen.setListeComptes( gest.getListeComptes() );
	}
	
	private void listerClients() {
		System.out.println("listerClients");
		fen.setListeClients( gest.getClients() );
	}
	
	private void tresorerieClient() {
		// TODO tresorerieClient()
		System.out.println("tresorerieClient");
	}
	
	private void portefeuilleGestionnaire() {
		// TODO portefeuilleGestionnaire()
		System.out.println("portefeuilleGestionnaire");
	}
	
	private void saveClients() {
		// TODO saveClients()
		System.out.println("saveClients");
	}
	
}
