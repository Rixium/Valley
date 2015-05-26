package screen;

import game.Instance;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.Timer;

import main.Main;
import math.Vector2;
import object.Button;

public class MenuScreen extends Screen {

	private boolean startLoad;
	private boolean showButtons;
	
	Timer menuLoadTimer;
	
	private Vector2 menuTitlePos;
	private Vector2 endMenuTitlePos;
	
	private Button[] buttons = new Button[4];
	
	
	public MenuScreen() {
		
		startLoad = false;
		
		buttons[0] = new Button(Main.resourceLoader.newGame, new Vector2(Screen.getCenter().x - Main.resourceLoader.newGame.getWidth() / 2,
				Screen.getCenter().y - Main.resourceLoader.newGame.getHeight()));
		buttons[1] = new Button(Main.resourceLoader.loadGame, new Vector2(Screen.getCenter().x - Main.resourceLoader.loadGame.getWidth() / 2,
				Screen.getCenter().y - Main.resourceLoader.loadGame.getHeight() + 100));
		buttons[2] = new Button(Main.resourceLoader.options, new Vector2(Screen.getCenter().x - Main.resourceLoader.options.getWidth() / 2,
				Screen.getCenter().y - Main.resourceLoader.options.getHeight() + 200));
		buttons[3] = new Button(Main.resourceLoader.quit, new Vector2(Screen.getCenter().x - Main.resourceLoader.quit.getWidth() / 2,
				Screen.getCenter().y - Main.resourceLoader.quit.getHeight() + 300));
		
		menuLoadTimer = new Timer(2000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startLoad = true;
				menuLoadTimer.stop();
			}
		});
		
		menuTitlePos = new Vector2(Main.GAME_WIDTH / 2 - Main.resourceLoader.menuTitle.getWidth() / 2,
				Main.GAME_HEIGHT / 2 - Main.resourceLoader.menuTitle.getHeight() / 2);
		
		endMenuTitlePos = new Vector2(Main.GAME_WIDTH / 2 - Main.resourceLoader.menuTitle.getWidth() / 2, 100);
		Main.resourceLoader.playClip(Main.resourceLoader.menuMusic, -15f, true);
		
		menuLoadTimer.start();
	}
	
	public void paint(Graphics2D g) {
		g.drawImage(Main.resourceLoader.menuBack, 0, 0, Main.GAME_WIDTH, Main.GAME_HEIGHT, null);
		
		g.drawImage(Main.resourceLoader.menuTitle, 
				menuTitlePos.x, menuTitlePos.y, null);
		
		if(showButtons) {
			for(int i = 0; i < buttons.length; i++) {
				buttons[i].paint(g);
			}
		}
	}
	
	public void update() {
		if(startLoad) {
			if(menuTitlePos.y > endMenuTitlePos.y) {
				menuTitlePos.y -= 1;
			} else {
				showButtons = true;
			}
		}
	}
	
	public void keyPressed(KeyEvent e) {
		
	}
	
	public void keyReleased(KeyEvent e) {
		
	}
	
	public void mouseClicked(MouseEvent e) {
		Rectangle mouseRect = new Rectangle(e.getX(), e.getY() - 20, 10, 10);
		
		if(menuTitlePos.y == endMenuTitlePos.y) {
			if(mouseRect.intersects(buttons[3].getRect())) {
				Main.resourceLoader.playClip(Main.resourceLoader.menuClick, 1f, false);
				System.exit(0);
			} else if (mouseRect.intersects(buttons[0].getRect())) {
				Main.resourceLoader.playClip(Main.resourceLoader.menuClick, 1f, false);
				Main.game.setScreen(new MapScreen());
			} else if(mouseRect.intersects(buttons[1].getRect())) {
				loadGame();
			}
		} else {
			menuTitlePos.y = endMenuTitlePos.y;
			showButtons = true;
		}
	}
	
	public void loadGame() {
		FileInputStream f_in = null;
		try {
			f_in = new 
					FileInputStream("mymap.map");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ObjectInputStream obj_in = null;
		try {
			obj_in = new ObjectInputStream (f_in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			GameScreen gameScreen = new GameScreen((Instance)obj_in.readObject());
			Main.game.setScreen(gameScreen);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
