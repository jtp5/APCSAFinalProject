package finalProject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Pirate extends MovingThing {
	private int speed;
	private Image image;

	public Pirate() {
		this(0, 0, 0);
	}

	public Pirate(int x, int y) {
		this(x, y, 0);
	}

	public Pirate(int x, int y, int s) {
		super(x, y);
		speed = s;
		try {
			image = ImageIO.read(new File("src\\finalProject\\pirate.png"));
		} catch (Exception e) {
			System.out.println("Image file not found");
		}
	}

	@Override
	public void setSpeed(int s) {
		// TODO Auto-generated method stub
		speed = s;
	}

	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return speed;
	}

	@Override
	public void draw(Graphics window) {
		// TODO Auto-generated method stub
		window.drawImage(image,  getX(),  getY(),  80, 80, null);
	}

}
