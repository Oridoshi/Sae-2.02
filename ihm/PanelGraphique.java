package ihm;

import controleur.Controleur;
import metier.*;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class PanelGraphique extends JPanel implements MouseListener
{
	private Sommet sommet1;
	private Controleur ctrl;

	public PanelGraphique(Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.addMouseListener(this);
	}

	public void paintComponent( Graphics g )
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		ArrayList<Arete> ensArete = this.ctrl.getMetier().getListArete();
		g2.setStroke(new BasicStroke( 4.0f ));
		for(int cpt=0; cpt < ensArete.size(); cpt++)
		{
			g2.setColor( ensArete.get(cpt).getColor() );
			g2.drawLine( ensArete.get(cpt).getSommet1().getPosX() * 6, ensArete.get(cpt).getSommet1().getPosY() * 6, 
			             ensArete.get(cpt).getSommet2().getPosX() * 6, ensArete.get(cpt).getSommet2().getPosY() * 6);
		}

		ArrayList<Region> ensRegion = this.ctrl.getMetier().getListRegion();
		g2.setStroke(new BasicStroke( 2.0f ));
		for(int cpt=0; cpt < ensRegion.size(); cpt++)
		{
			ArrayList<Sommet> ensSommet =  ensRegion.get(cpt).getEnsSommets();
			for (int cptSommet = 0 ; cptSommet < ensSommet.size() ; cptSommet ++ )
			{
				if (ensSommet.get(cptSommet).getIsSelected())
				{
					g2.setColor( ensRegion.get(cpt).getColor() );
					g2.fillOval( ensSommet.get(cptSommet).getPosX() * 6 - 10, ensSommet.get(cptSommet).getPosY() * 6 - 10, 24, 24);

				}
				else 
				{
					g2.setColor( ensRegion.get(cpt).getColor() );
					g2.fillOval( ensSommet.get(cptSommet).getPosX() * 6 - 8, ensSommet.get(cptSommet).getPosY() * 6 - 8, 20, 20);
					
				}		
				
				g2.setColor( Color.WHITE );
				g2.drawString(String.format("%02d", ensSommet.get(cptSommet).getId()), ensSommet.get(cptSommet).getPosX() * 6 - 5, ensSommet.get(cptSommet).getPosY() * 6 + 7 );	
			}	
		}
	}
	
	public void mouseClicked(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
	
		ArrayList<Sommet> ensSommet =  this.ctrl.getMetier().getListSommet();
		
		for (int cpt = 0 ; cpt < ensSommet.size() ; cpt ++ )
		{
			if( ensSommet.get(cpt).getPosX() * 6 -8 < x + 20  && ensSommet.get(cpt).getPosX() * 6 -8 > x - 20 &&
			    ensSommet.get(cpt).getPosY() * 6 -8 < y + 20  && ensSommet.get(cpt).getPosY() * 6 -8 > y - 20)
			{
				
				if( this.sommet1 != null && ensSommet.get(cpt) != this.sommet1 )
				{
					this.ctrl.getMetier().colorierArete(this.sommet1.getId() + "", ensSommet.get(cpt).getId() + "");
					this.sommet1.setIsSelected(false);
					this.sommet1 = null;
				}
				else
				{
					if( this.sommet1 == ensSommet.get(cpt))
					{
						this.sommet1.setIsSelected(false);
						this.sommet1 = null;
					}
					else
					{
						this.sommet1 = ensSommet.get(cpt);
						this.sommet1.setIsSelected(true);
					}
				}
				this.repaint();
			}
		}
	}
	
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	
}