package code;

import javafx.scene.image.Image;

public class Train {

	private int x, y;
	private Image image;
	private int speed;
	private int orientation;	 // ORIENTATION = 1 - LEFT, 3 - RIGHT, 0 - UP, 2 - DOWN
	private int lenght;
	
	public Train(int x, int y, Image image) {
		
		this.x = x;
		this.y = y;
		
		this.image = image;
		
		speed = 1;
		orientation = -1;
		lenght = 0;
		
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
	
}
