package entity;

import game.Entity;
import game.Map;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import main.Main;
import math.Vector2;

public class Cow extends Entity {

	private Random random = new Random();

	private Vector2 destinationPos = new Vector2(0, 0);
	private boolean hasDestination = false;
	private int speed = 2;
	private boolean atDestination = false;

	public Cow(Vector2 pos, Map map) {
		this.image = Main.resourceLoader.cow;
		this.pos = pos;
		this.rect = new Rectangle(pos.x, pos.y, image.getWidth(),
				image.getHeight());
		this.layer = pos.y / map.getSize();
	}

	public void update(int renderX, int renderY, Map map) {

		this.renderX = renderX;
		this.renderY = renderY;
		this.map = map;
		
		this.rect.x = pos.x + renderX;
		this.rect.y = pos.y + renderY;
		
		if (!hasDestination) {
			int getDestination = random.nextInt(1000);

			if (getDestination == 1) {
				int destinationX = random.nextInt(200);
				int destinationY = random.nextInt(200);
				destinationX -= 100;
				destinationY -= 100;

				if (pos.x + destinationX > 0) {
					if (pos.x + destinationX < map.getSize()
							* map.getTileSize()) {
						if (pos.y + destinationY > 0) {
							if (pos.y + destinationY < map.getSize()
									* map.getTileSize()) {
								destinationPos = new Vector2(pos.x + destinationX, pos.y + destinationY);

								hasDestination = true;
							}
						}
					}
				}
			}
		}

		if(hasDestination) {
			float directionX = destinationPos.x - pos.x;
			float directionY = destinationPos.y - pos.y;
	
			double length = Math.sqrt(directionX * directionX + directionY
					* directionY);
	
			directionX /= length;
			directionY /= length;
	
			if (length <= 10 && length >= -10) {
				atDestination = true;
				hasDestination = false;
			}
	
			if (length > 10 || length < -10) {
				pos.x += directionX * speed;
				pos.y += directionY * speed;
			}
		}
	}

	public void paint(Graphics2D g) {
		g.drawImage(image, pos.x + renderX, pos.y + renderY, null);
	}

}
