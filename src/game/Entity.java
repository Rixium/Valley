package game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

import math.Vector2;
import mechanics.Cycle;

public class Entity implements Serializable {

	transient protected BufferedImage image;
	protected Tile tile;
	protected Vector2 pos = new Vector2(0, 0);
	protected int renderX;
	protected int renderY;
	protected Rectangle rect = new Rectangle(0, 0, 0, 0);
	protected Map map;

	protected String entityName;
	protected Cycle cycle;

	protected boolean selected;

	protected int layer;

	public Entity() {

	}

	public void update(int renderX, int renderY, Map map, Cycle cycle) {
		this.cycle = cycle;
		this.map = map;
		this.renderX = renderX;
		this.renderY = renderY;
		this.layer = pos.y / map.getSize();
	}

	public void paint(Graphics2D g) {

	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public Tile getTile() {
		return this.tile;
	}

	public String getEntityName() {
		return this.entityName;
	}

	public Rectangle getRect() {
		return this.rect;
	}

	public boolean getSelected() {
		return this.selected;
	}

	public void setSelected(boolean bool) {
		this.selected = bool;
	}

	public Vector2 getPos() {
		return this.pos;
	}

	public int getY() {
		return this.rect.y;
	}

	public int getLayer() {
		return this.layer;
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