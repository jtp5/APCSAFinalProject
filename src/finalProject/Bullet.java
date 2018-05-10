package finalProject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class Bullet extends MovingThing
{
	private int speed;

	public Bullet()
	{
		this(0,0,0, "LEFT");
	}

	public Bullet(int x, int y)
	{
		this(x, y, 0, "LEFT");
	}

	public Bullet(int x, int y, int s, String direction)
	{
		setX(x);
		setY(y);
		setSpeed(s);
		setFacing(direction);
	}

	public void setSpeed(int s)
	{
	   speed = s;
	}

	public int getSpeed()
	{
	   return speed;
	}

	public void draw( Graphics window )
	{
		window.setColor(Color.YELLOW);
		window.fillRect(getX(), getY(), 10, 10);
		//window.drawRect(getX(), getY(), 10, 10);
	}

	public String toString()
	{
		return "";
	}
}
