package com.bourneless.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;

import com.bourneless.game.Entity;
import com.bourneless.game.Map;
import com.bourneless.main.Main;
import com.bourneless.math.Vector2;
import com.bourneless.mechanics.Cycle;

public class Stockpile extends Entity implements Serializable {

	private int woodCount;
	private int rockCount;
	private int rawFishCount = 5;
	private int rawMeatCount;
	private int cookedMeat = 0;
	private int cookedFish = 0;

	public Stockpile(Vector2 pos) {
		this.entityName = "Stockpile";
		this.pos = new Vector2(pos.x, pos.y);

		this.image = Main.resourceLoader.stockpile;
		this.rect = new Rectangle(this.pos.x, this.pos.y, image.getWidth(),
				image.getHeight());
		woodCount = 0;
		rockCount = 0;
	}

	public void update(int renderX, int renderY, Map map, Cycle cycle) {
		this.renderX = renderX;
		this.renderY = renderY;
		this.rect.x = pos.x + renderX;
		this.rect.y = pos.y + renderY;
		this.layer = (pos.y / map.getSize()) - 1;
	}

	public void paint(Graphics2D g) {
		g.drawImage(image, pos.x + renderX, pos.y + renderY, null);
	}

	public void addWood(int i) {
		this.woodCount += i;
	}

	public void addRock(int i) {
		this.rockCount += i;
	}

	public void addRawFish(int i) {
		this.rawFishCount += i;
	}

	public void addRawMeat(int i) {
		this.rawMeatCount += i;
	}

	public void removeWood(int i) {
		this.woodCount -= i;
	}

	public void removeRock(int i) {
		this.rockCount -= i;
	}

	public void removeRawFish(int i) {
		this.rawFishCount -= i;
	}

	public void removeRawMeat(int i) {
		this.rawMeatCount -= i;
	}

	public int getWood() {
		return this.woodCount;
	}

	public int getRock() {
		return this.rockCount;
	}

	public int getRawFish() {
		return this.rawFishCount;
	}

	public int getRawMeat() {
		return this.rawMeatCount;
	}
	
	public int getCookedMeatCount() {
		return this.cookedMeat;
	}

	public int getCookedFishCount() {
		return this.cookedFish;
	}
	
	public void addCookedMeat(int i) {
		this.cookedMeat += i;
	}

	public void addCookedFish(int i) {
		this.cookedFish += i;
	}

}
