package finalProject;

import javax.swing.JFrame;
import java.awt.Component;

public class PirateBrawler extends JFrame {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	public PirateBrawler()
	{
		super("PIRATEBRAWLER");
		setSize(WIDTH,HEIGHT);

		GameCanvas theGame = new GameCanvas();
		((Component)theGame).setFocusable(true);

		getContentPane().add(theGame);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		setVisible(true);
	}

	public static void main( String args[] )
	{
		PirateBrawler run = new PirateBrawler();
	}
}
