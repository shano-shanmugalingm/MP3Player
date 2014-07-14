package com.player.mp3;

import java.io.File;

public class MP3Player {

	private File mp3File;
	private boolean loop;
	
	private Player playableThread;

	public void setMp3File(File mp3File) {
		this.mp3File = mp3File;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public void play() throws MP3PlayException {
		instantiatePlayer();
		start();
	}

	private void start() {
		playableThread.start();
	}
	
	public void stop() {
		playableThread.interrupt();
		playableThread = null;
	}

	private void instantiatePlayer() throws MP3PlayException {
		playableThread = new Player(mp3File, loop);
		playableThread.setName("MP3Player");
	}
}
