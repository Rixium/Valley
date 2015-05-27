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

	private int woodcount;
	private int rockcount;

	public Stockpile(Vector2 pos) {
		this.entityName = "Stockpile";
		this.pos = new Vector2(pos.x, pos.y);

		this.image = Main.resourceLoader.stockpile;
		this.rect = new Rectangle(this.pos.x, this.pos.y, image.getWidth(),
				image.getHeight());
		woodcount = 0;
		rockcount = 0;
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
		this.woodcount += i;
	}

	public void addRock(int i) {
		this.rockcount += i;
	}

	public int getWood() {
		return this.woodcount;
	}

	public int getRock() {
		return this.rockcount;
	}

}
