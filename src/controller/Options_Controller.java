package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import handler.Graphics_Handler;
import handler.Sound_Handler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Options_Controller implements Initializable {
	
	@FXML
	private CheckBox cb_sounds;
	@FXML
	private Button b_back, b_right, b_left;
	@FXML
	private Label l_number;
	@FXML
	private ImageView iv_skin;
	
	private ArrayList<Image> trains = new ArrayList<Image>();
	
	private Graphics_Handler gh;
	
	private int skinNumber = 1;
	
	public void initialize(URL arg0, ResourceBundle arg1) {

		gh = new Graphics_Handler();
		
		if(Sound_Handler.isAllowed()) {
			
			cb_sounds.setSelected(true);
			
		}else {
			
			cb_sounds.setSelected(false);
			
		}
		
		if(gh.getPickedTrain() == 1) {
			
			skinNumber = 1;
			
		}else if(gh.getPickedTrain() == 2) {
			
			skinNumber = 2;
			
		}
		
		trains.add(gh.getTrain1RightStation());
		trains.add(gh.getTrain2RightStation());
		
		l_number.setText(skinNumber + "/" + trains.size());
		
		if(skinNumber == 1) {
			
			iv_skin.setImage(gh.getTrain1RightStation());
			
		}else if(skinNumber == 2) {
			
			iv_skin.setImage(gh.getTrain2RightStation());
			
		}
		
	}
	
	public void cb_sounds_onAction(){

		if(cb_sounds.isSelected()) {
			
			Sound_Handler.setAllowed(true);
			
		}else {
			
			Sound_Handler.setAllowed(false);
			
		}
		
	}
	
	public void b_back_onAction() throws IOException {
		
		String AbsolutePath = new File(".").getAbsolutePath();
    	
    	AbsolutePath = (AbsolutePath.substring(0, AbsolutePath.length() - 1));
    	
    	File options = new File(AbsolutePath + "OPTIONS.txt");
    	
    	FileWriter fw = new FileWriter(options.getAbsoluteFile());
    	BufferedWriter bw = new BufferedWriter(fw);
    	
    	if(Sound_Handler.isAllowed()) {
    		
    		bw.write("sounds=on");
    		
    	}else {
    		
    		bw.write("sounds=off");
    		
    	}
    	
    	bw.write("\n");
    	
    	if(skinNumber == 1) {
    		
    		bw.write("skin=1");
    		
    	}else if(skinNumber == 2) {
    		
    		bw.write("skin=2");
    		
    	}
    	
    	bw.flush();
    	bw.close();
		
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Menu.fxml"));
		Scene scene = new Scene(root, 720, 800);
		
		Stage primaryStage = new Stage();
		
		primaryStage.setTitle("Vlak - REMAKE");
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Stage previousStage = (Stage)b_back.getScene().getWindow();
		previousStage.close();
		
	}
	
	public void b_left_onAction() {
		
		if(skinNumber == 1) {
			
			
			
		}else {
			
			skinNumber--;
			l_number.setText(skinNumber + "/" + trains.size());
			
			if(skinNumber == 1) {
				
				iv_skin.setImage(gh.getTrain1RightStation());
				
			}else if(skinNumber == 2) {
				
				iv_skin.setImage(gh.getTrain2RightStation());
				
			}
			
		}
		
	}
	
	public void b_right_onAction() {
		
		if(skinNumber == trains.size()) {
			
			
			
		}else {
			
			skinNumber++;
			l_number.setText(skinNumber + "/" + trains.size());
			
			if(skinNumber == 1) {
				
				iv_skin.setImage(gh.getTrain1RightStation());
				
			}else if(skinNumber == 2) {
				
				iv_skin.setImage(gh.getTrain2RightStation());
				
			}
			
		}
		
	}

}
