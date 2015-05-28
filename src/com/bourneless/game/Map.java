package com.bourneless.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import com.bourneless.entity.Cow;
import com.bourneless.entity.Lake;
import com.bourneless.entity.Person;
import com.bourneless.entity.Rock;
import com.bourneless.entity.Stockpile;
import com.bourneless.entity.Tree;
import com.bourneless.item.Fire;
import com.bourneless.main.Main;
import com.bourneless.math.Vector2;
import com.bourneless.mechanics.Cycle;
import com.bourneless.mechanics.LightSource;
import com.bourneless.screen.GameScreen;

public class Map implements Serializable {

	private Tile[][] tiles;
	private ArrayList<Entity> entities = new ArrayList<Entity>();

	private Random random = new Random();

	private Rectangle mousePos;
	private int size;
	private int tileSize;

	private int renderX;
	private int renderY;
	private int treeGrowth;

	private Tile activeTile;
	private Person selectedPerson;

	private boolean hasFire;

	private God god;
	private Cycle cycle;

	private Item fire;

	private int population;
	private Tree thirstyTree = null;

	private Entity lastEntity;
	private Lake lake;
	private int selectedTileRole = TileRoles.EMPTY;

	private Rock rock;
	private boolean gotRock;

	private boolean canClick;

	private boolean showRole;

	private boolean isReady = false;

	private Stockpile stockpile;
	private boolean hasStockpile = false;

	private boolean giveStockpile = true;

	private boolean setSourcesInactive = false;
	private ArrayList<LightSource> lightSources = new ArrayList<LightSource>();

	private GameScreen gameScreen;

	public Map(String size, String treeGrowth, God god, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		population = 0;
		this.god = god;
		tileSize = 64;
		if (size.equals("small")) {
			this.size = 32;
			tiles = new Tile[32][32];
		} else if (size.equals("medium")) {
			this.size = 64;
			tiles = new Tile[64][64];
		} else if (size.equals("large")) {
			this.size = 128;
			tiles = new Tile[128][128];
		}

		if (treeGrowth.equals("low")) {
			this.treeGrowth = 40;
		} else if (treeGrowth.equals("high")) {
			this.treeGrowth = 20;
		}

		hasFire = false;
		createMap();
	}

	public void createMap() {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				tiles[i][j] = new Tile(TileTypes.GRASS, i * tileSize, j
						* tileSize);
			}
		}
		addLake();
		addTrees();
	}

	public void addLake() {
		int randomLakeX = 0;
		int randomLakeY = 0;

		while (randomLakeX < 0 + Main.resourceLoader.lake.getWidth()
				&& randomLakeY < 0 + Main.resourceLoader.lake.getHeight()) {
			randomLakeX = random.nextInt(size * tileSize
					- Main.resourceLoader.lake.getWidth());
			randomLakeY = random.nextInt(size * tileSize
					- Main.resourceLoader.lake.getHeight());
		}

		lake = new Lake(new Vector2(randomLakeX, randomLakeY));
		entities.add(lake);
	}

	public void addTrees() {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				int ranNum = random.nextInt(treeGrowth);
				if (ranNum == 1) {
					Rectangle treeRect = new Rectangle(i * tileSize, j
							* tileSize, tileSize, tileSize);
					if (!treeRect.intersects(lake.getRect())) {
						int randomint = random.nextInt(2);
						boolean isSapling = false;
						if (randomint == 1) {
							isSapling = true;
						} else {
							isSapling = false;
						}

						Tree tree = new Tree(tiles[i][j], this, isSapling);
						tiles[i][j].setOccupied(true);
						entities.add(tree);
					}
				}
			}
		}
		addRocks();
	}

	public void addRocks() {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				int ranNum = random.nextInt(100);
				if (ranNum == 1) {
					if (!tiles[i][j].isOccupied()) {
						Rock rock = new Rock(tiles[i][j].getPos());
						entities.add(rock);
					}
				}
			}
		}
		isReady = true;
	}

	public void update(int renderX, int renderY, God god, Cycle cycle) {
		if (isReady) {
			this.cycle = cycle;
			this.god = god;
			this.renderX = renderX;
			this.renderY = renderY;

			for (int i = 0; i < tiles.length; i++) {
				for (int j = 0; j < tiles[i].length; j++) {
					if (tiles[i][j].getPos().x < Main.GAME_WIDTH + -renderX
							&& tiles[i][j].getPos().y < Main.GAME_HEIGHT
									+ -renderY
							&& tiles[i][j].getPos().x > -renderX - tileSize
							&& tiles[i][j].getPos().y > -renderY - tileSize) {
						tiles[i][j].update(renderX, renderY);
						if (selectedTileRole != TileRoles.EMPTY || showRole) {
							tiles[i][j].showRole(true);
						} else {
							tiles[i][j].showRole(false);
						}
						if (tiles[i][j].getTileRole() == TileRoles.FARM) {
							int spawnCow = random.nextInt(5000);
							if (spawnCow == 1) {
								int tileXOffset = random.nextInt(100);
								int tileYOffset = random.nextInt(100);
								tileXOffset -= 50;
								tileYOffset -= 50;
								Cow cow = new Cow(new Vector2(
										tiles[i][j].getPos().x + tileXOffset,
										tiles[i][j].getPos().y + tileYOffset),
										this);
								entities.add(cow);
							}
						}
						if (mousePos != null) {
							if (mousePos.intersects(tiles[i][j].getRect())) {
								tiles[i][j].setHighlighted(true);
								activeTile = tiles[i][j];
							} else {
								tiles[i][j].setHighlighted(false);
							}
						}
					}
				}
			}

			for (int i = 0; i < lightSources.size(); i++) {
				lightSources.get(i).update(renderX, renderY);
				if (cycle.getDay()) {
					lightSources.get(i).setActive(false);
				} else {
					lightSources.get(i).setActive(true);
				}
			}

			for (int i = 0; i < entities.size(); i++) {
				Entity entity = entities.get(i);
				if (entity != null) {
					if (entity.getPos().x < Main.GAME_WIDTH + -renderX
							+ entity.image.getWidth()
							&& entity.getPos().y < Main.GAME_HEIGHT + -renderY
									+ entity.image.getHeight()
							&& entity.getPos().x > -renderX - tileSize
									- entity.image.getWidth()
							&& entity.getPos().y > -renderY - tileSize
									- entity.image.getHeight()) {
						entity.update(renderX, renderY, this, cycle);
					} else if (entity.entityName.equals("Person")) {
						entity.update(renderX, renderY, this, cycle);
					}

					if (entity.getEntityName().equals("Tree") && thirstyTree == null) {
						Tree tree = (Tree) entities.get(i);
						if (tree.getThirst() >= 90 && tree.getIsSapling()
								&& !tree.hasPerson()) {
							thirstyTree = tree;
						}
					} else if (entity.getEntityName().equals("Person")) {
						Person person = (Person) entities.get(i);
						if (lake.getRect().contains(person.getRect())) {
							person.setSwimming(true);
						} else {
							person.setSwimming(false);
						}

						if (person.getRole() == Role.farmer) {
							if (thirstyTree != null) {
								if (person.getStamina() > 5
										&& !person.isGettingStamina()) {
									if (!person.hasTree()) {
										if (cycle.getDay()) {
											if (thirstyTree.getIsSapling()) {
												if (!thirstyTree.hasPerson()) {
													thirstyTree
															.setHasPerson(true);
													person.giveTree(thirstyTree);
													thirstyTree = null;
												}
											} else {
												thirstyTree = null;
											}
										}
									}
								}
							}
						} else if (person.getRole() == Role.miner) {
							if (person.getStamina() > 5
									&& !person.isGettingStamina()) {
								if (!person.getHasRock()
										&& !person.isCarrying()) {
									if (cycle.getDay()) {
										if (gotRock) {
											person.giveRock(rock);
											rock.setHasPerson(true);
											rock = null;
											gotRock = false;
											System.out.println("Giving Rock");
										}
									}
								}
							}
						}
					} else if (entity.getEntityName().equals("Rock")) {
						Rock tempRock = (Rock) entity;
						if (!tempRock.getDead()
								&& tempRock.getHasPerson() == false) {
							rock = (Rock) entity;
							gotRock = true;
						}
					}
				}

				if (hasFire == true) {
					if (population < 5) {
						int spawnPerson;

						if (population >= 1) {
							spawnPerson = random.nextInt(population * 100000
									/ god.getPopularity());
						} else {
							spawnPerson = random.nextInt(100000 / god
									.getPopularity());
						}

						if (spawnPerson == 1) {
							System.out.println("Spawning Person..");

							population++;
							Person person = new Person(fire, this, god,
									giveStockpile, cycle);
							giveStockpile = false;
							entities.add(person);
						}
					}
				}
			}
		}

	}

	public void paint(Graphics2D g) {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				if (tiles[i][j].getPos().x < Main.GAME_WIDTH + -renderX
						&& tiles[i][j].getPos().y < Main.GAME_HEIGHT + -renderY
						&& tiles[i][j].getPos().x > -renderX - tileSize
						&& tiles[i][j].getPos().y > -renderY - tileSize) {
					tiles[i][j].paint(g);
				}
			}
		}

		for (int i = 0; i < lightSources.size(); i++) {
			lightSources.get(i).paint(g);
		}

		for (int currentLayer = 0; currentLayer < size * 2; currentLayer++) {
			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i) != null) {
					Entity entity = entities.get(i);
					if (entity.getPos().x < Main.GAME_WIDTH + -renderX
							+ entity.image.getWidth()
							&& entity.getPos().y < Main.GAME_HEIGHT + -renderY
									+ entity.image.getHeight()
							&& entity.getPos().x > -renderX - tileSize
									- entity.image.getWidth()
							&& entity.getPos().y > -renderY - tileSize
									- entity.image.getHeight()) {
						if (entities.get(i).getLayer() == currentLayer) {
							entities.get(i).paint(g);
						}
					}
				}
			}
		}

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 112) {
			for (int i = 0; i < tiles.length; i++) {
				for (int j = 0; j < tiles[i].length; j++) {
					tiles[i][j].showDebug(!tiles[i][j].getDebug());
				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		Rectangle mousePos = new Rectangle(e.getX(), e.getY() - 25, 1, 1);

		if (entities.size() > 0) {
			for (int i = 0; i < entities.size(); i++) {
				if (mousePos.intersects(entities.get(i).getRect())) {
					if (entities.get(i) != null) {
						if (entities.get(i).entityName.matches("Person")) {
							if (selectedPerson != null) {
								Person currentPerson = (Person) entities.get(i);
								if (selectedPerson != null
										&& selectedPerson.getName() != currentPerson
												.getName()) {
									selectedPerson.setSelected(false);
									selectedPerson = currentPerson;
									selectedPerson.setSelected(true);
									int randomVoice = random
											.nextInt(Main.resourceLoader.voices.length);
									Main.resourceLoader
											.playClip(
													Main.resourceLoader.voices[randomVoice],
													-5f, false);
								} else if (selectedPerson.getName() == currentPerson
										.getName()) {
									selectedPerson.setSelected(false);
									selectedPerson = null;
								}
							} else {
								System.out.println("selecting person");
								selectedPerson = (Person) entities.get(i);
								selectedPerson.setSelected(true);
								int randomVoice = random
										.nextInt(Main.resourceLoader.voices.length);
								Main.resourceLoader
										.playClip(
												Main.resourceLoader.voices[randomVoice],
												-5f, false);
							}
						}
					}
				}
			}
		}

		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				if (mousePos.intersects(tiles[i][j].getRect())) {
					if (tiles[i][j].getTileRole() != selectedTileRole) {
						tiles[i][j].setTileRole(selectedTileRole);
					} else {
						tiles[i][j].setTileRole(TileRoles.EMPTY);
					}
				}
			}
		}

		if (hasStockpile) {
			if (mousePos.intersects(stockpile.getRect())) {
				System.out.println("WoodCount: " + stockpile.getWood());
				System.out.println("RockCount: " + stockpile.getRock());
			}
		}
	}

	public void setMousePos(Rectangle rect) {
		this.mousePos = rect;
	}

	public int getSize() {
		return this.size;
	}

	public int getTileSize() {
		return this.tileSize;
	}

	public void addEntity(Item item, GameScreen gameScreen, UI ui) {
		if (!activeTile.getRect().intersects(lake.getRect())) {
			if (!activeTile.isOccupied()
					&& gameScreen.getInstance().hasCoins(item.getCost())) {
				System.out.println("Spawning " + item.getName() + "..");
				activeTile.setOccupied(true);
				gameScreen.getInstance().removeCoins(item.getCost());
				item.setPosition(new Vector2(activeTile.getPos().x + tileSize
						/ 2 - item.image.getWidth() / 2, activeTile.getPos().y
						+ tileSize / 2 - item.image.getHeight() / 2));
				item.setTile(activeTile);
				entities.add(item);

				if (item.getEntityName().matches("Fire")) {
					Fire.available = false;
					this.fire = (Fire) item;
					hasFire = true;
					LightSource lightSource = new LightSource(item.getPos());
					lightSources.add(lightSource);
				}
			}
		}
	}

	public int getPopulation() {
		return this.population;
	}

	public Person getSelectedPerson() {
		return this.selectedPerson;
	}

	public void deselectPerson() {
		this.selectedPerson = null;
	}

	public void setTileRole(int tileRole) {
		this.selectedTileRole = tileRole;
	}

	public Tile[][] getTiles() {
		return this.tiles;
	}

	public void showTileRole(boolean bool) {
		this.showRole = bool;
	}

	public boolean getIsReady() {
		return this.isReady;
	}

	public Stockpile getStockpile() {
		return this.stockpile;
	}

	public boolean getHasStockpile() {
		return this.hasStockpile;
	}

	public void dropStockpile(Vector2 pos) {
		this.stockpile = new Stockpile(new Vector2(pos.x, pos.y));
		entities.add(stockpile);
		hasStockpile = true;
	}

	public Cycle getCycle() {
		return this.cycle;
	}
}
