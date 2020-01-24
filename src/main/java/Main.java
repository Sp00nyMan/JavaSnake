import Graphics.SnakeWindow;


public class Main
{
	public static void main(String[] args)
	{
/*		Thread recogn = new Thread();
		recogn.start();*/

		SnakeWindow sw = new SnakeWindow();
		sw.startGame();

		System.exit(1);
	}
}

