package code_game;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Wagon {

	private int x, y;
	private Image image;
	
	private ArrayList<Integer> futureMoves = new ArrayList<Integer>();
	
	private int lastMove;
	
	private Image wagonR, wagonU, wagonD;
	
	private ArrayList<Integer> lastPosition = new ArrayList<Integer>();
	
	public Wagon(int x, int y, Image image, Image wagonR, Image wagonU, Image wagonD) {
		
		this.x = x;
		this.y = y;
		
		this.image = image;
		
		this.wagonR = wagonR;
		this.wagonU = wagonU;
		this.wagonD = wagonD;
		
		lastPosition.add(x);
		lastPosition.add(y);
		
	}

	public void render(GraphicsContext gc) {
		
		gc.clearRect(lastPosition.get(0), lastPosition.get(1), 36, 36);
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

	public ArrayList<Integer> getFutureMoves() {
		
		return futureMoves;
		
	}

	public void setFutureMoves(ArrayList<Integer> futureMoves) {
		
		this.futureMoves = futureMoves;
		
	}

	public int getLastMove() {
		
		return lastMove;
		
	}

	public void setLastMove(int lastMove) {
		
		this.lastMove = lastMove;
		
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

	public ArrayList<Integer> getLastPosition() {
		
		return lastPosition;
		
	}
	
}
