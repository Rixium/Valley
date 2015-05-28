package com.bourneless.item;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectStreamException;
import java.io.Serializable;

import javax.swing.Timer;

import com.bourneless.entity.Person;
import com.bourneless.entity.animation.Animation;
import com.bourneless.game.Item;
import com.bourneless.game.Map;
import com.bourneless.game.Role;
import com.bourneless.main.Main;
import com.bourneless.math.Vector2;

public class Fire extends Item implements Serializable {

	private transient Animation fireAnimation;
	private boolean isAnimating = false;

	public Fire() {
		fireAnimation = new Animation(Main.resourceLoader.flameImages, 100);
		this.entityName = "Fire";
		this.available = true;
		this.itemName = "Fire";
		this.price = 0;
		this.buttonImage = Main.resourceLoader.fireButtonImage;
		this.image = Main.resourceLoader.fireImage;

	}

	public Fire(Vector2 pos, Map map) {
		fireAnimation = new Animation(Main.resourceLoader.flameImages, 100);
		this.entityName = "Fire";
		this.itemName = "Fire";
		this.price = 0;
		this.pos = pos;
		this.layer = pos.y / map.getSize() * map.getTileSize();
		this.buttonImage = Main.resourceLoader.fireButtonImage;
		this.image = Main.resourceLoader.fireImage;
		this.rect = new Rectangle(pos.x, pos.y, image.getWidth(),
				image.getHeight());
	}

	public void update() {

	}

	public void paint(Graphics2D g) {
		super.paint(g);

		if (!Main.game.getScreen().getInstance().getCycle().getDay()) {
			if (!isAnimating) {
				fireAnimation.start();
				isAnimating = true;
			}
			fireAnimation.paint(g, new Vector2(pos.x + renderX, pos.y + renderY));
			
		} else {
			isAnimating = false;
			fireAnimation.stop();
		}
	}

	private Object readResolve() throws ObjectStreamException {
		Fire fire = this;
		fire.fireAnimation = new Animation(Main.resourceLoader.flameImages, 100);
		return fire;
	}
}
