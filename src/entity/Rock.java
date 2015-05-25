package entity;

import game.Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.Main;
import math.Vector2;

public class Rock extends Entity {

	public Rock(Vector2 pos) {
		this.image = Main.resourceLoader.rock;
		this.pos = pos;
		this.rect = new Rectangle(pos.x, pos.y, image.getWidth(),
				image.getHeight());
	}

	public void update() {

	}

	public void paint(Graphics2D g) {
		g.drawImage(image, pos.x + renderX, pos.y + renderY, null);
	}

}