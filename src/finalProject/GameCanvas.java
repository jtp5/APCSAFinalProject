package finalProject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class GameCanvas extends Canvas implements KeyListener, Runnable {

	private Pirate pirate;
	private Image background;

	private boolean[] keys;
	private BufferedImage back;
	private int round;

	public GameCanvas() {
		setBackground(Color.black);

		keys = new boolean[4];
		pirate = new Pirate(300, 300, 2);
		round = 0;

		try {
			background = ImageIO.read(new File("src\\finalProject\\beach.jpg"));
		} catch (Exception e) {
			System.out.println("Image file not found");
		}

		this.addKeyListener(this);
		new Thread(this).start();

		setVisible(true);
	}

	public void update(Graphics window) {
		paint(window);
	}

	public void paint(Graphics window){
		Graphics2D twoDGraph = (Graphics2D)window;
		
		if(back==null)
			back = (BufferedImage)(createImage(getWidth(), getHeight()));
		
		Graphics graphToBack = back.createGraphics();
		
		graphToBack.drawImage(background, 0, 0, 800, 600, null);
		
		pirate.draw(graphToBack);
		
		if(keys[0] == true)
		{
			pirate.move("LEFT");
		}

		if(keys[1] == true){
			pirate.move("RIGHT");
		}
		
		twoDGraph.drawImage(back, null, 0, 0);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			keys[0] = true;
			pirate.setFacing("LEFT");
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			keys[1] = true;
			pirate.setFacing("RIGHT");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			keys[0] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			keys[1] = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while (true) {
				Thread.currentThread().sleep(5);
				repaint();
			}
		} catch (Exception e) {
		}
	}

}
