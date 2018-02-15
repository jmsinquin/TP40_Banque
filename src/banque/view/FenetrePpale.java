package banque.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import banque.DemoAppli;
import banque.model.Compte;

/**
 * @author Jean-Marc
 * La partie VUE dans un pattern MVC
 */

public class FenetrePpale extends JFrame {

	/**
	 * TODO Trouver à quoi ça sert ce serialVersionUID signalé par le compilateur...
	 */
	private static final long serialVersionUID = 1L;
	
	
	// Constantes texte
	private static final String BTN_VALIDATION = "Valider";
	private static final String BTN_CLIENT = "Sélectionner";
	private static final String LBL_CLIENT = "Clients :";
	private static final String LBL_LST_COMPTES = "Liste des comptes :";
	private static final String LBL_CUMUL = "Cumul portefeuille gestionnaire";
	private static final String LBL_MENU_ACTION = "Menu actions :";
	private static final String LBL_PF_GEST = "Portefeuille gestionnaire :";
	private static final String MENU_0 = "0- Fin session";
	private static final String MENU_1 = "1- Liste des comptes";
	private static final String MENU_2 = "2- Liste des clients";
	private static final String MENU_3 = "3- Trésorerie d'un client";
	private static final String MENU_4 = "4- Porte-feuille gestionnaire";
	private static final String MENU_5 = "5- Sauvegarde clientele";
	private static final String MSG_GETCHOIX_01 = "Saisir un entier entre 0 et 5";
	
	// Constantes numériques
	private static final int LA_FEN = 800;			// Largeur fenêtre principale
	private static final int HT_FEN = 400;			// Hauteur fenêtre principale
	
	// Variables widgets
	private JLabel lblAction = new JLabel(LBL_MENU_ACTION);
	private JTextArea txtMenu = new JTextArea();
	private JTextField txtChoix = new JTextField(MSG_GETCHOIX_01);
	private JButton btnValider = new JButton(BTN_VALIDATION);
	private JLabel lblPF = new JLabel(LBL_PF_GEST);
	private JLabel lblPFCumul = new JLabel(LBL_CUMUL);
	private JTextField txtPFCumul = new JTextField(10);
	private JComboBox<String> cmbClients = new JComboBox<String>();
	private JLabel lblClient = new JLabel(LBL_CLIENT);
	private JButton btnClient = new JButton(BTN_CLIENT);
	private JLabel lblLstC = new JLabel(LBL_LST_COMPTES);
	private JTable jtbLstC = new JTable();
	private JScrollPane srlLstC = new JScrollPane(jtbLstC);
	
	// Elements graphiques Zones
	private Container panContent = this.getContentPane(); 				// Fenêtre principale
	private JPanel jpPpal = new JPanel();								// Panel principal à insérer dans le ContentPanel
	private JPanel jpMenu = new JPanel();								// Panel gérant le menu 
	private JPanel jpClie = new JPanel();								// Panel gérant les clients 
	private JPanel jpGest = new JPanel();								// Panel gérant les actions gestionnaire 
	private JPanel jpLstC = new JPanel();								// Panel gérant la liste des comptes
	
	// Liste scrollable
	//private DefaultListModel<String> listCpts = new DefaultListModel<String>(); 	// Contenu liste porte-feuille
	//JList<String> jlistPF = new JList<String>(listCpts);							// Liste avec source "listCpts"
	JList<String> jlistPF = new JList<String>();
	private JScrollPane srlPF = new JScrollPane(jlistPF);							// Scroll de la liste
	
	
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
		construirePanClients();				// Panel clients
		construirePanGest();				// Panel gestionnaire
		construirePanListeComptes();		// Panel liste des comptes
		
		// Positions et dimensions des sous-panels dans le panel principal
		jpMenu.setBounds(  10, 10, 195, HT_FEN-50);
		jpClie.setBounds( 215, 10, 200, HT_FEN-50);
		jpGest.setBounds( 425, 10, 350, HT_FEN-50);
		jpLstC.setBounds( 215, 10, 560, HT_FEN-50);
		
		// Comportement et apparence des composants
		jpMenu.setBorder(BorderFactory.createLineBorder(Color.black));
		jpClie.setBorder(BorderFactory.createLineBorder(Color.black));
		jpGest.setBorder(BorderFactory.createLineBorder(Color.black));	
		jpLstC.setBorder(BorderFactory.createLineBorder(Color.black));
	}

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
		// TODO Mettre en rouge les comptes en négatif
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
	
	/*
	 * JPanel milieu : actions clients
	 */
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
	
	/*
	 * JPanel de droite : actions gestionnaire
	 */
	private void construirePanGest() {
		// Squelette du JPanel :
		jpGest.setLayout( null );								// Aucun layout : positions absolues
		jpPpal.add(jpGest);										// Ajout du sous-panel au panel principal			
				
		// Ajout des widgets dans le JPanel
		jpGest.add(lblPF);										// Ajoute le label
		jpGest.add(srlPF);										// Ajoute le scrollPane
		jpGest.add(lblPFCumul);									// Ajoute le label
		jpGest.add(txtPFCumul);									// Ajoute le textbox
		
		// Position des widgets
		lblPF.setBounds(       10,  10, 150,  15);
		srlPF.setBounds(       10,  40, 330, 105);
		lblPFCumul.setBounds(  10, 155, 240,  25);
		txtPFCumul.setBounds( 260, 155,  80,  25);
		
		// Comportement et apparence des widgets
		txtPFCumul.setForeground(Color.BLUE);					// Couleur en bleu du résultat cumul
		txtPFCumul.setFont( new Font("Arial", Font.BOLD,16) );	// Mise en gras des résultats Ht.16
		lblPFCumul.setFont( new Font("Arial", Font.BOLD,16) );	// Mise en gras du label Ht.16
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
		ajouterMenu(MENU_3);
		ajouterMenu(MENU_4);
		ajouterMenu(MENU_5);	
		
		txtChoix.selectAll();		// Donne le focus à la textChoix
		jpClie.setVisible(false);	// Panneau client invisible par défaut
		jpGest.setVisible(false);	// Panneau gestionnaire invisible par défaut
		jpLstC.setVisible(false);	// Panneau liste compte invisible par défaut
	}
	

	/** Affichage de la liste des comptes du gestionnaire (Choix 1 du menu)
	 * @param cptListeComptes La liste des comptes à afficher
	 */
	public void setListeComptes(ArrayList<Compte> cptListeComptes) {
        
        setJpanelVisible(jpLstC);										// Affichage du panel jpLstC et masquage des autres panels (sauf panel menu)
        
		jtbLstC.setModel(new ModeleListeComptes( cptListeComptes ));	// Ecrit les données dans la JTable suivant le modèle donné.
        srlLstC.setViewportView(jtbLstC);								// Rajoute la jTable dans la zone scrollage
	}
	
	/**
	 * Affichage de la liste des clients du gestionnaire (Choix 2 du menu)
	 * @param liste La liste des clients à afficher 
	 */
	public void setListeClients(DefaultListModel<String> liste) {
		
		setJpanelVisible(jpGest);	// Affichage du panel jpGest et masquage des autres panels (sauf panel menu)
		
		jlistPF.setModel(liste);	// Mise à jour de la source de la liste
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
		if ( strRep.matches("^[0-9]+$") ) {							// Vérifie si la réponse est de type integer
			int intRep = Integer.parseInt(strRep);
			if (intRep>=0 && intRep<=5) {							// Vérifie si la réponse est dans {0-5}
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
	 * Ajoute un listener sur le bouton Valider
	 * @param listenerBtnValider 
	 */
	public void addValiderListener(ActionListener listenerBtnValider) {
		btnValider.addActionListener(listenerBtnValider);
	}
	
	/**
	 * Ajoute un listener sur la txtbox Choix
	 * @param txtChoixKeyListener 
	 */
	public void addTxtChoixKeyListener(KeyListener txtChoixKeyListener) {
		txtChoix.addKeyListener(txtChoixKeyListener);
	}
	
	public static void main(String[] args) {
		//new FenetrePpale();
		DemoAppli.main(null);
	}
}
