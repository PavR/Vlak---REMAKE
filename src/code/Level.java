package code;

import java.io.File;

public class Level {

	private File file;
	private int level;
	private String password;
	
	public Level(File file, int level, String password) {
		
		this.file = file;
		this.level = level;
		this.password = password;
		
	}

	public File getFile() {
		
		return file;
		
	}

	public void setFile(File file) {
		
		this.file = file;
		
	}

	public int getLevel() {
		
		return level;
		
	}

	public void setLevel(int level) {
		
		this.level = level;
		
	}

	public String getPassword() {
		
		return password;
		
	}

	public void setPassword(String password) {
		
		this.password = password;
		
	}
	
}
