import java.awt.*;

public class Barr 
{
	private final Image img = MetalBreaker.toolkit.getImage(getClass().getResource("/barr.png"));
	//private static final Image img = new ImageIcon("barr.png").getImage();
	private static int paso=5;
	static int width=150;
	static double x=(MetalBreaker.screenWidth>>1)-(width>>1);
	static double y=MetalBreaker.screenHeight-40;
	static long lastMoved=0;
	static long delay=10;
	
	static void moveLeft()
	{
		
		if(System.currentTimeMillis()>lastMoved+delay)
		{
			if(x>0)
				x-=paso;
			lastMoved=System.currentTimeMillis();
		}
	}
	
	static void moveRight()
	{
		if(System.currentTimeMillis()>lastMoved+delay)
		{
			if((x+width)<MetalBreaker.screenWidth)
				x+=paso;
			lastMoved=System.currentTimeMillis();
		}
	}
	
	void draw(Graphics g)
	{
		g.drawImage(img, (int)x, (int)y, null);
	}
	
}
