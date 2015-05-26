package entity;

import game.Entity;
import game.Map;
import game.Tile;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.Main;
import math.Vector2;
import mechanics.Cycle;

public class Tree extends Entity implements Serializable {

	private boolean canCut;
	private boolean isSapling;
	private int thirst = 0;
	private int age;
	private Random random = new Random();
	private boolean hasPerson = false;
	private boolean cut = false;

	public Tree(Tile tile, Map map, boolean isSapling) {
		this.entityName = "Tree";
		this.image = Main.resourceLoader.tree;
		this.isSapling = isSapling;
		if (isSapling) {
			canCut = false;
			thirst = 0;
			age = 0;
		} else {
			age = 10;
		}
		
		this.tile = tile;
		this.pos = tile.getPos();
		this.rect = new Rectangle(pos.x, pos.y, 64,
				64);
		this.layer = (pos.y + 10) / map.getSize();
	}

	public void update(int renderX, int renderY, Map map, Cycle cycle) {
		this.map = map;
		this.renderX = renderX;
		this.renderY = renderY;

		if (isSapling) {
			if (thirst >= 100) {
				if (age > 0) {
					age -= 1;
					thirst = 0;
				}
			}
			if (age >= 10) {
				thirst = 0;
				isSapling = false;
				hasPerson = false;
				canCut = true;
				this.pos = tile.getPos();
				this.layer = (pos.y + 10) / map.getSize();
			} else {
				int addThirst = random.nextInt(1000);
				if (addThirst == 1) {
					this.thirst++;
				}
			}
		}
	}

	public void paint(Graphics2D g) {
		if (!isSapling) {
			if (!cut) {
				g.drawImage(Main.resourceLoader.treeTop, pos.x + renderX, pos.y + renderY
						- Main.resourceLoader.treeBottom.getHeight(), null);
				g.drawImage(Main.resourceLoader.treeBottom, pos.x + renderX, pos.y + renderY, null);
			} else {
				g.drawImage(Main.resourceLoader.treeBottomCut, pos.x + renderX,
						pos.y + renderY, null);
			}

		} else if (isSapling) {
			g.drawImage(Main.resourceLoader.sapling, pos.x + renderX, pos.y + renderY, null);
		}
	}

	public void setCut(boolean bool) {
		this.canCut = bool;
	}

	public void setSapling(boolean bool) {
		this.isSapling = bool;
	}

	public boolean getIsSapling() {
		return this.isSapling;
	}

	public boolean getCut() {
		return this.canCut;
	}

	public int getThirst() {
		return this.thirst;
	}

	public Vector2 getPos() {
		return this.pos;
	}

	public void water() {
		this.hasPerson = false;
		this.thirst = 0;
		this.age += 1;

		if (age >= 10) {
			setSapling(false);
			setHasPerson(false);
		}
	}

	public boolean hasPerson() {
		return this.hasPerson;
	}

	public void setHasPerson(boolean bool) {
		this.hasPerson = bool;
	}

	public int getAge() {
		return this.age;
	}
	
}
