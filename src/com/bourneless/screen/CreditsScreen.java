package com.bourneless.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.bourneless.main.Main;
import com.bourneless.math.Vector2;

public class CreditsScreen extends Screen {

	public BufferedImage creditRoll;
	public Vector2 creditPos;

	public CreditsScreen() {
		creditRoll = Main.resourceLoader.creditRoll;
		creditPos = new Vector2(Main.GAME_WIDTH / 2
				- creditRoll.getWidth() / 2, Main.GAME_HEIGHT);
	}

	public void update() {
		creditPos.y--;
		if(creditPos.y < 0 - creditRoll.getHeight() - 100) {
			Main.game.setScreen(new MenuScreen());
		}
	}

	public void paint(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.GAME_WIDTH, Main.GAME_HEIGHT);
		g.drawImage(creditRoll, creditPos.x, creditPos.y, null);
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 27) {
			Main.game.setScreen(new MenuScreen());
		}
	}
}
