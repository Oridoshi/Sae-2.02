package ihm;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Color;

import controleur.Controleur;

public class Frame extends JFrame
{
	private Controleur     ctrl;
	private PanelOption    panelOption;
	private PanelGraphique panelGraphique;

	public Frame(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.setTitle("Graphique");
		this.setSize(700, 800); 

		this.panelOption = new PanelOption(this.ctrl);
		this.panelGraphique = new PanelGraphique(this.ctrl);

		this.add(this.panelOption       , BorderLayout.SOUTH );
		this.add(this.panelGraphique    , BorderLayout.CENTER);

		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void maj()
	{
		this.panelGraphique.repaint();
	}

	public void fin(String tableauPoint)
	{
		JOptionPane.showMessageDialog(null, tableauPoint);
	}

	public void majLabelIHM(String messErreur)
	{
		this.panelOption.majLabel(messErreur);
	}

	public void majCouleur(Color couleur)
	{
		this.panelOption.majCouleur(couleur);
	}
}