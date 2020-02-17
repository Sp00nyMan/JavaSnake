package Graphics;

import SnakeObjects.Snake;
import javax.swing.*;

public class SnakeWindow extends JFrame
{
	public SnakeWindow()
	{
		super("SNAKE");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, SnakeGame.fieldSize * Snake.partSize + 15, SnakeGame.fieldSize * Snake.partSize + 38);

		setVisible(true);
	}

	public void startGame()
	{

		SnakeGame snakeGame = new SnakeGame();

		add(snakeGame);
		addKeyListener(snakeGame);

		snakeGame.startGame();
		setVisible(false);
	}
}