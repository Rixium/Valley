package com.bourneless.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import com.bourneless.entity.animation.Animation;
import com.bourneless.game.Entity;
import com.bourneless.game.God;
import com.bourneless.game.Item;
import com.bourneless.game.Map;
import com.bourneless.game.Role;
import com.bourneless.game.Tile;
import com.bourneless.main.Main;
import com.bourneless.math.Vector2;
import com.bourneless.mechanics.Cycle;
import com.bourneless.person.Inventory;

public class Person extends Entity implements Serializable {

	private Item fire;
	private Vector2 firePos;

	private String title;
	private String name;

	private int health;
	private double stamina;
	private double speed;

	private String currentJob = "Nothing";

	private int hunger;

	private int head;
	private int body;

	private int woodcutting;
	private int mining;
	private int farming;
	private int cooking;
	private int fishing;

	private int strength;

	private int role;

	private boolean live;
	private boolean hasDestination;
	private boolean atDestination;
	private boolean swimming;

	private String myThought = "Nothing.";
	private String godName;

	private Vector2 destinationPos;
	private Random random = new Random();

	private double fireDistance;
	private double length;

	private Tree tree;
	private Rock rock;

	private boolean hasTree;
	private boolean hasRock;

	private Tile destinationTile;
	private Tile currentTile;

	private boolean isWatering = false;
	private boolean isMining = false;

	private Inventory inventory;

	private boolean carrying = false;
	private boolean carryingStockpile;

	private boolean atFire;
	private boolean walking;
	private boolean needStamina;

	private Animation walkAnimation;
	private boolean gotNightDestination;

	private Timer waterTimer = new Timer(3000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {

			hasTree = false;
			isWatering = false;
			destinationPos = firePos;
			tree.water();
			inventory.addWood(2);
			carrying = true;
			stamina -= 10;
			tree = null;
			((Timer) e.getSource()).stop();
		}
	});

	private Timer miningTimer = new Timer(3000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			rock.mine();
			setHasRock(false);
			setMining(false);
			destinationPos = firePos;
			inventory.addRock(2);
			carrying = true;
			stamina -= 10;
			rock = null;
			((Timer) e.getSource()).stop();
		}
	});

	public Person(Item fire, Map map, God god, boolean carryingStockpile,
			Cycle cycle) {
		this.entityName = "Person";
		godName = god.getName();
		this.name = setName();

		walkAnimation = new Animation(Main.resourceLoader.walkingImages, 100);
		this.carryingStockpile = carryingStockpile;
		this.cycle = cycle;

		this.inventory = new Inventory();

		head = random.nextInt(Main.resourceLoader.heads.length);

		body = random.nextInt(Main.resourceLoader.bodies.length);

		this.image = Main.resourceLoader.person;

		this.fire = fire;
		this.firePos = new Vector2(fire.getPos().x + fire.getImage().getWidth()
				/ 2, fire.getPos().y + fire.getImage().getHeight() / 2);

		this.speed = 1;

		int randomSpawnSideX = random
				.nextInt(map.getSize() * map.getTileSize());
		int randomSpawnSideY = random.nextInt(2);

		switch (randomSpawnSideY) {
		case 0:
			randomSpawnSideY = -image.getHeight();
			break;
		case 1:
			randomSpawnSideY = map.getSize() * map.getTileSize()
					+ image.getHeight();
			break;
		default:
			randomSpawnSideY = -image.getHeight();
			break;
		}

		this.pos = new Vector2(randomSpawnSideX, randomSpawnSideY);

		this.rect = new Rectangle(pos.x, pos.y, image.getWidth(),
				image.getHeight());

		this.setHealth(100);
		this.setStamina(5);
		this.setHunger(0);

		this.setWoodcutting(1);
		this.setMining(1);
		this.setFarming(1);
		this.setCooking(1);
		this.setFishing(1);
		this.setStrength(1);

		this.setRole(Role.normal);
		this.setLive(true);
	}

	public void update(int renderX, int renderY, Map map, Cycle cycle) {

		this.renderX = renderX;
		this.renderY = renderY;
		this.rect.x = pos.x + renderX;
		this.rect.y = pos.y + renderY;

		this.map = map;

		dayWalk();

		fireDistance = Math.sqrt((firePos.x - pos.x) * (firePos.x - pos.x)
				+ (firePos.y - pos.y) * (firePos.y - pos.y));

		if (fireDistance <= 50) {
			addStamina();
		}

		if (carryingStockpile) {
			returnToFire();
			if (fireDistance <= 50) {
				map.dropStockpile(pos);
				carryingStockpile = false;
			}
		}
		
		if(stamina <= 5) {
			needStamina = true;
		}

		removeStamina();

		returnToFire();
		createThought();

		float directionX = destinationPos.x - pos.x;
		float directionY = destinationPos.y - pos.y;

		length = Math.sqrt(directionX * directionX + directionY * directionY);

		directionX /= length;
		directionY /= length;

		if (role == Role.farmer) {
			waterTree();
		} else if (role == Role.miner) {
			mineRock();
		}

		if (length <= 10 && length >= -10) {
			atDestination = true;
			hasDestination = false;
			if (carrying) {
				if (map.getHasStockpile()) {
					clearInventory();
				}
			}
		}

		if (length > 10 || length < -10) {
			if (!walking) {
				walkAnimation.start();
				walking = true;
			}
			int stroke = random.nextInt(5);
			if (stroke == 1 || !swimming) {
				if (pos.x > destinationPos.x) {
					pos.x -= speed;
				} else if (pos.x < destinationPos.x) {
					pos.x += speed;
				}
				if (pos.y > destinationPos.y) {
					pos.y -= speed;
				} else if (pos.y < destinationPos.y) {
					pos.y += speed;
				}
			}

		} else if (length <= 10 && length >= -10) {
			walkAnimation.stop();
			walking = false;
		}

		super.update(renderX, renderY, map, cycle);

	}

	public void paint(Graphics2D g) {
		if (!swimming) {
			g.drawImage(Main.resourceLoader.heads[head], pos.x + renderX, pos.y
					+ renderY, null);
			if (!walking) {
				g.drawImage(
						Main.resourceLoader.bodies[body],
						pos.x + renderX,
						pos.y + renderY
								+ Main.resourceLoader.heads[head].getHeight(),
						null);
			} else {
				walkAnimation.paint(g,
						new Vector2(pos.x + renderX, pos.y + renderY
								+ Main.resourceLoader.heads[head].getHeight()));
			}
		} else {
			g.drawImage(Main.resourceLoader.swimming, pos.x + renderX, pos.y
					+ renderY + 5, null);
			g.drawImage(Main.resourceLoader.heads[head], pos.x + renderX, pos.y
					+ renderY, null);
		}

		if (isWatering) {
			g.drawImage(Main.resourceLoader.wateringcan, pos.x + renderX
					+ image.getWidth() - 6, pos.y + renderY + image.getHeight()
					/ 2 + 5, null);
		} else if (isMining) {
			g.drawImage(Main.resourceLoader.pickaxe,
					pos.x + renderX + image.getWidth() - 6, pos.y + renderY
							+ image.getHeight() / 2 + 5, null);
		}
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getStamina() {
		return (int) stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getHunger() {
		return hunger;
	}

	public void setHunger(int hunger) {
		this.hunger = hunger;
	}

	public int getWoodcutting() {
		return woodcutting;
	}

	public void setWoodcutting(int woodcutting) {
		this.woodcutting = woodcutting;
	}

	public int getMining() {
		return mining;
	}

	public void setMining(int mining) {
		this.mining = mining;
	}

	public int getFarming() {
		return farming;
	}

	public void setFarming(int farming) {
		this.farming = farming;
	}

	public int getCooking() {
		return cooking;
	}

	public void setCooking(int cooking) {
		this.cooking = cooking;
	}

	public int getFishing() {
		return fishing;
	}

	public void setFishing(int fishing) {
		this.fishing = fishing;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public String setName() {
		int num = random.nextInt(23);
		String name;

		switch (num) {
		case 0:
			name = "Derek";
			break;
		case 1:
			name = "Barney";
			break;
		case 2:
			name = "Gabe";
			break;
		case 3:
			name = "Damien";
			break;
		case 4:
			name = "Richard";
			break;
		case 5:
			name = "Mathias";
			break;
		case 6:
			name = "Daniel";
			break;
		case 7:
			name = "John";
			break;
		case 8:
			name = "Alex";
			break;
		case 9:
			name = "Andrew";
			break;
		case 10:
			name = "Callum";
			break;
		case 11:
			name = "David";
			break;
		case 12:
			name = "George";
			break;
		case 13:
			name = "Rhys";
			break;
		case 14:
			name = "Robert";
			break;
		case 15:
			name = "Nathan";
			break;
		case 16:
			name = "Jake";
			break;
		case 18:
			name = "Dexter";
			break;
		case 19:
			name = "Ned";
			break;
		case 20:
			name = "Jaime";
			break;
		case 21:
			name = "Kieran";
			break;
		case 22:
			name = "Hugo";
			break;
		default:
			name = "Steven";
			break;
		}

		return name;
	}

	public String getThought() {
		return myThought;
	}

	public void setThought(String myThought) {
		this.myThought = myThought;
	}

	public String getCurrentJob() {
		return currentJob;
	}

	public void setCurrentJob(String currentJob) {
		this.currentJob = currentJob;
	}

	public void setSwimming(boolean bool) {
		this.swimming = bool;
	}

	public void giveTree(Tree tree) {
		if (!hasTree && !needStamina && !carrying) {
			hasDestination = true;
			atDestination = false;
			hasTree = true;
			this.destinationPos = tree.getPos();
			this.tree = tree;
		}

	}

	public boolean hasTree() {
		return hasTree;
	}

	public boolean isGettingStamina() {
		return this.needStamina;
	}

	public boolean isMining() {
		return isMining;
	}

	public void setMining(boolean isMining) {
		this.isMining = isMining;
	}

	public boolean getHasRock() {
		return hasRock;
	}

	public void setHasRock(boolean hasRock) {
		this.hasRock = hasRock;
	}

	public void giveRock(ArrayList<Rock> rock) {
		Rock closestRock = rock.get(0);
		for(Rock r : rock) {
			if(!r.getDead()) {
				double closestRockLength = Math.sqrt((closestRock.getPos().x - pos.x) * (closestRock.getPos().x - pos.x) + (closestRock.getPos().y - pos.y) * (closestRock.getPos().y - pos.y));
				double newLength = Math.sqrt((r.getPos().x - pos.x) * (r.getPos().x - pos.x) + (r.getPos().y - pos.y) * (r.getPos().y - pos.y));
				
				if(newLength < closestRockLength && !r.getHasPerson() && !r.getDead()) {
					closestRock = r;
				}
			}
		}
		if (!hasRock && !needStamina && !carrying && !closestRock.getDead()) {
			hasDestination = true;
			atDestination = false;
			hasRock = true;
			closestRock.setHasPerson(true);
			this.destinationPos = new Vector2(closestRock.getPos().x, closestRock.getPos().y);
			this.rock = closestRock;
		}
	}

	public void clearInventory() {
		if (map.getHasStockpile()) {
			Stockpile stockpile = map.getStockpile();
			if (inventory.getWoodCount() > 0) {
				System.out.println("Adding " + inventory.getWoodCount()
						+ " Wood to the Stockpile.");
				stockpile.addWood(inventory.getWoodCount());
				inventory.removeWood();
			}
			if (inventory.getRockCount() > 0) {
				System.out.println("Adding " + inventory.getRockCount()
						+ " Rock to the Stockpile.");
				stockpile.addRock(inventory.getRockCount());
				inventory.removeRock();
			}
			carrying = false;
			hasDestination = false;
		}
	}

	public boolean getHasStockpile() {
		return this.carryingStockpile;
	}

	public boolean isCarrying() {
		return this.carrying;
	}

	private Object readResolve() throws ObjectStreamException {
		Person person = this;
		person.waterTimer.stop();
		person.miningTimer.stop();
		person.waterTimer.restart();
		person.miningTimer.restart();
		person.isMining = false;
		person.isWatering = false;
		person.hasTree = false;
		person.hasRock = false;
		person.rock = null;
		person.tree = null;
		person.walking = false;
		person.atDestination = false;
		person.hasDestination = false;
		person.needStamina = false;
		person.role = Role.normal;
		person.walkAnimation = new Animation(Main.resourceLoader.walkingImages,
				100);
		person.waterTimer = new Timer(3000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				hasTree = false;
				isWatering = false;
				destinationPos = firePos;
				tree.water();
				inventory.addWood(2);
				carrying = true;
				stamina -= 10;
				tree = null;
				((Timer) e.getSource()).stop();
			}
		});

		person.miningTimer = new Timer(3000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rock.mine();
				setHasRock(false);
				setMining(false);
				destinationPos = firePos;
				inventory.addRock(2);
				carrying = true;
				stamina -= 10;
				rock = null;
				((Timer) e.getSource()).stop();
			}
		});

		return person;
	}

	public void createThought() {
		int getThought = random.nextInt(1000);
		if (getThought == 1) {
			int currentThought = random.nextInt(10);
			switch (currentThought) {
			case 0:
				myThought = "I think " + godName + " is great.";
				break;
			case 1:
				myThought = "I want to cut trees..";
				break;
			case 2:
				myThought = "I need to pee..";
				break;
			case 3:
				myThought = "I need to partake in some banter.";
				break;
			case 4:
				myThought = "Should I leave the closet?";
				break;
			case 5:
				myThought = "Thoughts are tedious work..";
				break;
			case 6:
				myThought = "I want to play Video Games.";
				break;
			case 7:
				myThought = "Perhaps we should build stuff.";
				break;
			case 8:
				myThought = "I'm thinking about becoming a " + godName + "ist";
				break;
			case 9:
				myThought = godName + "ism is really popular.";
				break;
			default:
				myThought = "Thoughts are tedious work..";
				break;
			}
		}
	}

	public void dayWalk() {
		if (this.cycle.getDay()) {
			gotNightDestination = false;
			if (!hasDestination && stamina > 5 && !carryingStockpile
					&& !carrying) {
				if (!hasTree && !hasRock) {

					int getDestination = random.nextInt(300);
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
										destinationPos.x = pos.x + destinationX;
										destinationPos.y = pos.y + destinationY;

										hasDestination = true;
									}
								}
							}
						}
					}
				}
			} else if (carrying && map.getHasStockpile()) {
				destinationPos = new Vector2(map.getStockpile().getPos().x, map
						.getStockpile().getPos().y);
				hasDestination = true;
				atDestination = false;
			}
		}
	}

	public void addStamina() {
		if (stamina < 100) {
			stamina++;
		} else {
			needStamina = false;
		}
	}

	public void removeStamina() {
		if (fireDistance > 50) {
			if (stamina > 0) {
				int loseStamina = random.nextInt(100);
				if (loseStamina == 1) {
					if (carrying && stamina >= 2) {
						this.stamina -= 2;
					} else if (stamina >= 1) {
						this.stamina--;
					}
				}
			}
		}
	}

	public void returnToFire() {
		if (needStamina || !cycle.getDay() && !gotNightDestination) {
			this.waterTimer.stop();
			this.miningTimer.stop();
			this.isMining = false;
			this.isWatering = false;
			hasTree = false;
			hasRock = false;
			rock = null;
			tree = null;
			atDestination = false;
			hasDestination = false;
			if (!hasDestination) {
				destinationPos = new Vector2(firePos.x, firePos.y);
				if (!carryingStockpile) {
					int distanceFireX = random.nextInt(100);
					distanceFireX -= 50;

					int distanceFireY = random.nextInt(100);
					distanceFireY -= 50;
					destinationPos.x += distanceFireX;
					destinationPos.y += distanceFireY;
				} else {
					destinationPos.x += 40;
				}
				hasDestination = true;
				gotNightDestination = true;
			}
		}
	}

	public void mineRock() {
		if (hasRock) {
			if (length <= 10 && length >= -10) {
				isMining = true;
				miningTimer.start();
			}
		}
	}

	public void waterTree() {
		if (hasTree) {
			if (length <= 10 && length >= -10) {
				isWatering = true;

				waterTimer.start();
			}
		}
	}
}
