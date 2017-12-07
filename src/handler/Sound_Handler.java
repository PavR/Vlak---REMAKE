package handler;

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound_Handler {

	private Media menu, pick, dead, move;
	
	private MediaPlayer mediaPlayer;
	
	private boolean playing;;
	private static boolean allowed = true;
	
	public Sound_Handler() {
		
		playing = false;
		
		String AbsolutePath = new File(".").getAbsolutePath();
    	
    	AbsolutePath = (AbsolutePath.substring(0, AbsolutePath.length() - 1));
    	AbsolutePath = AbsolutePath + "sounds";
    	
		File fileA = new File(AbsolutePath + "/" + "Dead.wav");
		File fileB = new File(AbsolutePath + "/" + "Menu.wav");
		File fileC = new File(AbsolutePath + "/" + "Move.wav");
		File fileD = new File(AbsolutePath + "/" + "Pick.wav");
		
		try {
			
			dead = new Media(fileA.toURI().toURL().toString());
			menu = new Media(fileB.toURI().toURL().toString());
			move = new Media(fileC.toURI().toURL().toString());
			pick = new Media(fileD.toURI().toURL().toString());
			
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void playMenu() {
		
		if(allowed) {
			
			mediaPlayer = new MediaPlayer(menu);
			mediaPlayer.play();
			
			playing = true;
			
		}
		
	}
	
	public void playPick() {
		
		if(allowed) {
			
			mediaPlayer = new MediaPlayer(pick);
			mediaPlayer.play();
			
			playing = true;
			
		}
		
	}
	
	public void playDead() {
		
		if(allowed) {
			
			mediaPlayer = new MediaPlayer(dead);
			mediaPlayer.play();
			
			playing = true;
			
		}
		
	}
	
	public void playMove() {
		
		if(allowed) {
			
			mediaPlayer = new MediaPlayer(move);
			mediaPlayer.play();
			
			playing = true;
			
		}
		
	}
	
	public void stopSound() {
		
		if(allowed) {
			
			mediaPlayer.stop();
			
			playing = false;
			
		}
		
	}

	public boolean isPlaying() {
		
		return playing;
		
	}

	public void setPlaying(boolean playing) {
		
		this.playing = playing;
		
	}

	public static boolean isAllowed() {
		
		return allowed;
		
	}

	public static void setAllowed(boolean allowed) {
		
		Sound_Handler.allowed = allowed;
		
	}
	
}
