package finalProject;

import java.awt.Color;
import java.awt.Font;
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
	private Font menuFont;
	private Font instructionsFont;
	private Font statsFont;

	private ArrayList<Bullet> shots;
	private Skeletons skeletons;
	
	final int MENU_STATE = 0;
	final int GAME_STATE = 1;
	final int END_STATE = 2;
	private int CURRENT_STATE = MENU_STATE;

	public GameCanvas() {
		setBackground(Color.black);

		keys = new boolean[5];
		pirate = new Pirate(300, 300, 2);
		round = 1;
		timer = 0;
		shotTimer = 0;
		lives = 3;
		roundInProgress = true;
		menuFont = new Font("Ariel", Font.BOLD, 36);
		instructionsFont = new Font("Ariel", Font.BOLD, 24);
		statsFont = new Font("Ariel", Font.BOLD, 18);
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
		if(keys[4] == true) {
			CURRENT_STATE = GAME_STATE;
		}
		if(CURRENT_STATE == MENU_STATE) {
			paintIntroScreen(window);
		}
		else if(CURRENT_STATE == GAME_STATE) {
			paint(window);
		}
	}
	
	public void paintIntroScreen(Graphics window) {
		window.setColor(Color.BLACK);
		window.setFont(menuFont);
		window.drawString("Pirate Brawler", 280, 180);
		window.setFont(instructionsFont);
		window.drawString("By Jones Pearlman", 290, 205);
		window.drawString("Use the arrowkeys for movement", 220, 230);
		window.drawString("and the spacebar to shoot", 250, 250);
		window.drawString("press 's' to begin", 290, 270);
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
		
		graphToBack.setFont(statsFont);

		graphToBack.drawString("Round: " + round, 500, 550);

		graphToBack.drawString("Lives: " + lives, 590, 550);

		if (lives > 0) {
			pirate.draw(graphToBack);

			if (skeletons.getList().size() == round * 2) {
				roundInProgress = false;
				for (int i = 0; i < skeletons.getList().size(); i++) {
					if (skeletons.getList().get(i).isAlive()) {
						roundInProgress = true;
					}
				}
			}

			if (!roundInProgress) {
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
				if (skeletons.getList().get(i).isAlive())
					skeletons.getList().get(i).draw(graphToBack);
			}

			for (int i = 0; i < skeletons.getList().size(); i++) {
				if (skeletons.getList().get(i).getX() < pirate.getX() && skeletons.getList().get(i).isAlive()) {
					skeletons.getList().get(i).move("RIGHT");
				} else if (skeletons.getList().get(i).isAlive()) {
					skeletons.getList().get(i).move("LEFT");
				}
			}

			for (int i = 0; i < skeletons.getList().size(); i++) {
				if (skeletons.getList().get(i).getX() >= pirate.getX()
						&& skeletons.getList().get(i).getX() <= pirate.getX() + 40
						&& skeletons.getList().get(i).getY() > pirate.getY() - 20
						&& skeletons.getList().get(i).getY() < pirate.getY() + 60
						&& skeletons.getList().get(i).isAlive()) {
					skeletons.getList().get(i).setAlive(false);
					lives--;
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
			if (keys[3] == true && shotTimer >= 200) {
				if (pirate.getFacing().equals("LEFT")) {
					shots.add(new Bullet(pirate.getX(), pirate.getY() + 40, 5, "LEFT"));
					shotTimer = 0;
				} else {
					shots.add(new Bullet(pirate.getX() + 40, pirate.getY() + 40, 5, "RIGHT"));
					shotTimer = 0;
				}
			}

			for (int i = 0; i < shots.size(); i++) {
				shots.get(i).draw(graphToBack);
				shots.get(i).move(shots.get(i).getFacing());
				if (shots.get(i).getX() < -10 || shots.get(i).getX() > 810)
					shots.remove(i);
			}

			for (int i = 0; i < shots.size(); i++) {
				for (int j = 0; j < skeletons.getList().size(); j++) {
					if (shots.get(i).getX() < skeletons.getList().get(j).getX() + 80
							&& shots.get(i).getX() > skeletons.getList().get(j).getX()
							&& shots.get(i).getY() > skeletons.getList().get(j).getY()
							&& skeletons.getList().get(j).isAlive()) {
						skeletons.getList().get(j).setAlive(false);
						shots.remove(i);
					}
				}
			}
		}
		
		else {
			graphToBack.setFont(menuFont);
			graphToBack.drawString("GAME OVER", 280, 180);
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
		if(e.getKeyCode() == KeyEvent.VK_S) {
			keys[4] = true;
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
		if(e.getKeyCode() == KeyEvent.VK_S) {
			keys[4] = false;
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
