package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.Timer;

import main.Main;
import math.Vector2;
import entity.Lake;
import entity.Tree;

public class Person extends Entity {

	private Item fire;
	private Vector2 firePos;

	private String title;
	private String name;

	private int health;
	private double stamina;
	private double speed;

	private String currentJob = "Nothing";

	private int hunger;

	private BufferedImage head;
	private BufferedImage body;

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

	private Tree tree;
	private boolean hasTree;
	private boolean gettingStamina;

	private Tile destinationTile;
	private Tile currentTile;
	
	private boolean watering = true;

	public Person(Item fire, Map map, God god) {
		godName = god.getName();
		this.entityName = "Person";
		this.name = setName();

		int randomHead = random.nextInt(Main.resourceLoader.heads.length);
		this.head = Main.resourceLoader.heads[randomHead];

		int randomBody = random.nextInt(Main.resourceLoader.bodies.length);
		this.body = Main.resourceLoader.bodies[randomBody];

		this.image = Main.resourceLoader.person;

		this.fire = fire;
		this.firePos = new Vector2(fire.getPos().x + fire.getImage().getWidth()
				/ 2, fire.getPos().y + fire.getImage().getHeight() / 2);

		this.speed = 2;

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

	public void update(int renderX, int renderY, Map map) {

		this.map = map;

		if (!hasDestination && stamina > 5) {
			if (!hasTree) {

				int getDestination = random.nextInt(500);
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
		}

		this.renderX = renderX;
		this.renderY = renderY;

		fireDistance = Math.sqrt((firePos.x - pos.x) * (firePos.x - pos.x)
				+ (firePos.y - pos.y) * (firePos.y - pos.y));

		if (fireDistance <= 10 && stamina < 100 && gettingStamina) {
			int addStamina = random.nextInt(10);
			if (addStamina == 1) {
				stamina += 1;
			}
		} else if (stamina > 0) {
			int loseStamina = random.nextInt(100);
			if (loseStamina == 1) {
				this.stamina--;
			}
		}

		if (gettingStamina) {
			if (stamina >= 100) {
				gettingStamina = false;
				atDestination = false;
				hasDestination = false;
			}
		}

		if (stamina <= 5) {
			hasTree = false;
			tree = null;
			destinationPos = firePos;
			gettingStamina = true;
			if (!hasDestination) {
				int distanceFireX = random.nextInt(100);
				distanceFireX -= 50;
				destinationPos.x += distanceFireX;

				int distanceFireY = random.nextInt(100);
				distanceFireY -= 50;
				destinationPos.y += distanceFireY;
				hasDestination = true;
			}
		}

		this.rect.x = pos.x + renderX;
		this.rect.y = pos.y + renderY;

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

		float directionX = destinationPos.x - pos.x;
		float directionY = destinationPos.y - pos.y;

		double length = Math.sqrt(directionX * directionX + directionY
				* directionY);

		directionX /= length;
		directionY /= length;

		if (!gettingStamina) {
			if (hasTree) {
				if (length <= 10 && length >= -10) {
					hasTree = false;
					tree.water();
					watering = true;
					Timer waterTimer = new Timer(10, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							watering = false;
							destinationPos = firePos;
							

							if (tree.getAge() >= 10) {
								tree.setSapling(false);
								tree.setHasPerson(false);
							}
							
							tree = null;
						}
					});
					
				}
			}
		}
		if (length <= 10 && length >= -10) {
			atDestination = true;
			if (gettingStamina == false) {
				hasDestination = false;
			}
		}

		if (length > 10 || length < -10) {
			pos.x += directionX * speed;
			pos.y += directionY * speed;
		}

		super.update(renderX, renderY, map);

	}

	public void paint(Graphics2D g) {
		if (!swimming) {
			g.drawImage(body, pos.x + renderX, pos.y + head.getHeight()
					+ renderY, null);
			g.drawImage(head, pos.x + renderX, pos.y + renderY, null);
		} else {
			g.drawImage(Main.resourceLoader.swimming, pos.x + renderX, pos.y
					+ renderY + 5, null);
			g.drawImage(head, pos.x + renderX, pos.y + renderY, null);
		}
		
		if(watering) {
			g.drawImage(Main.resourceLoader.wateringcan, pos.x + renderX + image.getWidth() , pos.y + renderY + image.getHeight() / 2, null);
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
		if (!hasTree && !gettingStamina) {
			hasDestination = true;
			hasTree = true;
			this.destinationPos = tree.getPos();
			this.tree = tree;
		}

	}

	public boolean hasTree() {
		return hasTree;
	}

	public boolean isGettingStamina() {
		return this.gettingStamina;
	}
}
