package com.bourneless.screen;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.bourneless.game.Instance;
import com.bourneless.main.Main;
import com.bourneless.math.Vector2;

public class Screen {

	private static Vector2 center = new Vector2(Main.GAME_WIDTH / 2 , Main.GAME_HEIGHT / 2);
	protected Instance instance;
	
	public Screen() {
		
	}
	
	public void update() {
		
	}
	
	public void paint(Graphics2D g) {
		
	}
	
	public static Vector2 getCenter() {
		return center;
	}
	
	public void keyPressed(KeyEvent e) {
		
	}
	
	public void keyReleased(KeyEvent e) {
		
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public void mouseMoved(MouseEvent e) {
		
	}
	
	public Instance getInstance() {
		return this.instance;
	}
	
}
