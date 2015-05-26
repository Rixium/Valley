package game;

import java.io.Serializable;

public class God implements Serializable {

	private int popularity;
	private String name = "God";
	
	public God(String name) {
		if(name.isEmpty()) {
			this.name = "God";
		} else {
			this.name = name;
		}
		
		this.setPopularity(10);
		System.out.println("Spawning God known as.. " + this.name);
	}

	public int getPopularity() {
		return popularity;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	
	public String getName() {
		return this.name;
	}
	
}
