package com.bourneless.screen;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.bourneless.game.Instance;
import com.bourneless.main.Main;

public class GameScreen extends Screen implements Serializable {
	
	private Instance instance;
	private boolean savingGame;
	
	public GameScreen(String size, String treeGrowth, String godname) {
		instance = new Instance(size, treeGrowth, godname, this);
	}
	
	public GameScreen(Instance instance) {
		this.instance = instance;
	}
	
	public void update() {
		if(!savingGame) {
			instance.update();
		}
	}
	
	public void paint(Graphics2D g) {
		instance.paint(g);
		if(savingGame) {
			g.drawImage(Main.resourceLoader.saveImage, 0, 0, Main.GAME_WIDTH, Main.GAME_HEIGHT, null);
		}
	}

	public Instance getInstance() {
		return this.instance;
	}
	
	public void keyPressed(KeyEvent e) {
		instance.keyPressed(e);
		
		if(e.getKeyCode() == 123) {
			savingGame = true;
			this.paint((Graphics2D)Main.game.getGraphics());
			FileOutputStream f_out = null;
			ObjectOutputStream obj_out = null;
			try {
				f_out = new FileOutputStream("mymap.map");
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}
			
			try {
				obj_out = new ObjectOutputStream (f_out);
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			
			try {
				obj_out.writeObject(instance);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			savingGame = false;
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		instance.mouseClicked(e);
	}
	
	public void keyReleased(KeyEvent e) {
		instance.keyReleased(e);
	}
	
	public void mouseMoved(MouseEvent e) {
		instance.mouseMoved(e);
	}
	
	
	
}
