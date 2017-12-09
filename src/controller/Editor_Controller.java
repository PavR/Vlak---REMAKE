package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.ResourceBundle;

import code_editor.Editor_Object;
import code_editor.Editor_SelectedTile;
import code_editor.Editor_Tunnel;
import code_game.Main;
import handler.Graphics_Handler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Editor_Controller implements Initializable{

	@FXML
	private Canvas c_canvas;
	@FXML
	private Label l_1, l_2, l_3, l_4, l_5, l_6, l_7;
	@FXML
	private CheckBox cb_1, cb_2, cb_3, cb_4, cb_5, cb_6, cb_7;
	@FXML
	private ImageView iv_1, iv_2, iv_3, iv_4, iv_5, iv_6, iv_7;
	@FXML
	private MenuItem mi_load, mi_save;
	@FXML
	private TextField tf_level, tf_password;
	@FXML
	private Button b_back;
	
	private GraphicsContext gc;
	
	private ArrayList<Editor_Object> allEditor_Objects = new ArrayList<Editor_Object>();
	private ArrayList<Editor_Tunnel> allEditor_Tunnels = new ArrayList<Editor_Tunnel>();
	
	private Editor_SelectedTile st;
	
	private ArrayList<CheckBox> allCheckBoxes = new ArrayList<CheckBox>();
	
	private String selectedEditor_Object = "";
	
	private boolean trainPlaced = false;
	private boolean gatePlaced = false;
	
	private Formatter x;
	
	private boolean connectEditor_Tunnels = false;
	
	private Graphics_Handler gh;
	
	public void initialize(URL arg0, ResourceBundle arg1) {

		gh = new Graphics_Handler();
		
		Editor_Object wall = new Editor_Object(gh.getWall(), "Wall");
		
		l_1.setText(wall.getName());
		iv_1.setImage(wall.getImage());
		
		Editor_Object train = new Editor_Object(gh.getTrainRightStation(), "Train");
		
		l_2.setText(train.getName());
		iv_2.setImage(train.getImage());
		
		Editor_Object Editor_Object1 = new Editor_Object(gh.getObjectPizzaStation(), "Pizza");
		
		l_3.setText(Editor_Object1.getName());
		iv_3.setImage(Editor_Object1.getImage());
		
		Editor_Object gate = new Editor_Object(gh.getGate(), "Gate");
		
		l_4.setText(gate.getName());
		iv_4.setImage(gate.getImage());
		
		l_5.setText("Empty");
		
		Editor_Object Editor_Tunnel = new Editor_Object(gh.getTunnel(), "Tunnel");
		
		l_6.setText(Editor_Tunnel.getName());
		iv_6.setImage(Editor_Tunnel.getImage());
		
		Editor_Object Editor_Object2 = new Editor_Object(gh.getObjectUnicornStation(), "Unicorn");
		
		l_7.setText(Editor_Object2.getName());
		iv_7.setImage(Editor_Object2.getImage());
		
		allCheckBoxes.add(cb_1);
		allCheckBoxes.add(cb_2);
		allCheckBoxes.add(cb_3);
		allCheckBoxes.add(cb_4);
		allCheckBoxes.add(cb_5);
		allCheckBoxes.add(cb_6);
		allCheckBoxes.add(cb_7);

		setKeyboardInput();
		
		gc = c_canvas.getGraphicsContext2D();	
		
		st = new Editor_SelectedTile(0, 0);

		for(int x = 0; x <= 19; x++) {
			
			if(x == 0 || x == 19) {
				
				for(int y = 0; y <= 19; y++) {
					
					allEditor_Objects.add(new Editor_Object(x * 36, y * 36, gh.getWall(), "Wall"));
					
				}
				
			}else {
				
				allEditor_Objects.add(new Editor_Object(x * 36, 0, gh.getWall(), "Wall"));
				allEditor_Objects.add(new Editor_Object(x * 36, 19 * 36, gh.getWall(), "Wall"));
				
			}

		}
		
		drawCanvas();
		
	} 
	
	public void setKeyboardInput() {
		
		c_canvas.setOnKeyPressed(e -> {
			
		    if (e.getCode() == KeyCode.LEFT) {
		    	
		    	if(st.getX() != 0) {
		    		
		    		st.setX(st.getX() - 36);
			    	
			    	clearCanvas();
			    	drawCanvas();
		    		
		    	}
		        
		    }
		   
		    if (e.getCode() == KeyCode.RIGHT) {
		    	
		    	if(st.getX() < 720 - 36) {
		    		
		    		st.setX(st.getX() + 36);
			    	
			    	clearCanvas();
			    	drawCanvas();
		    		
		    	}
		    	
		    }
		    
		    if (e.getCode() == KeyCode.UP) {
		    	
		    	if(st.getY() != 0) {
		    		
		    		st.setY(st.getY() - 36);
				       
			    	clearCanvas();
			    	drawCanvas();
		    		
		    	}
		    	
		    }
		    
		    if (e.getCode() == KeyCode.DOWN) {
		    	
		    	if(st.getY() < 720 - 36) {
		    		
		    		st.setY(st.getY() + 36);
			    	
			    	clearCanvas();
			    	drawCanvas();
		    		
		    	}
		    	
		    }
		    
		    if(e.getCode() == KeyCode.ENTER) {
		    	
		    	int X = st.getX();
		    	int Y = st.getY();
		    	
		    	if(selectedEditor_Object.equals("Wall")) {

		    		if(allEditor_Objects.isEmpty()) {
		    			
		    			allEditor_Objects.add(new Editor_Object(X, Y, gh.getWall(), "Wall"));

			    		clearCanvas();
			    		drawCanvas();
		    			
		    		}else {
		    			
		    			for(int x = 0; x < allEditor_Objects.size(); x++) {

			    			if((allEditor_Objects.get(x).getX() == X) && (allEditor_Objects.get(x).getY() == Y)) {
			    				
			    				return;
			    				
			    			}
			    			
			    		}

	    				allEditor_Objects.add(new Editor_Object(X, Y, gh.getWall(), "Wall"));

			    		clearCanvas();
			    		drawCanvas();
		    			
		    		}
		    		
		    	}else if(selectedEditor_Object.equals("Train")) {
		    		
		    		if(trainPlaced == false) {
		    			
		    			for(int x = 0; x < allEditor_Objects.size(); x++) {

			    			if((allEditor_Objects.get(x).getX() == X) && (allEditor_Objects.get(x).getY() == Y)) {
			    				
			    				return;
			    				
			    			}
			    			
			    		}
		    			
		    			trainPlaced = true;
		    			
		    			allEditor_Objects.add(new Editor_Object(X, Y, gh.getTrainRightStation(), "Train"));

			    		clearCanvas();
			    		drawCanvas();
		    			
		    		}
		    		
		    	}else if(selectedEditor_Object.equals("Pizza")) {
		    		
		    		if(allEditor_Objects.isEmpty()) {
		    			
		    			allEditor_Objects.add(new Editor_Object(X, Y, gh.getObjectPizzaStation(), "Pizza"));

			    		clearCanvas();
			    		drawCanvas();
		    			
		    		}else {
		    			
		    			for(int x = 0; x < allEditor_Objects.size(); x++) {

			    			if((allEditor_Objects.get(x).getX() == X) && (allEditor_Objects.get(x).getY() == Y)) {
			    				
			    				return;
			    				
			    			}
			    			
			    		}

	    				allEditor_Objects.add(new Editor_Object(X, Y, gh.getObjectPizzaStation(), "Pizza"));

			    		clearCanvas();
			    		drawCanvas();
		    			
		    		}
		    		
		    	}else if(selectedEditor_Object.equals("Unicorn")){
		    		
		    		if(allEditor_Objects.isEmpty()) {
		    			
		    			allEditor_Objects.add(new Editor_Object(X, Y, gh.getObjectUnicornStation(), "Unicorn"));

			    		clearCanvas();
			    		drawCanvas();
		    			
		    		}else {
		    			
		    			for(int x = 0; x < allEditor_Objects.size(); x++) {

			    			if((allEditor_Objects.get(x).getX() == X) && (allEditor_Objects.get(x).getY() == Y)) {
			    				
			    				return;
			    				
			    			}
			    			
			    		}

	    				allEditor_Objects.add(new Editor_Object(X, Y, gh.getObjectUnicornStation(), "Unicorn"));

			    		clearCanvas();
			    		drawCanvas();
		    			
		    		}
		    		
		    	}else if(selectedEditor_Object.equals("Gate")) {
		    	
		    		if(gatePlaced == false) {
		    			
		    			for(int x = 0; x < allEditor_Objects.size(); x++) {

			    			if((allEditor_Objects.get(x).getX() == X) && (allEditor_Objects.get(x).getY() == Y)) {
			    				
			    				return;
			    				
			    			}
			    			
			    		}
		    			
		    			gatePlaced = true;
		    			
		    			allEditor_Objects.add(new Editor_Object(X, Y, gh.getGate(), "Gate"));

			    		clearCanvas();
			    		drawCanvas();
		    			
		    		}
		    		
		    	}else if(selectedEditor_Object.equals("Empty")) {
		    		
		    		for(int x = 0; x < allEditor_Objects.size(); x++) {

		    			if((allEditor_Objects.get(x).getX() == X) && (allEditor_Objects.get(x).getY() == Y)) {
		    				
		    				if(allEditor_Objects.get(x).getName().equals("Train")) {
		    					
		    					trainPlaced = false;
		    					
		    				}
		    				
		    				if(allEditor_Objects.get(x).getName().equals("Gate")) {
		    					
		    					gatePlaced = false;
		    					
		    				}
		    				
		    				allEditor_Objects.remove(x);
		    				
		    			}
		    			
		    		}
		    		
		    		for(int x = 0; x < allEditor_Tunnels.size(); x++) {

		    			if((allEditor_Tunnels.get(x).getX() == X) && (allEditor_Tunnels.get(x).getY() == Y)) {
		    				
		    				if(x > allEditor_Tunnels.indexOf(allEditor_Tunnels.get(x).getEnd())) {
		    					
		    					int a = allEditor_Tunnels.indexOf(allEditor_Tunnels.get(x).getEnd());
		    					
		    					allEditor_Tunnels.remove(x);
		    					allEditor_Tunnels.remove(a);
		    					
		    				}else {
		    					
		    					allEditor_Tunnels.remove(allEditor_Tunnels.indexOf(allEditor_Tunnels.get(x).getEnd()));
		    					allEditor_Tunnels.remove(x);
		    					
		    				}
		    				
		    			}
		    			
		    		}
		    		
		    		clearCanvas();
		    		drawCanvas();
		    		
		    	}else if(selectedEditor_Object.equals("Tunnel")) {
		    		
		    		if(allEditor_Objects.size() > 0) {
		    			
			    		for(int x = 0; x < allEditor_Objects.size(); x++) {
			    			
			    			if((allEditor_Objects.get(x).getX() == X) && (allEditor_Objects.get(x).getY() == Y)) {
			    				
			    				return;
			    				
			    			}else {
			    				
			    				if(allEditor_Tunnels.size() > 0) {
			    					
				    				for(int y = 0; y < allEditor_Tunnels.size();) {
				    					
					    				if((allEditor_Tunnels.get(y).getX() == X) && (allEditor_Tunnels.get(y).getY() == Y)) {
					    					
					    					return;
						    				
						    			}else {
						    				
						    				if(connectEditor_Tunnels == false) {
						    					
						    					allEditor_Tunnels.add(new Editor_Tunnel(X, Y, gh.getTunnel(), "Tunnel"));
						    					
						    					connectEditor_Tunnels = true;
						    					
						    					clearCanvas();
						    		    		drawCanvas();
						    					
						    					return;
						    					
						    				}else {
						    					
						    					connectEditor_Tunnels = false;
						    					
						    					allEditor_Tunnels.add(new Editor_Tunnel(X, Y, gh.getTunnel(), "Tunnel"));
						    					
						    					allEditor_Tunnels.get(allEditor_Tunnels.size() - 2).setEnd(allEditor_Tunnels.get(allEditor_Tunnels.size() - 1));
						    					allEditor_Tunnels.get(allEditor_Tunnels.size() - 1).setEnd(allEditor_Tunnels.get(allEditor_Tunnels.size() - 2));
						    					
						    					clearCanvas();
						    		    		drawCanvas();
						    					
						    					return;
						    				}
						    				
						    			}
				    					
				    				}
			    					
			    				}else {
			    					
			    					allEditor_Tunnels.add(new Editor_Tunnel(X, Y, gh.getTunnel(), "Tunnel"));
			    					
			    					connectEditor_Tunnels = true;
			    					
			    					clearCanvas();
			    		    		drawCanvas();
			    					
			    					return;
			    					
			    				}
			    				
			    			}
			    			
			    		}
		    			
		    		}else {
		    			
		    			if(allEditor_Tunnels.size() > 0) {
		    				
		    				for(int y = 0; y < allEditor_Tunnels.size();) {
		    					
			    				if((allEditor_Tunnels.get(y).getX() == X) && (allEditor_Tunnels.get(y).getY() == Y)) {
			    					
			    					return;
				    				
				    			}else {
				    				
				    				if(connectEditor_Tunnels == false) {
				    					
				    					allEditor_Tunnels.add(new Editor_Tunnel(X, Y, gh.getTunnel(), "Tunnel"));
				    					
				    					connectEditor_Tunnels = true;
				    					
				    					clearCanvas();
				    		    		drawCanvas();
				    					
				    					return;
				    					
				    				}else {
				    					
				    					connectEditor_Tunnels = false;
				    					
				    					allEditor_Tunnels.add(new Editor_Tunnel(X, Y, gh.getTunnel(), "Tunnel"));
				    					
				    					allEditor_Tunnels.get(allEditor_Tunnels.size() - 2).setEnd(allEditor_Tunnels.get(allEditor_Tunnels.size() - 1));
				    					allEditor_Tunnels.get(allEditor_Tunnels.size() - 1).setEnd(allEditor_Tunnels.get(allEditor_Tunnels.size() - 2));
				    					
				    					clearCanvas();
				    		    		drawCanvas();
				    					
				    					return;
				    					
				    				}
				    				
				    			}
		    					
		    				}
		    				
		    			}else {
		    				
		    				allEditor_Tunnels.add(new Editor_Tunnel(X, Y, gh.getTunnel(), "Tunnel"));
		    				
		    				connectEditor_Tunnels = true;
		    				
		    				clearCanvas();
				    		drawCanvas();
		    				
		    				return;
		    			}
		    			
		    		}
		    	
		    	}

		    }
		    
		});
		
	}
	
	public void drawCanvas() {
		
		gc.setLineWidth(1);
		gc.setStroke(Color.WHITE);
		
		for(int x = 0; x < 720 / 36; x++) {
			
			gc.strokeLine(x * 36, 0, x * 36, 720);
			
		}
		
		for(int y = 0; y < 720 / 36; y++) {
			
			gc.strokeLine(0, y * 36, 720, y * 36);
			
		}
		
		for(int x = 0; x < allEditor_Objects.size(); x++) {
			
			gc.drawImage(allEditor_Objects.get(x).getImage(), allEditor_Objects.get(x).getX(), allEditor_Objects.get(x).getY());
			
		}
		
		for(int x = 0; x < allEditor_Tunnels.size(); x++) {
			
			gc.drawImage(allEditor_Tunnels.get(x).getImage(), allEditor_Tunnels.get(x).getX(), allEditor_Tunnels.get(x).getY());
			
		}
		
		gc.setLineWidth(2.5);
		gc.setStroke(Color.RED);
		
		gc.strokeRect(st.getX(), st.getY(), 36, 36);
		
		gc.setLineWidth(1);
		gc.setStroke(Color.RED);
		
		if(allEditor_Tunnels.size() > 1) {
			
			if((allEditor_Tunnels.size() & 1) != 0) {
				
				for(int x = 0; x < allEditor_Tunnels.size() - 1; x = x + 2) {
					
					gc.strokeLine(allEditor_Tunnels.get(x).getX() + 16, allEditor_Tunnels.get(x).getY() + 16, allEditor_Tunnels.get(x + 1).getX() + 16, allEditor_Tunnels.get(x + 1).getY() + 16);
					
				}
				
			}else {
				
				for(int x = 0; x < allEditor_Tunnels.size(); x = x + 2) {
					
					gc.strokeLine(allEditor_Tunnels.get(x).getX() + 16, allEditor_Tunnels.get(x).getY() + 16, allEditor_Tunnels.get(x + 1).getX() + 16, allEditor_Tunnels.get(x + 1).getY() + 16);
					
				}
				
			}
			
		}
		
	}
	
	public void clearCanvas() {
		
		gc.clearRect(0, 0, c_canvas.getWidth(), c_canvas.getHeight());
		
	}
	
	public void cbOnAction() {
		
		for(int x = 0; x < allCheckBoxes.size(); x++) {
			
			if(allCheckBoxes.get(x).isSelected()) {
				
				allCheckBoxes.get(x).setDisable(false);
				
			}else {
				
				allCheckBoxes.get(x).setDisable(true);
				
			}
			
		}
		
		int a = 0;
		
		for(int x = 0; x < allCheckBoxes.size(); x++) {
			
			if(!allCheckBoxes.get(x).isSelected()) {
				
				a++;
				
			}
			
		}
		
		if(a == allCheckBoxes.size()) {
			
			for(int x = 0; x < allCheckBoxes.size(); x++) {
				
				allCheckBoxes.get(x).setDisable(false);
				
			}
			
		}
		
		for(int x = 0; x < allCheckBoxes.size(); x++) {
			
			if(allCheckBoxes.get(0).isSelected()) {
				
				selectedEditor_Object = "Wall";
				return;
				
			}else if(allCheckBoxes.get(1).isSelected()) {
				
				selectedEditor_Object = "Train";
				return;
				
			}else if(allCheckBoxes.get(2).isSelected()) {
				
				selectedEditor_Object = "Pizza";
				return;
				
			}else if(allCheckBoxes.get(3).isSelected()) {
				
				selectedEditor_Object = "Gate";
				return;
				
			}else if(allCheckBoxes.get(4).isSelected()) {
				
				selectedEditor_Object = "Empty";
				return;
				
			}else if(allCheckBoxes.get(5).isSelected()) {
				
				selectedEditor_Object = "Tunnel";
				return;
				
			}else if(allCheckBoxes.get(6).isSelected()) {
				
				selectedEditor_Object = "Unicorn";
				return;
				
			}
			
		}
		
	}
	
	public void onMouseMoved() {
		
		for(int x = 0; x < allCheckBoxes.size(); x++) {
			
			allCheckBoxes.get(x).setFocusTraversable(false);
			
		}
		
		c_canvas.requestFocus();
		
	}
	
	public void miLoadOnAction() throws IOException {
		
		clearCanvas();
		
		allEditor_Objects.clear();
		allEditor_Tunnels.clear();
		
		String AbsolutePath = new File(".").getAbsolutePath();
		AbsolutePath = (AbsolutePath.substring(0, AbsolutePath.length() - 1));
		AbsolutePath = AbsolutePath + "/levels";
		
		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(new File(AbsolutePath));
			
		File file = chooser.showOpenDialog(new Stage());
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		try {
			
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
		    
		    while (line != null) {
		    	
		    	if(line.startsWith("L")) {
		    		
		    		tf_level.setText(line.substring(2, line.length() - 1));
		    		
		    	}
		    	
		    	if(line.startsWith("P")) {
		    		
		    		tf_password.setText(line.substring(2, line.length() - 1));
		    		
		    	}
		    	
		    	if(line.startsWith("0")) {
		    		
		    		int X = Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(",")));
		    		int Y = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(")")));
		    		
		    		allEditor_Objects.add(new Editor_Object(X, Y, gh.getWall(), "Wall"));
		    		
		    	}
		    	
		    	if(line.startsWith("1")) {
		    		
		    		int X = Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(",")));
		    		int Y = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(")")));
		    		
		    		allEditor_Objects.add(new Editor_Object(X, Y, gh.getTrainRightStation(), "Train"));
		    		
		    	}
		    	
		    	if(line.startsWith("2")) {
		    		
		    		int X = Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(",")));
		    		int Y = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(")")));
		    		
		    		allEditor_Objects.add(new Editor_Object(X, Y, gh.getObjectPizzaStation(), "Object"));
		    		
		    	}
		    	
		    	if(line.startsWith("3")) {
		    		
		    		int X = Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(",")));
		    		int Y = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(")")));
		    		
		    		allEditor_Objects.add(new Editor_Object(X, Y, gh.getGate(), "Gate"));
		    		
		    	}
		    	
		    	if(line.startsWith("4")) {
		    		
		    		int X = Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(",")));
		    		int Y = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(")")));
		    		
		    		allEditor_Tunnels.add(new Editor_Tunnel(X, Y, gh.getTunnel(), "Tunnel"));
		    		
		    	}
		    	
		    	if(line.startsWith("5")) {
		    		
		    		int X = Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(",")));
		    		int Y = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(")")));
		    		
		    		allEditor_Objects.add(new Editor_Object(X, Y, gh.getObjectUnicornStation(), "Unicorn"));
		    		
		    	}
		    	
		    	sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		        
		    }
		    
		} finally {
			
		    br.close();
		    
		}

		drawCanvas();
		
	}
	
	public void miSaveOnAction() throws IOException {
		
		if(tf_level.getText().isEmpty() && tf_password.getText().isEmpty()) {
			
			Alert alert = new Alert(AlertType.NONE, "You need to fill level number and level password", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
				
				alert.close();
				
			}
			
		}else {
			
			if(connectEditor_Tunnels == true) {
				
				Alert alert = new Alert(AlertType.NONE, "You have some unconnected Editor_Tunnels", ButtonType.OK);
				alert.showAndWait();

				if (alert.getResult() == ButtonType.OK) {
					
					alert.close();
					
				}
				
			}else {
				
				createFile();
				addRecords();
				closeFile();
				
			}
			
		}
		
	}

	private void createFile() {
		
		String name = tf_level.getText() + ".txt";
		
		try {
			
			x = new Formatter(name);
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	private void addRecords() {
		
		x.format("L<" + tf_level.getText() + ">" + "\n");
		x.format("P<" + tf_password.getText() + ">" + "\n");
		
		for(int y = 0; y < allEditor_Objects.size(); y++) {
			
			if(allEditor_Objects.get(y).getName().equals("Wall")) {
				
				x.format(0 + "(" + allEditor_Objects.get(y).getX() + "," + allEditor_Objects.get(y).getY() + ")" + "\n");
				
			}else if(allEditor_Objects.get(y).getName().equals("Train")) {
				
				x.format(1 + "(" + allEditor_Objects.get(y).getX() + "," + allEditor_Objects.get(y).getY() + ")" + "\n");
				
			}else if(allEditor_Objects.get(y).getName().equals("Pizza")) {
				
				x.format(2 + "(" + allEditor_Objects.get(y).getX() + "," + allEditor_Objects.get(y).getY() + ")" + "\n");
				
			}else if(allEditor_Objects.get(y).getName().equals("Gate")) {
				
				x.format(3 + "(" + allEditor_Objects.get(y).getX() + "," + allEditor_Objects.get(y).getY() + ")" + "\n");
				
			}else if(allEditor_Objects.get(y).getName().equals("Unicorn")) {
				
				x.format(5 + "(" + allEditor_Objects.get(y).getX() + "," + allEditor_Objects.get(y).getY() + ")" + "\n");
				
			}
			
		}
		
		for(int y = 0; y < allEditor_Tunnels.size(); y++) {
			
			if(allEditor_Tunnels.get(y).getName().equals("Tunnel")) {
				
				x.format(4 + "(" + allEditor_Tunnels.get(y).getX() + "," + allEditor_Tunnels.get(y).getY() + ")" + "\n");
				
			}
			
		}
		
	}
	
	private void closeFile() throws IOException {
		
		x.close();
		
		String AbsolutePath = new File(".").getAbsolutePath();
		AbsolutePath = (AbsolutePath.substring(0, AbsolutePath.length() - 1));
		
		AbsolutePath = AbsolutePath + "/" + tf_level.getText() + ".txt";
		
		File a = new File(AbsolutePath);
		
		String AbsolutePath1 = new File(".").getAbsolutePath();
		AbsolutePath1 = (AbsolutePath1.substring(0, AbsolutePath1.length() - 1));
		
		Path sourcePath = Paths.get(a.getName());
		
		String ss = AbsolutePath1 + "levels/" + a.getName();
		
		Path destinationPath = Paths.get(ss);
		
		Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
		
	}
	
	public void b_back_onAction() throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Menu.fxml"));
		Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
		
		Stage primaryStage = new Stage();
		
		primaryStage.setTitle("Vlak - REMAKE");
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Stage previousStage = (Stage)b_back.getScene().getWindow();
		previousStage.close();
		
	}
	
}
