package screen;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import main.Main;
import math.Vector2;
import object.Button;

public class MapScreen extends Screen {

	private Button[] buttons;
	
	private String mapSize = "small";
	private String treeGrowth = "low";

	public MapScreen() {
		buttons = new Button[6];
		buttons[0] = new Button(Main.resourceLoader.small, new Vector2(Main.GAME_WIDTH / 2 - Main.resourceLoader.small.getWidth() / 2 - 200, 
				Main.GAME_HEIGHT / 2 - 110));
		buttons[1] = new Button(Main.resourceLoader.medium, new Vector2(Main.GAME_WIDTH / 2 - Main.resourceLoader.medium.getWidth() / 2 - 200, 
				Main.GAME_HEIGHT / 2 - 10));
		buttons[2] = new Button(Main.resourceLoader.large, new Vector2(Main.GAME_WIDTH / 2 - Main.resourceLoader.large.getWidth() / 2 - 200, 
				Main.GAME_HEIGHT / 2 + 90));
		buttons[3] = new Button(Main.resourceLoader.low, new Vector2(Main.GAME_WIDTH / 2 - Main.resourceLoader.small.getWidth() / 2 + 200, 
				Main.GAME_HEIGHT / 2 - 110));
		buttons[4] = new Button(Main.resourceLoader.high, new Vector2(Main.GAME_WIDTH / 2 - Main.resourceLoader.medium.getWidth() / 2 + 200, 
				Main.GAME_HEIGHT / 2 - 10));
		buttons[5] = new Button(Main.resourceLoader.createmap, new Vector2(Main.GAME_WIDTH / 2 - Main.resourceLoader.large.getWidth() / 2, 
				Main.GAME_HEIGHT / 2 + 200));
	}
	
	public void update() {
		
	}
	
	public void paint(Graphics2D g) {
		if(treeGrowth == "low") {
			g.drawImage(Main.resourceLoader.menuBack, 0, 0, Main.GAME_WIDTH, Main.GAME_HEIGHT, null);
		} else if (treeGrowth == "high") {
			g.drawImage(Main.resourceLoader.menuBackHighGrowth, 0, 0, Main.GAME_WIDTH, Main.GAME_HEIGHT, null);
		}
		
		g.drawImage(Main.resourceLoader.mapcreation, Main.GAME_WIDTH / 2 - Main.resourceLoader.mapcreation.getWidth() / 2, 30, null);
		
		g.drawImage(Main.resourceLoader.mapsize, Main.GAME_WIDTH / 2 - Main.resourceLoader.mapsize.getWidth() / 2 - 200, 150, null);
		g.drawImage(Main.resourceLoader.treegrowth, Main.GAME_WIDTH / 2 - Main.resourceLoader.treegrowth.getWidth() / 2 + 200, 150, null);
		
		
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].paint(g);
		}

	}
	
	public void keyPressed(KeyEvent e) {
		
	}
	
	public void keyReleased(KeyEvent e) {
		
	}
	
	public void mouseClicked(MouseEvent e) {
		Rectangle mouseRect = new Rectangle(e.getX(), e.getY() - 20, 10, 10);
		
		if(mouseRect.intersects(buttons[0].getRect())) {
			Main.resourceLoader.playClip(Main.resourceLoader.menuClick, 1f, false);
			mapSize = "small";
			System.out.println("Map Size: " + mapSize);
		} else if (mouseRect.intersects(buttons[1].getRect())) {
			Main.resourceLoader.playClip(Main.resourceLoader.menuClick, 1f, false);
			mapSize = "medium";
			System.out.println("Map Size: " + mapSize);
		} else if (mouseRect.intersects(buttons[2].getRect())) {
			Main.resourceLoader.playClip(Main.resourceLoader.menuClick, 1f, false);
			mapSize = "large";
			System.out.println("Map Size: " + mapSize);
		} else if (mouseRect.intersects(buttons[3].getRect())) {
			Main.resourceLoader.playClip(Main.resourceLoader.menuClick, 1f, false);
			treeGrowth = "low";
			System.out.println("Tree Growth: " + treeGrowth);
		} else if (mouseRect.intersects(buttons[4].getRect())) {
			Main.resourceLoader.playClip(Main.resourceLoader.menuClick, 1f, false);
			treeGrowth = "high";
			System.out.println("Tree Growth: " + treeGrowth);
		} else if (mouseRect.intersects(buttons[5].getRect())) {
			Main.resourceLoader.playClip(Main.resourceLoader.menuClick, 1f, false);
			String godName = JOptionPane.showInputDialog("Enter your Name: ");
			Main.game.setScreen(new GameScreen(mapSize, treeGrowth, godName));
		}
	}
	
}
