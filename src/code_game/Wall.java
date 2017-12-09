package code_game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Wall {

	private int x, y;
	private Image image;
	
	public Wall(int x, int y, Image image) {
		
		this.x = x;
		this.y = y;
		
		this.image = image;
		
	}
	
	public void render(GraphicsContext gc) {
			
		gc.drawImage(image, x, y);

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
	
}
