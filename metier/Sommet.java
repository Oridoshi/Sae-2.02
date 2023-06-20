package metier;

public class Sommet
{
	private int id;
	private int posX;
	private int posY;
	private boolean isSelected;

	public Sommet(int id, int posX, int posY)
	{
		this.id         = id;
		this.posX       = posX;
		this.posY       = posY;
		this.isSelected = false;
	}
	
	public int getId  (){ return this.id; }
	public int getPosX(){return this.posX;}
	public int getPosY(){return this.posY;}
	public boolean getIsSelected() { return this.isSelected; }

	public void setIsSelected(boolean select)
	{
		this.isSelected = select;
	}
}
