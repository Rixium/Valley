package mechanics;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import main.Main;
import math.Vector2;

public class Cycle implements Serializable {

	private Vector2 pos;

	private float time;

	private boolean isDay;
	private boolean goDay = false;

	Timer dayTimer = new Timer(1000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (time >= .7f) {
				isDay = false;
			} else if (time <= .5f) {
				isDay = true;
			}

			if (time >= .9f) {
				goDay = true;
			} else if (time == 0) {
				goDay = false;
			}

			if (!goDay) {
				if (time < .3) {
					time += .005f;
				} else {
					time += .01f;
				}
			} else if (goDay) {
				if (time > .6) {
					time -= .005f;
				} else {
					if (time - .01f >= 0) {
						time -= .01f;
					} else {
						time = 0;
					}
				}
			}
		}
	});

	public Cycle() {
		this.pos = new Vector2(0, 0);
		this.time = 0f;
		dayTimer.start();
	}

	public void update() {

	}

	public void paint(Graphics2D g) {
		g.setComposite(AlphaComposite
				.getInstance(AlphaComposite.SRC_OVER, time));
		g.drawImage(Main.resourceLoader.cycle, pos.x, pos.y, Main.GAME_WIDTH * 2,
				Main.GAME_HEIGHT * 2, null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
	}

	public boolean getDay() {
		return isDay;
	}
	
}
