package handler;

import java.util.ArrayList;

import code.Gate;
import code.Object;
import code.Train;
import code.Tunnel;
import code.Wagon;
import code.Wall;

public class Collision_Handler {
	
	private ArrayList<Object> allObjects = new ArrayList<Object>();
	private ArrayList<Wagon> allWagons = new ArrayList<Wagon>();
	private ArrayList<Wall> allWalls = new ArrayList<Wall>();
	private ArrayList<Train> allTrains = new ArrayList<Train>();
	private ArrayList<Gate> allGates = new ArrayList<Gate>();
	private ArrayList<Tunnel> allTunnels = new ArrayList<Tunnel>();
	
	public Collision_Handler() {
		
		
		
	}
	
	public void checkCollisionWithWagons() {
		
		
		
	}
	
	public void checkCollisionWithGate() {
		
		
		
	}
	
	public void checkCollisionWithWalls() {
		
		
		
	}
	
	public void checkCollisionWithObjects() {
		
		
		
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

	public ArrayList<Object> getAllObjects() {
		
		return allObjects;
		
	}

	public ArrayList<Wagon> getAllWagons() {
		
		return allWagons;
		
	}

	public ArrayList<Wall> getAllWalls() {
		
		return allWalls;
		
	}

	public ArrayList<Train> getAllTrains() {
		
		return allTrains;
		
	}

	public ArrayList<Gate> getAllGates() {
		
		return allGates;
		
	}

	public ArrayList<Tunnel> getAllTunnels() {
		
		return allTunnels;
		
	}
	
}
