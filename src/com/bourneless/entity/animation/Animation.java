package com.bourneless.entity.animation;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.swing.Timer;

import com.bourneless.math.Vector2;

public class Animation implements Serializable {

	private transient BufferedImage[] images;
	private boolean play;

	private int activeImage = 0;
	private int speed;

	private Timer animationTimer = new Timer(100, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (activeImage < images.length - 1) {
				activeImage++;
			} else {
				activeImage = 0;
			}
		}
	});

	public Animation(BufferedImage[] images, int speed) {
		this.images = images;
		animationTimer.setDelay(speed);
		this.speed = speed;
	}

	public void paint(Graphics2D g, Vector2 pos) {
		g.drawImage(images[activeImage], pos.x, pos.y, null);
	}
	
	public void start() {
		this.activeImage = 0;
		this.animationTimer.start();
	}
	
	public void stop() {
		this.animationTimer.stop();
	}
}
