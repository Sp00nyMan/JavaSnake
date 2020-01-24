import Graphics.SnakeWindow;

public class Main
{
	public static void main(String[] args)
	{
		Thread gameThread = new Thread( new Runnable() {
			@Override
			public void run()
			{
				SnakeWindow sw = new SnakeWindow();
				sw.startGame();
			}
		});
		gameThread.start();
		try
		{
			gameThread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		System.exit(1);
	}
}
