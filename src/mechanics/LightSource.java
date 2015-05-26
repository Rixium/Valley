package mechanics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.Main;
import math.Vector2;

public class LightSource {

	private BufferedImage image;
	private Vector2 pos;
	private boolean active;

	private int renderX;
	private int renderY;

	public LightSource(Vector2 pos) {
		this.image = Main.resourceLoader.lightsource;
		this.pos = new Vector2(pos.x - 17, pos.y - 5
				+ image.getHeight() / 2);
	}

	public void update(int renderX, int renderY) {
		this.renderX = renderX;
		this.renderY = renderY;
	}

	public void paint(Graphics2D g) {
		if(active) {
			g.drawImage(image, pos.x + renderX, pos.y + renderY, null);
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
