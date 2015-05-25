package game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import math.Vector2;


public class Item extends Entity {

	protected int price;
	protected String itemName;
	
	public static boolean available = false;
	
	protected BufferedImage buttonImage;
	
	protected Rectangle rect;
	
	
	public Item() {
		
	}
	
	public void update() {
		
	}
	
	public void paint(Graphics2D g) {
		g.drawImage(image, pos.x + renderX, pos.y + renderY, null);
	}
	
	public void setPosition(Vector2 pos) {
		this.pos = pos;
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
	
	public int getCost() {
		return this.price;
	}
	
	public String getName() {
		return this.itemName;
	}
	
	public BufferedImage getButtonImage() {
		return this.buttonImage;
	}
	
	public boolean isAvailable() {
		return this.available;
	}
	
	public Vector2 getPos() {
		return this.pos;
	}
	
	public void setAvailable(boolean available) {
		this.available = available;
	}

}
