package com.bourneless.item;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;

import com.bourneless.game.Item;
import com.bourneless.game.Map;
import com.bourneless.main.Main;
import com.bourneless.math.Vector2;

public class Fire extends Item implements Serializable {

	public Fire() {
		this.entityName = "Fire";
		this.available = true;
		this.itemName = "Fire";
		this.price = 0;
		this.buttonImage = Main.resourceLoader.fireButtonImage;
		this.image = Main.resourceLoader.fireImage;
		
	}
	
	public Fire(Vector2 pos, Map map) {
		this.entityName = "Fire";
		this.itemName = "Fire";
		this.price = 0;
		this.pos = pos;
		this.layer = pos.y / map.getSize() * map.getTileSize();
		this.buttonImage = Main.resourceLoader.fireButtonImage;
		this.image = Main.resourceLoader.fireImage;
		this.rect = new Rectangle(pos.x, pos.y, image.getWidth(), image.getHeight());
	}
	
	public void update() {
		
	}
	
	public void paint(Graphics2D g) {
		super.paint(g);
	}
}
