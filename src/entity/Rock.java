package entity;

import game.Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.Main;
import math.Vector2;

public class Rock extends Entity {

	private boolean dead = false;
	private boolean hasPerson = false;

	public Rock(Vector2 pos) {
		this.entityName = "Rock";
		this.image = Main.resourceLoader.rock;
		this.pos = pos;
		this.rect = new Rectangle(pos.x, pos.y, image.getWidth(),
				image.getHeight());
	}

	public void update() {

	}

	public void paint(Graphics2D g) {
		if (!dead) {
			g.drawImage(image, pos.x + renderX, pos.y + renderY, null);
		} else {
			g.drawImage(Main.resourceLoader.rockMined, pos.x + renderX, pos.y
					+ renderY, null);
		}
	}

	public void mine() {
		dead = true;
		hasPerson = false;
	}

	public boolean getHasPerson() {
		return this.hasPerson;
	}

	public boolean getDead() {
		return this.dead;
	}

	public void setHasPerson(boolean bool) {
		this.hasPerson = bool;
	}

}