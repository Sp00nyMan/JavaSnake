import Graphics.SnakeWindow;
import VoiceRecognition.Recognition;
import VoiceRecognition.VoiceFile;

import javax.sound.sampled.LineUnavailableException;


public class Main
{
	public static void main(String[] args)
	{
		Thread recogn = null;
		recogn = new Thread(new Recognition());
		recogn.start();







		SnakeWindow sw = new SnakeWindow();
		sw.startGame();

		System.exit(1);
	}
}

