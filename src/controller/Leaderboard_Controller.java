package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import code_game.Main;
import code_game.ScoreRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Leaderboard_Controller implements Initializable{

	@FXML
	private Button b_back, b_enter;
	@FXML
	private TextField tf_name;
	@FXML
	private TableView<ScoreRecord> tv_leaderboard;
	@FXML
	private TableColumn<ScoreRecord, Integer> tc_score;
	@FXML
	private TableColumn<ScoreRecord, String> tc_name;
	@FXML
	private Label l_score;
	
	private ObservableList<ScoreRecord> ol_leaderboard;
	
	@SuppressWarnings("deprecation")
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ol_leaderboard = FXCollections.observableArrayList(ScoreRecord.getLeaderboard());
		
		tc_score.setCellValueFactory(new PropertyValueFactory<>("score"));
		tc_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		tc_score.impl_setReorderable(false);
		tc_name.impl_setReorderable(false);
		
		tc_score.setResizable(false);
		tc_name.setResizable(false);
		
		tc_score.setSortable(false);
		tc_name.setSortable(false);
		
		tv_leaderboard.setItems(ol_leaderboard);
		tv_leaderboard.refresh();
		
		if(Menu_Controller.isLeaderboard()) {
			
			tf_name.setVisible(false);
			b_enter.setVisible(false);
			l_score.setVisible(false);
			
		}
		
		l_score.setText(Integer.toString(Main_Controller.getScore()));
		
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
	
	public void b_enter_onAction() throws IOException {
		
		if(!tf_name.getText().isEmpty()) {
			
			new ScoreRecord(tf_name.getText(), Main_Controller.getScore());
			
			ol_leaderboard = FXCollections.observableArrayList(ScoreRecord.getLeaderboard());
			
			tv_leaderboard.setItems(ol_leaderboard);
			tv_leaderboard.refresh();
			
		}
		
		b_enter.setDisable(true);
		
	}
	
}
