package code;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application{

	public static final int WIDTH = 720;
	public static final int HEIGHT = 800;
	
	public void start(Stage primaryStage) {
		
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
			Scene scene = new Scene(root, WIDTH, HEIGHT);
			
			primaryStage.setTitle("Vlak - REMAKE");
			primaryStage.setResizable(false);
			primaryStage.sizeToScene();
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
	}

	public static void main(String[] args) {
		
		launch(args);
		
	}

	public int getWIDTH() {
		
		return WIDTH;
		
	}

	public int getHEIGHT() {
		
		return HEIGHT;
		
	}

}
