package metier;

import java.util.ArrayList;

import java.awt.Color;

public class Region {
	private static int nbRegion = 0;

	private static final Color[] TAB_COLOR = new Color[] { new Color(  0,   0, 155), new Color(  0, 125,   0), new Color(200,  50, 115),
	                                                      new Color(200, 125,   0), new Color(115, 0, 149), new Color(132, 207, 207), 
	                                                     Color.BLACK              , new Color(169,   0,   0), new Color(175,  10, 225)};

	private Color couleur;
	private int id;
	private ArrayList<Sommet> ensSommets;

	public Region()
	{
		this.id = ++nbRegion;
		this.ensSommets = new ArrayList<Sommet>();
		if (this.id > Region.TAB_COLOR.length ) 
		{ 
			this.couleur = new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
		}
		else 
		{
			this.couleur = Region.TAB_COLOR[id-1];
		}
	
	}

	public int getId()
	{
		return this.id;
	}

	public ArrayList<Sommet> getEnsSommets()
	{
		return this.ensSommets;
	}

	public boolean aSommet(Sommet sommet)
	{
		if (this.ensSommets.contains(sommet)) { return true;}
		return false;
	}

	public void addSommet(Sommet s)
	{
		this.ensSommets.add(s);
	}

	public Color getColor()
	{
		return this.couleur;
	}
}