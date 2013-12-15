import java.awt.*;

public class Ball extends MetalBreaker implements Runnable
{
	static final long serialVersionUID=002;
	
	private Image img = MetalBreaker.toolkit.getImage(getClass().getResource("/ball.png"));
	private final Image imgAnim01 = MetalBreaker.toolkit.getImage(getClass().getResource("/ballAnim01.png"));
	private final Image imgAnim02 = MetalBreaker.toolkit.getImage(getClass().getResource("/ballAnim02.png"));
	private final Image imgAnim03 = MetalBreaker.toolkit.getImage(getClass().getResource("/ballAnim03.png"));
	private final Image imgAnim04 = MetalBreaker.toolkit.getImage(getClass().getResource("/ballAnim04.png"));
	
	//private final Image img = new ImageIcon("\ball.png").getImage();
	static int widthHeight=20;
	private int paso=1;
	final double Yinicial=screenHeight-widthHeight-40;
	final double Xinicial=(Barr.x+(Barr.width/2))-(widthHeight>>1);
	public double x=(screenWidth>>1)-(widthHeight>>1);
	public double y=Yinicial;
	double Xdir=0;
	int Ydir=paso;
	private int speed=5;
	boolean ballAlive=true;
	boolean stateAnimated=false;
	int animCoun=0;
	
	Ball()
	{
		x=(Barr.x+(Barr.width/2))-(widthHeight>>1);//Xinicial;
		y=Yinicial;
	}
	
	public void run()
	{
		while(ballAlive)
		{
			try{Thread.sleep(speed);}catch(Exception e){}
			switch(_state)
			{
			case state_inGame:
				if(!stateAnimated)
				{
					ballIsInBarr();
					if(MetalBreaker.blocks!=null)
						ballIsInBlock();
					move();
				}
				else
					animateBall();
				break;
			case state_Menu:
				x=(Barr.x+(Barr.width/2))-(widthHeight>>1);//Xinicial;
				y=Yinicial;
				break;
			}
		}
	}
	void animateBall()
	{
		switch(animCoun)
		{
		case 0:
			img=imgAnim01;
			break;
		case 20:
			img=imgAnim02;
			break;
		case 40:
			img=imgAnim03;
			break;
		case 60:
			img=imgAnim04;
			break;
		case 80:
			MetalBreaker.nBalls--;
			ballAlive=false;
		}
		animCoun++;
	}
	public boolean ballIsInBlock()
	{
		for(int x=0;x<MetalBreaker.VBlocks;x++)
			for(int y=0;y<MetalBreaker.HBlocks;y++)
				if(MetalBreaker.blocks[x][y]!=null)
				{
					double YBall = this.y;
					double XBall = this.x;
					try{
					if(((Xdir>0 && XBall+widthHeight>=MetalBreaker.blocks[x][y].x && XBall+widthHeight<=MetalBreaker.blocks[x][y].x+2) || (Xdir<0 && XBall<=MetalBreaker.blocks[x][y].x+Block.width && XBall>=MetalBreaker.blocks[x][y].x+Block.width-2)) && 
							YBall<=MetalBreaker.blocks[x][y].y+Block.height && YBall+widthHeight>=MetalBreaker.blocks[x][y].y)
					{
						changeXdir();
						//TODO-  block animation
						MetalBreaker.blocks[x][y]=null;
						return true;
					}
					
					if((Ydir>0 && YBall+widthHeight==MetalBreaker.blocks[x][y].y || Ydir<0 && YBall==MetalBreaker.blocks[x][y].y+Block.height) && 
							XBall+widthHeight>=MetalBreaker.blocks[x][y].x && XBall<=MetalBreaker.blocks[x][y].x+Block.width)
					{
						changeYdir();
						//TODO- block animation
						MetalBreaker.blocks[x][y]=null;
						return true;
					}
					}catch(NullPointerException e){}
				}
		return false;
	}
	
	void ballIsInBarr()
	{
		if(Ydir>0 && y+widthHeight>=Barr.y && x+widthHeight>=Barr.x && x<=Barr.x+Barr.width)
		{
			changeYdir();
			changeXdependingOnBarr((x-(MetalBreaker.screenWidth-Barr.x))-(Barr.x-(MetalBreaker.screenWidth-Barr.x)));
		}
	}
	
	void changeXdependingOnBarr(double barrXhit)
	{
		{
			Xdir=(barrXhit-(double)55)/(double)60;
		}
	}
	void changeXdir()
	{
		Xdir*=-1;
	}
	void changeYdir()
	{
		Ydir*=-1;
	}
	void move()
	{
		if(x<=0 || (x+widthHeight)>=screenWidth-9)
			changeXdir();
		if(y<=0 || (y+widthHeight)>=screenHeight-25)
			changeYdir();
		x+=Xdir;
		y+=Ydir;
		checkLose();
	}
	void checkLose()
	{
		if(y+widthHeight>=screenHeight-25)
		{
			stateAnimated=true;
		}
	}
	
	void draw(Graphics g)
	{
			g.drawImage(img, (int)x, (int)y, null);
	}
}
