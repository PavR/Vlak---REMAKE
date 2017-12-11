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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Editor_Controller implements Initializable{

	@FXML
	private Canvas c_canvas;
	@FXML
	private MenuItem mi_load, mi_save;
	@FXML
	private TextField tf_level, tf_password;
	@FXML
	private Button b_back;
	@FXML
	private ListView<Editor_Object> lw_objects;
	
	private ObservableList<Editor_Object> ol_objects;
	
	private GraphicsContext gc;
	
	private ArrayList<Editor_Object> allEditor_Objects = new ArrayList<Editor_Object>();
	private ArrayList<Editor_Tunnel> allEditor_Tunnels = new ArrayList<Editor_Tunnel>();
	
	private Editor_SelectedTile st;
	
	private String selectedEditor_Object = "";
	
	private boolean trainPlaced = false;
	private boolean gatePlaced = false;
	
	private Formatter x;
	
	private boolean connectEditor_Tunnels = false;
	
	private Graphics_Handler gh;
	
	private int dragX1, dragX2, dragY1, dragY2;
	
	private ArrayList<Integer> skipPlaceing = new ArrayList<Integer>();
	
	public void initialize(URL arg0, ResourceBundle arg1) {

		gh = new Graphics_Handler();

		setKeyboardInput();
		
		gc = c_canvas.getGraphicsContext2D();	
		
		st = new Editor_SelectedTile(0, 0);

		ol_objects = FXCollections.observableArrayList();
		
		ol_objects.add(new Editor_Object(gh.getWall(), "Wall"));
		ol_objects.add(new Editor_Object(gh.getTrain1RightStation(), "Train"));
		ol_objects.add(new Editor_Object(gh.getGate(), "Gate"));
		ol_objects.add(new Editor_Object(gh.getObjectPizzaStation(), "Pizza"));
		ol_objects.add(new Editor_Object(gh.getObjectUnicornStation(), "Unicorn"));
		ol_objects.add(new Editor_Object(gh.getEmpty(), "Empty"));
		ol_objects.add(new Editor_Object(gh.getTunnel(), "Tunnel"));
		
		lw_objects.setCellFactory(new Callback<ListView<Editor_Object>, ListCell<Editor_Object>>(){
			
			public ListCell<Editor_Object> call(ListView<Editor_Object> arg0) {
				
				ListCell<Editor_Object> cell = new ListCell<Editor_Object>() {
					
					protected void updateItem(Editor_Object object, boolean b) {
						
						super.updateItem(object, b);
						
						if(object != null) {
							
							ImageView view = new ImageView(object.getImage());
							setGraphic(view);
							setText(object.getName());
							
						}
						
					}
					
				};
				
				return cell;
			}
			
		});
		
		lw_objects.setItems(ol_objects);
		lw_objects.refresh();
		
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
		    	
		    	makeObject(X, Y);

		    }
		    
		});
		
		c_canvas.setOnMouseClicked(e -> {
			
			if(e.getButton() == MouseButton.PRIMARY) {
				
				int X = (int)((e.getSceneX() - 280) / 36);
		    	int Y = (int)((e.getSceneY() - 30) / 36);
		    	
		    	X = X * 36;
		    	Y = Y * 36;
		    	
		    	st.setX(X);
		    	st.setY(Y);
		    	
		    	makeObject(X, Y);
			}
			
		});
		
		c_canvas.setOnMousePressed(e -> {
			
			dragX1 = (int)((e.getSceneX() - 280) / 36);
	    	dragY1 = (int)((e.getSceneY() - 30) / 36);
	    	
	    	dragX1 = dragX1 * 36;
	    	dragY1 = dragY1 * 36;
			
		});
		
		c_canvas.setOnMouseReleased(e -> {
			
			dragX2 = (int)((e.getSceneX() - 280) / 36);
	    	dragY2 = (int)((e.getSceneY() - 30) / 36);
	    	
	    	dragX2 = dragX2 * 36;
	    	dragY2 = dragY2 * 36;
	    	
	    	st.setX(dragX2);
	    	st.setY(dragY2);
	    	
	    	int X, Y;
	    	
	    	if(dragX1 > dragX2) {
	    		
	    		X = dragX1 - dragX2;
	    		
	    	}else {
	    		
	    		X = dragX2 - dragX1;
	    		
	    	}
	    	
	    	if(dragY1 > dragY2) {
	    		
	    		Y = dragY1 - dragY2;
	    		
	    	}else {
	    		
	    		Y = dragY2 - dragY1;
	    		
	    	}
	    	
	    	X = X / 36;
	    	Y = Y / 36;
	    	
	    	selectedEditor_Object = lw_objects.getSelectionModel().getSelectedItem().getName();
	    	
			if(selectedEditor_Object.equals("Wall")) {
	    			
			    if(dragX2 > dragX1) {
			    		
				    for(int y = 0; y <= Y; y++) {
				    		
					   	for(int x = 0; x <= X; x++) {
					    		
					    	if(dragY2 > dragY1) {
					    		
					    		skipPlaceing.clear();
					    		
					    		for(int z = 0; z < allEditor_Objects.size(); z++) {
					    			
						    		if((allEditor_Objects.get(z).getX() == dragX1 + (x * 36)) && (allEditor_Objects.get(z).getY() == dragY1 + (y * 36))) {
					    				
						    			skipPlaceing.add(allEditor_Objects.get(z).getX());
						    			skipPlaceing.add(allEditor_Objects.get(z).getY());
					    				
					    			}
		
					    		}
					    		
					    		if(skipPlaceing.size() > 0) {
					    			
						    		if(skipPlaceing.get(0) == dragX1 + (x * 36) && skipPlaceing.get(1) == dragY1 + (y * 36)) {
						    			
						    			skipPlaceing.remove(1);
						    			skipPlaceing.remove(0);
						    			
						    		}else {
						    			
						    			allEditor_Objects.add(new Editor_Object(dragX1 + (x * 36), dragY1 + (y * 36), gh.getWall(), "Wall"));
						    			
						    		}
					    			
					    		}else {
					    			
					    			allEditor_Objects.add(new Editor_Object(dragX1 + (x * 36), dragY1 + (y * 36), gh.getWall(), "Wall"));
					    			
					    		}
					    			
					    	}else {
					    			
					    		skipPlaceing.clear();
					    		
					    		for(int z = 0; z < allEditor_Objects.size(); z++) {
					    			
						    		if((allEditor_Objects.get(z).getX() == dragX1 + (x * 36)) && (allEditor_Objects.get(z).getY() == dragY2 + (y * 36))) {
					    				
						    			skipPlaceing.add(allEditor_Objects.get(z).getX());
						    			skipPlaceing.add(allEditor_Objects.get(z).getY());
					    				
					    			}
		
					    		}
					    		
					    		if(skipPlaceing.size() > 0) {
					    			
						    		if(skipPlaceing.get(0) == dragX1 + (x * 36) && skipPlaceing.get(1) == dragY2 + (y * 36)) {
						    			
						    			skipPlaceing.remove(1);
						    			skipPlaceing.remove(0);
						    			
						    		}else {
						    			
						    			allEditor_Objects.add(new Editor_Object(dragX1 + (x * 36), dragY2 + (y * 36), gh.getWall(), "Wall"));
						    			
						    		}
					    			
					    		}else {
					    			
					    			allEditor_Objects.add(new Editor_Object(dragX1 + (x * 36), dragY2 + (y * 36), gh.getWall(), "Wall"));
					    			
					    		}
					    			
					    	}
					    		
					    }
				    		
				    }
			    		
			    }else {
			    		
				    for(int y = 0; y <= Y; y++) {
				    		
					   	for(int x = 0; x <= X; x++) {
					   		
					    	if(dragY2 > dragY1) {
					    			
					    		skipPlaceing.clear();
					    		
					    		for(int z = 0; z < allEditor_Objects.size(); z++) {
					    			
						    		if((allEditor_Objects.get(z).getX() == dragX2 + (x * 36)) && (allEditor_Objects.get(z).getY() == dragY1 + (y * 36))) {
					    				
						    			skipPlaceing.add(allEditor_Objects.get(z).getX());
						    			skipPlaceing.add(allEditor_Objects.get(z).getY());
					    				
					    			}
		
					    		}
					    		
					    		if(skipPlaceing.size() > 0) {
					    			
						    		if(skipPlaceing.get(0) == dragX2 + (x * 36) && skipPlaceing.get(1) == dragY1 + (y * 36)) {
						    			
						    			skipPlaceing.remove(1);
						    			skipPlaceing.remove(0);
						    			
						    		}else {
						    			
						    			allEditor_Objects.add(new Editor_Object(dragX2 + (x * 36), dragY1 + (y * 36), gh.getWall(), "Wall"));
						    			
						    		}
					    			
					    		}else {
					    			
					    			allEditor_Objects.add(new Editor_Object(dragX2 + (x * 36), dragY1 + (y * 36), gh.getWall(), "Wall"));
					    			
					    		}
					    			
					    	}else {
					    			
					    		skipPlaceing.clear();
					    		
					    		for(int z = 0; z < allEditor_Objects.size(); z++) {
					    			
						    		if((allEditor_Objects.get(z).getX() == dragX2 + (x * 36)) && (allEditor_Objects.get(z).getY() == dragY2 + (y * 36))) {
					    				
						    			skipPlaceing.add(allEditor_Objects.get(z).getX());
						    			skipPlaceing.add(allEditor_Objects.get(z).getY());
					    				
					    			}
		
					    		}
					    		
					    		if(skipPlaceing.size() > 0) {
					    			
						    		if(skipPlaceing.get(0) == dragX2 + (x * 36) && skipPlaceing.get(1) == dragY2 + (y * 36)) {
						    			
						    			skipPlaceing.remove(1);
						    			skipPlaceing.remove(0);
						    			
						    		}else {
						    			
							    		allEditor_Objects.add(new Editor_Object(dragX2 + (x * 36), dragY2 + (y * 36), gh.getWall(), "Wall"));
						    			
						    		}
					    			
					    		}else {
					    			
						    		allEditor_Objects.add(new Editor_Object(dragX2 + (x * 36), dragY2 + (y * 36), gh.getWall(), "Wall"));
					    			
					    		}
					    			
					    	}
					    		
					    }
				    		
				    }
			    		
			    }
				
			}else if(selectedEditor_Object.equals("Pizza")) {
				
				if(dragX2 > dragX1) {
		    		
				    for(int y = 0; y <= Y; y++) {
				    		
					   	for(int x = 0; x <= X; x++) {
					    		
					    	if(dragY2 > dragY1) {
					    		
					    		skipPlaceing.clear();
					    		
					    		for(int z = 0; z < allEditor_Objects.size(); z++) {
					    			
						    		if((allEditor_Objects.get(z).getX() == dragX1 + (x * 36)) && (allEditor_Objects.get(z).getY() == dragY1 + (y * 36))) {
					    				
						    			skipPlaceing.add(allEditor_Objects.get(z).getX());
						    			skipPlaceing.add(allEditor_Objects.get(z).getY());
					    				
					    			}
		
					    		}
					    		
					    		if(skipPlaceing.size() > 0) {
					    			
						    		if(skipPlaceing.get(0) == dragX1 + (x * 36) && skipPlaceing.get(1) == dragY1 + (y * 36)) {
						    			
						    			skipPlaceing.remove(1);
						    			skipPlaceing.remove(0);
						    			
						    		}else {
						    			
						    			allEditor_Objects.add(new Editor_Object(dragX1 + (x * 36), dragY1 + (y * 36), gh.getObjectPizzaStation(), "Pizza"));
						    			
						    		}
					    			
					    		}else {
					    			
					    			allEditor_Objects.add(new Editor_Object(dragX1 + (x * 36), dragY1 + (y * 36), gh.getObjectPizzaStation(), "Pizza"));
					    			
					    		}
					    			
					    	}else {
					    			
					    		skipPlaceing.clear();
					    		
					    		for(int z = 0; z < allEditor_Objects.size(); z++) {
					    			
						    		if((allEditor_Objects.get(z).getX() == dragX1 + (x * 36)) && (allEditor_Objects.get(z).getY() == dragY2 + (y * 36))) {
					    				
						    			skipPlaceing.add(allEditor_Objects.get(z).getX());
						    			skipPlaceing.add(allEditor_Objects.get(z).getY());
					    				
					    			}
		
					    		}
					    		
					    		if(skipPlaceing.size() > 0) {
					    			
						    		if(skipPlaceing.get(0) == dragX1 + (x * 36) && skipPlaceing.get(1) == dragY2 + (y * 36)) {
						    			
						    			skipPlaceing.remove(1);
						    			skipPlaceing.remove(0);
						    			
						    		}else {
						    			
						    			allEditor_Objects.add(new Editor_Object(dragX1 + (x * 36), dragY2 + (y * 36), gh.getObjectPizzaStation(), "Pizza"));
						    			
						    		}
					    			
					    		}else {
					    			
					    			allEditor_Objects.add(new Editor_Object(dragX1 + (x * 36), dragY2 + (y * 36), gh.getObjectPizzaStation(), "Pizza"));
					    			
					    		}
					    			
					    	}
					    		
					    }
				    		
				    }
			    		
			    }else {
			    		
				    for(int y = 0; y <= Y; y++) {
				    		
					   	for(int x = 0; x <= X; x++) {
					   		
					    	if(dragY2 > dragY1) {
					    			
					    		skipPlaceing.clear();
					    		
					    		for(int z = 0; z < allEditor_Objects.size(); z++) {
					    			
						    		if((allEditor_Objects.get(z).getX() == dragX2 + (x * 36)) && (allEditor_Objects.get(z).getY() == dragY1 + (y * 36))) {
					    				
						    			skipPlaceing.add(allEditor_Objects.get(z).getX());
						    			skipPlaceing.add(allEditor_Objects.get(z).getY());
					    				
					    			}
		
					    		}
					    		
					    		if(skipPlaceing.size() > 0) {
					    			
						    		if(skipPlaceing.get(0) == dragX2 + (x * 36) && skipPlaceing.get(1) == dragY1 + (y * 36)) {
						    			
						    			skipPlaceing.remove(1);
						    			skipPlaceing.remove(0);
						    			
						    		}else {
						    			
						    			allEditor_Objects.add(new Editor_Object(dragX2 + (x * 36), dragY1 + (y * 36), gh.getObjectPizzaStation(), "Pizza"));
						    			
						    		}
					    			
					    		}else {
					    			
					    			allEditor_Objects.add(new Editor_Object(dragX2 + (x * 36), dragY1 + (y * 36), gh.getObjectPizzaStation(), "Pizza"));
					    			
					    		}
					    			
					    	}else {
					    			
					    		skipPlaceing.clear();
					    		
					    		for(int z = 0; z < allEditor_Objects.size(); z++) {
					    			
						    		if((allEditor_Objects.get(z).getX() == dragX2 + (x * 36)) && (allEditor_Objects.get(z).getY() == dragY2 + (y * 36))) {
					    				
						    			skipPlaceing.add(allEditor_Objects.get(z).getX());
						    			skipPlaceing.add(allEditor_Objects.get(z).getY());
					    				
					    			}
		
					    		}
					    		
					    		if(skipPlaceing.size() > 0) {
					    			
						    		if(skipPlaceing.get(0) == dragX2 + (x * 36) && skipPlaceing.get(1) == dragY2 + (y * 36)) {
						    			
						    			skipPlaceing.remove(1);
						    			skipPlaceing.remove(0);
						    			
						    		}else {
						    			
							    		allEditor_Objects.add(new Editor_Object(dragX2 + (x * 36), dragY2 + (y * 36), gh.getObjectPizzaStation(), "Pizza"));
						    			
						    		}
					    			
					    		}else {
					    			
						    		allEditor_Objects.add(new Editor_Object(dragX2 + (x * 36), dragY2 + (y * 36), gh.getObjectPizzaStation(), "Pizza"));
					    			
					    		}
					    			
					    	}
					    		
					    }
				    		
				    }
			    		
			    }
				
			}else if(selectedEditor_Object.equals("Unicorn")) {
				
				if(dragX2 > dragX1) {
		    		
				    for(int y = 0; y <= Y; y++) {
				    		
					   	for(int x = 0; x <= X; x++) {
					    		
					    	if(dragY2 > dragY1) {
					    		
					    		skipPlaceing.clear();
					    		
					    		for(int z = 0; z < allEditor_Objects.size(); z++) {
					    			
						    		if((allEditor_Objects.get(z).getX() == dragX1 + (x * 36)) && (allEditor_Objects.get(z).getY() == dragY1 + (y * 36))) {
					    				
						    			skipPlaceing.add(allEditor_Objects.get(z).getX());
						    			skipPlaceing.add(allEditor_Objects.get(z).getY());
					    				
					    			}
		
					    		}
					    		
					    		if(skipPlaceing.size() > 0) {
					    			
						    		if(skipPlaceing.get(0) == dragX1 + (x * 36) && skipPlaceing.get(1) == dragY1 + (y * 36)) {
						    			
						    			skipPlaceing.remove(1);
						    			skipPlaceing.remove(0);
						    			
						    		}else {
						    			
						    			allEditor_Objects.add(new Editor_Object(dragX1 + (x * 36), dragY1 + (y * 36), gh.getObjectUnicornStation(), "Unicorn"));
						    			
						    		}
					    			
					    		}else {
					    			
					    			allEditor_Objects.add(new Editor_Object(dragX1 + (x * 36), dragY1 + (y * 36), gh.getObjectUnicornStation(), "Unicorn"));
					    			
					    		}
					    			
					    	}else {
					    			
					    		skipPlaceing.clear();
					    		
					    		for(int z = 0; z < allEditor_Objects.size(); z++) {
					    			
						    		if((allEditor_Objects.get(z).getX() == dragX1 + (x * 36)) && (allEditor_Objects.get(z).getY() == dragY2 + (y * 36))) {
					    				
						    			skipPlaceing.add(allEditor_Objects.get(z).getX());
						    			skipPlaceing.add(allEditor_Objects.get(z).getY());
					    				
					    			}
		
					    		}
					    		
					    		if(skipPlaceing.size() > 0) {
					    			
						    		if(skipPlaceing.get(0) == dragX1 + (x * 36) && skipPlaceing.get(1) == dragY2 + (y * 36)) {
						    			
						    			skipPlaceing.remove(1);
						    			skipPlaceing.remove(0);
						    			
						    		}else {
						    			
						    			allEditor_Objects.add(new Editor_Object(dragX1 + (x * 36), dragY2 + (y * 36), gh.getObjectUnicornStation(), "Unicorn"));
						    			
						    		}
					    			
					    		}else {
					    			
					    			allEditor_Objects.add(new Editor_Object(dragX1 + (x * 36), dragY2 + (y * 36), gh.getObjectUnicornStation(), "Unicorn"));
					    			
					    		}
					    			
					    	}
					    		
					    }
				    		
				    }
			    		
			    }else {
			    		
				    for(int y = 0; y <= Y; y++) {
				    		
					   	for(int x = 0; x <= X; x++) {
					   		
					    	if(dragY2 > dragY1) {
					    			
					    		skipPlaceing.clear();
					    		
					    		for(int z = 0; z < allEditor_Objects.size(); z++) {
					    			
						    		if((allEditor_Objects.get(z).getX() == dragX2 + (x * 36)) && (allEditor_Objects.get(z).getY() == dragY1 + (y * 36))) {
					    				
						    			skipPlaceing.add(allEditor_Objects.get(z).getX());
						    			skipPlaceing.add(allEditor_Objects.get(z).getY());
					    				
					    			}
		
					    		}
					    		
					    		if(skipPlaceing.size() > 0) {
					    			
						    		if(skipPlaceing.get(0) == dragX2 + (x * 36) && skipPlaceing.get(1) == dragY1 + (y * 36)) {
						    			
						    			skipPlaceing.remove(1);
						    			skipPlaceing.remove(0);
						    			
						    		}else {
						    			
						    			allEditor_Objects.add(new Editor_Object(dragX2 + (x * 36), dragY1 + (y * 36), gh.getObjectUnicornStation(), "Unicorn"));
						    			
						    		}
					    			
					    		}else {
					    			
					    			allEditor_Objects.add(new Editor_Object(dragX2 + (x * 36), dragY1 + (y * 36), gh.getObjectUnicornStation(), "Unicorn"));
					    			
					    		}
					    			
					    	}else {
					    			
					    		skipPlaceing.clear();
					    		
					    		for(int z = 0; z < allEditor_Objects.size(); z++) {
					    			
						    		if((allEditor_Objects.get(z).getX() == dragX2 + (x * 36)) && (allEditor_Objects.get(z).getY() == dragY2 + (y * 36))) {
					    				
						    			skipPlaceing.add(allEditor_Objects.get(z).getX());
						    			skipPlaceing.add(allEditor_Objects.get(z).getY());
					    				
					    			}
		
					    		}
					    		
					    		if(skipPlaceing.size() > 0) {
					    			
						    		if(skipPlaceing.get(0) == dragX2 + (x * 36) && skipPlaceing.get(1) == dragY2 + (y * 36)) {
						    			
						    			skipPlaceing.remove(1);
						    			skipPlaceing.remove(0);
						    			
						    		}else {
						    			
							    		allEditor_Objects.add(new Editor_Object(dragX2 + (x * 36), dragY2 + (y * 36), gh.getObjectUnicornStation(), "Unicorn"));
						    			
						    		}
					    			
					    		}else {
					    			
						    		allEditor_Objects.add(new Editor_Object(dragX2 + (x * 36), dragY2 + (y * 36), gh.getObjectUnicornStation(), "Unicorn"));
					    			
					    		}
					    			
					    	}
					    		
					    }
				    		
				    }
			    		
			    }
				
			}else if(selectedEditor_Object.equals("Empty")) {
				
				if(dragX2 > dragX1) {
		    		
				    for(int y = 0; y <= Y; y++) {
				    		
					   	for(int x = 0; x <= X; x++) {
					    		
					    	if(dragY2 > dragY1) {
					    		
					    		for(int z = 0; z < allEditor_Objects.size(); z++) {
					    			
						    		if((allEditor_Objects.get(z).getX() == dragX1 + (x * 36)) && (allEditor_Objects.get(z).getY() == dragY1 + (y * 36))) {
					    				
						    			if(allEditor_Objects.get(z).getName().equals("Train")) {
						    				
						    				trainPlaced = false;
						    				
						    			}
						    			
						    			if(allEditor_Objects.get(z).getName().equals("Gate")) {
						    				
						    				gatePlaced = false;
						    				
						    			}
						    			
						    			allEditor_Objects.remove(z);
					    				
					    			}
		
					    		}
					    			
					    	}else {
					    		
					    		for(int z = 0; z < allEditor_Objects.size(); z++) {
					    			
						    		if((allEditor_Objects.get(z).getX() == dragX1 + (x * 36)) && (allEditor_Objects.get(z).getY() == dragY2 + (y * 36))) {
					    				
						    			if(allEditor_Objects.get(z).getName().equals("Train")) {
						    				
						    				trainPlaced = false;
						    				
						    			}
						    			
						    			if(allEditor_Objects.get(z).getName().equals("Gate")) {
						    				
						    				gatePlaced = false;
						    				
						    			}
						    			
						    			allEditor_Objects.remove(z);
					    				
					    			}
		
					    		}
					    			
					    	}
					    		
					    }
				    		
				    }
			    		
			    }else {
			    		
				    for(int y = 0; y <= Y; y++) {
				    		
					   	for(int x = 0; x <= X; x++) {
					   		
					    	if(dragY2 > dragY1) {
					    		
					    		for(int z = 0; z < allEditor_Objects.size(); z++) {
					    			
						    		if((allEditor_Objects.get(z).getX() == dragX2 + (x * 36)) && (allEditor_Objects.get(z).getY() == dragY1 + (y * 36))) {
					    				
						    			if(allEditor_Objects.get(z).getName().equals("Train")) {
						    				
						    				trainPlaced = false;
						    				
						    			}
						    			
						    			if(allEditor_Objects.get(z).getName().equals("Gate")) {
						    				
						    				gatePlaced = false;
						    				
						    			}
						    			
						    			allEditor_Objects.remove(z);
					    				
					    			}
		
					    		}
					    			
					    	}else {
					    		
					    		for(int z = 0; z < allEditor_Objects.size(); z++) {
					    			
						    		if((allEditor_Objects.get(z).getX() == dragX2 + (x * 36)) && (allEditor_Objects.get(z).getY() == dragY2 + (y * 36))) {
					    				
						    			if(allEditor_Objects.get(z).getName().equals("Train")) {
						    				
						    				trainPlaced = false;
						    				
						    			}
						    			
						    			if(allEditor_Objects.get(z).getName().equals("Gate")) {
						    				
						    				gatePlaced = false;
						    				
						    			}
						    			
						    			allEditor_Objects.remove(z);
					    				
					    			}
		
					    		}
					    			
					    	}
					    		
					    }
				    		
				    }
			    		
			    }
				
			}
			
	    	clearCanvas();
    		drawCanvas();
			
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
	
	public void onMouseMoved() {
		
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
		    		
		    		allEditor_Objects.add(new Editor_Object(X, Y, gh.getTrain1RightStation(), "Train"));
		    		
		    	}
		    	
		    	if(line.startsWith("2")) {
		    		
		    		int X = Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(",")));
		    		int Y = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(")")));
		    		
		    		allEditor_Objects.add(new Editor_Object(X, Y, gh.getObjectPizzaStation(), "Pizza"));
		    		
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
	
	public void makeObject(int X, int Y) {
		
		selectedEditor_Object = lw_objects.getSelectionModel().getSelectedItem().getName();
		
		if(selectedEditor_Object.equals("Wall")) {

    		if(allEditor_Objects.isEmpty()) {
    			
    			allEditor_Objects.add(new Editor_Object(X, Y, gh.getWall(), "Wall"));
    			
    		}else {
    			
    			for(int x = 0; x < allEditor_Objects.size(); x++) {

	    			if((allEditor_Objects.get(x).getX() == X) && (allEditor_Objects.get(x).getY() == Y)) {
	    				
	    				return;
	    				
	    			}
	    			
	    		}

				allEditor_Objects.add(new Editor_Object(X, Y, gh.getWall(), "Wall"));
    			
    		}
    		
    	}else if(selectedEditor_Object.equals("Train")) {
    		
    		if(trainPlaced == false) {
    			
    			for(int x = 0; x < allEditor_Objects.size(); x++) {

	    			if((allEditor_Objects.get(x).getX() == X) && (allEditor_Objects.get(x).getY() == Y)) {
	    				
	    				return;
	    				
	    			}
	    			
	    		}
    			
    			trainPlaced = true;
    			
    			allEditor_Objects.add(new Editor_Object(X, Y, gh.getTrain1RightStation(), "Train"));
    			
    		}
    		
    	}else if(selectedEditor_Object.equals("Pizza")) {
    		
    		if(allEditor_Objects.isEmpty()) {
    			
    			allEditor_Objects.add(new Editor_Object(X, Y, gh.getObjectPizzaStation(), "Pizza"));
    			
    		}else {
    			
    			for(int x = 0; x < allEditor_Objects.size(); x++) {

	    			if((allEditor_Objects.get(x).getX() == X) && (allEditor_Objects.get(x).getY() == Y)) {
	    				
	    				return;
	    				
	    			}
	    			
	    		}

				allEditor_Objects.add(new Editor_Object(X, Y, gh.getObjectPizzaStation(), "Pizza"));
    			
    		}
    		
    	}else if(selectedEditor_Object.equals("Unicorn")){
    		
    		if(allEditor_Objects.isEmpty()) {
    			
    			allEditor_Objects.add(new Editor_Object(X, Y, gh.getObjectUnicornStation(), "Unicorn"));
    			
    		}else {
    			
    			for(int x = 0; x < allEditor_Objects.size(); x++) {

	    			if((allEditor_Objects.get(x).getX() == X) && (allEditor_Objects.get(x).getY() == Y)) {
	    				
	    				return;
	    				
	    			}
	    			
	    		}

				allEditor_Objects.add(new Editor_Object(X, Y, gh.getObjectUnicornStation(), "Unicorn"));
    			
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
				    					
				    					return;
				    					
				    				}else {
				    					
				    					connectEditor_Tunnels = false;
				    					
				    					allEditor_Tunnels.add(new Editor_Tunnel(X, Y, gh.getTunnel(), "Tunnel"));
				    					
				    					allEditor_Tunnels.get(allEditor_Tunnels.size() - 2).setEnd(allEditor_Tunnels.get(allEditor_Tunnels.size() - 1));
				    					allEditor_Tunnels.get(allEditor_Tunnels.size() - 1).setEnd(allEditor_Tunnels.get(allEditor_Tunnels.size() - 2));
				    					
				    					return;
				    				}
				    				
				    			}
		    					
		    				}
	    					
	    				}else {
	    					
	    					allEditor_Tunnels.add(new Editor_Tunnel(X, Y, gh.getTunnel(), "Tunnel"));
	    					
	    					connectEditor_Tunnels = true;
	    					
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
		    					
		    					return;
		    					
		    				}else {
		    					
		    					connectEditor_Tunnels = false;
		    					
		    					allEditor_Tunnels.add(new Editor_Tunnel(X, Y, gh.getTunnel(), "Tunnel"));
		    					
		    					allEditor_Tunnels.get(allEditor_Tunnels.size() - 2).setEnd(allEditor_Tunnels.get(allEditor_Tunnels.size() - 1));
		    					allEditor_Tunnels.get(allEditor_Tunnels.size() - 1).setEnd(allEditor_Tunnels.get(allEditor_Tunnels.size() - 2));
		    					
		    					return;
		    					
		    				}
		    				
		    			}
    					
    				}
    				
    			}else {
    				
    				allEditor_Tunnels.add(new Editor_Tunnel(X, Y, gh.getTunnel(), "Tunnel"));
    				
    				connectEditor_Tunnels = true;
    				
    				return;
    			}
    			
    		}
    	
    	}
		
		clearCanvas();
		drawCanvas();
		
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
