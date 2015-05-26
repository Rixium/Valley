package screen;

import game.God;
import game.Item;
import game.Map;
import game.Role;
import game.TileRoles;
import game.UI;
import item.Fire;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import main.Main;
import math.Vector2;
import mechanics.Cycle;
import object.Button;

public class GameScreen extends Screen {

	private Map map;
	private UI ui;

	private int renderX;
	private int renderY;

	private Button toggleSoundButton;
	private Button toggleSoundButtonOff;
	private Button quitButton;

	private String populationString;

	private Rectangle mousePos;

	private boolean moveMapLeft, moveMapRight, moveMapUp, moveMapDown;
	private int scrollSpeed;

	private int faith;

	private Item selectedItem;

	private boolean paused;

	private God god;
	private Cycle cycle;

	private int selectedTileRole = TileRoles.EMPTY;

	public GameScreen(String size, String treeGrowth, String godname) {
		if (godname == null) {
			godname = "God";
		}

		god = new God(godname);
		cycle = new Cycle();
		faith = 0;
		scrollSpeed = 5;
		quitButton = new Button(Main.resourceLoader.quit,
				new Vector2(Main.GAME_WIDTH / 2
						- Main.resourceLoader.quit.getWidth() / 2,
						Main.GAME_HEIGHT / 2
								- Main.resourceLoader.quit.getHeight() / 2));
		toggleSoundButton = new Button(Main.resourceLoader.toggleSound,
				new Vector2(20, 20));
		toggleSoundButtonOff = new Button(Main.resourceLoader.toggleSoundOff,
				new Vector2(20, 20));
		ui = new UI();
		map = new Map(size, treeGrowth, god);
	}

	public void update() {
		if (map.getIsReady()) {
			if (!paused) {
				if (moveMapLeft) {
					if (renderX < 0)
						renderX += scrollSpeed;
				} else if (moveMapRight) {
					if (renderX > -map.getSize() * map.getTileSize()
							+ Main.GAME_WIDTH) {
						renderX -= scrollSpeed;
					}
				}

				if (moveMapUp) {
					if (renderY < 0) {
						renderY += scrollSpeed;
					}
				} else if (moveMapDown) {
					if (renderY > -map.getSize() * map.getTileSize()
							+ Main.GAME_HEIGHT) {
						renderY -= scrollSpeed;
					}
				}

				if (renderY > 0) {
					renderY = 0;
				} else if (renderY < -map.getSize() * map.getTileSize()
						+ Main.GAME_HEIGHT) {
					renderY = -map.getSize() * map.getTileSize()
							+ Main.GAME_HEIGHT;
				}

				if (renderX > 0) {
					renderX = 0;
				} else if (renderX < -map.getSize() * map.getTileSize()
						+ Main.GAME_WIDTH) {
					renderX = -map.getSize() * map.getTileSize()
							+ Main.GAME_WIDTH;
				}

				map.update(renderX, renderY, god, cycle);

				if (map.getSelectedPerson() != null) {
					ui.showPersonStats(true, map.getSelectedPerson());
				} else {
					ui.showPersonStats(false, map.getSelectedPerson());
				}

				if (selectedItem != null) {
					selectedItem
							.setPosition(new Vector2(mousePos.x, mousePos.y));
				}
			}
		}
	}

	public void paint(Graphics2D g) {
		if (map.getIsReady()) {
			map.paint(g);
			cycle.paint(g);
			ui.paint(g);

			if (selectedItem != null) {
				selectedItem.paint(g);
			}

			g.setColor(Color.WHITE);
			g.drawString("Faith: " + faith, 10, Main.GAME_HEIGHT - 10);

			if (paused) {

				if (Main.resourceLoader.menuMusicPlaying) {
					toggleSoundButton.paint(g);
				} else if (!Main.resourceLoader.menuMusicPlaying) {
					toggleSoundButtonOff.paint(g);
				}

				g.setColor(Color.BLACK);
				g.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, .7f));
				g.fillRect(0, 0, Main.GAME_WIDTH, Main.GAME_HEIGHT);
				g.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 1));
				quitButton.paint(g);
			}
		} else {
			g.drawImage(Main.resourceLoader.loadImage, 0, 0, Main.GAME_WIDTH,
					Main.GAME_HEIGHT, null);
		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 27 && map.getSelectedPerson() == null) {
			paused = !paused;
		} else if (e.getKeyCode() == 27) {
			map.getSelectedPerson().setSelected(false);
			map.deselectPerson();
		}

		map.keyPressed(e);

		if (e.getKeyCode() == 65) {
			moveMapLeft = true;
			moveMapRight = false;
		} else if (e.getKeyCode() == 68) {
			moveMapLeft = false;
			moveMapRight = true;
		}

		if (e.getKeyCode() == 87) {
			moveMapUp = true;
			moveMapDown = false;

		} else if (e.getKeyCode() == 83) {
			moveMapUp = false;
			moveMapDown = true;
		}

	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 65) {
			moveMapLeft = false;
		}
		if (e.getKeyCode() == 68) {
			moveMapRight = false;
		}
		if (e.getKeyCode() == 87) {
			moveMapUp = false;
		}
		if (e.getKeyCode() == 83) {
			moveMapDown = false;
		}
	}

	public void mouseClicked(MouseEvent e) {
		Rectangle mousePos = new Rectangle(e.getX(), e.getY() - 20, 1, 1);
		this.mousePos = mousePos;

		boolean hitButton = false;

		for (int i = 0; i < ui.getTileRoles().length; i++) {
			if (!mousePos.intersects(ui.getTileRoles()[i].getRect())) {
				if (i == ui.getTileRoles().length - 1) {
					if (!hitButton) {
						map.mouseClicked(e);
					}
				}
			} else {
				hitButton = true;
			}
		}

		if (mousePos.intersects(ui.getRect())) {
			ui.openUI(!ui.getOpen());
			Main.resourceLoader
					.playClip(Main.resourceLoader.openUI, -5f, false);
		}

		if (mousePos.intersects(ui.getStats()[0].getRect())) {
			if (map.getSelectedPerson().getCurrentJob() != "Cooking") {
				map.getSelectedPerson().setCurrentJob("Cooking");
				map.getSelectedPerson().setRole(Role.cook);
			} else {
				map.getSelectedPerson().setCurrentJob("Nothing");
				map.getSelectedPerson().setRole(Role.normal);
			}
		} else if (mousePos.intersects(ui.getStats()[1].getRect())) {
			if (map.getSelectedPerson().getCurrentJob() != "Farming") {
				map.getSelectedPerson().setCurrentJob("Farming");
				map.getSelectedPerson().setRole(Role.farmer);
			} else {
				map.getSelectedPerson().setCurrentJob("Nothing");
				map.getSelectedPerson().setRole(Role.normal);
			}
		} else if (mousePos.intersects(ui.getStats()[2].getRect())) {
			if (map.getSelectedPerson().getCurrentJob() != "Fishing") {
				map.getSelectedPerson().setCurrentJob("Fishing");
				map.getSelectedPerson().setRole(Role.fisherman);
			} else {
				map.getSelectedPerson().setCurrentJob("Nothing");
				map.getSelectedPerson().setRole(Role.normal);
			}
		} else if (mousePos.intersects(ui.getStats()[3].getRect())) {
			if (map.getSelectedPerson().getCurrentJob() != "Mining") {
				map.getSelectedPerson().setCurrentJob("Mining");
				map.getSelectedPerson().setRole(Role.miner);
			} else {
				map.getSelectedPerson().setCurrentJob("Nothing");
				map.getSelectedPerson().setRole(Role.normal);
			}
		} else if (mousePos.intersects(ui.getStats()[4].getRect())) {
			if (map.getSelectedPerson().getCurrentJob() != "Woodcutting") {
				map.getSelectedPerson().setCurrentJob("Woodcutting");
				map.getSelectedPerson().setRole(Role.woodcutter);
			} else {
				map.getSelectedPerson().setCurrentJob("Nothing");
				map.getSelectedPerson().setRole(Role.normal);
			}
		}

		if (selectedItem == null) {
			for (int i = 0; i < ui.getItems().length; i++) {
				if (ui.getItems()[i].getAvailable()) {
					if (mousePos.intersects(ui.getItems()[i].getRect())) {
						selectedItem = new Fire(new Vector2(mousePos.x,
								mousePos.y), map);
					}
				}
			}
		} else {
			map.addEntity(selectedItem, this, ui);
			selectedItem = null;

		}

		if (paused) {
			if (mousePos.intersects(quitButton.getRect())) {
				Main.resourceLoader.playClip(Main.resourceLoader.menuClick, 1f,
						false);
				Main.game.setScreen(new MenuScreen());
			}
			if (mousePos.intersects(toggleSoundButton.getRect())) {
				System.out.println("Toggling Music..");
				if (Main.resourceLoader.menuMusicPlaying) {
					Main.resourceLoader.stopClip(Main.resourceLoader.menuMusic);
				} else {
					Main.resourceLoader.playClip(Main.resourceLoader.menuMusic,
							1, true);
				}

			}
		}

		if (mousePos.intersects(ui.getTileRoles()[0].getRect())) {
			ui.getTileRoles()[0].setSelected(true);
			ui.getTileRoles()[1].setSelected(false);
			ui.getTileRoles()[2].setSelected(false);
			ui.getTileRoles()[3].setSelected(false);
			map.setTileRole(TileRoles.EMPTY);
		} else if (mousePos.intersects(ui.getTileRoles()[1].getRect())) {
			ui.getTileRoles()[1].setSelected(true);
			ui.getTileRoles()[0].setSelected(false);
			ui.getTileRoles()[2].setSelected(false);
			ui.getTileRoles()[3].setSelected(false);
			map.setTileRole(TileRoles.PATH);
		} else if (mousePos.intersects(ui.getTileRoles()[2].getRect())) {
			ui.getTileRoles()[2].setSelected(true);
			ui.getTileRoles()[1].setSelected(false);
			ui.getTileRoles()[3].setSelected(false);
			ui.getTileRoles()[0].setSelected(false);
			map.setTileRole(TileRoles.FARM);
		} else if (mousePos.intersects(ui.getTileRoles()[3].getRect())) {
			ui.getTileRoles()[3].setSelected(true);
			ui.getTileRoles()[1].setSelected(false);
			ui.getTileRoles()[2].setSelected(false);
			ui.getTileRoles()[0].setSelected(false);
			map.setTileRole(TileRoles.BUILD);
		}
	}

	public void mouseMoved(MouseEvent e) {
		Rectangle mousePos = new Rectangle(e.getX(), e.getY() - 20, 1, 1);
		this.mousePos = mousePos;
		if (!mousePos.intersects(ui.getRect())
				&& !mousePos.intersects(toggleSoundButton.getRect())) {
			map.setMousePos(mousePos);
		}
		if (selectedItem == null) {
			map.showTileRole(false);
			for (int i = 0; i < ui.getItems().length; i++) {
				if (mousePos.intersects(ui.getItems()[i].getRect())) {
					if (ui.getItems()[i] != null) {
						ui.getItems()[i].showCost(true);
					}
				} else {
					ui.getItems()[i].showCost(false);
				}
			}
		} else if (selectedItem != null) {
			map.showTileRole(true);
		}
	}

	public void removeCoins(int count) {
		this.faith -= count;
	}

	public boolean hasCoins(int count) {
		if (this.faith - count >= 0) {
			return true;
		} else {
			return false;
		}
	}

	public Map getMap() {
		return this.map;
	}

}
