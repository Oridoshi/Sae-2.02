package ihm;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;

import controleur.Controleur;

public class PanelOption extends JPanel implements ActionListener
{
	private Controleur ctrl;
	private JTextField txtSommet1;
	private JTextField txtSommet2;
	private JButton    btnValider;

	private JLabel lblErreur;
	private JPanel panelCouleurActu;

	public PanelOption (Controleur ctrl)
	{
		this.ctrl = ctrl;

		JPanel panelGlobal;

		panelGlobal = new JPanel();

		this.txtSommet1 = new JTextField(8);
		this.txtSommet2 = new JTextField(8);
		this.lblErreur  = new JLabel(" ");

		this.panelCouleurActu = new JPanel();
		this.panelCouleurActu.setBackground(this.ctrl.getMetier().getCouleurActu());

		this.btnValider = new JButton("Valider");

		this.setLayout(new GridLayout(2, 1));
		panelGlobal.setLayout(new GridLayout(1, 4));
		JPanel panelTmp1;
		JPanel panelTmp2;

		panelTmp1 = new JPanel();
		panelTmp1.add(new JLabel("Sommet 1 : "));
		panelTmp1.add(this.txtSommet1);
		panelGlobal.add(panelTmp1);

		panelTmp1 = new JPanel();
		panelTmp1.add(new JLabel("Sommet 2 : "));
		panelTmp1.add(this.txtSommet2);
		panelGlobal.add(panelTmp1);

		panelTmp1 = new JPanel();
		panelTmp1.add(this.btnValider);
		panelGlobal.add(panelTmp1);

		panelGlobal.add(panelCouleurActu);

		panelTmp2 = new JPanel();
		panelTmp2.add(this.lblErreur);

		this.add(panelTmp2);
		this.add(panelGlobal);

		btnValider.addActionListener(this);
	}

	public void actionPerformed ( ActionEvent e )
	{
		if (e.getSource() == this.btnValider)
		{
			this.ctrl.getMetier().colorierArete(this.txtSommet1.getText(), this.txtSommet2.getText() );
			this.ctrl.maj();
		}
	}

	public void majLabel(String messErreur)
	{
		this.lblErreur.setText(messErreur);
	}

	public void majCouleur(Color couleur)
	{
		this.panelCouleurActu.setBackground(couleur);
	}
}