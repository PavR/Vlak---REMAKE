package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import code.Main;
import handler.Sound_Handler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Menu_Controller implements Initializable{

	@FXML
	private Button b_play;
	
	private Sound_Handler sh;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
			
		sh = new Sound_Handler();

		sh.playMenu();
		
	}

	public void b_play_onAction() throws IOException {
		
		sh.stopSound();
		
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
		Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
		
		Stage primaryStage = new Stage();
		
		primaryStage.setTitle("Vlak - REMAKE");
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Stage previousStage = (Stage)b_play.getScene().getWindow();
		previousStage.close();
		
	}
	
}
