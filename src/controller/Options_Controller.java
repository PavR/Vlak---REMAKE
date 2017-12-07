package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import handler.Sound_Handler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class Options_Controller implements Initializable {
	
	@FXML
	private CheckBox cb_sounds;

	@FXML
	private Button b_back;
	
	public void initialize(URL arg0, ResourceBundle arg1) {

		if(Sound_Handler.isAllowed()) {
			
			cb_sounds.setSelected(true);
			
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

}
