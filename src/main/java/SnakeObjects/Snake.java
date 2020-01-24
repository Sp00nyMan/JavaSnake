package SnakeObjects;

import java.awt.*;

import static SnakeObjects.Snake.diraction.*;

public class Snake
{
	public static final int partSize = 45;

	private class BodyPart
	{
		private int x;
		private int y;

		public BodyPart(final int x, final int y)
		{
			this.x = x;
			this.y = y;
		}

		public BodyPart(final BodyPart other)
		{
			this.x = other.x;
			this.y = other.y;
		}

		public void update(final int XOffset, final int YOffset)
		{
			x += XOffset;
			y += YOffset;
		}

		public void update(final BodyPart other)
		{
			this.x = other.x;
			this.y = other.y;
		}

		int getX() { return x; }

		int getY() { return y; }
	}

	public enum diraction
	{
		up,
		down,
		left,
		right
	}

	public static final int DEFAULT_SNAKE_LENGTH = 4;
	private BodyPart[] body = new BodyPart[DEFAULT_SNAKE_LENGTH];
	private diraction dir = left;
	private boolean alive = true;
	public boolean ateAllFood = true;

	public Snake(final int fieldSize)
	{
		int xOffset = 0, yOffset = 0;
		switch (dir)
		{
			case left:
				xOffset = partSize;
				break;
			case right:
				xOffset = -partSize;
				break;
			case up:
				yOffset = partSize;
				break;
			case down:
				yOffset = -partSize;
				break;
		}

		for (int i = 0; i < DEFAULT_SNAKE_LENGTH; i++)
			body[i] = new BodyPart((fieldSize / 2 * partSize) + xOffset * i - partSize, (fieldSize / 2 * partSize) + yOffset * i - partSize);
	}


	public boolean update(final int fieldSize, Point foodPos, diraction newDir)
	{
		BodyPart headCopy = new BodyPart(body[0]);
		// move snake's head
		if (newDir == null)
			newDir = dir;
		move(newDir, headCopy);
		// check for obstacle
		if (headCopy.x < 0 || headCopy.x > fieldSize * partSize - 1 || headCopy.y < 0 || headCopy.y > fieldSize * partSize - 1)
		{
			System.out.println("Collided a wall");
			return alive = false;
		}
		// check if collided with itself
		for (int i = 1; i < body.length; i++)
			if (headCopy.x == body[i].x && headCopy.y == body[i].y)
			{
				System.out.println("Collided itself");
				return alive = false;
			}

		//Move all the parts
		for (int i = body.length - 1; i > 0; i--)
			body[i].update(body[i - 1]);

		//If ate food create new part
		if (headCopy.x == foodPos.x && headCopy.y == foodPos.y)
		{
			ateAllFood = true;
			expand();
		}
		body[0].update(headCopy);

		if (body.length == fieldSize * fieldSize)
		{
			System.out.println("WON");
			return false;
		}
		return alive;
	}

	public Point[] getSnakePositions()
	{
		Point[] points = new Point[body.length];

		for (int i = 0; i < body.length; i++)
			points[i] = new Point(body[i].x, body[i].y);

		return points;
	}

	public boolean isAlive() {return alive;}

	private void expand()
	{
		//System.out.println("Expanded");
		BodyPart[] temp = new BodyPart[body.length + 1];

		int i = 0;
		for (BodyPart bp : body)
		{
			temp[i++] = new BodyPart(bp);
		}
		temp[i] = new BodyPart(body[body.length - 1]);

		body = temp;
	}

	void move(diraction newDirection, BodyPart headPart)
	{
		int xOffset = 0, yOffset = 0;

		switch (newDirection)
		{
			case up:
				if (dir == down)
				{
					System.out.println("Tried to move up while moving down");
					yOffset = partSize;
					newDirection = dir;
				}
				else
					yOffset = -partSize;
				break;
			case down:
				if (dir == up)
				{
					System.out.println("Tried to move down while moving up");
					yOffset = -partSize;
					newDirection = dir;
				}
				else
					yOffset = partSize;
				break;
			case left:
				if (dir == right)
				{
					System.out.println("Tried to move left while moving right");
					xOffset = partSize;
					newDirection = dir;
				}
				else
					xOffset = -partSize;
				break;
			case right:
				if (dir == left)
				{
					System.out.println("Tried to move right while moving left");
					xOffset = -partSize;
					newDirection = dir;
				}
				else
					xOffset = partSize;
				break;
		}

		dir = newDirection;
		headPart.update(xOffset, yOffset);
	}
}
