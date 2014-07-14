package com.player.mp3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class Player extends Thread {

	private AdvancedPlayer mp3Player;
	private File playableMP3File;
	private boolean loop;
	private boolean repeat;
	
	protected Player(File playableMP3File, boolean loop) {
		this.playableMP3File = playableMP3File;
		this.loop = loop;
	}
	
	public void run() {
		if (loop) {
			playContinously();
		} else {
			playOnce();
		}
		close();
	}
	
	private void playOnce() {
		try {
			InputStream playableMP3Stream = getMP3Stream(playableMP3File);
			mp3Player = new AdvancedPlayer(playableMP3Stream);
			mp3Player.play();
		} catch (JavaLayerException e) {
			throw new MP3PlayException("Playing MP3 failed.", e.getCause());
		}
	}
	
	private void playContinously() {
		repeat = true;
		while(repeat) {
			playOnce();
		}
	}
	
	private void close() {
		mp3Player.close();
	}
	
	private InputStream getMP3Stream(File mp3File) {
		InputStream stream = null;
		try {
			stream = new FileInputStream(mp3File);
		} catch (FileNotFoundException e) {
			throw new MP3PlayException("Playing MP3 failed.", e.getCause());
		}
		return stream;
	}
	
	public void interrupt() {
		super.interrupt();
		repeat = false;
	}

}
