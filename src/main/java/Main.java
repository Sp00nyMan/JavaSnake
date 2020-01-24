import Graphics.SnakeWindow;

public class Main
{
	public static void main(String[] args)
	{
		Runnable gameRunnable = new Runnable() {
			@Override
			public void run()
			{
				SnakeWindow sw = new SnakeWindow();
				sw.startGame();
			}
		};
		new Thread(gameRunnable).start();
	}
}
