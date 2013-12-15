import javax.swing.*;

public class Main
{
	public static void main(String[]args)
	{
		JFrame f = new JFrame("Metal Breaker");
		MetalBreaker game= new MetalBreaker();
		
		f.setSize(MetalBreaker.screenWidth,MetalBreaker.screenHeight+10);
		f.addKeyListener(game);
		f.add(game);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		new Thread(game).start();
	}
}