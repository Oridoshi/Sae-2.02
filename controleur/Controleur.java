package controleur;

import metier.Graphique;

import java.awt.Color;

import ihm.Frame;

public class Controleur
{
	private Graphique metier;
	private Frame ihm;

	public Controleur()
	{
		this.metier = new Graphique(this);
		this.ihm    = new Frame(this);
	}

	public Graphique getMetier()
	{
		return this.metier;
	}

	public void maj()
	{
		this.ihm.maj();
	}

	public void fin(String tableuPoint)
	{
		this.ihm.fin(tableuPoint);
		this.ihm.dispose();
	}

	public void majLabelIHM(String messLabel)
	{
		this.ihm.majLabelIHM(messLabel);
	}

	public static void main(String[] args)
	{
		new Controleur();
	}

	public void majCouleur(Color couleur)
	{
		this.ihm.majCouleur(couleur);
	}
}