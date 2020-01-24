package VoiceRecognition;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

import javax.sound.sampled.*;

public class VoiceFile
{
	private boolean isActive = false;
	private File soundFile = new File("test.wav");

	private Configuration config = new Configuration();
	private StreamSpeechRecognizer recognizer;
	private InputStream stream;

	AudioFormat format = new AudioFormat(44100, 16, 1, true, true);
	DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
	TargetDataLine dataLine = (TargetDataLine) AudioSystem.getLine(info);
	AudioInputStream sound = new AudioInputStream(dataLine);

	public VoiceFile() throws LineUnavailableException
	{
		dataLine.open(format);

		config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		config.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		config.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
	}

	public synchronized void read()
	{
		try
		{
			if (!isActive)
			{
				wait();
			}
		}
		catch (InterruptedException e)
		{
			System.out.println(e);
		}

		isActive = true;

		try
		{
			recognizer = new StreamSpeechRecognizer(config);
			stream = new FileInputStream(soundFile);
		}
		catch (IOException e)
		{
			System.out.println(e);
		}

		recognizer.startRecognition(stream);
		SpeechResult result;

		while ((result = recognizer.getResult()) != null)
		{
			System.out.println(result.getHypothesis());
		}

		recognizer.stopRecognition();
	}

	public synchronized void write()
	{
		try
		{
			if (isActive)
			{
				wait();
			}
		}
		catch (InterruptedException e)
		{
			System.out.println(e);
		}

		isActive = false;

		try
		{
			dataLine.start();
			AudioSystem.write(sound, AudioFileFormat.Type.WAVE, soundFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
