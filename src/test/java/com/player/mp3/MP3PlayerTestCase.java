package com.player.mp3;

import static org.testng.Assert.fail;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MP3PlayerTestCase {

	private MP3Player mp3Player;
	
	@BeforeMethod
	public void init() {
		mp3Player = new MP3Player();
	}
	
	@Test
	public void testShouldPlayMP3FileInContinousLoop() {
		try {
			play();
			putMainThreadToSleep();
		} catch (MP3PlayException e) {
			fail("The System should play the provided mp3 file", e.getCause());
		}
	}
	
	@Test
	public void testShouldStopPlayingWhenStopIsCalled() {
		try {
			play();
			createStopSoundThread();
			putMainThreadToSleep();
		} catch (MP3PlayException e) {
			fail("The System should play the provided mp3 file", e.getCause());
		}
	}
	
	private void play() throws MP3PlayException {
		File mp3File = getMP3File();
		mp3Player.setMp3File(mp3File);
		mp3Player.setLoop(true);
		mp3Player.play();
	}
	
	private void putMainThreadToSleep() {
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
		}
	}
	
	private void createStopSoundThread() {
		Thread t = new Thread(new Runnable() {
			
			public void run() {
				try {
					Thread.sleep(7500);
				} catch (InterruptedException e) {
				}
				mp3Player.stop();
			}
		});
		t.start();
	}
	
	private File getMP3File() {
		File mp3File = null;
		URL mp3URL = URLClassLoader.getSystemResource("sound.mp3");
		try {
			mp3File = new File(mp3URL.toURI());
		} catch (URISyntaxException e) {
			fail("Special Character File could not be created.");
		} finally {
		}
		return mp3File;
	}
	
	
}
