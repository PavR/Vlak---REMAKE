package handler;

import java.io.File;

import javafx.scene.image.Image;

public class Graphics_Handler {

	private Image wall, trainUp, trainLeft, trainDown, trainRight, gate, object, wagonRight, wagonUp, wagonDown, tunnel;
	
	public Graphics_Handler() {
		
		System.out.println("LOAD RESOURCES");
		String AbsolutePath = new File(".").getAbsolutePath();
    	
    	AbsolutePath = (AbsolutePath.substring(0, AbsolutePath.length() - 1));
		AbsolutePath = AbsolutePath + "res";
			
		File file1 = new File(AbsolutePath + "/" + "wall.png");
		
		File file2a = new File(AbsolutePath + "/" + "blueTrainUp.gif");
		File file2b = new File(AbsolutePath + "/" + "blueTrainDown.gif");
		File file2c = new File(AbsolutePath + "/" + "blueTrainLeft.gif");
		File file2d = new File(AbsolutePath + "/" + "blueTrainRight.gif");
		
		File file3 = new File(AbsolutePath + "/" + "gate.png");
		File file4 = new File(AbsolutePath + "/" + "object.gif");

		File file5a = new File(AbsolutePath + "/" + "wagonRight.png");
		File file5b = new File(AbsolutePath + "/" + "wagonUp.png");
		File file5c = new File(AbsolutePath + "/" + "wagonDown.png");
		
		File file6 = new File(AbsolutePath + "/" + "tunnel.png");
		
		wall = new Image(file1.toURI().toString());
		
		trainUp = new Image(file2a.toURI().toString());
		trainDown = new Image(file2b.toURI().toString());
		trainLeft = new Image(file2c.toURI().toString());
		trainRight = new Image(file2d.toURI().toString());
		
		gate = new Image(file3.toURI().toString());
		object = new Image(file4.toURI().toString());
	
		wagonRight = new Image(file5a.toURI().toString());
		wagonUp = new Image(file5c.toURI().toString());
		wagonDown = new Image(file5b.toURI().toString());
		
		tunnel = new Image(file6.toURI().toString());
		
	}

	public Image getWall() {
		
		return wall;
		
	}

	public Image getTrainUp() {
		
		return trainUp;
		
	}

	public Image getTrainLeft() {
		
		return trainLeft;
		
	}

	public Image getTrainDown() {
		
		return trainDown;
		
	}

	public Image getTrainRight() {
		
		return trainRight;
		
	}

	public Image getGate() {
		
		return gate;
		
	}

	public Image getObject() {
		
		return object;
		
	}

	public Image getWagonRight() {
		
		return wagonRight;
		
	}

	public Image getWagonUp() {
		
		return wagonUp;
		
	}

	public Image getWagonDown() {
		
		return wagonDown;
		
	}

	public Image getTunnel() {
		
		return tunnel;
		
	}
	
}
