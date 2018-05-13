package finalProject;

import java.awt.Color;
import java.awt.Graphics;

public abstract class MovingThing implements Locatable {
	private int xPos;
	private int yPos;
	private String facing;
	private boolean endedJump;

	public MovingThing() {
		setPos(200, 200);
		setFacing("LEFT");
		endedJump = false;
	}

	public MovingThing(int x, int y) {
		setPos(x, y);
		setFacing("LEFT");
		endedJump = false;
	}

	public void setPos(int x, int y) {
		setX(x);
		setY(y);
	}

	public void setX(int x) {
		xPos = x;
	}

	public void setY(int y) {
		yPos = y;
	}

	public void setFacing(String s) {
		facing = s;
	}

	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}

	public String getFacing() {
		return facing;
	}
	public void setEndedJump(boolean b){
		endedJump = b;
	}
	public boolean endedJump(){
		return endedJump;
	}

	public abstract void setSpeed(int s);

	public abstract int getSpeed();

	public abstract void draw(Graphics window);

	public void move(String direction) {
		if (direction.equals("LEFT"))
			setX(getX() - getSpeed());

		if (direction.equals("RIGHT"))
			setX(getX() + getSpeed());

		 if (direction.equals("UP") && !endedJump) {
				 setY(getY() - getSpeed());
				 if(getY() < 175){
					 endedJump = true;
				 }
		 }
		// if(direction.equals("DOWN"))
		// setY(getY()+getSpeed());
	}

	public String toString() {
		return "";
	}
}