package code_game;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class Wagon {

	private int x, y;
	private Image image;
	
	private ArrayList<Integer> futureMoves = new ArrayList<Integer>();
	
	private int lastMove;
	
	public Wagon(int x, int y, Image image) {
		
		this.x = x;
		this.y = y;
		
		this.image = image;
		
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
	
}
