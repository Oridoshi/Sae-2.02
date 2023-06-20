package metier;

import java.util.*;

import controleur.Controleur;

import java.io.FileInputStream;
import iut.algo.*;
import java.awt.Color;
import java.awt.geom.Line2D;

public class Graphique
{
	private ArrayList<Sommet> lstSommet;
	private ArrayList<Arete>  lstArete;
	private ArrayList<Region> lstRegion;
	private ArrayList<Arete>  lstAreteVisite;

	private ArrayList<Sommet> lstSommetVisite;
	private ArrayList<Sommet> lstCouleurVisite;

	private int pointsRouge;
	private int pointsBleu;

	private int   changement;
	private int   tour;

	private Color couleur;

	private Controleur ctrl;


	public Graphique(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.lstSommet = new ArrayList<Sommet>();
		this.lstArete  = new ArrayList<Arete> ();
		this.lstRegion = new ArrayList<Region>();

		this.lstSommetVisite = new ArrayList<Sommet>();
		this.lstCouleurVisite = new ArrayList<Sommet>();
		this.lstAreteVisite = new ArrayList<Arete>();
		
		this.pointsRouge = 0;
		this.pointsBleu = 0;

		this.changement = (int)(Math.random()*5) + 5;
		this.tour = 0;

		this.couleur = Color.RED;

		this.lecteurFichier();
	}

	// Lecture du fichier "Matrice.data"
	public void lecteurFichier()
	{
		try
		{
			Scanner sc = new Scanner ( new FileInputStream ( "./data/Matrice.data" ) );
			sc.nextLine();

			// BOUCLE NOEUDS (SOMMETS) //
			String ligne = sc.nextLine();
			do
			{
				Decomposeur dec = new Decomposeur(ligne);
				this.lstSommet.add( new Sommet(dec.getInt(0), dec.getInt(1), dec.getInt(2)) );

				ligne = sc.nextLine();
			} while(!ligne.isEmpty());

			sc.nextLine();

			// BOUCLE REGIONS //			
			ligne = sc.nextLine();
			do
			{
				Region regionTmp = new Region();

				String[] tabString = ligne.split("	");

				for (int cpt = 1; cpt < tabString.length; cpt++)
				{
					regionTmp.addSommet(lstSommet.get(Integer.parseInt(tabString[cpt]) - 1));
				}

				this.lstRegion.add(regionTmp);
				
				ligne = sc.nextLine();
			} while(!ligne.isEmpty());

			sc.nextLine();

			// BOUCLE ARETES //
			ligne = sc.nextLine();
			do
			{
				Sommet   sommetTmp1 = null;
				Sommet   sommetTmp2 = null;
				String[] tabString  = ligne.split("	");

				for(int cpt = 0; cpt < tabString.length; cpt++)
				{
					if(cpt % 2 == 0)
						sommetTmp1 = this.lstSommet.get(Integer.parseInt(tabString[cpt]) - 1);
					else
						sommetTmp2 = this.lstSommet.get(Integer.parseInt(tabString[cpt]) - 1);

					if(sommetTmp1 != null && sommetTmp2 != null)
						this.lstArete.add(new Arete(sommetTmp1, sommetTmp2));
				}

				ligne = sc.nextLine();
			} while(!ligne.isEmpty());

			if (sc.hasNextLine() && sc.nextLine().equals("[BONUS]"))
			{
				// BOUCLE BONUS //
				int bonus = Integer.parseInt(sc.nextLine()); // Récupération de la valeur du bonus
				
				while( sc.hasNextLine())
				{
					ligne = sc.nextLine();

					Decomposeur dec = new Decomposeur(ligne);
					
					this.getArete(this.getSommet(dec.getInt(0)), this.getSommet(dec.getInt(1))).setBonus(bonus);
				}
			}

			sc.close();
		}
		catch (Exception e){ e.printStackTrace(); }
	}

	public void colorierArete(String som1, String som2)
	{
		String tmp = VerifColoriage(som1, som2);

		this.ctrl.majLabelIHM(tmp);

		if( tmp.equals("L'arete a bien été colorié"))
		{
			this.changerCouleur();
		}
	}

	public boolean changerCouleur()
	{
		if(this.tour++ == this.changement)
		{
			if(this.couleur == Color.RED)
			{
				this.changement = (int)(Math.random()*5) + 5;
				this.tour       = 0;
				this.couleur    = Color.BLUE;
				this.resetCouleurVisite();
				this.ctrl.majCouleur(this.couleur);
				return true;
			}
			
			if( this.couleur == Color.BLUE )
			{
				this.ctrl.maj();
				this.ctrl.fin(totalPoints());
			}
		}

		this.ctrl.maj();
		return false;
	}

	// Vérifie si l'arête que l'on souhaite colorier remplit toutes les contraintes
	private String VerifColoriage(String som1, String som2)
	{
		Sommet sommet1;
		Sommet sommet2;
		Arete  arete1;

		// Vérifie que les deux sommets existes
		try
		{
			sommet1 = this.getSommet(Integer.parseInt(som1));
			sommet2 = this.getSommet(Integer.parseInt(som2));
		} catch (Exception e)
		{
			return( "Veuillez entrer des sommets valides" );
		}

		// Vérifie que l'arête existe
		arete1 = this.getArete(sommet1, sommet2);
		if(arete1 == null)
			return("Il n'y a pas d'arête entre vos 2 sommets");

		// Vérifie que l'arête n'est pas déjà possédé
		if (arete1.getColor() != Color.BLACK && arete1.getColor() != Color.GREEN) 
		{ 
			return "L'arete est déjà colorié";
		}

		// Vérifie que l'on part d'un sommet visité
		boolean sommetVisite = false;
		for (Sommet sommet : this.lstSommetVisite)
		{
			if (sommet == sommet1 || sommet == sommet2)
			{
				sommetVisite = true;
			}
		}

		if (!sommetVisite && this.lstSommetVisite.size() != 0 ) { return "Vous partez d'un sommet non-visité"; }

		// Vérifie que ça ne forme pas un cycle
		if (this.lstCouleurVisite.contains(sommet1) && this.lstCouleurVisite.contains(sommet2) ) 
		{ 
			return "Cette arete forme un cycle"; 
		}

		// Vérifie que l'arête ne croise pas une autre arête
		for (Arete arete2 : this.lstAreteVisite)
		{
			int[][] segment = {   {arete1.getSommet1().getPosX(), arete1.getSommet1().getPosY() ,
			                       arete1.getSommet2().getPosX(), arete1.getSommet2().getPosY()},
			                      {arete2.getSommet1().getPosX(), arete2.getSommet1().getPosY() , 
			                       arete2.getSommet2().getPosX(), arete2.getSommet2().getPosY()} };
			
			boolean intersect = doEdgesIntersect(segment[0], segment[1]);

			if (intersect) 
			{
				if (arete2.getColor() != Color.BLACK && arete2.getColor() != Color.GREEN) 
				{
					return "L'arete croise une arete déjà colorié";
				}
			}
		}

		// Change la couleur dans la classe Arete
		arete1.setCouleur(couleur);
		
		// Ajoute les arêtes visités à une liste pour la vérification de croisement
		this.lstAreteVisite.add(arete1);

		// Ajoute les sommets visités à des listes pour le comptage de points et la vérification de cycle
		if (!this.lstSommetVisite.contains(sommet1)) { this.lstSommetVisite.add(sommet1); }
		if (!this.lstSommetVisite.contains(sommet2)) { this.lstSommetVisite.add(sommet2); }

		if (!this.lstCouleurVisite.contains(sommet1)) { this.lstCouleurVisite.add(sommet1); }
		if (!this.lstCouleurVisite.contains(sommet2)) { this.lstCouleurVisite.add(sommet2); }

		return "L'arete a bien été colorié";
	}

	public Sommet getSommet(int id)
	{
		for (Sommet sommet : this.lstSommet)
		{
			if (sommet.getId() == id) { return sommet;}
		}
		return null;
	}
	
	public Arete getArete(Sommet s1, Sommet s2)
	{
		for (Arete arete : this.lstArete)
		{
			if (arete.getSommet1() == s1 && arete.getSommet2() == s2 ||
			    arete.getSommet1() == s2 && arete.getSommet2() == s1) { return arete;}
		}
		return null;
	}

	public ArrayList<Sommet> getListSommet()
	{
		return this.lstSommet;
	}

	public ArrayList<Arete> getListArete()
	{
		return this.lstArete;
	}

	public ArrayList<Region> getListRegion()
	{
		return this.lstRegion;
	}

	// Vérifie si deux segments se croisent
	public static boolean doEdgesIntersect(int[] cord1, int[] cord2)
	{
		// Création des objets Line2D pour représenter les arêtes
		Line2D line1 = new Line2D.Double(cord1[0], cord1[1], cord1[2], cord1[3]);
		Line2D line2 = new Line2D.Double(cord2[0], cord2[1], cord2[2], cord2[3]);
	
		// Vérification si les lignes se croisent
		if (line1.intersectsLine(line2)) { // Les arêtes se croisent

			// Vérification si les arêtes sont adjacentes
			if (line1.getP1().equals(line2.getP1()) || line1.getP1().equals(line2.getP2()) || line1.getP2().equals(line2.getP1()) || line1.getP2().equals(line2.getP2()))
			{
				return false; // Les arêtes sont adjacentes, elles ne se croisent pas
			}	
			return true; // Les arêtes se croisent
		}
		return false; // Les arêtes ne se croisent pas
	}

	// Réinitialise le tableau qui contient les sommets visités par une couleur 
	public void resetCouleurVisite()
	{
		this.pointsRouge = this.compterPoints();
		this.lstCouleurVisite = new ArrayList<Sommet>();
	}

	// Compte les points d'une couleur
	public int compterPoints()
	{
		int sommetsMax = 0;
		int nbRegion = 0;
		ArrayList<Region> lstRegionVisite = new ArrayList<Region>();
		
		for (Region region : this.lstRegion)
		{
			int CouleurSomMax = 0;
			for (Sommet sommet : this.lstCouleurVisite)
			{
				if (region.aSommet(sommet) ) 
				{ 
					if (!lstRegionVisite.contains(region))
					{
						nbRegion ++; 
						lstRegionVisite.add(region);
					}
					CouleurSomMax ++;
				}
			}
			if (CouleurSomMax > sommetsMax) { sommetsMax = CouleurSomMax;}
		}
		return nbRegion * sommetsMax;
	}

	public Color getCouleurActu(){return this.couleur;}
	// Compte les points totals des deux couleurs et additionne le bonus
	public String totalPoints()
	{
		int bonus = 0;
		this.pointsBleu = this.compterPoints();
		for (Arete arete : this.lstArete)
		{
			if (arete.getColor() != Color.BLACK && arete.getColor() != Color.GREEN)
			{
				bonus += arete.getValeur();
			}
		}

		String sRet = "Total Rouge : " + this.pointsRouge + "\n" +
		              "Total Bleu : "  + this.pointsBleu  + "\n" +
		              "Total Bonus : " + bonus            + "\n" +
		              "Total : "       + (this.pointsRouge + this.pointsBleu + bonus);

		return sRet;
	}
}