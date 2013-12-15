import java.awt.*;

public class Block 
{
	//5 lineas de 8 bloques
	//x=120 inicial
	//y=10 inicial
	private final Image img = MetalBreaker.toolkit.getImage(getClass().getResource("/block.png"));
	//private final Image img = new ImageIcon("/block.png").getImage();
	static int width=60;
	static int height=20;
	double x=0;
	double y=0;
	
	Block(double x, double y)
	{
		this.x=x;
		this.y=y;
	}
	void draw(Graphics g)
	{
		g.drawImage(img,(int)x,(int)y,null);
	}
	
}
