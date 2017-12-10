package handler;

import java.io.File;

import javafx.scene.image.Image;

public class Graphics_Handler {

	private Image wall, trainUp, trainLeft, trainDown, trainRight, gate, objectPizza, wagonPizzaRight, wagonPizzaUp, wagonPizzaDown,
				  tunnel, objectPizzaStation, trainRightStation, objectUnicorn, wagonUnicornRight, wagonUnicornUp, wagonUnicornDown, objectUnicornStation, empty;
	
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
		File file2e = new File(AbsolutePath + "/" + "blueTrainRightStation.png");
		
		File file3 = new File(AbsolutePath + "/" + "gate.png");
		
		File file4a = new File(AbsolutePath + "/" + "objectPizza.gif");
		File file4b = new File(AbsolutePath + "/" + "objectPizzaStation.png");

		File file5a = new File(AbsolutePath + "/" + "wagonPizzaRight.png");
		File file5b = new File(AbsolutePath + "/" + "wagonPizzaUp.png");
		File file5c = new File(AbsolutePath + "/" + "wagonPizzaDown.png");
		
		File file6 = new File(AbsolutePath + "/" + "tunnel.png");
		
		File file7a = new File(AbsolutePath + "/" + "objectUnicorn.gif");
		File file7b = new File(AbsolutePath + "/" + "objectUnicornStation.png");

		File file8a = new File(AbsolutePath + "/" + "wagonUnicornRight.png");
		File file8b = new File(AbsolutePath + "/" + "wagonUnicornUp.png");
		File file8c = new File(AbsolutePath + "/" + "wagonUnicornDown.png");
		
		File file9 = new File(AbsolutePath + "/" + "empty.png");
		
		wall = new Image(file1.toURI().toString());
		
		trainUp = new Image(file2a.toURI().toString());
		trainDown = new Image(file2b.toURI().toString());
		trainLeft = new Image(file2c.toURI().toString());
		trainRight = new Image(file2d.toURI().toString());
		trainRightStation = new Image(file2e.toURI().toString());
		
		gate = new Image(file3.toURI().toString());
		
		objectPizza = new Image(file4a.toURI().toString());
		objectPizzaStation = new Image(file4b.toURI().toString());
	
		wagonPizzaRight = new Image(file5a.toURI().toString());
		wagonPizzaUp = new Image(file5c.toURI().toString());
		wagonPizzaDown = new Image(file5b.toURI().toString());
		
		objectUnicorn = new Image(file7a.toURI().toString());
		objectUnicornStation = new Image(file7b.toURI().toString());
	
		wagonUnicornRight = new Image(file8a.toURI().toString());
		wagonUnicornUp = new Image(file8c.toURI().toString());
		wagonUnicornDown = new Image(file8b.toURI().toString());
		
		tunnel = new Image(file6.toURI().toString());
		
		empty = new Image(file9.toURI().toString());
		
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

	public Image getTunnel() {
		
		return tunnel;
		
	}

	public Image getTrainRightStation() {
		
		return trainRightStation;
		
	}

	public Image getObjectPizza() {
		
		return objectPizza;
		
	}

	public Image getWagonPizzaRight() {
		
		return wagonPizzaRight;
		
	}

	public Image getWagonPizzaUp() {
		
		return wagonPizzaUp;
		
	}

	public Image getWagonPizzaDown() {
		
		return wagonPizzaDown;
		
	}

	public Image getObjectPizzaStation() {
		
		return objectPizzaStation;
		
	}

	public Image getObjectUnicorn() {
		
		return objectUnicorn;
		
	}

	public Image getWagonUnicornRight() {
		
		return wagonUnicornRight;
		
	}

	public Image getWagonUnicornUp() {
		
		return wagonUnicornUp;
		
	}

	public Image getWagonUnicornDown() {
		
		return wagonUnicornDown;
		
	}

	public Image getObjectUnicornStation() {
		
		return objectUnicornStation;
		
	}

	public Image getEmpty() {
		
		return empty;
		
	}
	
}
