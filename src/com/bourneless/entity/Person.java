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

	private String title = "";
	private String name = "";

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

	private int fishFished;
	private int woodCut;
	private int rocksMined;
	private int treesFarmed;

	private boolean hasTitle = false;

	private int strength;

	private int role;

	private boolean live;
	private boolean hasDestination;
	private boolean atDestination;
	private boolean swimming;

	private String myThought = "Nothing.";
	private String godName;
	private String sex;

	private Vector2 destinationPos;
	private Random random = new Random();

	private double fireDistance;
	private double length;

	private Tree tree;
	private Rock rock;

	private boolean hasTree;
	private boolean hasRock;
	private boolean hasLake;

	private Tile destinationTile;
	private Tile currentTile;

	private boolean isWatering = false;
	private boolean isMining = false;
	private boolean isCutting = false;
	private boolean isFishing = false;

	private Inventory inventory;

	private boolean carrying = false;
	private boolean carryingStockpile;

	private boolean atFire;
	private boolean walking;
	private boolean needStamina;
	private boolean leveled;

	private Animation walkAnimation;
	private boolean gotNightDestination;

	private Lake lake;

	private Timer waterTimer = new Timer(3000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {

			hasTree = false;
			isWatering = false;
			treesFarmed++;
			if (treesFarmed % 10 == 0) {
				leveled = true;
				levelUp();
			}
			destinationPos = firePos;
			tree.water();
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
			rocksMined++;
			if (rocksMined % 10 == 0) {
				leveled = true;
				levelUp();
			}
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

	private Timer cutTimer = new Timer(3000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			hasTree = false;
			isCutting = false;
			destinationPos = firePos;
			woodCut++;
			if (woodCut % 10 == 0) {
				leveled = true;
				levelUp();
			}
			tree.cut();
			inventory.addWood(2);
			carrying = true;
			stamina -= 10;
			tree = null;
			((Timer) e.getSource()).stop();
		}
	});

	private Timer fishingTimer = new Timer(3000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			hasLake = false;
			isFishing = false;
			fishFished++;
			if (fishFished % 10 == 0) {
				leveled = true;
				levelUp();
			}
			destinationPos = firePos;
			inventory.addRawFish(1);
			carrying = true;
			stamina -= 10;
			((Timer) e.getSource()).stop();
		}
	});

	public Person(Item fire, Map map, God god, boolean carryingStockpile,
			Cycle cycle, Lake lake) {
		this.entityName = "Person";
		godName = god.getName();
		getSex();
		this.name = setName();
		this.lake = lake;

		walkAnimation = new Animation(Main.resourceLoader.walkingImages, 100);
		this.carryingStockpile = carryingStockpile;
		this.cycle = cycle;

		this.inventory = new Inventory();

		if (sex.matches("Man")) {
			head = random.nextInt(Main.resourceLoader.maleHeads.length);
			body = random.nextInt(Main.resourceLoader.maleBodies.length);
		} else if (sex.matches("Woman")) {
			head = random.nextInt(Main.resourceLoader.femaleHeads.length);
			body = random.nextInt(Main.resourceLoader.femaleBodies.length);
		}

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

		if (stamina <= 5) {
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
		} else if (role == Role.woodcutter) {
			cutTree();
		} else if (role == Role.fisherman) {
			if (hasLake) {
				fish();
			}
			if (!hasLake) {
				getLakeDestination();
			}

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
			if (sex.matches("Man")) {
				g.drawImage(Main.resourceLoader.maleHeads[head], pos.x
						+ renderX, pos.y + renderY, null);
			} else if (sex.matches("Woman")) {
				g.drawImage(Main.resourceLoader.femaleHeads[head], pos.x
						+ renderX, pos.y + renderY, null);
			}
			if (!walking) {
				if (sex.matches("Man")) {
					g.drawImage(Main.resourceLoader.maleBodies[body], pos.x
							+ renderX, pos.y + renderY
							+ Main.resourceLoader.maleHeads[head].getHeight(),
							null);
				} else if (sex.matches("Woman")) {
					g.drawImage(
							Main.resourceLoader.femaleBodies[body],
							pos.x + renderX,
							pos.y
									+ renderY
									+ Main.resourceLoader.femaleHeads[head]
											.getHeight(), null);
				}
			} else {
				if (sex.matches("Man")) {
					walkAnimation.paint(
							g,
							new Vector2(pos.x + renderX, pos.y
									+ renderY
									+ Main.resourceLoader.maleHeads[head]
											.getHeight()));
				} else if (sex.matches("Woman")) {
					walkAnimation.paint(
							g,
							new Vector2(pos.x + renderX, pos.y
									+ renderY
									+ Main.resourceLoader.femaleHeads[head]
											.getHeight()));
				}
			}
		} else {
			g.drawImage(Main.resourceLoader.swimming, pos.x + renderX, pos.y
					+ renderY + 5, null);
			if (sex.matches("Man")) {
				g.drawImage(Main.resourceLoader.maleHeads[head], pos.x
						+ renderX, pos.y + renderY, null);
			} else if (sex.matches("Woman")) {
				g.drawImage(Main.resourceLoader.femaleHeads[head], pos.x
						+ renderX, pos.y + renderY, null);
			}
		}

		if (isWatering) {
			g.drawImage(Main.resourceLoader.wateringcan, pos.x + renderX
					+ image.getWidth() - 6, pos.y + renderY + image.getHeight()
					/ 2 + 5, null);
		} else if (isMining) {
			g.drawImage(Main.resourceLoader.pickaxe,
					pos.x + renderX + image.getWidth() - 6, pos.y + renderY
							+ image.getHeight() / 2 + 5, null);
		} else if (isCutting) {
			g.drawImage(Main.resourceLoader.hatchet,
					pos.x + renderX + image.getWidth() - 6, pos.y + renderY
							+ image.getHeight() / 2 + 5, null);
		} else if (isFishing) {
			g.drawImage(Main.resourceLoader.rod,
					pos.x + renderX + image.getWidth() - 10, pos.y + renderY
							- image.getHeight() / 4 + 5, null);
		}
	}

	public void getSex() {
		int s = random.nextInt(2);
		if (s == 0) {
			sex = "Woman";
		} else {
			sex = "Man";
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
		String s;
		if (sex.matches("Man")) {
			int num = random.nextInt(Main.resourceLoader.maleNames.size());
			s = Main.resourceLoader.maleNames.get(num);
		} else {
			int num = random.nextInt(Main.resourceLoader.femaleNames.size());
			s = Main.resourceLoader.femaleNames.get(num);
		}
		return s;
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

	public void giveTree(ArrayList<Tree> tree) {
		Tree closestTree = tree.get(0);
		for (Tree t : tree) {
			double closestTreeLength = Math
					.sqrt((closestTree.getPos().x - pos.x)
							* (closestTree.getPos().x - pos.x)
							+ (closestTree.getPos().y - pos.y)
							* (closestTree.getPos().y - pos.y));
			double newLength = Math.sqrt((t.getPos().x - pos.x)
					* (t.getPos().x - pos.x) + (t.getPos().y - pos.y)
					* (t.getPos().y - pos.y));

			if (newLength < closestTreeLength && !t.hasPerson() && !t.getCut()
					&& !t.getIsSapling()) {
				closestTree = t;
			}
		}
		if (!hasTree && !needStamina && !carrying && !closestTree.getCut()
				&& !closestTree.getIsSapling() && !closestTree.hasPerson()) {
			closestTree.setHasPerson(true);
			hasDestination = true;
			atDestination = false;
			hasTree = true;
			this.destinationPos = closestTree.getPos();
			this.tree = closestTree;
		}

	}

	public void getLakeDestination() {
		if (!needStamina && !carrying && !hasLake && cycle.getDay()) {
			hasLake = true;
			hasDestination = true;
			atDestination = false;
			int getY = random.nextInt(Main.resourceLoader.lake.getHeight());
			int getX = 0;
			double rightLength = Math.sqrt((lake.getPos().x
					+ Main.resourceLoader.lake.getWidth() + 10 - pos.x)
					* (lake.getPos().x + Main.resourceLoader.lake.getWidth()
							+ 10 - pos.x)
					+ (lake.getPos().y + getY - pos.y)
					* (lake.getPos().y + getY - pos.y));
			double leftLength = ((lake.getPos().x - 10 - pos.x)
					* (lake.getPos().x - 10 - pos.x) + (lake.getPos().y + getY - pos.y)
					* (lake.getPos().y + getY - pos.y));
			double upLength;
			double downLength;
			if (rightLength < leftLength) {
				getX = lake.getPos().x + Main.resourceLoader.lake.getWidth()
						+ 20;
			} else {
				getX = lake.getPos().x - 20;
			}
			this.destinationPos = new Vector2(getX, lake.getPos().y + getY);
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
		for (Rock r : rock) {
			if (!r.getDead()) {
				double closestRockLength = Math
						.sqrt((closestRock.getPos().x - pos.x)
								* (closestRock.getPos().x - pos.x)
								+ (closestRock.getPos().y - pos.y)
								* (closestRock.getPos().y - pos.y));
				double newLength = Math.sqrt((r.getPos().x - pos.x)
						* (r.getPos().x - pos.x) + (r.getPos().y - pos.y)
						* (r.getPos().y - pos.y));

				if (newLength < closestRockLength && !r.getHasPerson()
						&& !r.getDead()) {
					closestRock = r;
				}
			}
		}
		if (!hasRock && !needStamina && !carrying && !closestRock.getDead()) {
			hasDestination = true;
			atDestination = false;
			hasRock = true;
			closestRock.setHasPerson(true);
			this.destinationPos = new Vector2(closestRock.getPos().x,
					closestRock.getPos().y);
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
			if (inventory.getRawFishCount() > 0) {
				System.out.println("Adding " + inventory.getRawFishCount()
						+ " Raw Fish to the Stockpile.");
				stockpile.addRawFish(inventory.getRawFishCount());
				inventory.removeRawFish();
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
		person.fishingTimer.stop();
		person.waterTimer.stop();
		person.miningTimer.stop();
		person.cutTimer.stop();
		person.fishingTimer.restart();
		person.cutTimer.restart();
		person.waterTimer.restart();
		person.miningTimer.restart();
		person.isFishing = false;
		person.hasLake = false;
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

		person.cutTimer = new Timer(3000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hasTree = false;
				isCutting = false;
				destinationPos = firePos;
				tree.cut();
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

		person.fishingTimer = new Timer(3000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hasLake = false;
				isFishing = false;
				destinationPos = firePos;
				inventory.addRawFish(1);
				carrying = true;
				stamina -= 10;
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
				if (!hasTree && !hasRock && !hasLake) {

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

	public void fish() {
		if (length <= 10 && length >= -10) {
			isFishing = true;
			fishingTimer.start();
		}
	}

	public void cutTree() {
		if (hasTree) {
			if (length <= 10 && length >= -10) {
				isCutting = true;
				cutTimer.start();
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

	public String getMySex() {
		return this.sex;
	}

	private void levelUp() {
		if (leveled) {
			if (fishFished > 0 && fishFished % 10 == 0) {
				fishing++;
				leveled = false;
			} else if (rocksMined > 0 && rocksMined % 10 == 0) {
				mining++;
				leveled = false;
			} else if (woodCut > 0 && woodCut % 10 == 0) {
				woodcutting++;
				leveled = false;
			} else if (treesFarmed > 0 && treesFarmed % 10 == 0) {
				farming++;
				leveled = false;
			}
		}
		if (!hasTitle) {
			if (fishFished >= 10) {
				this.title = "the Fisher" + sex.toLowerCase();
				hasTitle = true;
			} else if (rocksMined >= 10) {
				this.title = "the Miner";
				hasTitle = true;
			} else if (woodCut >= 10) {
				this.title = "the Woodcutter";
				hasTitle = true;
			} else if (treesFarmed >= 10) {
				this.title = "the Farmer";
				hasTitle = true;
			}
		}
	}
}
