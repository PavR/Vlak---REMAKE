package handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.image.Image;

public class Graphics_Handler {

	private Image wall, train1Up, train1Left, train1Down, train1Right, gate, objectPizza, wagonPizzaRight, wagonPizzaUp, wagonPizzaDown,
				  tunnel, objectPizzaStation, train1RightStation, objectUnicorn, wagonUnicornRight, wagonUnicornUp, wagonUnicornDown, objectUnicornStation, empty,
				  train2Up, train2Left, train2Down, train2Right, train2RightStation, trainPickedUp, trainPickedLeft, trainPickedDown, trainPickedRight, trainPickedUpStation;
	
	private int pickedTrain = 1;
	
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
		
		File file10a = new File(AbsolutePath + "/" + "razerTrainUp.gif");
		File file10b = new File(AbsolutePath + "/" + "razerTrainDown.gif");
		File file10c = new File(AbsolutePath + "/" + "razerTrainLeft.gif");
		File file10d = new File(AbsolutePath + "/" + "razerTrainRight.gif");
		File file10e = new File(AbsolutePath + "/" + "razerTrainRightStation.png");
		
		wall = new Image(file1.toURI().toString());
		
		train1Up = new Image(file2a.toURI().toString());
		train1Down = new Image(file2b.toURI().toString());
		train1Left = new Image(file2c.toURI().toString());
		train1Right = new Image(file2d.toURI().toString());
		train1RightStation = new Image(file2e.toURI().toString());
		
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
		
		train2Up = new Image(file10a.toURI().toString());
		train2Down = new Image(file10b.toURI().toString());
		train2Left = new Image(file10c.toURI().toString());
		train2Right = new Image(file10d.toURI().toString());
		train2RightStation = new Image(file10e.toURI().toString());
		
		AbsolutePath = new File(".").getAbsolutePath();
    	
    	AbsolutePath = (AbsolutePath.substring(0, AbsolutePath.length() - 1));
    	
    	File options = new File(AbsolutePath + "OPTIONS.txt");

    	BufferedReader br;
    	
		try {
			
			br = new BufferedReader(new FileReader(options));
			
			try {
				
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();
			    
			    while (line != null) {
			    	
			    	if(line.startsWith("skin")) {
			    		
			    		if(line.contains("=1")) {
			    			
			    			trainPickedUp = train1Up;
			    			trainPickedLeft = train1Left;
			    			trainPickedDown = train1Down;
			    			trainPickedRight = train1Right;
			    			trainPickedUpStation = train1RightStation;
			    			
			    			pickedTrain = 1;
			    			
			    		}else if(line.contains("=2")){
			    			
			    			trainPickedUp = train2Up;
			    			trainPickedLeft = train2Left;
			    			trainPickedDown = train2Down;
			    			trainPickedRight = train2Right;
			    			trainPickedUpStation = train2RightStation;
			    			
			    			pickedTrain = 2;
			    			
			    		}
			    		
			    	}
			    	
			    	
			    	sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			        
			    }
			    
			} catch (IOException e) {
				
				e.printStackTrace();
				
			} finally {
				
			    try {
			    	
					br.close();
					
				} catch (IOException e) {
					
					e.printStackTrace();
					
				}
			    
			}
			
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
			
		}
		
	}

	public Image getWall() {
		
		return wall;
		
	}

	public Image getTrain1Up() {
		
		return train1Up;
		
	}

	public Image getTrain1Left() {
		
		return train1Left;
		
	}

	public Image getTrain1Down() {
		
		return train1Down;
		
	}

	public Image getTrain1Right() {
		
		return train1Right;
		
	}

	public Image getGate() {
		
		return gate;
		
	}

	public Image getTunnel() {
		
		return tunnel;
		
	}

	public Image getTrain1RightStation() {
		
		return train1RightStation;
		
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

	public Image getTrain2Up() {
		
		return train2Up;
		
	}

	public Image getTrain2Left() {
		
		return train2Left;
		
	}

	public Image getTrain2Down() {
		
		return train2Down;
		
	}

	public Image getTrain2Right() {
		
		return train2Right;
		
	}

	public Image getTrain2RightStation() {
		
		return train2RightStation;
		
	}

	public Image getTrainPickedUp() {
		
		return trainPickedUp;
		
	}

	public Image getTrainPickedLeft() {
		
		return trainPickedLeft;
		
	}

	public Image getTrainPickedDown() {
		
		return trainPickedDown;
		
	}

	public Image getTrainPickedRight() {
		
		return trainPickedRight;
		
	}

	public Image getTrainPickedUpStation() {
		
		return trainPickedUpStation;
		
	}

	public int getPickedTrain() {
		
		return pickedTrain;
		
	}
	
}
