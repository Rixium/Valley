package game;

import item.Fire;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Main;
import math.Vector2;
import object.Button;

public class UI {

	private Vector2 closedVector;
	private Vector2 openVector;
	private Rectangle openRect;
	private Image image;
	private BufferedImage personStatScreenImage;

	private boolean uiOpen;
	private boolean showingPersonStats;

	private Button[] items = new Button[1];
	private Button[] stats = new Button[5];
	private Button[] tileRoles = new Button[4];

	private Person selectedPerson;

	private BufferedImage[] statImages = new BufferedImage[7];

	public UI() {

		image = Main.resourceLoader.uiBackground;
		personStatScreenImage = Main.resourceLoader.personStatScreen;

		closedVector = new Vector2(Main.GAME_WIDTH - 30, 0);
		openVector = new Vector2(Main.GAME_WIDTH - image.getWidth(null), 0);
		openRect = new Rectangle(closedVector.x, closedVector.y, 50,
				Main.GAME_HEIGHT);

		items[0] = new Button(new Fire(), new Vector2(openVector.x + 25,
				openVector.y + 10));

		statImages[0] = Main.resourceLoader.cooking;
		statImages[1] = Main.resourceLoader.farming;
		statImages[2] = Main.resourceLoader.fishing;
		statImages[3] = Main.resourceLoader.mining;
		statImages[4] = Main.resourceLoader.woodcutting;

		stats[0] = new Button(statImages[0], new Vector2(100, Main.GAME_HEIGHT
				/ 2 - personStatScreenImage.getHeight() + 110));
		stats[1] = new Button(statImages[1], new Vector2(100, Main.GAME_HEIGHT
				/ 2 - personStatScreenImage.getHeight() + 160));
		stats[2] = new Button(statImages[2], new Vector2(100, Main.GAME_HEIGHT
				/ 2 - personStatScreenImage.getHeight() + 210));
		stats[3] = new Button(statImages[3], new Vector2(100, Main.GAME_HEIGHT
				/ 2 - personStatScreenImage.getHeight() + 260));
		stats[4] = new Button(statImages[4], new Vector2(100, Main.GAME_HEIGHT
				/ 2 - personStatScreenImage.getHeight() + 310));

		tileRoles[0] = new Button(Main.resourceLoader.emptyButton, new Vector2(
				20, 20));
		tileRoles[1] = new Button(Main.resourceLoader.pathButton, new Vector2(
				20 + Main.resourceLoader.emptyButton.getWidth() + 20, 20));
		tileRoles[2] = new Button(Main.resourceLoader.farmButton, new Vector2(
				20 + Main.resourceLoader.emptyButton.getWidth() * 2 + 40, 20));
		;
		tileRoles[3] = new Button(Main.resourceLoader.buildButton, new Vector2(
				20 + Main.resourceLoader.emptyButton.getWidth() * 3 + 60, 20));
		;

	}

	public void update(Map map) {

	}

	public void paint(Graphics2D g) {
		if (uiOpen) {
			g.drawImage(image, openVector.x, openVector.y,
					image.getWidth(null), Main.GAME_HEIGHT, null);
			openRect.x = openVector.x;
			openRect.y = openVector.y;

			for (int i = 0; i < items.length; i++) {
				items[i].paint(g);
			}

		} else if (!uiOpen) {
			g.drawImage(image, closedVector.x, closedVector.y,
					image.getWidth(null), Main.GAME_HEIGHT, null);
			openRect.x = closedVector.x;
			openRect.y = closedVector.y;
		}

		if (showingPersonStats) {
			g.drawImage(personStatScreenImage, 30, Main.GAME_HEIGHT / 2
					- personStatScreenImage.getHeight(),
					personStatScreenImage.getWidth() * 2,
					personStatScreenImage.getHeight() * 2, null);
			g.setColor(Color.WHITE);

			g.drawString("Name: " + selectedPerson.getName(), 100,
					Main.GAME_HEIGHT / 2 - personStatScreenImage.getHeight()
							+ 70);
			g.drawString("Health: " + selectedPerson.getHealth(), 100,
					Main.GAME_HEIGHT / 2 - personStatScreenImage.getHeight()
							+ 85);
			g.drawString("Stamina: " + selectedPerson.getStamina(), 100,
					Main.GAME_HEIGHT / 2 - personStatScreenImage.getHeight()
							+ 100);

			stats[0].paint(g);
			g.drawString("" + selectedPerson.getCooking(), 150,
					Main.GAME_HEIGHT / 2 - personStatScreenImage.getHeight()
							+ 130);

			stats[1].paint(g);
			g.drawString("" + selectedPerson.getFarming(), 150,
					Main.GAME_HEIGHT / 2 - personStatScreenImage.getHeight()
							+ 180);

			stats[2].paint(g);
			g.drawString("" + selectedPerson.getFishing(), 150,
					Main.GAME_HEIGHT / 2 - personStatScreenImage.getHeight()
							+ 230);

			stats[3].paint(g);
			g.drawString("" + selectedPerson.getMining(), 150, Main.GAME_HEIGHT
					/ 2 - personStatScreenImage.getHeight() + 280);

			stats[4].paint(g);
			g.drawString("" + selectedPerson.getWoodcutting(), 150,
					Main.GAME_HEIGHT / 2 - personStatScreenImage.getHeight()
							+ 330);

			g.drawString("Thinking: " + selectedPerson.getThought(), 100,
					Main.GAME_HEIGHT / 2 - personStatScreenImage.getHeight()
							+ 370);
			g.drawString("Current Job: " + selectedPerson.getCurrentJob(), 100,
					Main.GAME_HEIGHT / 2 - personStatScreenImage.getHeight()
							+ 385);

			g.setColor(Color.red);
			if (selectedPerson.getCurrentJob() == "Cooking") {
				g.drawRect(stats[0].getRect().x, stats[0].getRect().y, 39, 39);
			} else if (selectedPerson.getCurrentJob() == "Farming") {
				g.drawRect(stats[1].getRect().x, stats[1].getRect().y, 39, 39);
			} else if (selectedPerson.getCurrentJob() == "Fishing") {
				g.drawRect(stats[2].getRect().x, stats[2].getRect().y, 39, 39);
			} else if (selectedPerson.getCurrentJob() == "Mining") {
				g.drawRect(stats[3].getRect().x, stats[3].getRect().y, 39, 39);
			} else if (selectedPerson.getCurrentJob() == "Woodcutting") {
				g.drawRect(stats[4].getRect().x, stats[4].getRect().y, 39, 39);
			}
		}

		for (int i = 0; i < tileRoles.length; i++) {
			tileRoles[i].paint(g);
		}
	}

	public Rectangle getRect() {
		return this.openRect;
	}

	public void openUI(boolean bool) {
		this.uiOpen = bool;
	}

	public boolean getOpen() {
		return this.uiOpen;
	}

	public Button[] getItems() {
		return this.items;
	}

	public Button[] getStats() {
		return this.stats;
	}

	public void showPersonStats(boolean bool, Person person) {
		this.showingPersonStats = bool;
		if (person != null) {
			this.selectedPerson = person;
		} else {
			this.selectedPerson = null;
		}
	}

	public Button[] getTileRoles() {
		return this.tileRoles;
	}
}
