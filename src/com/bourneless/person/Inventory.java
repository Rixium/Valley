package com.bourneless.person;

import java.io.Serializable;

public class Inventory implements Serializable {

	private int rockCount = 0;
	private int woodCount = 0;
	private int rawFishCount = 0;
	private int rawMeatCount = 0;
	private int cookedMeat = 0;
	private int cookedFish = 0;

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

	public int getRawMeatCount() {
		return this.rawMeatCount;
	}

	public int getCookedMeatCount() {
		return this.cookedMeat;
	}

	public int getCookedFishCount() {
		return this.cookedFish;
	}

	public void addRock(int i) {
		rockCount += i;
	}

	public void addWood(int i) {
		woodCount += i;
	}

	public void removeRock() {
		this.rockCount = 0;
	}

	public void removeWood() {
		this.woodCount = 0;
	}

	public void addRawMeat(int i) {
		this.rawMeatCount += i;
	}

	public void addRawFish(int i) {
		this.rawFishCount += 1;
	}

	public void removeRawFish(int i) {
		this.rawFishCount -= i;
	}

	public void removeRawMeat(int i) {
		this.rawMeatCount -= i;
	}

	public void addCookedMeat(int i) {
		this.cookedMeat += i;
	}

	public void addCookedFish(int i) {
		this.cookedFish += i;
	}

	public void removeCookedFish(int i) {
		this.cookedFish -= i;
	}

	public void removeCookedMeat(int i) {
		this.cookedMeat -= i;
	}

}
