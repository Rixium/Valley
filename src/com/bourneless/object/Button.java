package com.bourneless.object;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

import com.bourneless.game.Item;
import com.bourneless.main.Main;
import com.bourneless.math.Vector2;

public class Button implements Serializable {

	transient BufferedImage image;
	private boolean selected;
	Vector2 pos;
	Rectangle rect;
	Item item;
	boolean showCost;
	boolean itemButton;
	
	public Button(BufferedImage image, Vector2 pos) {
		this.image = image;
		this.pos = pos;
		this.rect = new Rectangle(pos.x, pos.y, image.getWidth(), image.getHeight());
	}
	
	public Button(Item item, Vector2 pos) {
		itemButton = true;
		this.item = item;
		this.image = item.getButtonImage();
		this.pos = pos;
		this.rect = new Rectangle(pos.x, pos.y, image.getWidth(), image.getHeight());
	}
	
	public void update() {
		
	}
	
	public void paint(Graphics2D g) {
		g.drawImage(image, pos.x, pos.y, null);
		
		if(showCost) {
			if(item != null) {
				g.setColor(Color.WHITE);
				g.drawString("" + item.getCost(), pos.x, pos.y + image.getHeight() + 15);
			}
		}
		
		if(item != null) {
			if(!item.isAvailable()) {
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
				g.drawImage(Main.resourceLoader.lockedImage, pos.x, pos.y, null);
			}
		}
		
		if(selected) {
			g.setColor(Color.red);
			g.drawRect(pos.x, pos.y, rect.width - 1, rect.height - 1);
		}
	}
	
	public Rectangle getRect() {
		return this.rect;
	}
	
	public void showCost(boolean bool) {
		this.showCost = bool;
	}
	
	public boolean getAvailable() {
		return this.item.isAvailable();
	}
	
	public boolean isSelected() {
		return this.selected;
	}
	
	public void setSelected(boolean bool) {
		this.selected = bool;
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
        ImageIO.write(image, "png", out);
    }
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		image = ImageIO.read(in);
    }
}
