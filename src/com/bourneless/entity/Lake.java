package com.bourneless.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;

import com.bourneless.entity.animation.Animation;
import com.bourneless.game.Entity;
import com.bourneless.game.Map;
import com.bourneless.main.Main;
import com.bourneless.math.Vector2;
import com.bourneless.mechanics.Cycle;

public class Lake extends Entity implements Serializable {

	private Animation animation;
	
	public Lake(Vector2 pos) {
		animation = new Animation(Main.resourceLoader.lakeImages, 300);
		System.out.println("Spawning Lake at: " + pos.x + " | " + pos.y + ".");
		this.image = Main.resourceLoader.lake;
		this.pos = pos;
		this.rect = new Rectangle(pos.x, pos.y, Main.resourceLoader.lakeImages[0].getWidth(), Main.resourceLoader.lakeImages[0].getHeight());
		
		animation.start();
	}
	
	public void update(int renderX, int renderY, Map map, Cycle cycle) {
		this.renderX = renderX;
		this.renderY = renderY;
		this.rect.x = pos.x + renderX;
		this.rect.y = pos.y + renderY;
	}
	
	public void paint(Graphics2D g) {
		animation.paint(g, new Vector2(pos.x + renderX, pos.y + renderY));
	}
}
