package banque.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.Vector;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Jean-Marc
 * La partie VUE dans un pattern MVC
 */

public class FenetrePpale extends JFrame {

	/**
	 * TODO Trouver � quoi �a sert ce serialVersionUID signal� par le compilateur...
	 */
	private static final long serialVersionUID = 1L;
	
	
	// Constantes texte
	private static final String BTN_VALIDATION = "Valider";
	private static final String BTN_CLIENT = "S�lectionner";
	private static final String LBL_CLIENT = "Clients :";
	private static final String LBL_CUMUL = "Cumul portefeuille gestionnaire";
	private static final String LBL_MENU_ACTION = "Menu actions :";
	private static final String LBL_PF_GEST = "Portefeuille gestionnaire :";
	private static final String MENU_0 = "0- Fin session";
	private static final String MENU_1 = "1- Liste des comptes";
	private static final String MENU_2 = "2- Liste des clients";
	private static final String MENU_3 = "3- Tr�sorerie d'un client";
	private static final String MENU_4 = "4- Porte-feuille gestionnaire";
	private static final String MENU_5 = "5- Sauvegarde clientele";
	private static final String MSG_GETCHOIX_01 = "Saisir un entier entre 0 et 5";
	
	// Constantes num�riques
	private static final int LA_FEN = 800;
	private static final int HT_FEN = 400;
	
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
	
	// Elements graphiques Zones
	private Container panContent = this.getContentPane(); 				// Fen�tre principale
	private JPanel jpPpal = new JPanel();								// Panel principal � ins�rer dans le ContentPanel (Layout horizontal)
	private JPanel jpMenu = new JPanel();								// Panel g�rant le menu (Layout vertical)
	private JPanel jpClie = new JPanel();								// Panel g�rant les clients (Layout vertical)
	private JPanel jpGest = new JPanel();								// Panel g�rant les actions gestionnaire (Layout vertical)
	
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
		construireFenetre();		// Taille et comportement de la fen�tre
		construireContenu();		// Contenu de la fen�tre
		initialiser();				// Initialisation des valeurs (menu...)
		setVisible(true);			// Afficher la fen�tre lors de son instantiation
		txtChoix.requestFocus();	// Donne le focus au textFiled "choix"
	}
	
	/* 
	 * D�fini l'objet fen�tre
	 */
	private void construireFenetre() {
		setTitle("NFA035 TP40 Banque version IHM");			// Titre
		setLocationRelativeTo(null);						// Centrage de la fen�tre � sa cr�ation
		setResizable(false); 								// Fen�tre non redimentionnable
		setSize(LA_FEN,HT_FEN);								// Taille vue
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 	// Fermeture programme quand vue est ferm�e
	}
	
	/*
	 *  D�fini le contenu de la fen�tre
	 */
	private void construireContenu() {
		// Squelette de la fen�tre
		jpPpal.setLayout( null );			// Panel principal contenu dans le contentPane sans layout (positions absolues)
		panContent.add(jpPpal); 			// Ajout du panel principal dans le contentPane
		
		// Ajout des diff�rents sous-panels contenus dans le panel principal
		construirePanMenu();				// Panel gauche : menu
		construirePanClients();				// Panel milieu : clients
		construirePanGest();				// Panel droit : gestionnaire
		
		// Positions et dimensions des sous-panels dans le panel principal
		jpMenu.setBounds(  10, 10, 195, HT_FEN-50);
		jpClie.setBounds( 215, 10, 200, HT_FEN-50);
		jpGest.setBounds( 425, 10, 350, HT_FEN-50);
		
		// Comportement et apparence des composants
		jpMenu.setBorder(BorderFactory.createLineBorder(Color.black));
		jpClie.setBorder(BorderFactory.createLineBorder(Color.black));
		jpGest.setBorder(BorderFactory.createLineBorder(Color.black));		
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
		jpMenu.add(txtChoix);								// Ajout txtbox r�sultat
		jpMenu.add(btnValider);								// Ajoute le bouton	
		
		// Position des widgets (car layout type absolu) :
		lblAction.setBounds(  10,  10,  150,  10);
		txtMenu.setBounds(    10,  40 , 170, 105);
		txtChoix.setBounds(   10, 155 , 170,  25);
		btnValider.setBounds( 55, 190 , 125,  25);
						
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
		lblClient.setBounds( 10, 10,  150, 10);
		cmbClients.setBounds(10, 40 , 150, 25);
		btnClient.setBounds( 35, 75 , 125, 25);
		
		// Apparence des composants :
	}
	
	/*
	 * JPanel de droite : actions gestionnaire
	 */
	private void construirePanGest() {
		// Squelette du JPanel :
		jpGest.setLayout( null );							// Aucun layout : positions absolues
		jpPpal.add(jpGest);									// Ajout du sous-panel au panel principal			
				
		// Ajout des widgets dans le JPanel
		jpGest.add(lblPF);												// Ajoute le label
		jpGest.add(srlPF);												// Ajoute le scrollPane
		jpGest.add(lblPFCumul);											// Ajoute le label
		jpGest.add(txtPFCumul);											// Ajoute le textbox

		// Position des widgets
		lblPF.setBounds(       10,  10, 150,  10);
		srlPF.setBounds(       10,  40, 330, 105);
		lblPFCumul.setBounds(  10, 155, 240,  25);
		txtPFCumul.setBounds( 260, 155,  80,  25);
		
		// Comportement et apparence des widgets
		txtPFCumul.setForeground(Color.BLUE);							// Couleur en bleu du r�sultat cumul
		txtPFCumul.setFont( new Font("Arial", Font.BOLD,16) );			// Mise en gras des r�sultats Ht.16
		lblPFCumul.setFont( new Font("Arial", Font.BOLD,16) );			// Mise en gras du label Ht.16
		
		// Ajout de donn�es dans la DefaultListModel "listCpts"
//		listCpts.addElement("toto");
//		listCpts.addElement("tata");
//		listCpts.addElement("titi");
//		listCpts.addElement("toto");
		
	}
	
	/*
	 *  Ajoute des �l�ments dans la textArea menu
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
		
		txtChoix.selectAll();		// Donne le focus � la textChoix
		jpClie.setVisible(false);	// Panneau client invisible par d�faut
		jpGest.setVisible(false);	// Panneau gestionnaire invisible par d�faut
	}
	
	/**
	 * @param liste La liste � afficher dans la zone scrollable gestionnaire
	 */
	public void setListeClients(DefaultListModel<String> liste) {
		fen.setPanClieVisible(false);	// Rend invisible le panneau Client
		fen.setPanGestVisible(true);	// Rend visible le panneau Gestionnaire
		jlistPF.setModel(liste);		// Mise � jour de la source de la liste
	}
	
	/**
	 * @return Le choix de l'utilisateur. Retourne -1 si erreur de choix
	 */
	public int getChoix() {
		String strRep = txtChoix.getText();							// R�cup�ration de la valeur entr�e
		
		// V�rification de la validit� de l'entr�e
		int rep = -1;												// Initialise la valeur retourn�e � -1 (erreur par d�faut)
		if ( strRep.matches("^[0-9]+$") ) {							// V�rifie si la r�ponse est de type integer
			int intRep = Integer.parseInt(strRep);
			if (intRep>=0 && intRep<=5) {							// V�rifie si la r�ponse est dans {0-5}
				rep = intRep;										// Initialise la valeur retourn� avec le choix
			}	
		}
		if ( rep == -1 ) {
			JOptionPane.showMessageDialog(this, MSG_GETCHOIX_01);	// Retourne un message d'erreur	si mauvaise entr�e
		}
		
		// Quelque soit l'issue (bon ou mauvais choix)
		txtChoix.setText(MSG_GETCHOIX_01);							// R� affiche le message de choix
		txtChoix.selectAll();										// S�lectionne le texte
		txtChoix.requestFocus();									// Redonne le focus pour un nouveau choix
		
		// Retourne la r�ponse
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
	
	/**
	 * @param b Rend visible ou non le panneau Gestionnaire
	 */
	public void setPanGestVisible(boolean b) {
		jpGest.setVisible(b);
	}
	
	/**
	 * @param b Rend visible ou non le panneau Gestionnaire
	 */
	public void setPanClieVisible(boolean b) {
		jpClie.setVisible(b);
	}
	
	public static void main(String[] args) {
		new FenetrePpale();
	}
}
