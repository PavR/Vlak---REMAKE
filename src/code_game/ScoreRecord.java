package code_game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreRecord {

	private String name;
	private int score;
	
	private static ArrayList<ScoreRecord> leaderboard = new ArrayList<ScoreRecord>();
	
	public ScoreRecord(String name, int score) throws IOException {
		
		this.name = name;
		this.score = score;
		
		if(leaderboard.size() > 100) {
			
			if(leaderboard.get(leaderboard.size() - 1).getScore() < score) {
				
				leaderboard.add(this);
				
			}
			
		}else {
			
			leaderboard.add(this);
			
		}
		
		Collections.sort(leaderboard, new Comparator<ScoreRecord>() {
			
		    public int compare(ScoreRecord p1, ScoreRecord p2) {
		        	
		         return p2.getScore() - p1.getScore();
		            
		    }

		});
		
		String AbsolutePath = new File(".").getAbsolutePath();
    	
    	AbsolutePath = (AbsolutePath.substring(0, AbsolutePath.length() - 1));
    	
    	File options = new File(AbsolutePath + "LEADERBOARD.txt");
    	
    	FileWriter fw = new FileWriter(options.getAbsoluteFile());
    	BufferedWriter bw = new BufferedWriter(fw);
    	
    	for(int x = 0; x < leaderboard.size(); x++) {
    		
    		bw.write("<N>" + leaderboard.get(x).getName());
    		bw.write("\n");
    		bw.write("<S>" + leaderboard.get(x).getScore());
    		bw.write("\n");
    		
    	}
    	
    	bw.flush();
    	bw.close();
		
	}

	public String getName() {
		
		return name;
		
	}

	public void setName(String name) {
		
		this.name = name;
		
	}

	public int getScore() {
		
		return score;
		
	}

	public void setScore(int score) {
		
		this.score = score;
		
	}

	public static ArrayList<ScoreRecord> getLeaderboard() {
		
		return leaderboard;
		
	}
	
}
