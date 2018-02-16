package banque.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
//import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import banque.model.Client;
import banque.model.Compte;
import banque.model.Gestionnaire;
import banque.model.TrierComptes;

/**
 * La partie VUE dans un pattern MVC
 * @author Jean-Marc SINQUIN
 * @version V1.0
 */

public class FenetrePpale extends JFrame {

	private static final long serialVersionUID = 1L;
	
	
	// Constantes texte
	private static final String BTN_VALIDATION = "Valider";
//	private static final String BTN_CLIENT = "Sélectionner";
//	private static final String LBL_CLIENT = "Clients :";
	private static final String LBL_CLIENT_COMPTES = "Liste des comptes de ";
	private static final String LBL_LST_COMPTES = "Liste des comptes :";
	private static final String LBL_MENU_ACTION = "Menu actions :";
	private static final String LBL_PF_GEST = "Liste clients du gestionnaire :";
	private static final String LBL_PAT_CLIENT = "Patrimoine du client :";
	private static final String LBL_SAVE = "Eléments à sauvegarder :";
	private static final String BTN_SAVE = "Sauvegarder";
	private static final String MENU_0 = "0- Fin session";
	private static final String MENU_1 = "1- Liste des comptes";
	private static final String MENU_2 = "2- Liste des clients";
//	private static final String MENU_3 = "3- Trésorerie d'un client";
//	private static final String MENU_4 = "4- Porte-feuille gestionnaire";
	private static final String MENU_5 = "3- Sauvegarde clientele";
	private static final String MSG_GETCHOIX_01 = "Saisir un entier entre 0 et 3";
	
	// Constantes numériques
	private static final int LA_FEN = 800;			// Largeur fenêtre principale
	private static final int HT_FEN = 400;			// Hauteur fenêtre principale
	
	// Variables widgets
	private JLabel 		lblAction = new JLabel(LBL_MENU_ACTION);
	private JTextArea 	txtMenu = new JTextArea();
	private JTextField 	txtChoix = new JTextField(MSG_GETCHOIX_01);
	private JButton 	btnValider = new JButton(BTN_VALIDATION);
	private JLabel 		lblPF = new JLabel(LBL_PF_GEST);
	private JLabel 		lblCliComptes = new JLabel(LBL_CLIENT_COMPTES);	// Label liste des comptes du client sélectionné
//	private JComboBox<String> cmbClients = new JComboBox<String>();
//	private JLabel 		lblClient = new JLabel(LBL_CLIENT);
//	private JButton 	btnClient = new JButton(BTN_CLIENT);
	private JLabel 		lblLstC = new JLabel(LBL_LST_COMPTES);
	private JTable 		jtbLstC = new JTable();							// Table liste des comptes
	private JScrollPane srlLstC = new JScrollPane(jtbLstC);				// Scroll pour la table liste des comptes
	// Panels client
	private JTable 		jtbClient = new JTable();						// Table liste des client
	private JTable 		jtbCpCl = new JTable();							// Table liste des comptes d'un client
	private JScrollPane srlCpCl = new JScrollPane(jtbCpCl);				// Scroll pour la table liste des comptes d'un client
	private JLabel 		lblPatClient = new JLabel(LBL_PAT_CLIENT);		// Label patrimoine client
	private JTextField 	txtPatClient = new JTextField();				// TextBox patrimoine client
	// Panel Save
	private JLabel 		lblSave = new JLabel(LBL_SAVE);					// Label
	private JTextArea 	txtSave = new JTextArea();						// Textarea
	private JButton 	btnSave = new JButton(BTN_SAVE);				// Bouton
	private JScrollPane srlSave = new JScrollPane();					// Scroll zone
	
	// Elements graphiques Zones
	private Container panContent = this.getContentPane(); 				// Fenêtre principale
	private JPanel jpPpal = new JPanel();								// Panel principal à insérer dans le ContentPanel
	private JPanel jpMenu = new JPanel();								// Panel gérant le menu 
	private JPanel jpClie = new JPanel();								// Panel gérant les clients 
	private JPanel jpGest = new JPanel();								// Panel gérant les actions gestionnaire 
	private JPanel jpLstC = new JPanel();								// Panel gérant la liste des comptes
	private JPanel jpCpCl = new JPanel();								// Panel gérant la liste des comptes d'un client
	private JPanel jpSave = new JPanel();								// Panel gérant la sauvegarde de la clientele

	private JList<String> jlistPF = new JList<String>();
	private JScrollPane srlPF = new JScrollPane(jlistPF);				// Scroll de la liste
	
	
	/*
	 *  Constructeur
	 */
	public FenetrePpale() {
		super();					// Pas super utile :p
		construireFenetre();		// Taille et comportement de la fenêtre
		construireContenu();		// Contenu de la fenêtre
		initialiser();				// Initialisation des valeurs (menu...)
		setVisible(true);			// Afficher la fenêtre lors de son instantiation
		txtChoix.requestFocus();	// Donne le focus au textFiled "choix"
	}
	
	/* 
	 * Défini l'objet fenêtre
	 */
	private void construireFenetre() {
		setTitle("NFA035 TP40 Banque version IHM");			// Titre
		setLocationRelativeTo(null);						// Centrage de la fenêtre à sa création
		setResizable(false); 								// Fenêtre non redimentionnable
		setSize(LA_FEN,HT_FEN);								// Taille vue
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 	// Fermeture programme quand vue est fermée
	}
	
	/*
	 *  Défini le contenu de la fenêtre
	 */
	private void construireContenu() {
		// Squelette de la fenêtre
		jpPpal.setLayout( null );			// Panel principal contenu dans le contentPane sans layout (positions absolues)
		panContent.add( jpPpal ); 			// Ajout du panel principal dans le contentPane
		
		// Ajout des différents sous-panels contenus dans le panel principal
		construirePanMenu();				// Panel gauche : menu
//		construirePanClients();				// Panel clients
		construirePanGest();				// Panel gestionnaire
		construirePanListeComptes();		// Panel liste des comptes
		construirePanSave();				// Panel sauvegarde
		
		// Positions et dimensions des sous-panels dans le panel principal
		jpMenu.setBounds(  10, 10, 195, HT_FEN-50);
//		jpClie.setBounds( 215, 10, 200, HT_FEN-50);
		jpGest.setBounds( 425, 10, 350, HT_FEN-50);
		jpLstC.setBounds( 215, 10, 560, HT_FEN-50);
		jpSave.setBounds( 215, 10, 560, HT_FEN-50); 	// Panneau Save
		
		// Comportement et apparence des composants
		jpMenu.setBorder(BorderFactory.createLineBorder(Color.black));
		jpClie.setBorder(BorderFactory.createLineBorder(Color.black));
		jpGest.setBorder(BorderFactory.createLineBorder(Color.black));	
		jpLstC.setBorder(BorderFactory.createLineBorder(Color.black));
		jpSave.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	/**
	 * JPanel affichant une JTable contenant la liste des comptes
	 */
	private void construirePanListeComptes() {
		// Squelette du JPanel :
		jpLstC.setLayout( null );							// Aucun layout : positions absolues
		jpPpal.add(jpLstC);									// Ajout du sous-panel au panel principal
		
		// Ajout des widgets dans le JPanel Vertical
		jpLstC.add(lblLstC);								// Ajoute le label
		srlLstC = new JScrollPane(jtbLstC);					// Ajoute une JTable dans un scrollpanel
		jpLstC.add(srlLstC);								// Ajoute le scrollPanel dans le sous-panel

		// Position des widgets (car layout type absolu) :
		lblLstC.setBounds(10, 10, 150,  15);
		srlLstC.setBounds(10, 40, 530, 290);
		
		// Comportement et apparence des composants
		
	}
	
	/*
	 * JPanel de gauche : menu et actions
	 */
	private void construirePanMenu() {
		// Squelette du JPanel :
		jpMenu.setLayout( null );							// Aucun layout : positions absolues
		jpPpal.add(jpMenu);									// Ajout du sous-panel au panel principal
		
		// Ajout des widgets dans le JPanel Vertical
		jpMenu.add(lblAction);								// Ajoute le label
		jpMenu.add(txtMenu);								// Ajout menu
		jpMenu.add(txtChoix);								// Ajout txtbox résultat
		jpMenu.add(btnValider);								// Ajoute le bouton	
		
		// Position des widgets (car layout type absolu) :
		lblAction.setBounds(  10,  10, 150,  15);
		txtMenu.setBounds(    10,  40, 170, 105);
		txtChoix.setBounds(   10, 155, 170,  25);
		btnValider.setBounds( 55, 190, 125,  25);
						
		// Comportement et apparence des composants
		txtMenu.setEditable(false);										// Verrouille le contenu du menu
		txtMenu.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));	// Marges autour du texte menu
		txtChoix.setToolTipText(MSG_GETCHOIX_01);						// Infobulle sur la textbox choix
	}
	
	private void construirePanSave() {
		// Squelette du JPanel :
			jpSave.setLayout( null );							// Aucun layout : positions absolues
			jpPpal.add(jpSave);									// Ajout du sous-panel au panel principal
			
			// Ajout des widgets dans le JPanel Vertical
			jpSave.add(lblSave);								// Ajoute le label
			jpSave.add(srlSave);								// Ajoute la zone scrollable
			srlSave.add(txtSave);								// Ajout la fenêtre de texte dans la zone scrollable
			jpSave.add(btnSave);								// Ajoute le bouton	Save
			
			// Position des widgets (car layout type absolu) :
			lblSave.setBounds( 10,  10, 150,  15);
			srlSave.setBounds( 10,  40, 540, 255);
			btnSave.setBounds( 420, 310, 125,  25);
							
			// Comportement et apparence des composants
			txtSave.setEditable(false);										// Verrouille le contenu de la zone de texte
			txtSave.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));	// Marges autour du texte menu
	}
	
/*	
	
	 * JPanel milieu : Trésorerie des clients
	 
	private void construirePanClients() {
		// Squelette du JPanel :
		jpClie.setLayout( null );							// Aucun layout : positions absolues
		jpPpal.add(jpClie);									// Ajout du sous-panel au panel principal
		
		// Ajout des widgets :
		jpClie.add(lblClient);								// Ajout label
		jpClie.add(cmbClients);								// Ajout combobox
		jpClie.add(btnClient);								// Ajout bouton
			
		// Position des widget (car layout type absolu) :
		lblClient.setBounds( 10, 10,  150, 15);
		cmbClients.setBounds(10, 40 , 150, 25);
		btnClient.setBounds( 35, 75 , 125, 25);
		
		// Apparence des composants :
	}
*/	
	
	/*
	 * JPanel de droite : Affiche la liste des noms et id des clients d'un gestionnaire
	 */
	private void construirePanGest() {
		// Squelette du JPanel :
		jpGest.setLayout( null );								// Aucun layout : positions absolues
		jpPpal.add(jpGest);										// Ajout du sous-panel au panel principal	
		construirePanComptesClient();							// Ajout du sous-panel listeComptesClient à ce sous panel
				
		// Ajout des widgets dans le JPanel
		jpGest.add(lblPF);										// Ajoute le label
		jpGest.add(srlPF);										// Ajoute le scrollPane
		
		
		// Position des widgets
		lblPF.setBounds(        10,  10, 330,  15);				// Label
		srlPF.setBounds(        10,  40, 330, 100);				// ScrollList clients
		jpCpCl.setBounds(       10, 150, 330, 190);       		// Sous panel ListeDesComptesDuClient
		
		// Comportement et apparence des widgets
		jpCpCl.setVisible(false);								// Non visible par défaut
		jpCpCl.setBorder(BorderFactory.createLineBorder(Color.black));

	}
	
	private void construirePanComptesClient() {
		// Squelette du JPanel :
		jpCpCl.setLayout( null );								// Aucun layout : positions absolues
		jpGest.add(jpCpCl);										// Ajout du sous-panel au panel Clients d'un gestionnaire
		
		// Ajout des widgets dans le JPanel
		jpCpCl.add(lblCliComptes);								// Ajoute label 'Voir comptes'jpGest.add(jtbCpCl);									// Ajoute bouton 'Voir comptes'
		srlCpCl = new JScrollPane(jtbCpCl);						// Ajoute une JTable dans un scrollpanel
		jpCpCl.add(srlCpCl);									// Ajoute le scrollPanel dans le sous-panel
		jpCpCl.add(lblPatClient);								// Label patrimone du client
		jpCpCl.add(txtPatClient);								// textbox patrimoine du client
		
		// Position des widgets
		lblCliComptes.setBounds(10,  10, 200,  15);				// Label "Liste des compte de 'client'"
		srlCpCl.setBounds(		10,  40, 300, 100);				// ScrollList
		lblPatClient.setBounds(	50, 160, 120,  15);
		txtPatClient.setBounds(185, 155, 125,  25);
		
		// Comportement et apparence des widgets
		txtPatClient.setHorizontalAlignment(JTextField.RIGHT );
	}
	
	/*
	 *  Ajoute des éléments dans la textArea menu
	 */
	private void ajouterMenu(String msg) {
		if ( txtMenu.getText().equals("") ) {
			txtMenu.append(msg);
		} else {
			txtMenu.append("\n" + msg);
		}
	}
	
	/* 
	 * Initialisation
	 */
	private void initialiser() {
		// Ajouter les options du menu
		ajouterMenu(MENU_0);
		ajouterMenu(MENU_1);
		ajouterMenu(MENU_2);
//		ajouterMenu(MENU_3);
//		ajouterMenu(MENU_4);
		ajouterMenu(MENU_5);	
		
		txtChoix.selectAll();		// Donne le focus à la textChoix
		jpClie.setVisible(false);	// Panneau client invisible par défaut
		jpGest.setVisible(false);	// Panneau gestionnaire invisible par défaut
		jpLstC.setVisible(false);	// Panneau liste compte invisible par défaut
		jpSave.setVisible(true);	// Panneau sauvegarde invisible par défaut
	}
	
	
	/**
	 * Affiche les comptes d'un client sélectionné dans la liste
	 * @param client L'objet client dont il faut afficher les comptes
	 */
	public void setListeComptesClient(Client client) {
		jpCpCl.setVisible(true);																// Rend le panel visible
		
		ArrayList<Compte> comptes = client.getListeComptes();									// Récupérer la liste des comptes du client
		
		if (comptes.size() != 0) {																// Si le client à des comptes
			lblCliComptes.setText("Liste des comptes de " + client.getPrenom() + " :");
			Collections.sort(comptes, new TrierComptes());										// Trier les comptes
			jtbCpCl.setModel(new ModeleListeComptes( comptes ));
			jtbCpCl.getColumnModel().getColumn(1).setCellRenderer(new RenduCellTabComptes());	// Formatage des n° de comptes
			jtbCpCl.getColumnModel().getColumn(2).setCellRenderer(new RenduCellTabComptes());	// Formatage des soldes
			srlCpCl.setViewportView(jtbCpCl);													// Mise à jour de la zone scrollable
			srlCpCl.setVisible(true);															// Rend visible la zone scrollable
			lblPatClient.setVisible(true);
			txtPatClient.setVisible(true);
			txtPatClient.setText(client.getPatrimoineToString() + "");
		} 
		else {																					// Le client n'a pas de compte
			lblCliComptes.setText("Le client " + client.getPrenom() + " n'a aucun compte");
			srlCpCl.setVisible(false);
			lblPatClient.setVisible(false);
			txtPatClient.setVisible(false);
		}
	}
		
	/** Affichage de la liste des comptes du gestionnaire (Choix 1 du menu)
	 * @param cptListeComptes La liste des comptes à afficher
	 */
	public void setListeComptes(ArrayList<Compte> cptListeComptes) {
        
        setJpanelVisible(jpLstC);															// Affichage du panel jpLstC et masquage des autres panels (sauf panel menu)
        
        Collections.sort(cptListeComptes, new TrierComptes());								// Trie croissant des soldes des comptes
		jtbLstC.setModel(new ModeleListeComptes( cptListeComptes ));						// Ecrit les données dans la JTable suivant le modèle donné.
		jtbLstC.getColumnModel().getColumn(1).setCellRenderer(new RenduCellTabComptes());	// Formatage des n° de comptes
		jtbLstC.getColumnModel().getColumn(2).setCellRenderer(new RenduCellTabComptes());	// Formatage des soldes
        srlLstC.setViewportView(jtbLstC);													// Rajoute la jTable dans la zone scrollage
	}
	
	/**
	 * Affichage de la liste des clients du gestionnaire (Choix 2 du menu)
	 * @param lstClients La liste des clients à afficher 
	 */
	public void setListeClients(TreeSet<Client> lstClients) {
		
		// Affichage du panel jpGest et masquage des autres panels (sauf panel menu)
		setJpanelVisible(jpGest);
				
		jtbClient.setModel(new ModeleListeClients( lstClients ));	// Mise à jour de la source de la table
		srlPF.setViewportView(jtbClient);							// Mise à jour scrollList
		
	}
	
	public void setSave(Gestionnaire gest) {
		// Affichage du panel jpSave et masquage des autres panels (sauf panel menu)
		setJpanelVisible(jpSave);
		// Rajoute les données dans la zone de texte
		txtSave.append(gest.getDonneesClients());
		srlSave.setViewportView(txtSave);
	}
	
	
	/**
	 * Affiche le JPanel Menu et le JPanel donné en paramètre
	 * @param jp Le JPanel à afficher
	 */
	private void setJpanelVisible(JPanel jp) {
		Component[] tabComp = jpPpal.getComponents();
		
		for (Component c : tabComp) {
			if (c.getClass().getName().equals("javax.swing.JPanel")) {
				if (c == jp || c == jpMenu) c.setVisible(true);
				else c.setVisible(false);
			}
		}
	}
	
	/**
	 * @return Le choix de l'utilisateur. Retourne -1 si erreur de choix
	 */
	public int getChoix() {
		String strRep = txtChoix.getText();							// Récupération de la valeur entrée
		
		// Vérification de la validité de l'entrée
		int rep = -1;												// Initialise la valeur retournée à -1 (erreur par défaut)
		if ( strRep.matches("^[0-3]+$") ) {							// Vérifie si la réponse est de type integer
			int intRep = Integer.parseInt(strRep);
			if (intRep>=0 && intRep<=3) {							// Vérifie si la réponse est dans {0-5}
				rep = intRep;										// Initialise la valeur retourné avec le choix
			}	
		}
		if ( rep == -1 ) {
			JOptionPane.showMessageDialog(this, MSG_GETCHOIX_01);	// Retourne un message d'erreur	si mauvaise entrée
		}
		
		// Quelque soit l'issue (bon ou mauvais choix)
		txtChoix.setText(MSG_GETCHOIX_01);							// Ré affiche le message de choix
		txtChoix.selectAll();										// Sélectionne le texte
		txtChoix.requestFocus();									// Redonne le focus pour un nouveau choix
		
		// Retourne la réponse
		return rep;
	}
	
	
	/**
	 * La liste des clients (Choix No2 du menu) doit pouvoir être écoutée
	 * On retourne cette liste pour que le controleur puisse ensuite le traiter
	 * @return l'objet jlistPF (liste des clients avec leur id)
	 */
	public JTable getWidgetListeClient() {
		return jtbClient;
	}
	
	
	/**
	 * Retourne les données à sauvegarder
	 * @return Les données contenues dans la textBox txtSave
	 */
	public String getDatasToSave() {
		return txtSave.getText();
	}
	
	/**
	 * Ajoute un listener sur le bouton Valider
	 * @param listenerBtnValider Le listener du bouton Valider
	 */
	public void addValiderListener(ActionListener listenerBtnValider) {
		btnValider.addActionListener(listenerBtnValider);
	}
	
	/**
	 * Ajoute un listener sur le bouton Sauvegarder
	 * @param listenerBtnSave Le listener du bouton Sauvegarder
	 */
	public void addSaveListener(ActionListener listenerBtnSave) {
		btnSave.addActionListener(listenerBtnSave);
	}
	
	/**
	 * Ajoute un listener sur la txtbox Choix
	 * @param txtChoixKeyListener Le key listener
	 */
	public void addTxtChoixKeyListener(KeyListener txtChoixKeyListener) {
		txtChoix.addKeyListener(txtChoixKeyListener);
	}
	
//	public static void main(String[] args) {
//		//new FenetrePpale();
//		DemoAppli.main(null);
//	}
}
