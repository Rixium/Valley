package com.bourneless.game;

import java.awt.Color;
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

public class Tile implements Serializable {

	private Vector2 pos;
	transient private BufferedImage image;
	private int tileSize;
	
	private int renderX;
	private int renderY;
	
	private Rectangle rect;
	
	private boolean showDebug;
	private boolean highlighted;
	private boolean showRole;
	
	private boolean occupied;
	
	private int tileRole;
	
	private Color highlightColor = Color.GREEN;
	private Color emptyColor = new Color(10, 10, 10);
	private Color buildColor = new Color(30, 43, 175);
	private Color farmColor = new Color(81, 46, 24);
	private Color pathColor = new Color(115, 115, 115);
	
	public Tile(int tileType, int x, int y) {
		
		setTileRole(TileRoles.EMPTY);
		
		occupied = false;
		tileSize = 64;
		pos = new Vector2(x, y);
		rect = new Rectangle(x, y, tileSize, tileSize);
		
		if(tileType == TileTypes.GRASS) {
			image = Main.resourceLoader.grass;
		}
	}
	
	public void update(int renderX, int renderY) {
		this.renderX = renderX;
		this.renderY = renderY;
		rect.setLocation(pos.x + renderX,  pos.y + renderY);
	}
	
	public void paint(Graphics2D g) {
		g.drawImage(image, pos.x + renderX, pos.y + renderY, tileSize, tileSize, null);
		
		if(showDebug) {
			if(tileRole == TileRoles.EMPTY) {
				g.setColor(emptyColor);
				g.drawString("EMPTY", pos.x + renderX + tileSize / 4, pos.y + renderY + tileSize / 2);
			} else if (tileRole == TileRoles.BUILD) {
				g.setColor(buildColor);
				g.drawString("BUILD", pos.x + renderX + tileSize / 4, pos.y + renderY + tileSize / 2);
			} else if (tileRole == TileRoles.FARM) {
				g.setColor(farmColor);
				g.drawString("FARM", pos.x + renderX + tileSize / 4, pos.y + renderY + tileSize / 2);
			} else if (tileRole == TileRoles.PATH) {
				g.setColor(pathColor);
				g.drawString("PATH", pos.x + renderX + tileSize / 4, pos.y + renderY + tileSize / 2);
			}
			g.drawRect(rect.x + 1, rect.y + 1, rect.width - 2, rect.height - 2);
		}
		
		if(showRole) {
			if(tileRole == TileRoles.EMPTY) {
					
			} else if (tileRole == TileRoles.BUILD) {
				g.setColor(Color.BLUE);
				g.drawString("BUILD", pos.x + renderX + tileSize / 4, pos.y + renderY + tileSize / 2);
			} else if (tileRole == TileRoles.FARM) {
				g.setColor(Color.YELLOW);
				g.drawString("FARM", pos.x + renderX + tileSize / 4, pos.y + renderY + tileSize / 2);
			} else if (tileRole == TileRoles.PATH) {
				g.setColor(Color.GRAY);
				g.drawString("PATH", pos.x + renderX + tileSize / 4, pos.y + renderY + tileSize / 2);
			}
			if(tileRole != TileRoles.EMPTY) {
				g.drawRect(rect.x + 1, rect.y + 1, rect.width - 2, rect.height - 2);
			}
		}
		
		if(highlighted && showRole) {
			g.setColor(highlightColor);
			g.drawRect(pos.x + renderX, pos.y + renderY, tileSize - 1, tileSize - 1);
		}
	}
	
	public void showDebug(boolean bool) {
		showDebug = bool;
	}
	
	public boolean getDebug() {
		return this.showDebug;
	}
	
	public Vector2 getPos() {
		return this.pos;
	}
	
	public Rectangle getRect() {
		return this.rect;
	}
	
	public void setHighlighted(boolean bool) {
		this.highlighted = bool;
	}
	
	public boolean isHighlighted() {
		return this.highlighted;
	}
	
	public boolean isOccupied() {
		return this.occupied;
	}
	
	public void setOccupied(boolean bool) {
		this.occupied = bool;
	}
	
	public void setType(int tileType) {

	}

	public int getTileRole() {
		return tileRole;
	}

	public void setTileRole(int tileRole) {
		this.tileRole = tileRole;
	}
	
	public void showRole(boolean bool) {
		this.showRole = bool;
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
