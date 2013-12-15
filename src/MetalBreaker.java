import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MetalBreaker extends Canvas implements Runnable, KeyListener
{
	static final long serialVersionUID=001;
	static Toolkit toolkit = Toolkit.getDefaultToolkit();           

	Thread thread=null;
	boolean playing=true;
	
	static final int state_Menu=0;
	static final int state_inGame=1;
	static final int state_showLevel=2;
	static int _state=state_showLevel;
	
	static final int screenWidth=600;
	static final int screenHeight=400;
	static int nBalls=0;
	static int maxBalls=200;
	Ball [] balls = new Ball[maxBalls];
	static int HBlocks=10;
	static int VBlocks=5;
	static Block [][] blocks = new Block[VBlocks][HBlocks];
	Barr barr = new Barr();
	static int Level=1;
	static int upLevelCount=0;
	boolean moveRight=false;
	boolean moveLeft=false;
	
	MetalBreaker()
	{
		setBackground(Color.white);
	}
	
	void paintLevel( Graphics g)
	{
		switch(upLevelCount)
		{
			case 1000:
				upLevelCount=0;
				if(Level!=4)
				{
					initGame();
					_state=state_Menu;
				}
				break;
			case 0:
				moveRight=false;
				moveLeft=false;
			default:
				g.setColor(Color.BLACK);
				g.setFont(new Font("SansSerif", Font.BOLD, 48));
				if(Level==4)
					g.drawString("YOU WIN!",(screenWidth>>1)-100,screenHeight>>1);
				else
					g.drawString("Level: "+Level,(screenWidth>>1)-100,screenHeight>>1);
				upLevelCount++;
		}
	}

	void initGame()
	{
		nBalls=0;
		initBalls();
		initBlocks();

		Barr.x=(MetalBreaker.screenWidth>>1)-(Barr.width>>1);
		Barr.y=MetalBreaker.screenHeight-40;
		
		addNewBall();
	}
	
	void initBalls()
	{
		for(int i=0;i<maxBalls;i++)
		{
			if(balls[i]!=null)
				balls[i].ballAlive=false;
			balls[i]=null;
		}
	}
	void initBlocks()
	{
		int xInicial=0;
		int yMas=10;
		switch(Level)
		{
			case 1:
				xInicial=(screenWidth-(Block.width*VBlocks))>>1;
				break;
			case 2:
			case 3:
				xInicial=Block.width>>1;
		}
		
		for(int x=0;x<VBlocks;x++)
		{
			int yTmp=yMas;
			for(int y=0;y<HBlocks;y++)
			{
				blocks[x][y]=new Block(xInicial,((y*Block.height)+10)+(Level==3?yTmp:0));
				yTmp+=10;
			}
			xInicial+=Block.width+(Level>1?Block.width:0);
		}
	}
	
	public void run()
	{
		while(playing)
		{
			repaint();
			try{Thread.sleep(10);}catch(Exception e){}
		}
	}

	public void addNewBall()
	{
		if(nBalls<maxBalls)
		{
			for(int i=0;i<maxBalls;i++)
				if((balls[i]!=null && !balls[i].ballAlive) || balls[i]==null)
				{
					balls[i]=new Ball();
					new Thread(balls[i]).start();
					break;
				}
			nBalls++;
		}
		
	}
	public void keyPressed(KeyEvent key)
	{
		int code = key.getKeyCode();
		switch(_state)
		{
			case state_showLevel:
			{
				if(code==KeyEvent.VK_ESCAPE)
				{
					if(Level!=1)
					{
						upLevelCount=0;
						Level=1;
						_state=state_showLevel;
					}
					
				}
			}
			break;
			case state_Menu:
			{
				if(code==KeyEvent.VK_SPACE)
				{
					_state=state_inGame;
					break;
				}
			}
			case state_inGame:
			{
				if(code==KeyEvent.VK_LEFT)
				{
					moveLeft=true;
				}
				else if(code==KeyEvent.VK_RIGHT)
				{
					moveRight=true;
				}
				else if(code==KeyEvent.VK_SPACE)
				{
					addNewBall();
				}
				else if(code==KeyEvent.VK_ESCAPE)
				{
					Level=1;
					_state=state_showLevel;
					
				}
			}
			break;
		}
	}
	
	public void keyReleased(KeyEvent key)
	{
		int code = key.getKeyCode();
		switch(_state)
		{
			case state_Menu:
			case state_inGame:
			{
				if(code==KeyEvent.VK_LEFT)
				{
					moveLeft=false;
				}
				else if(code==KeyEvent.VK_RIGHT)
				{
					moveRight=false;
				}
			}
		}
	}
    public void keyTyped(KeyEvent event){}
    
    void drawBlocks(Graphics g)
    {
		for(int x=0;x<VBlocks;x++)
			for(int y=0;y<HBlocks;y++)
			{
				if(blocks[x][y]!=null)
					blocks[x][y].draw(g);
			}
    }
    /*
    void freeMemory()
    {
    	for(int i=0;i<maxBalls;i++)
			if(balls[i]!=null)
				if(balls[i].ballAlive==false)
					balls[i]=null;
    }
    */
    void paintBalls(Graphics g)
    {
    	for(int i=0;i<maxBalls;i++)
			if(balls[i]!=null && balls[i].ballAlive)
				balls[i].draw(g);
    }
    boolean emptyBlocks()
    {
    	if(blocks!=null)
    	{
	    	for(int x=0;x<VBlocks;x++)
				for(int y=0;y<HBlocks;y++)
					if(blocks[x][y]!=null)
						return false;
    	}
    	else
    		return false;
		return true;
    }
	public void update(Graphics g){paint(g);}
	Image bBuffer=null;
	Graphics bBg=null;
	public void paint(Graphics g)
	{
		//super.paint(g);
		
		if(bBuffer==null)
		{
			bBuffer=createImage(screenWidth,screenHeight);
			bBg=bBuffer.getGraphics();
		}
		
		bBg.setColor(Color.WHITE);
		bBg.fillRect(0,0,screenWidth,screenHeight);
		
		switch(_state)
		{
			case state_Menu:
			case state_inGame:
				if(moveRight)
					Barr.moveRight();
				else if(moveLeft)
					Barr.moveLeft();
				
				drawBlocks(bBg);
				paintBalls(bBg);
				barr.draw(bBg);
				if(emptyBlocks())
				{
					Level++;
					_state=state_showLevel;
				}
				break;
			case state_showLevel:
				paintLevel(bBg);
		}
		g.drawImage(bBuffer,0,0,null); 
	}
}
