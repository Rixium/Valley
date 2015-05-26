package Person;

import java.io.Serializable;

public class Inventory implements Serializable {

	private int rockcount = 0;
	private int woodcount = 0;

	public Inventory() {

	}

	public int getRockCount() {
		return this.rockcount;
	}

	public int getWoodCount() {
		return this.woodcount;
	}

	public void addRock(int i) {
		rockcount += i;
	}

	public void addWood(int i) {
		woodcount += i;
	}
	
	public void removeRock() {
		this.rockcount = 0;
	}
	
	public void removeWood() {
		this.woodcount = 0;
	}

}
