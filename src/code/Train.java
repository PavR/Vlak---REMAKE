package code;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.scene.image.Image;

public class Train {

	private int x, y;
	private Image image;
	private int speed;
	private int orientation;	 // ORIENTATION = 1 - LEFT, 3 - RIGHT, 0 - UP, 2 - DOWN
	private int lenght;
	
	private Clip move, pick, death;
	
	public Train(int x, int y, Image image) {
		
		this.x = x;
		this.y = y;
		
		this.image = image;
		
		speed = 1;
		orientation = -1;
		lenght = 0;
		
		String AbsolutePath = new File(".").getAbsolutePath();
    	
    	AbsolutePath = (AbsolutePath.substring(0, AbsolutePath.length() - 1));
    	AbsolutePath = AbsolutePath + "sounds";
    	
		File fileA = new File(AbsolutePath + "/" + "Move.wav");
		File fileB = new File(AbsolutePath + "/" + "Pick.wav");
		File fileC = new File(AbsolutePath + "/" + "Death.wav");
		
		try {
			
			move = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(fileA);
			move.open(inputStream);
			
			pick = AudioSystem.getClip();
			inputStream = AudioSystem.getAudioInputStream(fileB);
			pick.open(inputStream);
			
			death = AudioSystem.getClip();
			inputStream = AudioSystem.getAudioInputStream(fileC);
			death.open(inputStream);
			
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e1) {
			
			e1.printStackTrace();
		
		}
		
		setMove(move);
		setPick(pick);
		setDeath(death);
		
	}

	public int getX() {
		
		return x;
		
	}

	public void setX(int x) {
		
		this.x = x;
		
	}

	public int getY() {
		
		return y;
		
	}

	public void setY(int y) {
		
		this.y = y;
		
	}

	public Image getImage() {
		
		return image;
		
	}

	public void setImage(Image image) {
		
		this.image = image;
		
	}

	public int getSpeed() {
		
		return speed;
		
	}

	public void setSpeed(int speed) {
		
		this.speed = speed;
		
	}

	public int getOrientation() {
		
		return orientation;
		
	}

	public void setOrientation(int orientation) {
		
		this.orientation = orientation;
		
	}

	public int getLenght() {
		
		return lenght;
		
	}

	public void setLenght(int lenght) {
		
		this.lenght = lenght;
		
	}

	public Clip getMove() {
		
		return move;
		
	}

	public void setMove(Clip move) {
		
		this.move = move;
		
	}

	public Clip getPick() {
		
		return pick;
		
	}

	public void setPick(Clip pick) {
		
		this.pick = pick;
		
	}

	public Clip getDeath() {
		
		return death;
		
	}

	public void setDeath(Clip death) {
		
		this.death = death;
		
	}
	
}
