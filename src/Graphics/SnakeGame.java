package Graphics;

import SnakeObjects.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class SnakeGame extends JPanel implements KeyListener
{
	public static final int fieldSize = 20;
	private static final int updateDelayMS = (int) (Snake.partSize * 2.5f);

	Snake snake;
	boolean isGameRunning = false;
	private Snake.diraction newDirection;
	private Point food;

	//private static final Image FoodImg;
	//private static final Image HeadImg;
	//private static final Image BodyImg;
	private static final Rectangle FoodImg = new Rectangle(Snake.partSize, Snake.partSize);
	private static final Rectangle HeadImg = new Rectangle(Snake.partSize, Snake.partSize);
	private static final Rectangle BodyImg = new Rectangle(Snake.partSize, Snake.partSize);

	public SnakeGame()
	{

		snake = new Snake(fieldSize);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e)
			{
				System.out.println(e.getKeyChar());
				newDirection = getDirectionByKey(e);
			}
		});
	}

	private boolean gameCycle()
	{
		if(snake.ateAllFood)
			createFood();
		isGameRunning = snake.update(fieldSize, food, newDirection);
		newDirection = null;
		if(!isGameRunning)
			if(snake.isAlive())
				System.out.println("WON");
			else
				System.out.println("LOST");
		this.repaint();
		return isGameRunning;
	}

	private Point createFood()
	{

		Point foodPos = new Point();
		Random random = new Random(System.currentTimeMillis());

		Point[] currentSnake = snake.getSnakePositions();
		boolean matchesSnake;
		do
		{
			foodPos.x = random.nextInt(fieldSize) * Snake.partSize; //Для округления до ближайшего кратного Snake.partSize числа
			foodPos.y = random.nextInt(fieldSize) * Snake.partSize;

			matchesSnake = false;

			for (Point p : currentSnake)
				if((Point.distance(p.x, p.y, foodPos.x, foodPos.y)) < 1)
				{
					System.out.println("Tried to generate food inside of a snake");
					matchesSnake = true;
				}
		}while (matchesSnake);

		snake.ateAllFood = false;
		System.out.println("Food created");

		return food = foodPos;
	}

	private Snake.diraction getDirectionByKey(KeyEvent keyEvent)
	{
		switch (keyEvent.getKeyCode())
		{
			case KeyEvent.VK_DOWN:
				return Snake.diraction.down;
			case KeyEvent.VK_UP:
				return Snake.diraction.up;
			case KeyEvent.VK_LEFT:
				return Snake.diraction.left;
			case KeyEvent.VK_RIGHT:
				return Snake.diraction.right;
		}
		return null;
	}


	@Override
	public void paintComponent(Graphics gg)
	{
		Graphics2D g = (Graphics2D) gg;//.create();
		super.paintComponent(gg);
		Point[] snakeP = snake.getSnakePositions();

		g.setColor(Color.black);
		g.fillRect(0,0, fieldSize * Snake.partSize, fieldSize * Snake.partSize);

		g.setColor(Color.yellow); //HEAD COLOR
		g.fillRect(snakeP[0].x, snakeP[0].y, HeadImg.width, HeadImg.height); //Head shape

		g.setColor(Color.red); // Food Color
		g.fillRect(food.x, food.y, FoodImg.width, FoodImg.height); //Food shape

		g.setColor(Color.green);
		for(int i = 1; i < snakeP.length; i++)
		{
			g.fillRect(snakeP[i].x, snakeP[i].y, BodyImg.width, BodyImg.height);
		}
	}

	@Override
	public void keyTyped(KeyEvent e){}

	@Override
	public void keyPressed(KeyEvent e)
	{
		newDirection = getDirectionByKey(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	public void startGame()
	{
		while(gameCycle())
		{
			try {
				Thread.sleep(updateDelayMS);
			} catch (InterruptedException e){}
		}
		System.out.println(snake.getSnakePositions().length - Snake.DEFAULT_SNAKE_LENGTH);
		System.out.println("Game over!");
	}
}
