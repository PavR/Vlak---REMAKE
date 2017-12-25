package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import code_game.Main;
import code_game.ScoreRecord;
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
	
	private static boolean leaderboard = true;
	
	private static boolean firstStart = true;
	
	String name;
	int score;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
			
		sh = new Sound_Handler();

		sh.playMenu();
		
		if(firstStart) {
			
			String AbsolutePath = new File(".").getAbsolutePath();
	    	
	    	AbsolutePath = (AbsolutePath.substring(0, AbsolutePath.length() - 1));
	    	
	    	File leaderboard = new File(AbsolutePath + "LEADERBOARD.txt");

	    	BufferedReader br;
	    	
	    	try {
				
				br = new BufferedReader(new FileReader(leaderboard));
				
				try {
					
				    StringBuilder sb = new StringBuilder();
				    String line = br.readLine();
				    
				    while (line != null) {
				    	
				    	if(line.startsWith("<N>")) {
				    		
				    		name = line.substring(3, line.length());
				    		
				    	}
				    	
				    	if(line.startsWith("<S>")) {
				    		
				    		score = Integer.parseInt(line.substring(3, line.length()));
				    		
				    		new ScoreRecord(name, score);
				    		
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
		
		firstStart = false;
		
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
		
		leaderboard = false;
		
	}
	
	public void b_options_onAction() throws IOException {
		
		sh.stopSound();
		
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Options.fxml"));
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
	
	public void b_editor_onAction() throws IOException {
		
		sh.stopSound();
		
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Editor.fxml"));
		Scene scene = new Scene(root, 1000, 750);
		
		Stage primaryStage = new Stage();
		
		primaryStage.setTitle("Vlak - REMAKE");
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Stage previousStage = (Stage)b_play.getScene().getWindow();
		previousStage.close();
		
	}
	
	public void b_achievements_onAction() throws IOException {
		
		sh.stopSound();
		
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Achievements.fxml"));
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
	
	public void b_leaderboard_onAction() throws IOException {
		
		leaderboard = true;
		
		sh.stopSound();
		
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Leaderboard.fxml"));
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
	
	public void b_exit_onAction() {
		
		sh.stopSound();
		
		System.exit(0);
		
	}

	public static boolean isLeaderboard() {
		
		return leaderboard;
		
	}
	
}
