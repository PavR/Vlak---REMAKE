package code_editor;

import javafx.scene.image.Image;

public class Editor_Tunnel {

	private int x, y;
	private Image image;
	private String name;
	
	private Editor_Tunnel end;
	
	public Editor_Tunnel(int x, int y, Image image, String name) {
		
		this.x = x;
		this.y = y;
		
		this.image = image;
		this.name = name;
		
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

	public String getName() {
		
		return name;
		
	}

	public void setName(String name) {
		
		this.name = name;
	
	}

	public Editor_Tunnel getEnd() {
		
		return end;
		
	}

	public void setEnd(Editor_Tunnel end) {
		
		this.end = end;
		
	}
	
}
