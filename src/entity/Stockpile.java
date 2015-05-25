package entity;

import game.Entity;
import game.Map;

import java.awt.Graphics2D;

public class Stockpile extends Entity {

	private int woodcount;
	private int rockcount;

	public Stockpile() {
		woodcount = 0;
		rockcount = 0;
	}

	public void update(int renderX, int renderY, Map map) {

	}

	public void paint(Graphics2D g) {

	}

	public void addWood(int i) {
		this.woodcount += i;
	}

	public void addRock(int i) {
		this.rockcount += i;
	}

}
