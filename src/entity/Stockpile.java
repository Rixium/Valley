package entity;

import game.Entity;
import game.Map;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.Main;
import math.Vector2;

public class Stockpile extends Entity {

	private int woodcount;
	private int rockcount;

	public Stockpile(Vector2 pos) {
		this.pos = pos;

		this.image = Main.resourceLoader.stockpile;
		this.rect = new Rectangle(pos.x, pos.y, image.getWidth(),
				image.getHeight());
		woodcount = 0;
		rockcount = 0;
	}

	public void update(int renderX, int renderY, Map map) {
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
