package com.bourneless.person;

import java.io.Serializable;

public class Inventory implements Serializable {

	private int rockCount = 0;
	private int woodCount = 0;
	private int rawFishCount = 0;

	public Inventory() {

	}

	public int getRockCount() {
		return this.rockCount;
	}

	public int getWoodCount() {
		return this.woodCount;
	}
	
	public int getRawFishCount() {
		return this.rawFishCount;
	}

	public void addRock(int i) {
		rockCount += i;
	}

	public void addWood(int i) {
		woodCount += i;
	}
	
	public void addRawFish(int i) {
		rawFishCount += i;
	}
	
	public void removeRock() {
		this.rockCount = 0;
	}
	
	public void removeWood() {
		this.woodCount = 0;
	}
	
	public void removeRawFish() {
		this.rawFishCount = 0;
	}

}
