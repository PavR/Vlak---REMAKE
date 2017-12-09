package code_game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Object {

	private int x, y;
	private Image image;
	
	private Image wagonR, wagonU, wagonD;
	
	public Object(int x, int y, Image image, Image wagonR, Image wagonU, Image wagonD) {
		
		this.x = x;
		this.y = y;
		
		this.image = image;
		
		this.wagonR = wagonR;
		this.wagonU = wagonU;
		this.wagonD = wagonD;
		
	}

	public void render(GraphicsContext gc) {
		
		gc.clearRect(x, y, 36, 36);
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

	public Image getWagonR() {
		
		return wagonR;
		
	}

	public void setWagonR(Image wagonR) {
		
		this.wagonR = wagonR;
		
	}

	public Image getWagonU() {
		
		return wagonU;
		
	}

	public void setWagonU(Image wagonU) {
		
		this.wagonU = wagonU;
		
	}

	public Image getWagonD() {
		
		return wagonD;
		
	}

	public void setWagonD(Image wagonD) {
		
		this.wagonD = wagonD;
		
	}
	
}
