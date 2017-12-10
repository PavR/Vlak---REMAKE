package code_editor;

import javafx.scene.image.Image;

public class Editor_Object {

	private int x, y;
	private Image image;
	private String name;
	
	public Editor_Object(int x, int y, Image image, String name) {
		
		this.x = x;
		this.y = y;
		
		this.image = image;
		
		this.name = name;
		
	}
	
	public Editor_Object(Image image, String name) {
		
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
	
	public String toString() {
		
		return name;
		
	}
	
}
