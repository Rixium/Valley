package com.bourneless.mechanics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

import com.bourneless.main.Main;
import com.bourneless.math.Vector2;

public class LightSource implements Serializable {

	private Vector2 pos;
	private boolean active;

	private int renderX;
	private int renderY;

	public LightSource(Vector2 pos) {
		this.pos = new Vector2(pos.x - 17, pos.y - 5
				+ Main.resourceLoader.lightsource.getHeight() / 2);
	}

	public void update(int renderX, int renderY) {
		this.renderX = renderX;
		this.renderY = renderY;
	}

	public void paint(Graphics2D g) {
		if(active) {
			g.drawImage(Main.resourceLoader.lightsource, pos.x + renderX, pos.y + renderY, null);
		}
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Vector2 getPos() {
		return pos;
	}

	public void setPos(Vector2 pos) {
		this.pos = pos;
	}
	

}
