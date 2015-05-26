package entity;

import game.Entity;
import game.Map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.Main;
import math.Vector2;
import mechanics.Cycle;

public class Lake extends Entity {

	public Lake(Vector2 pos) {
		System.out.println("Spawning Lake at: " + pos.x + " | " + pos.y + ".");
		this.image = Main.resourceLoader.lake;
		this.pos = pos;
		this.rect = new Rectangle(pos.x, pos.y, image.getWidth(), image.getHeight());
	}
	
	public void update(int renderX, int renderY, Map map, Cycle cycle) {
		this.renderX = renderX;
		this.renderY = renderY;
		this.rect.x = pos.x + renderX;
		this.rect.y = pos.y + renderY;
	}
	
	public void paint(Graphics2D g) {
		g.drawImage(image, pos.x + renderX, pos.y + renderY, null);
	}
}
