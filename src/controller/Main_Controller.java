package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import code_game.Gate;
import code_game.Level;
import code_game.Object;
import code_game.Train;
import code_game.Tunnel;
import code_game.Wagon;
import code_game.Wall;
import handler.Graphics_Handler;
import handler.Sound_Handler;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class Main_Controller implements Initializable{

	@FXML
	private Canvas c_canvas;
	@FXML
	private Label l_score, l_level, l_f4;
	@FXML
	private TextField tf_password;
	
	private GraphicsContext gc;
	
	private int framsePassed = 0;
	
	private KeyCode lastKeyPressed = KeyCode.A;
	
	private ArrayList<Object> allObjects = new ArrayList<Object>();
	private ArrayList<Wagon> allWagons = new ArrayList<Wagon>();
	private ArrayList<Wall> allWalls = new ArrayList<Wall>();
	private ArrayList<Train> allTrains = new ArrayList<Train>();
	private ArrayList<Gate> allGates = new ArrayList<Gate>();
	private ArrayList<Level> allLevels = new ArrayList<Level>();
	private ArrayList<String> allPasswords = new ArrayList<String>();
	private ArrayList<Tunnel> allTunnels = new ArrayList<Tunnel>();
	
	private boolean alive = true;
	private boolean won = false;

	private int currentLevel = 1;
	
	private int level;
	private String password;
	
	private int score;
	private int currentLevelScore;
	
	private boolean passwordActive = false;
	
	private Sound_Handler sh;
	private Graphics_Handler gh;
	
	private boolean shouldRender;
	
	public void initialize(URL location, ResourceBundle resources) {
		
		sh = new Sound_Handler();
		gh = new Graphics_Handler();
		
		setAnimationTimer();
		setCanvasKeyboardInput();
		setPasswordKeyboardInput();
		
		gc = c_canvas.getGraphicsContext2D();
		
		try {
			
			loadAllLevels();
			loadLevel();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	private void update() {
		
		framsePassed++;
		
		if(framsePassed == 20) {
			
			framsePassed = 0;
			
			if(alive) {
				
				if(allTrains.size() > 0) {
				
					if(lastKeyPressed != KeyCode.A) {
						
						sh.playMove();
						
						for(int x = 0; x < allTrains.size(); x++) {
							
							if(allTrains.get(x).getOrientation() == 0) {
								
								allTrains.get(x).setImage(gh.getTrainUp());
								
							}else if(allTrains.get(x).getOrientation() == 1) {
								
								allTrains.get(x).setImage(gh.getTrainLeft());
								
							}else if(allTrains.get(x).getOrientation() == 2) {
								
								allTrains.get(x).setImage(gh.getTrainDown());
								
							}else if(allTrains.get(x).getOrientation() == 3) {
								
								allTrains.get(x).setImage(gh.getTrainRight());
								
							}
							
						}
						
						for(int x = 0; x < allWagons.size(); x++) {
							
							if(allWagons.get(x).getFutureMoves().get(0) == 0) {
								
								allWagons.get(x).setImage(allWagons.get(x).getWagonU());
								
							}else if(allWagons.get(x).getFutureMoves().get(0) == 1) {
								
								allWagons.get(x).setImage(allWagons.get(x).getWagonR());
								
							}else if(allWagons.get(x).getFutureMoves().get(0) == 2) {
								
								allWagons.get(x).setImage(allWagons.get(x).getWagonD());
								
							}else if(allWagons.get(x).getFutureMoves().get(0) == 3) {
								
								allWagons.get(x).setImage(allWagons.get(x).getWagonR());
								
							}
							
						}
						
						autoMovement();
						
						checkCollisionWithWagons();
						
						autoMoveWagons();
						
						checkCollisionWithGate();
						checkCollisionWithWalls();
						checkCollisionWithObjects();
						checkCollisionWithTunnels();
						
					}
					
				}
				
			}
			
		}
		
	}

	private void render() {
		
		if(alive) {
			
			if(shouldRender == false) {
				
				for(int x = 0; x < allTrains.size(); x++) {
					System.out.println("RENDER TRAIN");
					allTrains.get(x).render(gc);
					
				}
				
				for(int x = 0; x < allObjects.size(); x++) {
					System.out.println("RENDER OBJECT");
					allObjects.get(x).render(gc);
					
				}
				
				for(int x = 0; x < allWagons.size(); x++) {
					System.out.println("RENDER WAGON");
					allWagons.get(x).render(gc);
					
				}
				
			}else {
				
				for(int x = 0; x < allWalls.size(); x++) {
					System.out.println("RENDER WALL");
					allWalls.get(x).render(gc);
					
				}
				
				for(int x = 0; x < allGates.size(); x++) {
					System.out.println("RENDER GATE");
					allGates.get(x).render(gc);
					
				}
				
				for(int x = 0; x < allTunnels.size(); x++) {
					System.out.println("RENDER TUNNEL");
					allTunnels.get(x).render(gc);
					
				}
				
				for(int x = 0; x < allTrains.size(); x++) {
					System.out.println("RENDER TRAIN");
					allTrains.get(x).render(gc);
					
				}
				
				for(int x = 0; x < allObjects.size(); x++) {
					System.out.println("RENDER OBJECT");
					allObjects.get(x).render(gc);
					
				}
				
				for(int x = 0; x < allWagons.size(); x++) {
					System.out.println("RENDER WAGON");
					allWagons.get(x).render(gc);
					
				}
				
				shouldRender = false;
				
			}
			
		}
		
	}

	public void autoMovement() {
		System.out.println("AUTOMOVEMENT");
		
		allTrains.get(0).getLastPosition().clear();
		
		allTrains.get(0).getLastPosition().add(allTrains.get(0).getX());
		allTrains.get(0).getLastPosition().add(allTrains.get(0).getY());
		
		if(allTrains.get(0).getOrientation() == 0) {
			
			allTrains.get(0).setY(allTrains.get(0).getY() - (allTrains.get(0).getSpeed() * 36));
			
			for(int x = 0; x < allWagons.size(); x++) {
				
				allWagons.get(x).getFutureMoves().add(0);
				
			}
			
		}else if(allTrains.get(0).getOrientation() == 2) {
			
			allTrains.get(0).setY(allTrains.get(0).getY() + (allTrains.get(0).getSpeed() * 36));
			
			for(int x = 0; x < allWagons.size(); x++) {
				
				allWagons.get(x).getFutureMoves().add(2);
				
			}
			
		}else if(allTrains.get(0).getOrientation() == 1) {
			
			allTrains.get(0).setX(allTrains.get(0).getX() - (allTrains.get(0).getSpeed() * 36));
			
			for(int x = 0; x < allWagons.size(); x++) {
				
				allWagons.get(x).getFutureMoves().add(1);
				
			}
			
		}else if(allTrains.get(0).getOrientation() == 3) {
			
			allTrains.get(0).setX(allTrains.get(0).getX() + (allTrains.get(0).getSpeed() * 36));
			
			for(int x = 0; x < allWagons.size(); x++) {
				
				allWagons.get(x).getFutureMoves().add(3);
				
			}
			
		}
		
		if(allTrains.get(0).getOrientation() == -1)
			return;
		
	}
	
	public void loadAllLevels() throws IOException {
		System.out.println("LOAD ALL LEVELS");
		String AbsolutePath = new File(".").getAbsolutePath();
		AbsolutePath = (AbsolutePath.substring(0, AbsolutePath.length() - 1));
		AbsolutePath = AbsolutePath + "levels";
		
		int fileCount = new File(AbsolutePath).listFiles().length;

		int levelNumber = 0;
		String levelPassword = "";
		
		for(int x = 1; x <= fileCount; x++) {
			
			File file = new File(AbsolutePath + "/" + (allLevels.size() + 1) + ".txt");
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			try {
				
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();
			    
			    while (line != null) {
			    	
			    	if(line.startsWith("L")) {
			    		
			    		levelNumber = Integer.parseInt(line.substring(2, line.length() - 1));
			    		
			    	}
			    	
			    	if(line.startsWith("P")) {
			    		
			    		levelPassword = line.substring(2, line.length() - 1);
			    		allPasswords.add(levelPassword);
			    		
			    	}
			    	
			    	sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			        
			    }
			    
			} finally {
				
				allLevels.add(new Level(file, levelNumber, levelPassword));
				
			    br.close();
			    
			}
			
		}
		
	}
	
	public void loadLevel() throws IOException {
		System.out.println("LOAD LEVEL");
		
		gc.clearRect(0, 0, 720, 720);
		
		shouldRender = true;
		
		if(allTrains.size() > 0) {

			allTrains.get(0).setSpeed(1);
			
			if(sh.isPlaying()) {
				
				sh.stopSound();
				
			}
			
			allWagons.clear();
			allObjects.clear();
			allWalls.clear();
			allWagons.clear();
			allTrains.clear();
			allGates.clear();
			allTunnels.clear();
			
		}
		
		alive = true;
		lastKeyPressed = KeyCode.A;
		won = false;
		
		File file = allLevels.get(currentLevel - 1).getFile();
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		try {
			
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
		    
		    while (line != null) {
		    	
		    	if(line.startsWith("L")) {
		    		
		    		level = Integer.parseInt(line.substring(2, line.length() - 1));
		    		l_level.setText(Integer.toString(level));
		    		
		    	}
		    	
		    	if(line.startsWith("P")) {
		    		
		    		password = line.substring(2, line.length() - 1);
		    		tf_password.setText(password);
		    		
		    	}
		    	
		    	if(line.startsWith("0")) {
		    		
		    		int X = Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(",")));
		    		int Y = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(")")));
		    		
		    		allWalls.add(new Wall(X, Y, gh.getWall()));
		    		
		    	}
		    	
		    	if(line.startsWith("1")) {
		    		
		    		int X = Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(",")));
		    		int Y = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(")")));
		    		
		    		allTrains.add(new Train(X, Y, gh.getTrainRight()));
		    		
		    		allTrains.get(0).getLastPosition().add(X);
		    		allTrains.get(0).getLastPosition().add(Y);
		    		
		    	}
		    	
		    	if(line.startsWith("2")) {
		    		
		    		int X = Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(",")));
		    		int Y = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(")")));
		    		
		    		allObjects.add(new Object(X, Y, gh.getObjectPizza(), gh.getWagonPizzaRight(), gh.getWagonPizzaUp(), gh.getWagonPizzaDown()));
		    		
		    	}
		    	
		    	if(line.startsWith("3")) {
		    		
		    		int X = Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(",")));
		    		int Y = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(")")));
		    		
		    		allGates.add(new Gate(X, Y, gh.getGate()));
		    		
		    	}
		    	
		    	if(line.startsWith("4")) {
		    		
		    		int X = Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(",")));
		    		int Y = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(")")));
		    		
		    		allTunnels.add(new Tunnel(X, Y, gh.getTunnel()));
		    		
		    	}
		    	
		    	if(line.startsWith("5")) {
		    		
		    		int X = Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(",")));
		    		int Y = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(")")));
		    		
		    		allObjects.add(new Object(X, Y, gh.getObjectUnicorn(), gh.getWagonUnicornRight(), gh.getWagonUnicornUp(), gh.getWagonUnicornDown()));
		    		
		    	}
		    	
		    	sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		        
		    }
		    
		} finally {
			
		    br.close();
		    
		    if(allTunnels.size() > 0) {
		    	
			    for(int x = 0; x < allTunnels.size(); x = x + 2) {
			    	
			    	allTunnels.get(x).setEnd(allTunnels.get(x + 1));
			    	allTunnels.get(x + 1).setEnd(allTunnels.get(x));
			    	
			    }
		    	
		    }
		    
		}
		
	}
	
	public void checkCollisionWithWalls() {
		System.out.println("CHECK COLLISION WITH WALLS");
		if(alive) {
			
			for(int x = 0; x < allWalls.size(); x++) {
				
				if(allTrains.get(0).getX() == allWalls.get(x).getX() && allTrains.get(0).getY() == allWalls.get(x).getY()) {
					
					dead();
					
				}
				
			}
			
		}
		
	}
	
	public void setCanvasKeyboardInput() {
		System.out.println("SET CANVAS KEYBOAR INPUT");
		c_canvas.setOnKeyPressed(e -> {
			
		    if (e.getCode() == KeyCode.LEFT) {
		    	
		    	if(lastKeyPressed != KeyCode.LEFT) {
		    		
		    		if(allTrains.get(0).getOrientation() == 3 && allWagons.size() > 0) {
		    			
		    			dead();
		    			
		    		}else {
		    			
		    			lastKeyPressed = KeyCode.LEFT;
				    	
		    			allTrains.get(0).setOrientation(1);
		    			
		    		}	    	
		    		
		    	}
		        
		    }
		   
		    if (e.getCode() == KeyCode.RIGHT) {
		    	
		    	if(lastKeyPressed != KeyCode.RIGHT) {
		    		
		    		if(allTrains.get(0).getOrientation() == 1 && allWagons.size() > 0) {
		    			
		    			dead();
		    			
		    		}else {
		    			
		    			lastKeyPressed = KeyCode.RIGHT;
				    	
		    			allTrains.get(0).setOrientation(3);
		    			
		    		}
		    		
		    	}
		    	
		    }
		    
		    if (e.getCode() == KeyCode.UP) {
		    	
		    	if(lastKeyPressed != KeyCode.UP) {
		    		
		    		if(allTrains.get(0).getOrientation() == 2 && allWagons.size() > 0) {
		    			
		    			dead();	
		    			
		    		}else {
		    			
		    			lastKeyPressed = KeyCode.UP;
				    	
		    			allTrains.get(0).setOrientation(0);
		    			
		    		}
		    		
		    	}
		       
		    }
		    
		    if (e.getCode() == KeyCode.DOWN) {
		    	
		    	if(lastKeyPressed != KeyCode.DOWN) {
		    		
		    		if(allTrains.get(0).getOrientation() == 0 && allWagons.size() > 0) {
		    			
		    			dead();
		    			
		    		}else {
		    			
		    			lastKeyPressed = KeyCode.DOWN;
				    	
		    			allTrains.get(0).setOrientation(2);
		    			
		    		}
		    		
		    	}
		    	
		    }
		    
		    if(e.getCode() == KeyCode.F4) {
		    	
		    	if(passwordActive == false) {
		    		
		    		if(allTrains.get(0).getOrientation() == -1) {
		    			
				    	l_f4.setVisible(false);
				    	tf_password.setVisible(true);
				    	
				    	tf_password.requestFocus();
				    	
				    	passwordActive = true;
		    			
		    		}
		    		
		    	}
		    	
		    }
		    
		});
		
	}
	
	public void setPasswordKeyboardInput() {
		System.out.println("SET PASSWORD KEYBOARD INPUT");
		tf_password.setOnKeyPressed(e -> {
			
			if(e.getCode() == KeyCode.ESCAPE) {
		    	
		    	if(passwordActive == true) {
		    		
		    		l_f4.setVisible(true);
			    	tf_password.setVisible(false);
			    	
			    	c_canvas.requestFocus();
			    	
			    	passwordActive = false;
		    		
		    	}
		    	
		    }
		    
		    if(e.getCode() == KeyCode.ENTER) {
		    	
		    	if(passwordActive == true) {
		    		
		    		l_f4.setVisible(true);
			    	tf_password.setVisible(false);
		    		
			    	c_canvas.requestFocus();
			    	
			    	passwordActive = false;
			    	
		    		String key = tf_password.getText();
		    		key = key.toUpperCase();
		    		
		    		for(int x = 0; x < allPasswords.size(); x++) {
		    			
		    			if(key.equals(allPasswords.get(x))) {
		    				
		    				currentLevel = x + 1;
		    				
		    				try {
		    					
								loadLevel();
								
							} catch (IOException e1) {
								
								e1.printStackTrace();
								
							}
		    				
		    			}else{
		    				
		    				tf_password.setText(password);
		    				
		    			}
		    			
		    		}
		    		
		    	}
		    	
		    }
			
		});
		
	}
	
	public void setAnimationTimer() {
		System.out.println("SET ANIMATION TIMER");
		AnimationTimer timer = new AnimationTimer() {

			public void handle(long arg0) {
				
				update();
				render();
				
			}
			
		};
		
		timer.start();
		
	}	
	
	private void checkCollisionWithObjects() {
		System.out.println("CHECK COLLISION WITH OBJECTS");
		
		if(alive) {
			
			for(int x = 0; x < allObjects.size(); x++) {
				
				if(allTrains.get(0).getX() == allObjects.get(x).getX() && allTrains.get(0).getY() == allObjects.get(x).getY()) {
					
					sh.stopSound();
					sh.playPick();
					
					score = score + 100;
					currentLevelScore = currentLevelScore + 100;
					
					l_score.setText(Integer.toString(score));
					
					allTrains.get(0).setLenght(allTrains.get(0).getLenght() + 1);
					
					if(allWagons.size() == 0) {
						
						if(allTrains.get(0).getOrientation() == 0) {
							
							// ORIENTATION = 1 - LEFT, 3 - RIGHT, 0 - UP, 2 - DOWN
							
							allWagons.add(new Wagon(allObjects.get(x).getX(), allObjects.get(x).getY() + (36 * allTrains.get(0).getLenght()), allObjects.get(x).getWagonU(), allObjects.get(x).getWagonR(), allObjects.get(x).getWagonU(), allObjects.get(x).getWagonD()));
							
							for(int y = 0; y < allWagons.size(); y++) {
								
								allWagons.get(allWagons.size() - 1).getFutureMoves().add(0);
								
							}
							
						}else if(allTrains.get(0).getOrientation() == 1) {
							
							allWagons.add(new Wagon(allObjects.get(x).getX() + (36 * allTrains.get(0).getLenght()), allObjects.get(x).getY(), allObjects.get(x).getWagonR(), allObjects.get(x).getWagonR(), allObjects.get(x).getWagonU(), allObjects.get(x).getWagonD()));
							
							for(int y = 0; y < allWagons.size(); y++) {
								
								allWagons.get(allWagons.size() - 1).getFutureMoves().add(1);
								
							}
							
						}else if(allTrains.get(0).getOrientation() == 2) {
							
							allWagons.add(new Wagon(allObjects.get(x).getX(), allObjects.get(x).getY() - (36 * allTrains.get(0).getLenght()), allObjects.get(x).getWagonD(), allObjects.get(x).getWagonR(), allObjects.get(x).getWagonU(), allObjects.get(x).getWagonD()));
							
							for(int y = 0; y < allWagons.size(); y++) {
								
								allWagons.get(allWagons.size() - 1).getFutureMoves().add(2);
								
							}
							
						}else if(allTrains.get(0).getOrientation() == 3) {
							
							allWagons.add(new Wagon(allObjects.get(x).getX() - (36 * allTrains.get(0).getLenght()), allObjects.get(x).getY(), allObjects.get(x).getWagonR(), allObjects.get(x).getWagonR(), allObjects.get(x).getWagonU(), allObjects.get(x).getWagonD()));
							
							for(int y = 0; y < allWagons.size(); y++) {
								
								allWagons.get(allWagons.size() - 1).getFutureMoves().add(3);
								
							}
							
						}
						
					}else {
						
						if(allWagons.get(allWagons.size() - 1).getLastMove() == 0) {
							
							int X = allWagons.get(allWagons.size() - 1).getX();
							int Y = allWagons.get(allWagons.size() - 1).getY();
							
							allWagons.add(new Wagon(X, Y + 36, allObjects.get(x).getWagonU(), allObjects.get(x).getWagonR(), allObjects.get(x).getWagonU(), allObjects.get(x).getWagonD()));
							
							for(int y = 0; y < allWagons.get(allWagons.size() - 2).getFutureMoves().size(); y++) {
								
								allWagons.get(allWagons.size() - 1).getFutureMoves().add(allWagons.get(allWagons.size() - 2).getFutureMoves().get(y));
								
							}
							
							allWagons.get(allWagons.size() - 1).getFutureMoves().add(0, 0);
						
						}else if(allWagons.get(allWagons.size() - 1).getLastMove() == 1) {
							
							int X = allWagons.get(allWagons.size() - 1).getX();
							int Y = allWagons.get(allWagons.size() - 1).getY();

							allWagons.add(new Wagon(X + 36, Y, allObjects.get(x).getWagonR(), allObjects.get(x).getWagonR(), allObjects.get(x).getWagonU(), allObjects.get(x).getWagonD()));
							
							for(int y = 0; y < allWagons.get(allWagons.size() - 2).getFutureMoves().size(); y++) {
								
								allWagons.get(allWagons.size() - 1).getFutureMoves().add(allWagons.get(allWagons.size() - 2).getFutureMoves().get(y));
								
							}
							
							allWagons.get(allWagons.size() - 1).getFutureMoves().add(0, 1);
							
						}else if(allWagons.get(allWagons.size() - 1).getLastMove() == 2) {
							
							int X = allWagons.get(allWagons.size() - 1).getX();
							int Y = allWagons.get(allWagons.size() - 1).getY();
							
							allWagons.add(new Wagon(X, Y - 36, allObjects.get(x).getWagonD(), allObjects.get(x).getWagonR(), allObjects.get(x).getWagonU(), allObjects.get(x).getWagonD()));
							
							for(int y = 0; y < allWagons.get(allWagons.size() - 2).getFutureMoves().size(); y++) {
								
								allWagons.get(allWagons.size() - 1).getFutureMoves().add(allWagons.get(allWagons.size() - 2).getFutureMoves().get(y));
								
							}
							
							allWagons.get(allWagons.size() - 1).getFutureMoves().add(0, 2);
							
						}else if(allWagons.get(allWagons.size() - 1).getLastMove() == 3) {
							
							int X = allWagons.get(allWagons.size() - 1).getX();
							int Y = allWagons.get(allWagons.size() - 1).getY();
							
							allWagons.add(new Wagon(X - 36, Y, allObjects.get(x).getWagonR(), allObjects.get(x).getWagonR(), allObjects.get(x).getWagonU(), allObjects.get(x).getWagonD()));
							
							for(int y = 0; y < allWagons.get(allWagons.size() - 2).getFutureMoves().size(); y++) {
								
								allWagons.get(allWagons.size() - 1).getFutureMoves().add(allWagons.get(allWagons.size() - 2).getFutureMoves().get(y));
								
							}
							
							allWagons.get(allWagons.size() - 1).getFutureMoves().add(0, 3);
							
						}
						
					}
					
					gc.clearRect(allObjects.get(x).getX(), allObjects.get(x).getY(), 36, 36);
					allObjects.remove(allObjects.get(x));
					
					return;
					
				}
				
			}
			
		}
		
	}
	
	private void autoMoveWagons() {
		System.out.println("AUTO MOVE WAGONS");
		
		for(int x = 0; x < allWagons.size(); x++) {
			
			allWagons.get(x).getLastPosition().clear();
			
			allWagons.get(x).getLastPosition().add(allWagons.get(x).getX());
			allWagons.get(x).getLastPosition().add(allWagons.get(x).getY());
			
			if(allWagons.get(x).getFutureMoves().size() != 0) {
				
				// ORIENTATION = 1 - LEFT, 3 - RIGHT, 0 - UP, 2 - DOWN
				
				allWagons.get(x).setLastMove(allWagons.get(x).getFutureMoves().get(0));
				
				if(allWagons.get(x).getFutureMoves().get(0) == 0) {
					
					allWagons.get(x).setY(allWagons.get(x).getY() - 36);
					allWagons.get(x).getFutureMoves().remove(0);
					
				}else if(allWagons.get(x).getFutureMoves().get(0) == 1) {
					
					allWagons.get(x).setX(allWagons.get(x).getX() - 36);
					allWagons.get(x).getFutureMoves().remove(0);
					
				}else if(allWagons.get(x).getFutureMoves().get(0) == 2) {
					
					allWagons.get(x).setY(allWagons.get(x).getY() + 36);
					allWagons.get(x).getFutureMoves().remove(0);
					
				}else if(allWagons.get(x).getFutureMoves().get(0) == 3) {
					
					allWagons.get(x).setX(allWagons.get(x).getX() + 36);
					allWagons.get(x).getFutureMoves().remove(0);
					
				}
				
			}
			
		}
		
	}
	
	private void checkCollisionWithGate() {
		System.out.println("CHECK COLLISION WITH GATE");
		if(allObjects.size() == 0) {
			
			if(!won) {
				
				if((allTrains.get(0).getX() == allGates.get(0).getX()) && (allTrains.get(0).getY() == allGates.get(0).getY())) {

					won = true;
					
					allTrains.get(0).setSpeed(0);
					alive = false;
					currentLevelScore = 0;
					
					for(int y = 0; y < allWagons.size(); y++) {
						
						allWagons.get(y).getFutureMoves().clear();
						
					}
					
					currentLevel++;
					
					try {
						
						loadLevel();
						
					} catch (IOException e) {
						
						e.printStackTrace();
						
					}
					
				}
				
			}
			
		}else {
			
			if(alive) {
				
				if((allTrains.get(0).getX() == allGates.get(0).getX()) && (allTrains.get(0).getY() == allGates.get(0).getY())) {

					dead();
					
				}
				
			}
			
		}
		
	}
	
	private void checkCollisionWithWagons() {
		System.out.println("CHECK COLLISION WITH WAGONS");
		if(alive) {
			
			for(int x = 0; x < allWagons.size(); x++) {
				
				if((allTrains.get(0).getX() == allWagons.get(x).getX()) && (allTrains.get(0).getY() == allWagons.get(x).getY())) {
					
					dead();
					
				}
				
			}
			
		}
		
	}
	
	public void dead() {
		
		System.out.println("DEAD");
		allTrains.get(0).setSpeed(0);
		alive = false;
		
		lastKeyPressed = KeyCode.A;
		
		sh.stopSound();
		sh.playDead();
		
		score = score - currentLevelScore - 1000;
		currentLevelScore = 0;
		
		l_score.setText(Integer.toString(score));
		
		for(int y = 0; y < allWagons.size(); y++) {
			
			allWagons.get(y).getFutureMoves().clear();
			
		}
		
		allWagons.clear();
		allObjects.clear();
		allWalls.clear();
		allWagons.clear();
		allTrains.clear();
		allGates.clear();
		
		gc.clearRect(0, 0, 720, 720);
		
		try {
			
			loadLevel();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void checkCollisionWithTunnels() {
		System.out.println("CHECK COLLISION WITH TUNNELS");
		
		if(allTunnels.size() > 0) {
			
			for(int x = 0; x < allTunnels.size(); x++) {
				
				if((allTrains.get(0).getX() == allTunnels.get(x).getX()) && (allTrains.get(0).getY() == allTunnels.get(x).getY())) {
					
					if(allTrains.get(0).getOrientation() == 0) {
						
						allTrains.get(0).setX(allTunnels.get(x).getEnd().getX());
						allTrains.get(0).setY(allTunnels.get(x).getEnd().getY() - 36);
						
					}else if(allTrains.get(0).getOrientation() == 1) {
						
						allTrains.get(0).setX(allTunnels.get(x).getEnd().getX() - 36);
						allTrains.get(0).setY(allTunnels.get(x).getEnd().getY());
						
					}else if(allTrains.get(0).getOrientation() == 2) {
						
						allTrains.get(0).setX(allTunnels.get(x).getEnd().getX());
						allTrains.get(0).setY(allTunnels.get(x).getEnd().getY() + 36);
						
					}else if(allTrains.get(0).getOrientation() == 3) {
						
						allTrains.get(0).setX(allTunnels.get(x).getEnd().getX() + 36);
						allTrains.get(0).setY(allTunnels.get(x).getEnd().getY());
						
					}
					
				}
				
				if(allWagons.size() > 0) {
					
					for(int y = 0; y < allWagons.size(); y++) {
						
						if((allWagons.get(y).getX() == allTunnels.get(x).getX()) && (allWagons.get(y).getY() == allTunnels.get(x).getY())) {
							
							if(allWagons.get(y).getLastMove() == 0) {
								
								allWagons.get(y).setX(allTunnels.get(x).getEnd().getX());
								allWagons.get(y).setY(allTunnels.get(x).getEnd().getY() - 36);
								
							}else if(allWagons.get(y).getLastMove() == 1) {
								
								allWagons.get(y).setX(allTunnels.get(x).getEnd().getX() - 36);
								allWagons.get(y).setY(allTunnels.get(x).getEnd().getY());
								
							}else if(allWagons.get(y).getLastMove() == 2) {
								
								allWagons.get(y).setX(allTunnels.get(x).getEnd().getX());
								allWagons.get(y).setY(allTunnels.get(x).getEnd().getY() + 36);
								
							}else if(allWagons.get(y).getLastMove() == 3) {
								
								allWagons.get(y).setX(allTunnels.get(x).getEnd().getX() + 36);
								allWagons.get(y).setY(allTunnels.get(x).getEnd().getY());
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
}
