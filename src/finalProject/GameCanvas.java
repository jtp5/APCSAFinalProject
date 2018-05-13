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
	private int timer;
	private int shotTimer;
	private int lives;
	private boolean roundInProgress;

	private ArrayList<Bullet> shots;
	private Skeletons skeletons;

	public GameCanvas() {
		setBackground(Color.black);

		keys = new boolean[4];
		pirate = new Pirate(300, 300, 2);
		round = 1;
		timer = 0;
		shotTimer = 0;
		lives = 3;
		roundInProgress = true;
		shots = new ArrayList<Bullet>();
		skeletons = new Skeletons();

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

	public void paint(Graphics window) {
		Graphics2D twoDGraph = (Graphics2D) window;
		System.out.println(timer);
		System.out.println(skeletons.getList().size());
		timer++;
		shotTimer++;
		if (back == null)
			back = (BufferedImage) (createImage(getWidth(), getHeight()));

		Graphics graphToBack = back.createGraphics();

		graphToBack.drawImage(background, 0, 0, 800, 600, null);
		
		graphToBack.drawString("Round: " + round, 500, 550);
		
		graphToBack.drawString("Lives: " + lives, 575, 550);

		pirate.draw(graphToBack);

		if(skeletons.getList().size() == round * 2) {
			roundInProgress = false;
		for (int i = 0; i < skeletons.getList().size(); i++) {
			if(skeletons.getList().get(i).isAlive()) {
				roundInProgress = true;
			}
		}
		}
		
		if(!roundInProgress) {
			skeletons.getList().clear();
			round++;
			roundInProgress = true;
		}
		
		if (skeletons.getList().size() < round * 2) {
			for (int i = 0; i < round; i++) {
				if (timer >= 200) {
					if (skeletons.getList().size() % 2 == 0) {
						skeletons.add(new Skeleton(-100, 300, 1));
						timer = 0;
						System.out.println("test");
					} else {
						skeletons.add(new Skeleton(900, 300, 1));
						timer = 0;
					}
				}
			}
		}
		
		for (int i = 0; i < skeletons.getList().size(); i++) {
			if(skeletons.getList().get(i).isAlive())
			skeletons.getList().get(i).draw(graphToBack);
		}

		for (int i = 0; i < skeletons.getList().size(); i++) {
			if (skeletons.getList().get(i).getX() < pirate.getX() && skeletons.getList().get(i).isAlive()) {
				skeletons.getList().get(i).move("RIGHT");
			} else if(skeletons.getList().get(i).isAlive()) {
				skeletons.getList().get(i).move("LEFT");
			}
		}

		if (pirate.endedJump()) {
			pirate.setY(pirate.getY() + pirate.getSpeed());
		}

		if (pirate.getY() >= 300) {
			if (pirate.endedJump()) {
				keys[2] = false;
			}
			pirate.setEndedJump(false);
		}

		if (keys[0] == true) {
			pirate.move("LEFT");
		}

		if (keys[1] == true) {
			pirate.move("RIGHT");
		}
		if (keys[2] == true && pirate.getY() <= 300 && pirate.getY() >= pirate.getY() - 125) {
			pirate.move("UP");
		}
		if(keys[3] == true && shotTimer >= 200){
			if(pirate.getFacing().equals("LEFT")){
				shots.add(new Bullet(pirate.getX(), pirate.getY() + 40, 5, "LEFT"));
				shotTimer = 0;
			}
			else{
				shots.add(new Bullet(pirate.getX() + 40, pirate.getY() + 40, 5, "RIGHT"));
				shotTimer = 0;
			}
		}
		
		for (int i = 0; i < shots.size(); i++) {
			shots.get(i).draw(graphToBack);
			shots.get(i).move(shots.get(i).getFacing());
			if(shots.get(i).getX() < -10 || shots.get(i).getX() > 810)
				shots.remove(i);
		}
		
		for(int i = 0; i < shots.size(); i++){
			for(int j = 0; j < skeletons.getList().size(); j++){
				if(shots.get(i).getX() < skeletons.getList().get(j).getX() + 80 && shots.get(i).getX() > skeletons.getList().get(j).getX() && shots.get(i).getY() > skeletons.getList().get(j).getY() && skeletons.getList().get(j).isAlive()){
					skeletons.getList().get(j).setAlive(false);
					shots.remove(i);
				}
			}
		}
		
		
		twoDGraph.drawImage(back, null, 0, 0);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keys[0] = true;
			pirate.setFacing("LEFT");
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			keys[1] = true;
			pirate.setFacing("RIGHT");
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			keys[2] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			keys[3] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keys[0] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			keys[1] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			keys[3] = false;
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
