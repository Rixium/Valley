package com.bourneless.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

import com.bourneless.main.Main;
import com.bourneless.math.Vector2;


public class Item extends Entity implements Serializable {

	protected int price;
	protected String itemName;
	
	public static boolean available = false;
	
	transient protected BufferedImage buttonImage;
	
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

	private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        ImageIO.write(buttonImage, "png", out);
    }
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		buttonImage = ImageIO.read(in);
    }
}
