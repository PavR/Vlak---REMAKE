package code;

import javafx.scene.image.Image;

public class Tunnel {

	private int x, y;
	private Image image;
	
	private Tunnel end;
	
	public Tunnel(int x, int y, Image image) {
		
		this.x = x;
		this.y = y;
		
		this.image = image;
		
	}

	public int getX() {
		
		return x;
		
	}

	public int getY() {
		
		return y;
		
	}

	public Image getImage() {
		
		return image;
		
	}

	public void setX(int x) {
		
		this.x = x;
		
	}

	public void setY(int y) {
		
		this.y = y;
		
	}

	public void setImage(Image image) {
		
		this.image = image;
		
	}

	public Tunnel getEnd() {
		
		return end;
		
	}

	public void setEnd(Tunnel end) {
		
		this.end = end;
		
	}
	
}
