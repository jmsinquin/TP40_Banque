package banque.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RenduCellTabComptes extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);


		// Formatage du texte du solde
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);	// Format du nombre suivant standard FR
		DecimalFormat df = (DecimalFormat)nf;

		String texte = value.toString();									// On récupère la valeur
		float nombre = Float.parseFloat(texte);								// Convertion de value en type float

		String colName = table.getColumnName(column);						// Nom de la colone

		// Formatage du n° de compte XXX XXX 
		if (colName.equals("Numéro")) {
			df.applyPattern("000,000");
			setText(df.format(nombre));

		// Formatage du solde XXX XXX,XX €
		} else if (colName.equals("Solde")) {

			df.applyPattern("###,###.00 €");								// Formatage de l'affichage du solde
			setText(df.format(nombre));										// Renvoi du texte formaté pour affichage

			if (nombre < 0) {												// Affichage du texte en rouge si solde négatif
				System.out.println("tot");
				setForeground(Color.RED);
				setFont(getFont().deriveFont(Font.BOLD));
			} else {
				setForeground(null);										// Sinon, couleur par défaut (celle du composant parent)
			}
		}

		// Que ce soit la colonne numéro ou solde :
		setHorizontalAlignment(RIGHT);										// Aligner le texte à droite
		setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));					// Changer la police en type Monospace (Courrier new ou équivalent)

		return this;
	}

}
